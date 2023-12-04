package ar.edu.utn.frba.dds.db;

import ar.edu.utn.frba.dds.CSVManagement.TipoOrganizacion;
import ar.edu.utn.frba.dds.model.Organizacion;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class DbTest implements SimplePersistenceTest {
  @Test
  void contextUp() {
    Assertions.assertNotNull(entityManager());
  }

  @Test
  @Disabled
  void testContext() {
    Organizacion organizacion = new Organizacion();
    organizacion.setTipo(TipoOrganizacion.ORGANISMO_CONTROL);
    organizacion.setEmail("email");
    organizacion.setNombre("nombre");

    entityManager().persist(organizacion);
    Assertions.assertEquals((Long) 1L, organizacion.getId());

    Organizacion organizacionBuscada = entityManager().find(Organizacion.class, 1L);

    Assertions.assertNotNull(organizacionBuscada);
    Assertions.assertEquals(organizacion.getId(), organizacionBuscada.getId());
  }

  @Test
  @Disabled
  void testComumidad() {
    Organizacion organizacion = new Organizacion();
    organizacion.setTipo(TipoOrganizacion.ORGANISMO_CONTROL);
    organizacion.setEmail("email");
    organizacion.setNombre("nombre");

    entityManager().persist(organizacion);
    Assertions.assertEquals((Long) 1L, organizacion.getId());

    Organizacion organizacionBuscada = entityManager().find(Organizacion.class, 1L);

    Assertions.assertNotNull(organizacionBuscada);
    Assertions.assertEquals(organizacion.getId(), organizacionBuscada.getId());
  }

  @Test
  void testOrganizacion() {
    Organizacion organizacion = new Organizacion();
    organizacion.setTipo(TipoOrganizacion.ORGANISMO_CONTROL);
    organizacion.setEmail("email");
    organizacion.setNombre("nombre");

    entityManager().persist(organizacion);
    Assertions.assertEquals((Long) 1L, organizacion.getId());

    Organizacion organizacionBuscada = entityManager().find(Organizacion.class, 1L);

    Assertions.assertNotNull(organizacionBuscada);
    Assertions.assertEquals(organizacion.getId(), organizacionBuscada.getId());
  }
}
