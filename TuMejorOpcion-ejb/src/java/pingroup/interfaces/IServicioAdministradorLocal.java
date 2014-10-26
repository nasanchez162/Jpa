/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pingroup.interfaces;

import java.util.List;
import pingroup.vos.Tienda;

/**
 *Interfaz que declara los servicios del administardor
 */
public interface IServicioAdministradorLocal {
    
   /**
    * Metodo que retorna las tiendas que estan registradas en el sistema
    * @return Las tiendas del sistema
    */
    public List<Tienda> darTiendas();
    
    /**
     * Metodo que agrega una nueva tienda al sistema
     * @param t, la tienda a agegar
     */
    public void agregarTienda(Tienda t);
    
    /**
     * Metodo que permite eliminar una tienda determinada
     * @param el index en donde se encuentra la tienda
     */
    public void eliminarTienda(int pos);
    
    /**
     * Metodo que permite eliminar una tienda determinada
     * @param t, La tienda que se desea eliminar 
     */
    public void eliminarTienda(Tienda t);
    
    public void eliminarTienda (String nombre);
}
