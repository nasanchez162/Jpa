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
 *
 * @author Banana
 */
public interface IServicioMenuUsuarioLocal {
    
    /**
     * Método que se encarga de crear un cupon nuevo para un usuario
     * @param usr, el usuario al que se le va a crear el cupon
     * @param tienda la tienda para la cual se va a crear el cupon
     * @param saldo el saldo que se le va a poner al cupon
     * @returns el cupon creado
     */
    public Cupon crearCupon(Usuario usr, Tienda tienda, int saldo);
    
    /**
     * Método que retorna las tiendas que estan en el sistema
     * @return tiendas, las tiendas del sistema
     */
    public List<Tienda> darTiendas();
    
    /**
     * Método que busca un amigo dado su ID
     * @param idFB id de facebook del amigo 
     * @param usr el usuario del cual se quiere el amigo.
     * @return amigo, el amigo Buscado
     */
    public Usuario darAmigo( String idFB, Usuario usr);
    
    /**
     * Retorna una tienda segun el nombre de esta
     * @param nombreT
     * @return Tienda la tienda buscada
     */
    public Tienda darTiendaPorNombre (String nombreT); 
    
    /**
     * Envia un cupon a su destinatario
     * @param cup, el cupon que se va a enviar
     */
    public void enviarCupon (Cupon cup, Usuario usr);

    public List<Cupon> getCuponesUsuario(Usuario usuario);

}
