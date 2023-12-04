package ar.edu.utn.frba.dds.validator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorstPasswords implements Validator {
  public String error = "La contrasenia es una de las peores 10000";
  private List<String> getWorstPasswords() {
    String path = new File("").getAbsolutePath();
    List<String> passwords = new ArrayList<>();
    FileReader fr;
    try {
      fr = new FileReader(path + "/src/main/java/ar/edu/utn/frba/dds/constants/worstPasswords.txt");
    } catch (IOException exception) {
      System.out.println(exception);
      throw new RuntimeException("Error al conseguir la lista de contrasenias");
    }
    int i;
    String partialPassword = "";
    try {
      while ((i = fr.read()) != -1) {
        char c = (char) i;
        if (c == '\n') {
          passwords.add(partialPassword);
          partialPassword = "";
        } else if (c != '\r') {
          partialPassword += c;
        }
      }
    } catch (IOException exception) {
      System.out.println(exception);
      throw new RuntimeException("Error al buscar en la lista de contrasenias");
    }
    return passwords;
  }

  public String validate(String password) {
    List<String> worstPasswords = getWorstPasswords();
    if(worstPasswords.stream().anyMatch(n-> n.equals(password)))
        return error;
    return null;
  }
}
