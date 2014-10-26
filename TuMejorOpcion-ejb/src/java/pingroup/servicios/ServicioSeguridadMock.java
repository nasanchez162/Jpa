/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingroup.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import pingroup.excepciones.AutenticacionException;
import pingroup.interfaces.IServicioFacebookLocal;
import pingroup.interfaces.IServicioPersistenciaLocal;
import pingroup.interfaces.IServicioSeguridadLocal;
import pingroup.vos.Tienda;
import pingroup.vos.Usuario;

/**
 * Un mock del servicio de seguridad
 */
@Stateless
@Local
public class ServicioSeguridadMock implements IServicioSeguridadLocal {

    //Configuracion de mongo
    private static Properties prop = new Properties();
    private static InputStream input = null;

    static {
        try {
            input = ServicioSeguridadMock.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Interface con referencia al servicio de persistencia en el sistema
     */
    //@EJB
    private IServicioPersistenciaLocal persistencia;
    @EJB
    private IServicioFacebookLocal facebook;

    /**
     * Constructor sin argumentos de la clase
     */
    public ServicioSeguridadMock() {

        //Modificación de Mongo
        try {
            persistencia = (IServicioPersistenciaLocal) new InitialContext().lookup(prop.getProperty("jndi_persistencia"));
        } catch (NamingException ex) {

            Logger.getLogger(ServicioSeguridadMock.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Método que se encarga de autenticar un usario en el sistema
     *
     * @param token El token del usuario
     * @return El objeto del usuario
     * @throws AutenticacionException Retorna excepcion si hubo algún problema
     */
    @Override
    public Usuario ingresar(String token) throws AutenticacionException {
        Usuario r = persistencia.buscarUsuarioPorIdFacebook(facebook.getUserId(token));
        if (r != null) {
            try {
                List<Tienda> l = facebook.getTiendasEnLikes(token, r);

                l.addAll(facebook.getTiendasEnWall(token, r));
                for (Tienda t : l) {
                    persistencia.aniadirTiendaAUsuario(r, t);
                }
            } catch (Exception ex) {
                throw new AutenticacionException(ex.getMessage());
            }
            return r;
        } else {
            try {
                Usuario usr = facebook.getUsuario(token);;
                persistencia.aniadirUsuario(usr);
                r = persistencia.buscarUsuarioPorToken(token);
                List<Usuario> us = facebook.getAmigosQueUsanApp(token, r);
               
                for (Usuario usu : us) {
                    persistencia.volverseAmigos(usu, r);
                }
                List<Tienda> l = facebook.getTiendasEnLikes(token, r);

                l.addAll(facebook.getTiendasEnWall(token, r));
                for (Tienda t : l) {
                    persistencia.aniadirTiendaAUsuario(r, t);
                }
                return persistencia.buscarUsuarioPorToken(token);
            } catch (Exception ex) {
                throw new AutenticacionException(ex.getMessage());
            }

        }
    }

}
