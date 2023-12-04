package ar.edu.utn.frba.dds.georef.response;

import ar.edu.utn.frba.dds.location.Municipio;

public class GetMunicipiosResponse extends GetLocalizacionResponse {
  private Municipio[] municipios;

  public Municipio[] getMunicipios() {
    return municipios;
  }

  public void setMunicipios(Municipio[] municipios) {
    this.municipios = municipios;
  }
}
