package ar.edu.utn.frba.dds.georef.response;

public class GetLocalizacionResponse {
  private int cantidad;
  private int total;
  private int inicio;

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getInicio() {
    return inicio;
  }

  public void setInicio(int inicio) {
    this.inicio = inicio;
  }
}
