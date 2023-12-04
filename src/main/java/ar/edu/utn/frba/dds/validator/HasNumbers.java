package ar.edu.utn.frba.dds.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HasNumbers implements Validator {
  public String error = "La contrasenia debe tener por lo menos un numero";
  public String validate(String password) {
    String regex = ".*\\d.*";
    Matcher matcher = Pattern.compile(regex).matcher(password);
    if (matcher.matches()) return null;
    return error;
  }
}
