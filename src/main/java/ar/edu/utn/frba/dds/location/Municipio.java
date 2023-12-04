package ar.edu.utn.frba.dds.location;

import ar.edu.utn.frba.dds.georef.response.GeoRefUbicacion;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("M")
public class Municipio extends Localizacion {
  @Column(name = "nombre_completo")
  private String nombre_completo;
  @ManyToOne
  private Provincia provincia;

  public String getNombre_completo() {
    return nombre_completo;
  }

  public void setNombre_completo(String nombre_completo) {
    this.nombre_completo = nombre_completo;
  }

  public Provincia getProvincia() {
    return provincia;
  }

  public void setProvincia(Provincia provincia) {
    this.provincia = provincia;
  }

  public Municipio() {
    this.categoria = CategoriaLocacion.MUNICIPIO;
  }

  @Override
  public Localizacion getLocalizacion(GeoRefUbicacion ubicacion) {
    return ubicacion.getMunicipio();
  }
}
