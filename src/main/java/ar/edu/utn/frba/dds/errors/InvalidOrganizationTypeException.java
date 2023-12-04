package ar.edu.utn.frba.dds.errors;

public class InvalidOrganizationTypeException extends RuntimeException {
  public InvalidOrganizationTypeException(String errorMessage) {
    super(errorMessage);
  }
}