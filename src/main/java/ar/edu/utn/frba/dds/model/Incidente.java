package ar.edu.utn.frba.dds.model;

import ar.edu.utn.frba.dds.location.Localizacion;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Incidentes")
public class Incidente {
  @ManyToOne
  private Interes interes;
  private Date fechaCreacion;
  private Date fechaCierre;
  private String observacion;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public Incidente() {}

  public String getObservacion() {
    return observacion;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public Date getFechaCierre() {
    return fechaCierre;
  }

  public void setObservaciones(String observacion) {
    this.observacion = observacion;
  }

  public Servicio getServicio() {
    return interes.getServicio();
  }

  public Entidad getEntidad() {
    return interes.getEntidad();
  }

  public Incidente(Interes interes, Date fechaCreacion) {
    this.interes = interes;
    this.fechaCreacion = fechaCreacion;
  }

  public Incidente(Servicio servicio, String observacion) {
    this.interes = new Interes(servicio);
    this.fechaCreacion = new Date();
    this.observacion = observacion;
  }

  public boolean tieneMasDe24Horas() {
    if (fechaCierre != null)
      return true;
    var hoy = new Date().getTime();
    return Math.floor(hoy - fechaCreacion.getTime() / (1000 * 60 * 60 * 24)) > 1;
  }

  public boolean estaAbierto() {
    return fechaCierre == null;
  }

  public boolean estaCerrado() {
    return !estaAbierto();
  }

  public Date cerrar() {
    Date hoy = new Date();
    setFechaCierre(hoy);
    return hoy;
  }

  public boolean estaEnLaSemanaActual() {
    Calendar hoy = Calendar.getInstance();
    Calendar fechaCreacionCalendar = Calendar.getInstance();
    fechaCreacionCalendar.setTime(fechaCreacion);
    return hoy.get(Calendar.WEEK_OF_YEAR) == fechaCreacionCalendar.get(Calendar.WEEK_OF_YEAR) &&
        hoy.get(Calendar.YEAR) == fechaCreacionCalendar.get(Calendar.YEAR);
  }

  public long getDiferenciaEntreCierreYCreacion() {
    return fechaCierre.getTime() - fechaCreacion.getTime();
  }

  public Interes getInteres() {
    return interes;
  }

  public boolean estaCercaDe(Localizacion ubicacion) {
    return interes.estaCercaDe(ubicacion);
  }

  public void setFechaCierre(Date fechaCierre) {
    this.fechaCierre = fechaCierre;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
