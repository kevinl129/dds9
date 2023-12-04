package ar.edu.utn.frba.dds.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HasNoWhiteSpaces implements Validator {
  public String error = "La contrasenia no debe tener espacios";
  public String validate(String password) {
    String regex = "\\S+";
    Matcher matcher = Pattern.compile(regex).matcher(password);
    if (matcher.matches()) return null;
    return error;
  }
}
