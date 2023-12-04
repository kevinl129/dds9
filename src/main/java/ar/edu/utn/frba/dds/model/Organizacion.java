package ar.edu.utn.frba.dds.model;

import ar.edu.utn.frba.dds.CSVManagement.TipoOrganizacion;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Organizaciones")
public class Organizacion  {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;

  @Enumerated(EnumType.STRING)
  private TipoOrganizacion tipo;

  private String email;

  public Organizacion(String nombre, TipoOrganizacion tipo, String email) {
    this.nombre = nombre;
    this.tipo = tipo;
    this.email = email;
  }

  public Organizacion() {}

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
     this.nombre = nombre;
  }

  public TipoOrganizacion getTipo() {
    return tipo;
  }

  public void setTipo(TipoOrganizacion tipo) {
    this.tipo = tipo;
  }

  public String getEmail() { return email; }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
