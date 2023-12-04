package ar.edu.utn.frba.dds.notification;

import ar.edu.utn.frba.dds.model.Comunidad;
import ar.edu.utn.frba.dds.model.Incidente;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Notificaciones")
public class Notificacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @ManyToOne
  Comunidad comunidad;
  @ManyToOne
  Incidente incidente;
  String evento;
  String mensaje;

  public Notificacion() {}

  public Notificacion(Comunidad comunidad, Incidente incidente, String evento, String mensaje) {
    this.comunidad = comunidad;
    this.incidente = incidente;
    this.mensaje = mensaje;
    this.evento = evento;
  }
}
