package ar.edu.utn.frba.dds.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HasBothCases implements Validator {
  public String error = "La contrasenia debe tener por lo menos una mayuscula y una minuscula";
  public String validate(String password) {
    String regex = ".*(?=.*[a-z])(?=.*[A-Z]).*";
    Matcher matcher = Pattern.compile(regex).matcher(password);
    if (matcher.matches()) return null;
    return "La contrasenia debe tener por lo menos una mayuscula y una minuscula";
  }
}
