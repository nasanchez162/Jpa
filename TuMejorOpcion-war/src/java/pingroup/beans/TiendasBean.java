/* * To change this license header, choose License Headers in Project Properties. * To change this template file, choose Tools | Templates * and open the template in the editor. */ package pingroup.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import pingroup.interfaces.IServicioAdministradorLocal;
import pingroup.vos.Tienda;

/**
 * * * @author Banana
 */
public class TiendasBean implements Serializable {

    /**
     * * La tienda
     */
    private Tienda tienda;
    /**
     * * La lista de las tiendas
     */
    private List<Tienda> tiendas;
    /**
     * * El servicio de la tienda
     */
    @EJB
    IServicioAdministradorLocal servicio;

    public TiendasBean() {
        tienda = new Tienda();
    }

    /**
     * * Getters y Setters
     */
    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public void agregarTienda() {
        servicio.agregarTienda(tienda);
        tienda = new Tienda();
    }

    public void limpiar() {
        tienda = new Tienda();
    }

    public List<Tienda> getTiendas() {
        if (tiendas == null) {
            tiendas = servicio.darTiendas();
        }
        return tiendas;
    }

    public void setTiendas(List<Tienda> tiendas) {
        this.tiendas = tiendas;
    }

    public void eliminarTienda(int pos) {
        servicio.eliminarTienda(pos);
    }
}
