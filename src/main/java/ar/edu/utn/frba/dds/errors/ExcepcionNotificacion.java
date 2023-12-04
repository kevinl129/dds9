package ar.edu.utn.frba.dds.errors;

public class ExcepcionNotificacion extends RuntimeException {

  public ExcepcionNotificacion(String errorMessage, Throwable err) {
    super(errorMessage, err);

  }
}