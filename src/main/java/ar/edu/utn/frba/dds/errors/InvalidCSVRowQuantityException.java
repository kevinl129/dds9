package ar.edu.utn.frba.dds.errors;

public class InvalidCSVRowQuantityException extends RuntimeException {
  public InvalidCSVRowQuantityException(String errorMessage) {
    super(errorMessage);
  }
}