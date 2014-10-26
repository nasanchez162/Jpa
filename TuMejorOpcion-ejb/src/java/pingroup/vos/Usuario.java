/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pingroup.vos;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

/**
 * La clase que representa un usuario dentro del sistema
 */
@Entity
public class Usuario {
    /**
     * El username del usuario
     */
    private String username;
    
    //@Transient
    private List<String> datas;
    /**
     * El id del usuario dentro de Facebook
     */
    @Id
    private String idFacebook;
    
    /**
     * El correo del usuario
     */
    private String correo = "";
    
    /**
     * El token de acceso de Facebook
     */
    private String tokenFacebook;

    /**
     * La lista de cupones que tiene el usuario
     */
    //@Transient
    //private List<Cupon> cupones;
    
    /**
     * La lista de tiendas que le gustan al usuario
     */
    @Reference
    private List<Tienda> tiendaLike;
    
    /**
     * Los nombres de las tiendas que le gustan al amig
     */
    private List<String> nombreTiedas;
    
    /**
     * La lista de amigos del usuario 
     */
    @Reference
    private List<Usuario> amigos;

    //TODO
    //@EJB
    //private ServicioPersistenciaJPA persistencia;
    
    public Usuario(String username, String idFacebook, String correo, String tokenFacebook, List<Cupon> cupones, List<Tienda> tiendaLike, List<Usuario> amigos) {
        this.username = username;
        this.idFacebook = idFacebook;
        this.correo = correo;
        this.tokenFacebook = tokenFacebook;
        //this.cupones = cupones;
        this.tiendaLike = tiendaLike;
        nombreTiedas = new ArrayList<String>();
        for (Tienda tt : tiendaLike) {
            nombreTiedas.add(tt.getNombre());
        }
        this.amigos = amigos;
        datas = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            String lol = username + i;
            datas.add(lol);
        }
    }
    
    public Usuario() {
        
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTokenFacebook() {
        return tokenFacebook;
    }

    public void setTokenFacebook(String tokenFacebook) {
        this.tokenFacebook = tokenFacebook;
    }

    public List<Cupon> getCupones() {
        //TODO persistencia
        //return cupones;
        return null;
    }

    //public void setCupones(List<Cupon> cupones) {
    //    this.cupones = cupones;
    //}

    public List<Tienda> getTiendaLike() {
        return tiendaLike;
    }

    public void setTiendaLike(List<Tienda> tiendaLike) {
        this.tiendaLike = tiendaLike;
    }

    public List<Usuario> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<Usuario> amigos) {
        this.amigos = amigos;
    }
    
    public List<String> getDatas()
    {
        return datas;
    }
    
    public List<String> getNombreTiendas()
    {
        return nombreTiedas;
    }
    
    public void setNombreTiendas( List<String> nombreTiendas)
    {
        this.nombreTiedas = nombreTiendas;
    }
    
    @Override
    public String toString()
    {
        return username;
    }
}
