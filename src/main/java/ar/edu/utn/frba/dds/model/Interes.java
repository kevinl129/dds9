package ar.edu.utn.frba.dds.model;

import ar.edu.utn.frba.dds.location.Localizacion;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Intereses")
public class Interes {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Transient
  Entidad entidad;
  @ManyToOne
  Servicio servicio;
  public Interes(Entidad entidad, Servicio servicio) {
    this.entidad = entidad;
    this.servicio = servicio;
  }

  public Interes(Servicio servicio) {
    this.servicio = servicio;
  }

  public Interes() {}

  public boolean estaCercaDe(Localizacion localizacion) {
    return servicio.getLocalizacion().estaCercaDe(localizacion);
  }

  public Servicio getServicio() {
    return servicio;
  }
  public void setServicio(Servicio servicio){
    this.servicio = servicio;
  }
  public Entidad getEntidad() {
    return entidad;
  }
  public boolean esIgualAOtroInteres(Interes interes) {
    return getServicio().equals(interes.getServicio()) && getEntidad().equals(interes.getEntidad());
  }

  public Long getId() {
    return id;
  }
}
