/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pingroup.servicios;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import pingroup.interfaces.IServicioAdministradorLocal;
import pingroup.interfaces.IServicioPersistenciaLocal;
import pingroup.vos.Tienda;

/**
 *
 * @author Banana
 */
@Stateless
public class ServicioAdministradorMock implements IServicioAdministradorLocal{

    @EJB
    private IServicioPersistenciaLocal servicio;
    
    @Override
    public List<Tienda> darTiendas() {
        return servicio.getTiendas();
    }

    @Override
    public void agregarTienda(Tienda t) {
        servicio.aniadirTienda(t);
    }

    @Override
    public void eliminarTienda(int pos) {
        eliminarTienda(servicio.getTiendas().get(pos));
    }

    @Override
    public void eliminarTienda(Tienda t) {
        servicio.eliminarTienda(t);
    }

    @Override
    public void eliminarTienda(String nombre) {
        this.eliminarTienda(servicio.buscarTiendaPorNombre(nombre));
    }
    
}
