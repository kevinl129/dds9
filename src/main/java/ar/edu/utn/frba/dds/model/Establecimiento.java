package ar.edu.utn.frba.dds.model;

import ar.edu.utn.frba.dds.location.Localizacion;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Establecimientos")
public class Establecimiento {
  private String nombre;
  @OneToOne
  private Localizacion ubicacion;
  @OneToMany
  private List<Servicio> servicios;
  @Enumerated(EnumType.STRING)
  private TipoEstablecimiento tipo;
  @Transient
  private Entidad entidad;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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

  public Localizacion getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(Localizacion ubicacion) {
    this.ubicacion = ubicacion;
  }

  public List<Servicio> getServicios() {
    return servicios;
  }

  public void setServicios(List<Servicio> servicios) {
    this.servicios = servicios;
  }

  public TipoEstablecimiento getTipo() {
    return tipo;
  }

  public void setTipo(TipoEstablecimiento tipo) {
    this.tipo = tipo;
  }

  public Entidad getEntidad() {
    return entidad;
  }

  public void setEntidad(Entidad entidad) {
    this.entidad = entidad;
  }
}
