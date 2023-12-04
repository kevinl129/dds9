package ar.edu.utn.frba.dds.georef.errors;

public class InvalidRequestException extends RuntimeException {
  public InvalidRequestException(String errorMessage) {
    super(errorMessage);
  }
}