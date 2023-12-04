package ar.edu.utn.frba.dds.errors;

public class CsvNotFoundException extends RuntimeException {
  public CsvNotFoundException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}