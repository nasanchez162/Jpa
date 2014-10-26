/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pingroup.servicios;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import pingroup.interfaces.IServicioMenuUsuarioLocal;
import pingroup.interfaces.IServicioPersistenciaLocal;
import pingroup.vos.Cupon;
import pingroup.vos.Tienda;
import pingroup.vos.Usuario;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *Servicio de el menu de usuario
 */
@Stateless
public class ServicioMenuUsuarioMock implements IServicioMenuUsuarioLocal{
    
    /**
     * Servicio de persistencia
     */
    @EJB
    private IServicioPersistenciaLocal servicio;
    
    public ServicioMenuUsuarioMock(){
        
    }

    @Override
    public Cupon crearCupon(Usuario usr, Tienda tienda, int saldo) {
        Cupon cup = new Cupon(false, saldo, saldo, tienda, null, usr.getIdFacebook());
        servicio.aniadirCupon(cup, usr);
        return cup;
    }

    @Override
    public List<Tienda> darTiendas() {
        return servicio.getTiendas();
    }

    @Override
    public Usuario darAmigo(String idFB, Usuario usr) {
        for(Usuario ac: usr.getAmigos())
            if(ac.getIdFacebook().compareTo(idFB)==0)
                return ac;
        return null;
    }

    @Override
    public Tienda darTiendaPorNombre(String nombreT) {
        return servicio.buscarTiendaPorNombre(nombreT);
    }

    @Override
    public void enviarCupon(Cupon cup, Usuario usr) {
        // SMTP info
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "tmejoropcion@gmail.com";
        String password = "putasharry";
 
        // message info
        //String mailTo = usr.getCorreo();
        String mailTo = usr.getCorreo();
        String subject = "Te han regalado un cupón de regalo a través de tu mejor opción!";
        String message = "Aqui puedes ver tu nuevo codigo de descuento para la tienda " + cup.getTienda().getNombre();
 
        // attachments
        String[] attachFiles = new String[1];
        attachFiles[0] = servicio.darRutaImagenCupon(cup);
 
        try {
            sendEmailWithAttachments(host, port, mailFrom, password, mailTo,
                subject, message, attachFiles);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    
    }
    
    public void sendEmailWithAttachments(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message, String[] attachFiles)
            throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds attachments
        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
            }
        }
 
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
 
        // sends the e-mail
        Transport.send(msg);
 
    }

    @Override
    public List<Cupon> getCuponesUsuario(Usuario usuario) {
        return servicio.darCuponesUsuario( usuario );
    }
 


    
}
