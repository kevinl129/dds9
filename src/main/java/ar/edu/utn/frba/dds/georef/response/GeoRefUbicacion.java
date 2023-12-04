package ar.edu.utn.frba.dds.georef.response;

import ar.edu.utn.frba.dds.location.Departamento;
import ar.edu.utn.frba.dds.location.Municipio;
import ar.edu.utn.frba.dds.location.Provincia;

public class GeoRefUbicacion {
  private float lat;
  private float lon;
  private Provincia provincia;
  private Departamento departamento;
  private Municipio municipio;

  public float getLon() {
    return lon;
  }

  public void setLon(float lon) {
    this.lon = lon;
  }

  public float getLat() {
    return lat;
  }

  public void setLat(float lat) {
    this.lat = lat;
  }

  public Provincia getProvincia() {
    return provincia;
  }

  public void setProvincia(Provincia provincia) {
    this.provincia = provincia;
  }

  public Departamento getDepartamento() {
    return departamento;
  }

  public void setDepartamento(Departamento departamento) {
    this.departamento = departamento;
  }

  public Municipio getMunicipio() {
    return municipio;
  }

  public void setMunicipio(Municipio municipio) {
    this.municipio = municipio;
  }
}
