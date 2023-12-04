package ar.edu.utn.frba.dds.location;

import ar.edu.utn.frba.dds.georef.response.GeoRefUbicacion;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.checkerframework.checker.units.qual.C;

@Entity
@Table(name = "Localizaciones")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type")
public abstract class Localizacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;
  private String idApi;
  private String nombre;
  @Embedded
  private Centroide centroide;
  @Enumerated(EnumType.STRING)
  public CategoriaLocacion categoria;

  public String getId() {
    return idApi;
  }

  public void setId(String id) {
    this.idApi = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Centroide getCentroide() {
    return centroide;
  }

  public void setCentroide(Centroide centroide) {
    this.centroide = centroide;
  }

  public CategoriaLocacion getCategoria() {
    return categoria;
  }

  public abstract Localizacion getLocalizacion(GeoRefUbicacion ubicacion);

  public boolean estaCercaDe(Localizacion localizacion) {
    return centroide.estaCercaDe(localizacion.centroide);
  }

  public static Localizacion randomLocalizacion() {
    Departamento departamento = new Departamento();
    int max = 50;
    int min = -50;
    float lat = (float) Math.floor(Math.random() *(max - min + 1) + min);
    float lon = (float) Math.floor(Math.random() *(max - min + 1) + min);
    departamento.setCentroide(new Centroide(lat, lon));
    return departamento;
  }
}
