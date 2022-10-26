/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *  
 */
package bean.servicio;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author hpulgar
 * Barquisimeto, 20 octubre 2020 11:11 }
 *
 * Docs envio de correos con JavaMail:
 *
 * http://chuwiki.chuidiang.org/index.php?title=Enviar_correos_con_JavaMail_y_Java
 *
 * https://desarrolloweb.com/articulos/2243.php
 *
 * https://www.adictosaltrabajo.com/2008/12/01/javamail/
 *
 * http://www.chuidiang.org/java/herramientas/javamail/enviar-correo-javamail.php
 * 
 */
public class EmailSenderService {

    private Properties properties = new Properties();
    //private String password; 
    private Session sesion;

    private void init() {
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");   // 25? 
        //properties.put("mail.smtp.mail.sender", "desica.informatica@gmail.com");  // ?????
        properties.put("mail.smtp.sender", "nomina.desica@gmail.com");  // ?????
        //properties.put("mail.smtp.user", "desica.informatica@gmail.com");  // ????
        properties.put("mail.smtp.user", "nomina.desica@gmail.com");  // ????
        properties.put("mail.smtp.auth", "true");
        sesion = Session.getDefaultInstance(properties);
    }  // init 

    public void sendMail(String asunto, String texto, String adjunto, String nombreAdjunto, String destinatario) {
        init();
        try {
            MimeMessage message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress((String) properties.getProperty("mail.smtp.sender")));
            //message.addRecipients(Message.RecipientType.TO, new InternetAddress("nomina.desica@gmail.com") );
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            //  * ASUNTO *
            message.setSubject(asunto);
           //  * TEXTO DEL CUERPO *
            //message.setText(textoCuerpo,"html");  // java.io.UnsupportedEncodingException: html
            BodyPart textoBP = new MimeBodyPart();
            textoBP.setText(texto);
           //message.setText(texto);
            // * ADJUNTO "
            BodyPart adjuntoBP = null;  
            if (adjunto!=null && !adjunto.isEmpty() ) {
                adjuntoBP = new MimeBodyPart();
                //adjuntoBP.setDataHandler(new DataHandler( new FileDataSource("/home/hpulgar/Temp/2000.pdf") ) );
                adjuntoBP.setDataHandler(new DataHandler(new FileDataSource(adjunto)));
                // opcional: 
                //adjunto.setFileName("2000.pdf");
                 adjuntoBP.setFileName(nombreAdjunto);
            }
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(textoBP);
            multiParte.addBodyPart(adjuntoBP);
            //
            message.setContent(multiParte);
            //
            Transport t = sesion.getTransport("smtp");
            //t.connect("smtp.gmail.com", (String) properties.get("mail.smtp.user"), "desica.informatica*001");
            t.connect((String) properties.get("mail.smtp.host"), (String) properties.get("mail.smtp.user"), "nomina01*");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException me) {
            System.err.println(";( ERROR AL ENVIAR EL CORREO: ");
            me.printStackTrace();
        } // try. 
    }  // sendMail 

}  // public class EmailSenderService
