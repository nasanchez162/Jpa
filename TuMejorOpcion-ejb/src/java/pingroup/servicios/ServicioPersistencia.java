/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pingroup.servicios;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mongodb.MongoClient;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import pingroup.interfaces.IServicioFacebookLocal;
import pingroup.interfaces.IServicioPersistenciaLocal;
import pingroup.vos.Cupon;
import pingroup.vos.Tienda;
import pingroup.vos.Usuario;

/**
 *
 * @author estudiante
 */
@Stateless
@Local
@EJB(beanInterface = IServicioPersistenciaLocal.class, beanName = "ServicioPersistencia", name = "ServicioPersistencia")
public class ServicioPersistencia extends MongoConfig implements Serializable , IServicioPersistenciaLocal{

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------
    
    
    /**
     * La entidad encargada de persistir en la base de datos
     */
    @PersistenceContext
    private EntityManager entityManager;
    
    private Datastore ds;
    
    @EJB
    private IServicioFacebookLocal beanFacebook;
    
    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------

    /**
     * Constructor de la clase. Inicializa los atributos.
     */
    public ServicioPersistencia()
    {
        Morphia morphia = new Morphia();
        morphia.map(Tienda.class);
        morphia.map(Usuario.class);
        ds = morphia.createDatastore((MongoClient) db.getMongo(), "Usuario");
    }

    @Override
    public Usuario buscarUsuarioPorToken(String token) {
        List<Usuario> l = ds.find(Usuario.class, "tokenFacebook = ", token).asList();
        
        if(l.isEmpty())
            return null;
        Usuario uu = l.get(0);  
        if (uu.getNombreTiendas() == null)
            uu.setNombreTiendas(new Vector<String> ());
        return uu;
    }

    @Override
    public void aniadirUsuario(Usuario usuario) {
        ds.save(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorNombre(String nombre) {
        List<Usuario> l = ds.find(Usuario.class, "username = ", nombre).asList();
        if(l.isEmpty())
            return null;
        Usuario uu = l.get(0);  
        if (uu.getNombreTiendas() == null)
            uu.setNombreTiendas(new Vector<String> ());
        return uu;
    }

    @Override
    public Usuario buscarUsuarioPorIdFacebook(String idFacebook) {
        System.out.println("Es null");
        System.out.println(ds.find(Usuario.class, "idFacebook = ", idFacebook) == null);
         List<Usuario> l = ds.find(Usuario.class, "idFacebook = ", idFacebook).asList();
        
        if(l.isEmpty())
            return null;
       
        Usuario uu = l.get(0);  
        if (uu.getNombreTiendas() == null)
            uu.setNombreTiendas(new Vector<String> ());
        return uu;
    }

    @Override
    public void volverseAmigos(Usuario usuario1, Usuario usuario2) {
        boolean found = false;
        for(Usuario ac: usuario1.getAmigos())
            if(ac.getIdFacebook().compareTo(usuario2.getIdFacebook())==0)
                found=true;
        if(!found)
        {
            List<Usuario> amigosUs1 = usuario1.getAmigos();
            amigosUs1.add(usuario2);
            usuario1.setAmigos(amigosUs1);
        }
       found = false;
        for(Usuario ac: usuario2.getAmigos())
            if(ac.getIdFacebook().compareTo(usuario1.getIdFacebook())==0)
                found=true;
        if(!found)
        {
            List<Usuario> amigosUs2 = usuario2.getAmigos();
            amigosUs2.add(usuario1);
            usuario2.setAmigos(amigosUs2);
        }
        actualizarUsuario(usuario2);
        actualizarUsuario(usuario1);
    }

    @Override
    public void aniadirTiendaAUsuario(Usuario usuario, Tienda tienda) {
        List<Tienda> tiendas1 = usuario.getTiendaLike();
        boolean found = false;
        for (Tienda tienda1 : tiendas1) {
            if (tienda1.getIdFacebook().equals(tienda.getIdFacebook()))
            {
                found = true;
                break;
            }
        }
        if (!found)
        {
            tiendas1.add(tienda);
            usuario.setTiendaLike(tiendas1);
            List<String> noms = usuario.getNombreTiendas();
            noms.add(tienda.getNombre());
            usuario.setNombreTiendas(noms);

            this.actualizarTienda(tienda);
            this.actualizarUsuario(usuario);
        }
    }


    @Override
    public void aniadirCupon(Cupon cupon, Usuario usuario) {
        String id = this.generateIdCupon();
        cupon.setIdCupon(id);
        entityManager.persist(cupon);
        System.out.println("El cupon es: " + darCuponPorId(id).toString());
    }

    @Override
    public Cupon darCuponPorId(String idCupon) {
         return entityManager.find(Cupon.class, idCupon);
    }

    @Override
    public boolean descontarDineroCupon(double dineroADescontar, Cupon cupon) {
        return true;
    }

    @Override
    public List<Usuario> getUsuarios() {
       return ds.find(Usuario.class).asList();    
    }

    @Override
    public List<Tienda> getTiendas() {
        return ds.find(Tienda.class).asList();  
    }

    @Override
    public List<Cupon> getCupones() {
        return entityManager.createQuery("select O from " + Cupon.class.getSimpleName() + " as O").getResultList();
    }

     @Override
    public void aniadirTienda(Tienda tienda) {
        ds.save(tienda);
    }

    @Override
    public Tienda buscarTiendaPorId(String idTienda) {
       List<Tienda> l = ds.find(Tienda.class, "idFacebook = ", idTienda).asList();
        
        if(l.isEmpty())
            return null;
        
        return l.get(0);
    }

    @Override
    public Tienda buscarTiendaPorNombre(String nombre) {
        List<Tienda> l = ds.find(Tienda.class, "nombre = ", nombre).asList();
        
        if(l.isEmpty())
            return null;
        
        return l.get(0);
    }
    
    

    public Usuario darUsuarioPrueba()
    {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override
    public void eliminarTienda(Tienda tienda) {
        ds.delete(tienda);
    }

    @Override
    public String darRutaImagenCupon(Cupon cupi) {
    String myCodeText = cupi.getIdCupon();
        String filePath = "C:/QRUsuarios/"+myCodeText+"_QR.png";
        int size = 250;
        String fileType = "png";
        File myFile = new File(filePath);   
        try {
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText,BarcodeFormat.QR_CODE, size, size, hintMap);
            int QRWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(QRWidth, QRWidth,
                    BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, QRWidth, QRWidth);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < QRWidth; i++) {
                for (int j = 0; j < QRWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, fileType, myFile);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("You have successfully created QR Code.");
        return filePath;    
    }

     /**
     * Genera un Id para cupones con alta probabilidad de ser unico
     * @return Un id para cupon
     */
    private String generateIdCupon(){
        long r= this.getCupones().size();
        for(int i=-5;i< Math.random()*10;i++)
            r=(r*r)%1020792161;
        r=(r*(this.hashCode()+1))%1000000007;
        return "Cup"+r+""+this.hashCode();
    }

    @Override
    public List<Cupon> darCuponesUsuario(Usuario usuario) {
        final String qstring = "SELECT e FROM Cupon e WHERE e.idUsuario = :usuario";
        TypedQuery<Cupon> query = entityManager.createQuery(qstring, Cupon.class);
        query.setParameter("usuario", usuario.getIdFacebook());
        List<Cupon> cups = query.getResultList();
        return cups;
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
         ds.save(usuario);
    }

    @Override
    public void actualizarTienda(Tienda tienda) {
         ds.save(tienda);
    }
    
      //Clase auxiliar BufferedImageLuminanceSource
    public final class BufferedImageLuminanceSource extends LuminanceSource {

        private final BufferedImage image;
        private final int left;
        private final int top;
        private int[] rgbData;

        public BufferedImageLuminanceSource(BufferedImage image) {
            this(image, 0, 0, image.getWidth(), image.getHeight());
        }

        public BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width,
                int height) {
            super(width, height);

            int sourceWidth = image.getWidth();
            int sourceHeight = image.getHeight();
            if (left + width > sourceWidth || top + height > sourceHeight) {
                throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
            }

            this.image = image;
            this.left = left;
            this.top = top;
        }

        // These methods use an integer calculation for luminance derived from:
        // <code>Y = 0.299R + 0.587G + 0.114B</code>
        @Override
        public byte[] getRow(int y, byte[] row) {
            if (y < 0 || y >= getHeight()) {
                throw new IllegalArgumentException("Requested row is outside the image: " + y);
            }
            int width = getWidth();
            if (row == null || row.length < width) {
                row = new byte[width];
            }

            if (rgbData == null || rgbData.length < width) {
                rgbData = new int[width];
            }
            image.getRGB(left, top + y, width, 1, rgbData, 0, width);
            for (int x = 0; x < width; x++) {
                int pixel = rgbData[x];
                int luminance = (306 * ((pixel >> 16) & 0xFF)
                        + 601 * ((pixel >> 8) & 0xFF)
                        + 117 * (pixel & 0xFF)) >> 10;
                row[x] = (byte) luminance;
            }
            return row;
        }

        @Override
        public byte[] getMatrix() {
            int width = getWidth();
            int height = getHeight();
            int area = width * height;
            byte[] matrix = new byte[area];

            int[] rgb = new int[area];
            image.getRGB(left, top, width, height, rgb, 0, width);
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    int pixel = rgb[offset + x];
                    int luminance = (306 * ((pixel >> 16) & 0xFF)
                            + 601 * ((pixel >> 8) & 0xFF)
                            + 117 * (pixel & 0xFF)) >> 10;
                    matrix[offset + x] = (byte) luminance;
                }
            }
            return matrix;
        }

        @Override
        public boolean isCropSupported() {
            return true;
        }

        @Override
        public LuminanceSource crop(int left, int top, int width, int height) {
            return new BufferedImageLuminanceSource(image, this.left + left, this.top + top, width, height);
        }

        // Can't run AffineTransforms on images of unknown format.
        @Override
        public boolean isRotateSupported() {
            return image.getType() != BufferedImage.TYPE_CUSTOM;
        }

        @Override
        public LuminanceSource rotateCounterClockwise() {
            if (!isRotateSupported()) {
                throw new IllegalStateException("Rotate not supported");
            }
            int sourceWidth = image.getWidth();
            int sourceHeight = image.getHeight();

            // Rotate 90 degrees counterclockwise.
            AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);

            // Note width/height are flipped since we are rotating 90 degrees.
            BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, image.getType());

            // Draw the original image into rotated, via transformation
            Graphics2D g = rotatedImage.createGraphics();
            g.drawImage(image, transform, null);
            g.dispose();

            // Maintain the cropped region, but rotate it too.
            int width = getWidth();
            return new BufferedImageLuminanceSource(rotatedImage, top, sourceWidth - (left + width),
                    getHeight(), width);
        }
    }
}
