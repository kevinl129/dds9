package ar.edu.utn.frba.dds.georef.response;

import ar.edu.utn.frba.dds.location.Provincia;

public class GetProvinciasResponse extends GetLocalizacionResponse {
  private Provincia[] provincias;
  public Provincia[] getProvincias() {
    return provincias;
  }
  public void setProvincias(Provincia[] provincias) {
    this.provincias = provincias;
  }
}
