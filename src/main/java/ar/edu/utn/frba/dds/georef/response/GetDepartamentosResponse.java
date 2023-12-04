package ar.edu.utn.frba.dds.georef.response;

import ar.edu.utn.frba.dds.location.Departamento;

public class GetDepartamentosResponse extends GetLocalizacionResponse {
  private Departamento[] departamentos;
  public Departamento[] getDepartamentos() {
    return departamentos;
  }
  public void setDepartamentos(Departamento[] departamentos) {
    this.departamentos = departamentos;
  }
}
