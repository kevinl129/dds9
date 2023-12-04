package ar.edu.utn.frba.dds.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HasSpecialCharacter implements Validator {
  public String error = "La contrasenia tiene que tener uno de estos caracteres especiales: @#$%^&+=_-";
  public String validate(String password) {
    String regex = ".*[@#$%^&+=_-].*";
    Matcher matcher = Pattern.compile(regex).matcher(password);
    if (matcher.matches()) return null;
    return error;
  }
}
