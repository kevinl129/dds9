package ar.edu.utn.frba.dds.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "AgrupacionesServicio")
public class AgrupacionServicio {
  private String nombre;
  @ManyToMany
  private List<Servicio> servicios;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public AgrupacionServicio(String nombre, List<Servicio> servicios) {
    this.nombre = nombre;
    this.servicios = servicios;
  }

  public AgrupacionServicio() {}

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<Servicio> getServicios() {
    return servicios;
  }

  public void setServicios(List<Servicio> servicios) {
    this.servicios = servicios;
  }
}
