package ar.edu.utn.frba.dds.georef.errors;

public class RequestFailException extends RuntimeException {
  public RequestFailException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}