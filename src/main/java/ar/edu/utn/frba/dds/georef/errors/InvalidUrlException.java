package ar.edu.utn.frba.dds.georef.errors;

public class InvalidUrlException extends RuntimeException {
  public InvalidUrlException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}