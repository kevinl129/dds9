package ar.edu.utn.frba.dds.notification;

import ar.edu.utn.frba.dds.model.Usuario;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Entity
@DiscriminatorColumn(name = "M")
public class EnvioNotificacionMail extends EnvioNotificacion {
  private final String mailOrigen = "";
  private final String passwordMailOrigen = "";
  @Override
  public void enviar(Usuario usuario, String evento, String mensaje) {

    // Config
    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");

    // Configuración de la sesión de correo
    Session session = Session.getDefaultInstance(properties, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(mailOrigen, passwordMailOrigen);
      }
    });

    try {
      // Crear y enviar mensaje de correo

      MimeMessage emailMessage = new MimeMessage(session);
      emailMessage.setFrom(new InternetAddress(mailOrigen));

      emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(usuario.getEmail()));

      emailMessage.setSubject("Notificación NO funcionamiento servicio");
      emailMessage.setText(mensaje);

      Transport.send(emailMessage);

    } catch (MessagingException e) {
      e.printStackTrace();
      System.out.println("Error al enviar la notificación por correo electrónico a todos los destinatarios: " + e.getMessage());
    }
  }

  @Override
  public String getTipo() {
    return "Mail";
  }
}
