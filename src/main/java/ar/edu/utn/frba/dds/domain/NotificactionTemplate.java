package ar.edu.utn.frba.dds.domain;

public class NotificactionTemplate {
  private String notificacionTitulo;
  private String notificacionBody;

  public NotificactionTemplate(String notificacionTitulo, String notificacionBody) {
    this.notificacionBody = notificacionBody;
    this.notificacionTitulo = notificacionTitulo;
  }
  public String getNotificacionBody() {
    return notificacionBody;
  }

  public void setNotificacionBody(String notificacionBody) {
    this.notificacionBody = notificacionBody;
  }

  public String getNotificacionTitulo() {
    return notificacionTitulo;
  }

  public void setNotificacionTitulo(String notificacionTitulo) {
    this.notificacionTitulo = notificacionTitulo;
  }
}
