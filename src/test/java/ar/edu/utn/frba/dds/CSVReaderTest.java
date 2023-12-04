package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertThrows;
import ar.edu.utn.frba.dds.CSVManagement.CSVReader;
import ar.edu.utn.frba.dds.CSVManagement.TipoOrganizacion;
import ar.edu.utn.frba.dds.errors.CsvNotFoundException;
import ar.edu.utn.frba.dds.errors.InvalidCSVHeadersException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class CSVReaderTest {
  private CSVReader reader;
  private final String pathroot="/src/test/java/ar/edu/utn/frba/dds/mocks/";
  @BeforeEach
  void initCSVReader() {
    reader = new CSVReader();
  }

  @Test
  public void testReadCSVOK() {
    String csvFile = "CSVOrganismos.csv";
    var csvData = reader.getEntidadesFromCSV(pathroot + csvFile);
    var organizaciones = csvData.getEntidades();
    // Verificar que se haya leído al menos una línea de datos
    Assertions.assertFalse(organizaciones.isEmpty(), "El archivo CSV está vacío.");
    Assertions.assertTrue(csvData.getErrors().isEmpty());
    // Verificar la cantidad de registros
    int expectedFilas = 5;
    int actualFilas = organizaciones.toArray().length;
    Assertions.assertEquals(expectedFilas, actualFilas, "La cantidad de columnas no coincide.");
    var primerDato = organizaciones.get(0);
    Assertions.assertEquals(primerDato.getNombre(), "Galeno");
    Assertions.assertEquals(primerDato.getTipo(), TipoOrganizacion.ENTIDAD_PRESTADORA);
    Assertions.assertEquals(primerDato.getEmail(), null);
    var segundoDato = organizaciones.get(1);
    Assertions.assertEquals(segundoDato.getNombre(), "Superintendencia");
    Assertions.assertEquals(segundoDato.getTipo(), TipoOrganizacion.ORGANISMO_CONTROL);
    Assertions.assertEquals(segundoDato.getEmail(), null);
    var tercerDato = organizaciones.get(2);
    Assertions.assertEquals(tercerDato.getNombre(), "Telecentro");
    Assertions.assertEquals(tercerDato.getTipo(), TipoOrganizacion.ENTIDAD_PRESTADORA);
    Assertions.assertEquals(tercerDato.getEmail(), null);
    var cuartoDato = organizaciones.get(3);
    Assertions.assertEquals(cuartoDato.getNombre(), "IBM");
    Assertions.assertEquals(cuartoDato.getTipo(), TipoOrganizacion.ENTIDAD_PRESTADORA);
    Assertions.assertEquals(cuartoDato.getEmail(), null);
    var quintoDato = organizaciones.get(4);
    Assertions.assertEquals(quintoDato.getNombre(), "Procuradoria");
    Assertions.assertEquals(quintoDato.getTipo(), TipoOrganizacion.ORGANISMO_CONTROL);
    Assertions.assertEquals(quintoDato.getEmail(), null);
  }

  @Test
  public void testReadCSVOKWithHeadersInDifferentPositions() {
    String csvFile = "CSVOrganismos.csv";
    var csvData = reader.getEntidadesFromCSV(pathroot + csvFile);
    var organizaciones = csvData.getEntidades();
    // Verificar que se haya leído al menos una línea de datos
    Assertions.assertFalse(organizaciones.isEmpty(), "El archivo CSV está vacío.");
    Assertions.assertTrue(csvData.getErrors().isEmpty());
    // Verificar la cantidad de registros
    int expectedFilas = 5;
    int actualFilas = organizaciones.toArray().length;
    Assertions.assertEquals(expectedFilas, actualFilas, "La cantidad de columnas no coincide.");
    var primerDato = organizaciones.get(0);
    Assertions.assertEquals(primerDato.getNombre(), "Galeno");
    Assertions.assertEquals(primerDato.getTipo(), TipoOrganizacion.ENTIDAD_PRESTADORA);
    Assertions.assertEquals(primerDato.getEmail(), null);
    var segundoDato = organizaciones.get(1);
    Assertions.assertEquals(segundoDato.getNombre(), "Superintendencia");
    Assertions.assertEquals(segundoDato.getTipo(), TipoOrganizacion.ORGANISMO_CONTROL);
    Assertions.assertEquals(segundoDato.getEmail(), null);
    var tercerDato = organizaciones.get(2);
    Assertions.assertEquals(tercerDato.getNombre(), "Telecentro");
    Assertions.assertEquals(tercerDato.getTipo(), TipoOrganizacion.ENTIDAD_PRESTADORA);
    Assertions.assertEquals(tercerDato.getEmail(), null);
    var cuartoDato = organizaciones.get(3);
    Assertions.assertEquals(cuartoDato.getNombre(), "IBM");
    Assertions.assertEquals(cuartoDato.getTipo(), TipoOrganizacion.ENTIDAD_PRESTADORA);
    Assertions.assertEquals(cuartoDato.getEmail(), null);
    var quintoDato = organizaciones.get(4);
    Assertions.assertEquals(quintoDato.getNombre(), "Procuradoria");
    Assertions.assertEquals(quintoDato.getTipo(), TipoOrganizacion.ORGANISMO_CONTROL);
    Assertions.assertEquals(quintoDato.getEmail(), null);
  }

  @Test
  public void testReadCSVFailWithNotFoundException() {
    String csvFile = "CSVOrganismos1.csv";
    Assertions.assertThrows(CsvNotFoundException.class, () -> {reader.getEntidadesFromCSV(pathroot + csvFile);});
  }

  @Test
  public void testReadCSVSoloConTitulos() {
    String csvFile = "CSVOrganismosSoloConTitulos.csv";
    var csvData = reader.getEntidadesFromCSV(pathroot + csvFile);

    Assertions.assertTrue(csvData.getEntidades().isEmpty());
    Assertions.assertTrue(csvData.getErrors().isEmpty());
  }

  @Test
  public void testReadCSVVacio() {
    String csvFile = "CSVOrganismosSinNada.csv";
    CSVReader reader = new CSVReader();
    var csvData = reader.getEntidadesFromCSV(pathroot + csvFile);
    Assertions.assertTrue(csvData.getEntidades().isEmpty());
    Assertions.assertTrue(csvData.getErrors().isEmpty());
  }

  @Test
  public void testReadCSVInvalido() {
    String csvFile = "CSVOrganismosInvalido.csv";
    CSVReader reader = new CSVReader();
    Exception exception = assertThrows(InvalidCSVHeadersException.class, () -> reader.getEntidadesFromCSV(pathroot + csvFile));
    // Assert
    Assertions.assertEquals("La cantidad de campos en los headers es invalida", exception.getMessage());
  }

  @Test
  public void testReadCSVInvalidoConValoresValidos() {
    String csvFile = "CSVOrganismosInvalidoConValoresValidos.csv";
    CSVReader reader = new CSVReader();
    var csvData = reader.getEntidadesFromCSV(pathroot + csvFile);
    // Errores
    var errores = csvData.getErrors();
    Assertions.assertFalse(errores.isEmpty());
    Assertions.assertEquals(4, errores.toArray().length);
    var primerDato = errores.get(0);
    Assertions.assertEquals(primerDato.getLine(), "Galeno,NO,ezeilan10@gmail.com");
    Assertions.assertEquals(primerDato.getRow(), 1);
    Assertions.assertEquals(primerDato.getMessage(), "El tipo: NO es invalido");
    var segundoDato = errores.get(1);
    Assertions.assertEquals(segundoDato.getLine(), "1,asad,asdada");
    Assertions.assertEquals(segundoDato.getRow(), 2);
    Assertions.assertEquals(segundoDato.getMessage(), "El tipo: asad es invalido");
    var tercerDato = errores.get(2);
    Assertions.assertEquals(tercerDato.getLine(), "asdad,asda,asda");
    Assertions.assertEquals(tercerDato.getRow(), 3);
    Assertions.assertEquals(tercerDato.getMessage(), "El tipo: asda es invalido");
    var cuartoDato = errores.get(3);
    Assertions.assertEquals(cuartoDato.getLine(), "ASDADA,ASDADA,");
    Assertions.assertEquals(cuartoDato.getRow(), 5);
    Assertions.assertEquals(cuartoDato.getMessage(), "Cantidad de columnas (2) en la fila es invalida, se esperan 3");
    // Organizaciones
    var organizaciones = csvData.getEntidades();
    Assertions.assertFalse(organizaciones.isEmpty());
    int expectedFilas = 2;
    int actualFilas = organizaciones.toArray().length;
    Assertions.assertEquals(expectedFilas, actualFilas);
    var primerDatoOrganizaciones = organizaciones.get(0);
    Assertions.assertEquals(primerDatoOrganizaciones.getNombre(), "IBM");
    Assertions.assertEquals(primerDatoOrganizaciones.getTipo(), TipoOrganizacion.ENTIDAD_PRESTADORA);
    Assertions.assertEquals(primerDatoOrganizaciones.getEmail(), "ezeilan10@gmail.com");
    var segundoDatoOrganizaciones = organizaciones.get(1);
    Assertions.assertEquals(segundoDatoOrganizaciones.getNombre(), "Procuradoria");
    Assertions.assertEquals(segundoDatoOrganizaciones.getTipo(), TipoOrganizacion.ORGANISMO_CONTROL);
    Assertions.assertEquals(segundoDatoOrganizaciones.getEmail(), "ezeilan10@gmail.com");
  }
}
