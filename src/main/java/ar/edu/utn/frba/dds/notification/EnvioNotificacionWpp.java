package ar.edu.utn.frba.dds.notification;

import ar.edu.utn.frba.dds.model.Usuario;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import java.net.URI;
import java.math.BigDecimal;

@Entity
@DiscriminatorColumn(name = "W")
public class EnvioNotificacionWpp extends EnvioNotificacion{
  public EnvioNotificacionWpp() {}
  public static final String ACCOUNT_SID = "ACf8019156bf745f9a2009b9a0953a7820";
  public static final String AUTH_TOKEN = "19b94d9671bbf3c1ac24bf5dad32a936";
  @Override
  public void enviar(Usuario usuario, String evento, String mensaje) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
        new com.twilio.type.PhoneNumber("whatsapp:+"+usuario.getTelefono()),
        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
        "Titulo: " + evento + System.getProperty("line.separator") + "Body: " + mensaje).create();
    System.out.println(message.getSid());
  }

  @Override
  public String getTipo() {
    return "Whatsapp";
  }
}
