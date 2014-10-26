/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pingroup.beans;

import com.icesoft.faces.component.ext.HtmlInputText;
import java.io.Serializable;
import javax.ejb.EJB;
import pingroup.excepciones.AutenticacionException;
import pingroup.interfaces.IServicioSeguridadLocal;
import pingroup.vos.Usuario;

/**
 *
 * @author Banana
 */
public class LoginBean implements Serializable{
    
    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------

    /**
     * El token del index que se acutaliza cuando el usuario hace login
     */
    private HtmlInputText usrToken;
    
    /**
     * El usuario que se encuentra actualmente logeado
     */
    private Usuario usuario;
    
    /**
     * El servicio de seguridad que permite hacer el Login
     */
    @EJB
    private IServicioSeguridadLocal servicio;
    
    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------

    /**
     * Constructor sin argumentos de la clase
     */
    public LoginBean()
    {
        usrToken = new HtmlInputText();
    }

    //-----------------------------------------------------------
    // Getters y setters
    //-----------------------------------------------------------
   
    /**
     * Retorna el input de user Token
     * @return usrToken, el elemento de html que se actualiza con el token del usuario
     */
    public HtmlInputText getUsrToken(){
        return usrToken;
    }

     /**
     * Setea el input de user Token
     * @param usr, el nuevo elemento HTML para representar el parametro.
     */
    public void setUsrToken(HtmlInputText usr)
    {
        this.usrToken = usr;
    }
    
    /**
     * Retorna la sesion actual del usuario
     * @return usuario. El usuario que hizo login
     */
    public Usuario getUsuario()
    {
        return usuario;
    }
    
    /**
     * Setea el usuario 
     * @param usr . El nuevo usuario
     */
    public void setUsuario( Usuario usr)
    {
        this.usuario = usr;
    }
    
    /**
     * Metodo que se encarga de hacer login en la aplicacion
     * @return String error, para una ejecion equivocada, 
     * login para la creacion exitosa de un usuario y fail para un error de login
     */
    public String hacerLogin ()
    {
        String token = (String) usrToken.getValue();
        //System.out.println(token);
        if (usrToken.getValue() == "NoHayId")
        {
            return "error";
        }
        else 
        {
            try{
                usuario = servicio.ingresar(token);
                return "login"; 
            }
            catch(AutenticacionException error)
            {
                //TODO: Crear vista para un error en el login.
                return "fail";
            }
            
        }
    }
    
}
