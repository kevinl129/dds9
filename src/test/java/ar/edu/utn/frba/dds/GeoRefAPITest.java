package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.georef.GeoRefAPIClient;
import ar.edu.utn.frba.dds.location.CategoriaLocacion;
import ar.edu.utn.frba.dds.location.Departamento;
import ar.edu.utn.frba.dds.location.Municipio;
import ar.edu.utn.frba.dds.location.Provincia;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeoRefAPITest {
  private GeoRefAPIClient geoRefAPIClient;

  @BeforeEach
  void initGeoRefAPIClient() {
    geoRefAPIClient = new GeoRefAPIClient();
  }

  @Test
  public void getProvincias() {
    var response = geoRefAPIClient.GetProvincias();
    var provincias = response.getProvincias();
    Assertions.assertNotNull(provincias);
    Assertions.assertTrue(provincias.length > 0);
    Arrays.stream(provincias).forEach(provincia -> {
      Assertions.assertTrue(provincia.categoria.equals(CategoriaLocacion.PROVINCIA));
      Assertions.assertNotNull(provincia.getNombre());
      var centroide = provincia.getCentroide();
      Assertions.assertNotNull(centroide);
      Assertions.assertNotNull(centroide.getLat());
      Assertions.assertNotNull(centroide.getLon());
    });
  }

  @Test
  public void getMunicipios() {
    var response = geoRefAPIClient.GetMunicipios();
    var municipios = response.getMunicipios();
    Assertions.assertNotNull(municipios);
    Assertions.assertTrue(municipios.length > 0);
    Arrays.stream(municipios).forEach(municipio -> {
      Assertions.assertTrue(municipio.categoria.equals(CategoriaLocacion.MUNICIPIO));
      Assertions.assertNotNull(municipio.getNombre());
      var centroide = municipio.getCentroide();
      Assertions.assertNotNull(centroide);
      Assertions.assertNotNull(centroide.getLat());
      Assertions.assertNotNull(centroide.getLon());
    });
  }

  @Test
  public void getDepartamentos() {
    var response = geoRefAPIClient.GetDepartamentos();
    var departamentos = response.getDepartamentos();
    Assertions.assertNotNull(departamentos);
    Assertions.assertTrue(departamentos.length > 0);
    Arrays.stream(departamentos).forEach(departamento -> {
      Assertions.assertTrue(departamento.categoria.equals(CategoriaLocacion.DEPARTAMENTO));
      Assertions.assertNotNull(departamento.getNombre());
      var centroide = departamento.getCentroide();
      Assertions.assertNotNull(centroide);
      Assertions.assertNotNull(centroide.getLat());
      Assertions.assertNotNull(centroide.getLon());
    });
  }

  @Test
  public void cambiarLocalizacionAProvincia() {
    var nuevaLocalizacion = geoRefAPIClient.CambiarLocacion(new Provincia(),  -32.8551545f, -60.697636f);
    Assertions.assertTrue(nuevaLocalizacion.categoria.equals(CategoriaLocacion.PROVINCIA));
    Assertions.assertNotNull(nuevaLocalizacion.getNombre());
    var centroide = nuevaLocalizacion.getCentroide();
    Assertions.assertNotNull(centroide);
    Assertions.assertNotNull(centroide.getLat());
    Assertions.assertNotNull(centroide.getLon());
  }

  @Test
  public void cambiarLocalizacionADepartamento() {
    var nuevaLocalizacion = geoRefAPIClient.CambiarLocacion(new Departamento(),  -32.8551545f, -60.697636f);
    Assertions.assertTrue(nuevaLocalizacion.categoria.equals(CategoriaLocacion.DEPARTAMENTO));
    Assertions.assertNotNull(nuevaLocalizacion.getNombre());
    var centroide = nuevaLocalizacion.getCentroide();
    Assertions.assertNotNull(centroide);
    Assertions.assertNotNull(centroide.getLat());
    Assertions.assertNotNull(centroide.getLon());
  }

  @Test
  public void cambiarLocalizacionAMunicipio() {
    var nuevaLocalizacion = geoRefAPIClient.CambiarLocacion(new Municipio(),  -32.8551545f, -60.697636f);
    Assertions.assertTrue(nuevaLocalizacion.categoria.equals(CategoriaLocacion.MUNICIPIO));
    Assertions.assertNotNull(nuevaLocalizacion.getNombre());
    var centroide = nuevaLocalizacion.getCentroide();
    Assertions.assertNotNull(centroide);
    Assertions.assertNotNull(centroide.getLat());
    Assertions.assertNotNull(centroide.getLon());
  }

  @Test
  public void DeMunicipPasoAProvincia() {
    var response = geoRefAPIClient.GetMunicipios();
    var municipios = response.getMunicipios();
    var centroideViejo = municipios[0].getCentroide();
    var nuevaLocalizacion = geoRefAPIClient.CambiarLocacion(
        new Provincia(),
        centroideViejo.getLat(),
        centroideViejo.getLon());

    Assertions.assertTrue(nuevaLocalizacion.categoria.equals(CategoriaLocacion.PROVINCIA));
    Assertions.assertNotNull(nuevaLocalizacion.getNombre());
    Assertions.assertNotEquals(municipios[0].getNombre(), nuevaLocalizacion.getNombre());
    var centroideNuevo = nuevaLocalizacion.getCentroide();
    Assertions.assertNotNull(centroideNuevo);
    Assertions.assertNotNull(centroideNuevo.getLat());
    Assertions.assertEquals(centroideViejo.getLat(), centroideNuevo.getLat());
    Assertions.assertNotNull(centroideNuevo.getLon());
    Assertions.assertEquals(centroideViejo.getLon(), centroideNuevo.getLon());
  }
}