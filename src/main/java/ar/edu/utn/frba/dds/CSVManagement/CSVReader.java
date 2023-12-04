package ar.edu.utn.frba.dds.CSVManagement;

import ar.edu.utn.frba.dds.errors.InvalidCSVHeadersException;
import ar.edu.utn.frba.dds.errors.InvalidCSVRowQuantityException;
import ar.edu.utn.frba.dds.errors.InvalidOrganizationTypeException;
import ar.edu.utn.frba.dds.model.Entidad;
import ar.edu.utn.frba.dds.errors.CsvNotFoundException;
import ar.edu.utn.frba.dds.errors.ErrorWhileReadingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVReader {
  private final String csvSplitBy = ",";
  private String line;
  private List<String> possibleHeaders = Arrays.asList("EMAIL", "NOMBRE", "TIPO");

  private String[] GetHeaders(String line) {
    var headers = line.split(csvSplitBy);
    var headersForValidation = headers.clone();
    if (headersForValidation.length != 3) throw new InvalidCSVHeadersException("La cantidad de campos en los headers es invalida");
    var toUpperCaseHeaders = Arrays.stream(headersForValidation).map(header -> header.toUpperCase()).sorted().collect(Collectors.toList());
    if (!toUpperCaseHeaders.toString().equals(possibleHeaders.toString()))
      throw new InvalidCSVHeadersException("Hay headers invalidos, los aceptados son: " + possibleHeaders.stream().collect(Collectors.joining(" | ")));
    return headers;
  }

  private Entidad ParseEntidad(String[] campos, String[] headers) {
    String email = null;
    String nombre = null;
    TipoOrganizacion tipo = null;
    for (int i = 0; i < headers.length; i++) {
      if (headers[i].toUpperCase().equals("EMAIL")) {
        email = campos[i].trim().equals("") ? null : campos[i];
      } else if (headers[i].toUpperCase().equals("TIPO")) {
        var campo = campos[i].toUpperCase();
        if (campo.equals("ORGANISMO_CONTROL")) {
          tipo = TipoOrganizacion.ORGANISMO_CONTROL;
        } else if (campo.equals("ENTIDAD_PRESTADORA")) {
          tipo = TipoOrganizacion.ENTIDAD_PRESTADORA;
        } else {
          throw new InvalidOrganizationTypeException("El tipo: " + campos[i] + " es invalido");
        }
      } else {
        nombre = campos[i];
      }
    }
    return new Entidad(nombre, tipo, email);
  }

  public CSVRead getEntidadesFromCSV(String pathArchivo) {
    String path = new File("").getAbsolutePath();
    BufferedReader br;
    try {
      br = new BufferedReader(new FileReader(path + pathArchivo));
    } catch (Exception e) {
      throw new CsvNotFoundException("Ha fallado al buscar el csv indicado", e);
    }
    List<Entidad> entidades = new ArrayList<>();
    List<LineError> errors = new ArrayList<>();
    try {
      line = br.readLine();
      if (line == null) return new CSVRead(entidades, errors);
      var headers = GetHeaders(line);
      int index = 1;
      while ((line = br.readLine()) != null) {
        String[] campos = line.split(csvSplitBy);
        try {
          if (campos.length != 3) throw new InvalidCSVRowQuantityException("Cantidad de columnas (" + campos.length + ") en la fila es invalida, se esperan 3");
          entidades.add(ParseEntidad(campos, headers));
        } catch (Exception e) {
          errors.add(new LineError(index, line, e.getMessage()));
        }
        index++;
      }
    } catch (IOException e) {
      throw new ErrorWhileReadingException("Ha ocurrido un error al leer el CSV", e);
    }
    return new CSVRead(entidades, errors);
  }
}

