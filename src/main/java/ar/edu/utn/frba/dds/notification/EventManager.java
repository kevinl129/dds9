package ar.edu.utn.frba.dds.notification;

import ar.edu.utn.frba.dds.model.Usuario;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class EventManager {
  List<Notificacion> notificaciones = new ArrayList();

  public List<Notificacion> getNotificaciones() {
    return notificaciones;
  }

  private static EventManager eventManager;
  public static EventManager getSingletonInstance() {
    if (eventManager == null) {
      eventManager = new EventManager();
    }
    return eventManager;
  }

  public void addNotificacion(Notificacion notificacion) {
    notificaciones.add(notificacion);
  }

  public void notificar() {
    Calendar ahora = Calendar.getInstance();
    List<Notificacion> notificacionesABorrar = new ArrayList<>();
    // Crear notificacion con el usuario, no con la comunidad. Chequear que el usuario siga
    // interesado en el servicio antes de ser notificado
    notificaciones.stream().forEach(notificacion -> {
      List<Usuario> miembrosANotificar = notificacion.comunidad.getMiembros().stream()
          .filter(miembro -> miembro.tieneInteresEnServicio(notificacion.incidente.getServicio()))
          .collect(Collectors.toList());
      miembrosANotificar.stream()
          .filter(miembroANotificar -> miembroANotificar.quiereNotificacion(ahora))
          .forEach(miembroANotificar -> {
            miembroANotificar.notificar(notificacion.evento, notificacion.mensaje);
            notificacionesABorrar.add(notificacion);
          });
    });
    notificacionesABorrar.stream().forEach(notificacion -> notificaciones.remove(notificacion));
  }
}
