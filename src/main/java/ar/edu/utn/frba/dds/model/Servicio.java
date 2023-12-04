package ar.edu.utn.frba.dds.model;

import ar.edu.utn.frba.dds.location.Localizacion;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Servicios")
public class Servicio {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  String nombre;
  @OneToOne
  Localizacion ubicacionServicio;

  public Servicio(String nombre){
    this.nombre = nombre;
  }

  public Servicio() {}

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getId() { return id; }

  public void setId(Long id) {
    this.id = id;
  }
  public void setLocalizacion(Localizacion localizacion) {
    ubicacionServicio = localizacion;
  }

  public Localizacion getLocalizacion() {
    return ubicacionServicio;
  }
}
