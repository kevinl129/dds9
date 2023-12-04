package ar.edu.utn.frba.dds.errors;

public class ValidationError extends RuntimeException {
  public ValidationError(String message) {
    super(message);
  }
}
