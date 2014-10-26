/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pingroup.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import pingroup.interfaces.IServicioMenuUsuarioLocal;
import pingroup.vos.Cupon;
import pingroup.vos.Tienda;
import pingroup.vos.Usuario;

/**
 * @author Banana
 */
public class MenuUsuarioBean implements Serializable{
    
    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------
    
    /**
     * Servicio del menu de usuario
     */
    @EJB
    private IServicioMenuUsuarioLocal servicio;
    
    /**
     * Servicio de Login 
     */
    
    /**
     * Usuario loggeado en la aplicacion
     **/
    private Usuario usuario;
   
    /**
     * Mapa de amigos y sus ids
     */
    
    private String amigoId;
    
    /**
     * Lista de amigos del usuario
     */
    private List <Usuario> amigos;
    
    
    /**
     * Lista de bonos del usuario
     */
    private List <Cupon> cupones;
    
    /**
     * Cupon que se va a crear
     */
    private int saldo;
    
    /**
     * La tienda que se va a asociar al cupon
     */
    private String tienda;
    
    /**
     * El amigo al que se va a asociar el cupon
     */
    private Usuario amigo;
    
    private String[] admins = {"10204751290763514", "10152362478413339" };
    
    private boolean esAdmin;

    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------

    /**
     * Constructor sin argumentos de la clase
     */
    public MenuUsuarioBean()
    {
        saldo = 0;
        tienda = "";
        amigo = new Usuario();
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("loginBean"))
        {
            LoginBean sessionSecurity = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
            usuario = sessionSecurity.getUsuario();
            amigos = usuario.getAmigos();
            System.out.println("Los amigos son:" + amigos.size());
            esAdmin = isAdmin();
        }
    }

    //-----------------------------------------------------------
    // Getters y setters
    //-----------------------------------------------------------

    /**
     * Devuelve el efecto de la tabla del carrito
     * @return efectoTablaCarrito Efecto
     */
    public String getNombreUsuario()
    {
        if (usuario!= null)
            return usuario.getUsername();
        return "Nombre no Disponible";
    }
    
    /**
     * Devuelve la lista de amigos 
     * @return la lista de amigos del usuario
     */
    public List<Usuario> getAmigos()
    {
        System.out.println("Los amigos son:" + amigos.size());
        return amigos;
    }
    
    /**
     * Devuelve la lista de cupones que tiene el usuario
     * @return la lista de Cupones del usuario
     */
    
    public List<Cupon> getCupones()
    {
        return servicio.getCuponesUsuario(usuario);
    }
    
    /**
     * Retorna el saldo que tiene el bean
     * @return saldo, el saldo del Bean
     */
    public int getSaldo()
    {
        return saldo;
    }
    
    /**
     * Setea el saldo
     * @param saldo, el nuevo saldo
     */
    public void setSaldo( int saldo)
    {
        this.saldo = saldo;
    }
    
     /**
     * Retorna la tienda que tiene el bean
     * @return tienda, la tienda del Bean
     */
    public String getTienda()
    {
        return tienda;
    }
    
    /**
     * Setea la tienda
     * @param tienda, la nueva tienda
     */
    public void setTienda( String tienda)
    {
        this.tienda = tienda;
    }
    
     /**
     * Retorna el amigo que tiene el bean
     * @return amigo, el amigo del Bean
     */
    public Usuario getAmigo()
    {
        return amigo;
    }
    
    /**
     * Setea el amigo
     * @param amigo, el nuevo amigo 
     */
    public void setAmigo( Usuario amigo)
    {
        this.amigo = amigo;
    }
    
    //-----------------------------------------------------------
    // Metodos
    //-----------------------------------------------------------
    
    public void sayHi()
    {
        System.out.println("HI!!");
    }
    /**
     * Crea un cupon para el usuario
     */
    public void crearCupon()
    {
        Tienda tt = servicio.darTiendaPorNombre(tienda);
        Cupon cup = servicio.crearCupon(usuario, tt, saldo);
        amigo = servicio.darAmigo(amigoId, usuario);
        servicio.enviarCupon(cup, amigo);
        tienda = "";
        amigo = new Usuario();
        saldo = 0;
    }
    
    /**
     * Retorna las tiendas en el sistema
     * @return tiendas del sistema
     */
    public List<String> darTiendas()
    {
        ArrayList<String> nomTiendas = new ArrayList<String>();
        List<Tienda> tiendas = servicio.darTiendas();
        for (Tienda tienda : tiendas) {
            nomTiendas.add(tienda.getNombre());
        }
        return nomTiendas;
    }
    
    /**
     * Cuando se selecciona una tienda de un amigo
     */
    public void cambiarTienda( String idUsuario ) {
        Usuario paraAmigo = servicio.darAmigo(idUsuario, usuario);
        this.amigo = paraAmigo;
        this.amigoId = amigo.getIdFacebook();
    }
    
    public String getAmigoId()
    {
        return amigoId;
    }
    
    public void setAmigoId(String data)
    {
        this.amigoId = data;
    }
    
    public boolean isAdmin()
    {
        for (int i = 0; i < admins.length; i++) {
            if (usuario.getIdFacebook().equals(admins[i]))
                return true;
        }
        return false;
    }
    
    public boolean getEsAdmin()
    {
        return esAdmin;
    }
    
    public void setEsAdmin ( boolean esAdmin )
    {
        this.esAdmin = esAdmin;
    }
}