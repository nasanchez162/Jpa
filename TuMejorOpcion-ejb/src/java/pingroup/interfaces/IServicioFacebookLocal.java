/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pingroup.interfaces;

import java.util.ArrayList;
import java.util.List;
import pingroup.vos.Tienda;
import pingroup.vos.Usuario;


/**
 * Interfaz que declara los servicios de persistencia
 */
public interface IServicioFacebookLocal {

    /**
     * Retorna la tiendas que estan registradas en el sistema y el usuario le ha dado like
     * @param token 
     * @return Lista con las tiendas
     */
    public List<Tienda> getTiendasEnLikes(String token, Usuario usuario)throws Exception;

    /**
     * Conecta todos los amigos que usan la aplicación
     * @param token El token de facebook
     * @param usuarioActual El usuario que estamos conectando
     */
     public List<Usuario> getAmigosQueUsanApp(String token, Usuario usuarioActual);
    
    /**
     * Retorna un POJO Usuario con toda la informacion necesaria jalada de Facebook
     * @param token
     */
    public Usuario getUsuario(String token);
    
    /**
     * Retorna solo el username con el token
     * @param token
     * @return Un String con el username
     */
    public String getUserId(String token);
    
    public List<Tienda> getTiendasEnWall(String token, Usuario usu);
}