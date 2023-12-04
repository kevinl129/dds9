package ar.edu.utn.frba.dds.location;

import javax.persistence.Embeddable;

@Embeddable
public class Centroide {
  private float lat;
  private float lon;

  public Centroide(float lat, float lon) {
    this.lat = lat;
    this.lon = lon;
  }

  public Centroide() {}

  public float getLat() {
    return lat;
  }

  public float getLon() {
    return lon;
  }

  public void setLat(float lat) {
    this.lat = lat;
  }

  public void setLon(float lon) {
    this.lon = lon;
  }

  public boolean estaCercaDe(Centroide centroide) {
    double distancia = Math.sqrt(Math.pow(centroide.lat - lat, 2) + Math.pow(centroide.lon - lat, 2));
    return distancia > 50;
  }
}
