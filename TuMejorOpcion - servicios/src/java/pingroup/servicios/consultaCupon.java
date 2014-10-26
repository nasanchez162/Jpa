/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingroup.servicios;

import static com.sun.jersey.api.uri.UriComponent.Type.PATH;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import pingroup.interfaces.IServicioPersistenciaLocal;
import pingroup.vos.Cupon;
import pingroup.vos.Tienda;

/**
 * REST Web Service
 * 
* @author estudiante
 */
@Path("pingroup.servicios")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class consultaCupon {

    @Context
    private UriInfo context;
    
    @EJB
    private IServicioPersistenciaLocal persistenciaBean;

    /**
     * Creates a new instance of cuponServicio
     */
    public consultaCupon() {
    }

    /**
     * Retorna si un id de cupon es valido y retorna la informacion del cupon
     *
     * @param id El id que se quiere verifcar
     * @return Un JSON con el objeto buscado o null si no existe
     */
    @GET
    @Path("validarCupon/{id}")
    public Cupon validarCupon(@PathParam("id") String id) {
        Cupon ac = persistenciaBean.darCuponPorId(id);
        if(ac!=null)
        {
            Tienda t = ac.getTienda();
         //   t.setListaCupones(new ArrayList<Cupon> ());
            ac.setTienda(t);
        }
        return (persistenciaBean.darCuponPorId(id) != null) ? ac : new Cupon(true, -1, -1, null, "CUPON INVALIDO", "");
    }

    @GET
    @Path("redimirCupon/{id}/{cant}")
    public void redimirCupon(@PathParam("id") String id, @PathParam("cant") String cant) {
        if (persistenciaBean.darCuponPorId(id) != null) {
            persistenciaBean.descontarDineroCupon(Double.parseDouble(cant), persistenciaBean.darCuponPorId(id));
        }
    }
}
