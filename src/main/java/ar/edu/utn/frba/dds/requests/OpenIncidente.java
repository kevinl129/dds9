package ar.edu.utn.frba.dds.requests;

public class OpenIncidente {
  private Long service;
  private String observacion;

  public String getObservacion() {
    return observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

  public Long getService() {
    return service;
  }

  public void setService(Long service) {
    this.service = service;
  }
}
