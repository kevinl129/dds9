package ar.edu.utn.frba.dds.db;

import ar.edu.utn.frba.dds.CSVManagement.TipoOrganizacion;
import ar.edu.utn.frba.dds.location.CategoriaLocacion;
import ar.edu.utn.frba.dds.location.Centroide;
import ar.edu.utn.frba.dds.location.Departamento;
import ar.edu.utn.frba.dds.location.Localizacion;
import ar.edu.utn.frba.dds.location.Municipio;
import ar.edu.utn.frba.dds.location.Provincia;
import ar.edu.utn.frba.dds.model.Organizacion;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocalizacionTest implements SimplePersistenceTest {
  @Test
  void contextUp() {
    Assertions.assertNotNull(entityManager());
  }

  @Test
  void testContext() {
    Provincia provincia = new Provincia();
    provincia.setId("20");
    provincia.setCentroide(new Centroide(10, 20));
    provincia.setNombre_largo("Tiene Nombre largo");
    provincia.setNombre("nombre");

    Municipio municipio = new Municipio();
    municipio.setId("10");
    municipio.setNombre_completo("Municio");
    municipio.setNombre("Hola");
    municipio.setCentroide(new Centroide(10, 10));
    municipio.setProvincia(provincia);

    Departamento departamento = new Departamento();
    departamento.setCentroide(new Centroide(20, 30));
    departamento.setNombre_completo("Nombre completo");
    departamento.setProvincia(provincia);
    departamento.setNombre("nombre2");
    departamento.setId("id");

    entityManager().persist(provincia);
    entityManager().persist(municipio);
    entityManager().persist(departamento);

    Provincia provinciaBuscada = entityManager().find(Provincia.class, 1L);
    Municipio municipioBuscado = entityManager().find(Municipio.class, 2L);
    Departamento departamentoBuscado = entityManager().find(Departamento.class, 3L);

    Assertions.assertEquals(municipioBuscado.getProvincia(), departamentoBuscado.getProvincia());
    Assertions.assertEquals(provinciaBuscada.categoria, provincia.categoria);
    Assertions.assertEquals(CategoriaLocacion.MUNICIPIO, municipioBuscado.categoria);
    Assertions.assertEquals(CategoriaLocacion.DEPARTAMENTO, departamentoBuscado.categoria);
    Assertions.assertEquals(CategoriaLocacion.PROVINCIA, provinciaBuscada.categoria);
  }
}
