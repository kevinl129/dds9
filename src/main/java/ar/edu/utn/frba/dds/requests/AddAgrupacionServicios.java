package ar.edu.utn.frba.dds.requests;

import java.util.List;

public class AddAgrupacionServicios {
  private String nombre;
  private List<Long> servicios;

  public List<Long> getServicios() {
    return servicios;
  }

  public void setServicios(List<Long> servicios) {
    this.servicios = servicios;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
}
