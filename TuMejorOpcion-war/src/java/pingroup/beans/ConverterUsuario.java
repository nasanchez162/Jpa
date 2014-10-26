/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingroup.beans;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import pingroup.interfaces.IServicioPersistenciaLocal;
import pingroup.vos.Usuario;

/**
 *
 * @author Banana
 */
@FacesConverter("converterUsuario")
public class ConverterUsuario implements Converter {
    
     /**
     * El servicio de seguridad que permite hacer el Login
     */
    @EJB
    private IServicioPersistenciaLocal servicio;
    
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {

        if (value != null && value.trim().length() > 0) {
            String id = String.valueOf(value);
            Usuario usr = servicio.buscarUsuarioPorIdFacebook(id);
            return usr;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value != null) {
            Usuario usr = (Usuario) value;
            return usr.getIdFacebook();
        }
        return null;
    }
}
