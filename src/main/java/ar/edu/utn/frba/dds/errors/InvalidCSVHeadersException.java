package ar.edu.utn.frba.dds.errors;

public class InvalidCSVHeadersException extends RuntimeException {
  public InvalidCSVHeadersException(String errorMessage) {
    super(errorMessage);
  }
}