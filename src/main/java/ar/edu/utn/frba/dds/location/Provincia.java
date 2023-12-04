package ar.edu.utn.frba.dds.location;

import ar.edu.utn.frba.dds.georef.response.GeoRefUbicacion;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("P")
public class Provincia extends Localizacion {
  @Column(name = "nombre_completo")
  private String nombre_largo;

  public String getNombre_largo() {
    return nombre_largo;
  }

  public void setNombre_largo(String nombre_largo) {
    this.nombre_largo = nombre_largo;
  }

  public Provincia() {
    this.categoria = CategoriaLocacion.PROVINCIA;
  }

  @Override
  public Localizacion getLocalizacion(GeoRefUbicacion ubicacion) {
    return ubicacion.getProvincia();
  }
}
