package ar.edu.utn.frba.dds;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.CSVManagement.TipoOrganizacion;
import ar.edu.utn.frba.dds.model.Comunidad;
import ar.edu.utn.frba.dds.model.Entidad;
import ar.edu.utn.frba.dds.model.Interes;
import ar.edu.utn.frba.dds.model.Rango;
import ar.edu.utn.frba.dds.model.Servicio;
import ar.edu.utn.frba.dds.model.Usuario;
import ar.edu.utn.frba.dds.notification.EnvioNotificacionMail;
import ar.edu.utn.frba.dds.notification.EnvioNotificacionWpp;
import ar.edu.utn.frba.dds.notification.EventManager;
import ar.edu.utn.frba.dds.notification.MainNotificaciones;
import java.util.Arrays;
import java.util.Calendar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class NotificationTest {
  private Usuario usuario;
  private Interes interes;
  private Servicio servicio;

  @BeforeEach
  void initNotification() {
    servicio = new Servicio("Escaleras");
    interes = new Interes(new Entidad("Nombre", TipoOrganizacion.ENTIDAD_PRESTADORA, null), servicio);
    usuario = new Usuario("Roberto", "Alcaraz", "contrasenia", "telefono", "email", true);
    usuario.agregarInteres(interes);
  }

  @Test
  public void enviarNotificacionMail() {
    // Arrange
    var usuario = mock(Usuario.class);
    Comunidad comunidad = new Comunidad();
    comunidad.agregarUsuario(usuario);
    comunidad.agregarInteres(this.interes);
    when(usuario.tieneInteresEnServicio(servicio)).thenReturn(true);
    when(usuario.quiereNotificacion(any(Calendar.class))).thenReturn(true);
    when(usuario.getComunidades()).thenReturn(Arrays.asList(comunidad));
    when(usuario.getRangoNotificaciones()).thenReturn(Arrays.asList(new Rango(Calendar.getInstance(), Calendar.getInstance())));
    // Act
    comunidad.abrirIncidente(interes);
    Assertions.assertFalse(EventManager.getSingletonInstance().getNotificaciones().isEmpty());
    MainNotificaciones.NotificarAsincronicamente();
    // Assert
    verify(usuario, times(1)).notificar("Apertura", "Se abre el incidente asociado al servicio Escaleras");
    Assertions.assertTrue(EventManager.getSingletonInstance().getNotificaciones().isEmpty());
  }

  @Disabled
  @Test
  public void enviarNotificacionWhatsapp() {
      new EnvioNotificacionWpp().enviar(
          new Usuario("Roberto", "Alcaraz", "contrasenia", "541169590974", "email", true),
          "Testing",
          "Testeando"
          );
  }

  @Disabled
  @Test
  public void enviarNotificacionEmail() {
    new EnvioNotificacionMail().enviar(
        new Usuario("Roberto", "Alcaraz", "contrasenia", "541169590974", "elitvak@frba.utn.edu.ar", true),
        "Testing",
        "Testeando"
    );
  }
}
