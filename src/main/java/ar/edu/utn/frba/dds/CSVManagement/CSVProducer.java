package ar.edu.utn.frba.dds.CSVManagement;

import ar.edu.utn.frba.dds.model.Entidad;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVProducer {
  private final String csvSplitBy = ",";

  private String EntidadToString(Entidad entidad) {
    var email = entidad.getEmail() != null ? entidad.getEmail() : " " + csvSplitBy;
    return Arrays.asList(entidad.getNombre(), entidad.getTipo().toString(), email).stream().collect(Collectors.joining(csvSplitBy)) + "\n";
  }

  private String CSVName(String name) {
    return name != null ? name + ".csv" : "organizaciones-ranking-" + new Date().getTime() + ".csv";
  }

  private String Header() {
    return Arrays.asList("Nombre", "Tipo", "Email").stream().collect(Collectors.joining(csvSplitBy)) + "\n";
  }

  public void WriteCsv(List<Entidad> entidades, String name) {
    List<String> dataLines = Stream.concat(Arrays.asList(Header()).stream(), entidades.stream().map(entidad -> EntidadToString(entidad))).toList();
    File csvOutputFile = new File("src/main/java/ar/edu/utn/frba/dds/CSVManagement/CSVs/" + CSVName(name));
    try {
      FileWriter fileWriter = new FileWriter(csvOutputFile);
      for (String line: dataLines) {
        fileWriter.write(line);
      }
      fileWriter.close();
    } catch (IOException e) {
      throw new RuntimeException("Fail writing csv");
    }
  }
}
