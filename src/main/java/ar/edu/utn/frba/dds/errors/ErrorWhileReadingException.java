package ar.edu.utn.frba.dds.errors;

public class ErrorWhileReadingException extends RuntimeException {
  public ErrorWhileReadingException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}