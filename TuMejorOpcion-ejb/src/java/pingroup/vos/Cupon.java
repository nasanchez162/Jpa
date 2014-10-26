
package pingroup.vos;

import javax.ejb.EJB;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import pingroup.interfaces.IServicioPersistenciaLocal;
import pingroup.servicios.ServicioPersistencia;

/**
 * Clase que representa los cupones en el sistema
 */
@Entity
public class Cupon {
    
    /**
     * Atributo que indica si el cupon ya fue redimido
     */
    @Column
    private boolean redimido;
    
    /**
     *  El saldo restante en el cupon
     */
    @Column
    private double saldo;
    
    /**
     * El coste original del cupon
     */
    @Column
    private double costo;
    
    /**
     * El id del usuario que compro el cupon
     */
    @Column
    private String idUsuario;
    
    /**
     * El id de la tienda al que pertenece el cupon
     */
    @Column
    private String idTienda;
    
    /**
     * El servicio de la persistencia
     */
    @Transient
    private IServicioPersistenciaLocal persistencia;
    
    
    /**
     * El id único del cupon
     */
    @Id
    private String idCupon;

    public Cupon(boolean redimido, double saldo, double costo, Tienda tienda, String idCupon, String idUsuario) {
        this.redimido = redimido;
        this.saldo = saldo;
        this.costo = costo;
        this.idTienda = tienda.getIdFacebook();
        this.idCupon = idCupon;
        this.persistencia = new ServicioPersistencia();
        this.idUsuario = idUsuario;
    }
    
    /**
     * Constructor vacio del cupon
     */
    public Cupon()
    {
        this.persistencia = new ServicioPersistencia();
    }
    
    
    public boolean isRedimido() {
        return redimido;
    }

    public void setRedimido(boolean redimido) {
        this.redimido = redimido;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public Tienda getTienda(){
        System.out.println("La tienda es : " + idTienda);
        return persistencia.buscarTiendaPorId(idTienda);
    }
    
    public void setTienda( Tienda t){
        this.idTienda = t.getIdFacebook();
    }

    public String getIdCupon() {
        return idCupon;
    }

    public void setIdCupon(String idCupon) {
        this.idCupon = idCupon;
    }
    
    public String getIdUsuario (){
        return idUsuario;
    }
    
    public void setIdUsuario (String idUsuario){
        this.idUsuario = idUsuario;
    }
    
}
