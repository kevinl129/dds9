package ar.edu.utn.frba.dds.validator;

public class HasMinimumLength implements Validator {
  public int minimumLength = 8;
  public String error = "La contrasenia tiene que 8 o mas caracteres";

  public String validate(String password) {
    if (password.length() >= minimumLength) return null;
    return error;
  }
}
