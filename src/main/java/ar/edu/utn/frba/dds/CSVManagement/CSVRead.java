package ar.edu.utn.frba.dds.CSVManagement;

import ar.edu.utn.frba.dds.model.Entidad;
import java.util.List;

public class CSVRead {
  List<LineError> errors;
  List<Entidad> entidades;

  public CSVRead(List<Entidad> entidades, List<LineError> errors) {
    this.errors = errors;
    this.entidades = entidades;
  }

  public List<Entidad> getEntidades() {
    return entidades;
  }

  public List<LineError> getErrors() {
    return errors;
  }
}
