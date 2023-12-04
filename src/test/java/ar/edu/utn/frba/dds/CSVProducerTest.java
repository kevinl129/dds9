package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.CSVManagement.CSVProducer;
import ar.edu.utn.frba.dds.CSVManagement.CSVReader;
import ar.edu.utn.frba.dds.CSVManagement.TipoOrganizacion;
import ar.edu.utn.frba.dds.model.Entidad;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CSVProducerTest {
  private CSVProducer csvProducer;
  private CSVReader reader;

  private final String pathroot="/src/main/java/ar/edu/utn/frba/dds/CSVManagement/CSVs/csvTest.csv";
  @BeforeEach
  void initCSVProducer() {
    csvProducer = new CSVProducer();
    reader = new CSVReader();
  }

  @Test
  public void testCreateAndReadCSVOK() {
    var entidades = Arrays.asList(
        new Entidad("Hola", TipoOrganizacion.ENTIDAD_PRESTADORA, "ezeilan10@gmail.com"),
        new Entidad("chau", TipoOrganizacion.ORGANISMO_CONTROL, null));
    csvProducer.WriteCsv(entidades, "csvTest");
    var leido = reader.getEntidadesFromCSV(pathroot);
    Assertions.assertTrue(leido.getErrors().isEmpty());
    var entidadesLeidas = leido.getEntidades();
    Assertions.assertFalse(entidadesLeidas.isEmpty());
    Assertions.assertEquals(entidades.get(0).getEmail(), entidadesLeidas.get(0).getEmail());
    Assertions.assertEquals(entidades.get(0).getNombre(), entidadesLeidas.get(0).getNombre());
    Assertions.assertEquals(entidades.get(0).getTipo(), entidadesLeidas.get(0).getTipo());
    Assertions.assertEquals(entidades.get(1).getEmail(), entidadesLeidas.get(1).getEmail());
    Assertions.assertEquals(entidades.get(1).getNombre(), entidadesLeidas.get(1).getNombre());
    Assertions.assertEquals(entidades.get(1).getTipo(), entidadesLeidas.get(1).getTipo());
  }
}
