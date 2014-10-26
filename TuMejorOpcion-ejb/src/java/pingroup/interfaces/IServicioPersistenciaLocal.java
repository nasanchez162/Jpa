/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pingroup.interfaces;

import java.util.List;
import pingroup.vos.Cupon;
import pingroup.vos.Tienda;
import pingroup.vos.Usuario;

/**
 * Interfaz que declara los servicios de persistencia
 */
public interface IServicioPersistenciaLocal {

   /**
    * Busca un usuario por su token
    * @param token El token por el que se quiere buscar
    * @return El usuario o null si no está registrado el usuario
    */
    public Usuario buscarUsuarioPorToken(String token); 

    /**
     * Aniade un usuario al sistema, persistiéndolo
     * @param usuario El usuario que se quiere aniadir
     */
    public void aniadirUsuario(Usuario usuario);
    
    /**
     * Busca un usuario dado su nombre
     * @param nombre Nombre del usuario
     * @return El usuario o null si no esta en el sistema
     */
    public Usuario buscarUsuarioPorNombre(String nombre);
    
    /**
     * Busca un usuario dado su id de facebook
     * @param idFacebook Id de facebook del usuario
     * @return El usuario o null si no esta en el sistema
     */
    public Usuario buscarUsuarioPorIdFacebook (String idFacebook);
    
    /**
     * Vuelve a dos usuarios amigos
     * @param usuario1 Primer usuario para volver amigo
     * @param usuario2 Segundo usuario para volver amigo
     */
    public void volverseAmigos(Usuario usuario1, Usuario usuario2);
    
    /**
     * Aniade una tienda a un usuario
     * @param usario El usuario al que nos referimos
     * @param tienda La tienda que se quiere anidair
     */
    public void aniadirTiendaAUsuario(Usuario usario, Tienda tienda);

    /**
     * Aniade un cupon al sistema, dado que el cupon sabe de que tienda es
     * @param cupon El cupon nuevo con la tienda a la cual pertenece
     * @param usuario El usuario que esta comprando el cupon
     */
    public void aniadirCupon (Cupon cupon, Usuario usuario) ;
    
    /**
     * Busca un cupon dado su id, retorna null si no existe el cupon
     * @param idCupon El id del cupon que se esta buscando
     */
    public Cupon darCuponPorId(String idCupon);
    
    /**
     * Descuenta dinero del cupón y retorna true si fue posible descontar ese dinero
     * @param dineroADescontar La cantidad de dinero a descontar
     * @param cupon El cupon del que se quiere descontar
     * @return  True si es una transacción valida y false de lo contrario.
     */
    public boolean descontarDineroCupon (double dineroADescontar, Cupon cupon);
    /**
     * Da la lista de usuarios en el sistema
     * @return List con los usuarios del sistema
     */
    public List<Usuario> getUsuarios();
    
    /**
     * Da la lista de tiendas en el sistema
     * @return List con las tiendas del sistema
     */
    public List<Tienda> getTiendas();
    
    /**
     * Da la lista de cupones en el sistema
     * @return List con los cupones del sistema 
     */
    public List<Cupon> getCupones();
    
    /**
     * Aniade una nuvea tienda en el sistema
     * @param tienda La tienda nueva que se quiere aniadir en el sistema
     */
    public void aniadirTienda (Tienda tienda);
    
    /**
     * Busca una tienda por su Id de Facebook
     * @param idTienda El Id de la tienda
     * @return La tienda buscada o null si no está en el sistema
     */
    public Tienda buscarTiendaPorId (String idTienda);
    
    /**
     * Busca una tienda por su nombre
     * @param nombre Nombre de la tienda buscada
     * @return La tienda buscada o null si no está en el sistema
     */
    public Tienda buscarTiendaPorNombre (String nombre);
    
    public Usuario darUsuarioPrueba();
    
    /**
     * Crea una imagen a partir de el cupon y retorna la url de la imagen
     * @param cupi
     * @return La url de la imagen.
     */
    public String darRutaImagenCupon (Cupon cupi);
    
    public void eliminarTienda (Tienda tienda);

    public List<Cupon> darCuponesUsuario(Usuario usuario);
    
    /**
     * Actualiza el estado del nuevo usuario, si no existe en la base de datos, lo crea
     * @param usuario 
     */
    public void actualizarUsuario(Usuario usuario);
    
    /**
     * Actualiza el estado de la nueva tienda, si no existe en la base de datos, la crea
     * @param tienda 
     */
    public void actualizarTienda(Tienda tienda);
}
