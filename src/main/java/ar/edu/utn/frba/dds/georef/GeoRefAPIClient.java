package ar.edu.utn.frba.dds.georef;

import ar.edu.utn.frba.dds.georef.errors.InvalidRequestException;
import ar.edu.utn.frba.dds.georef.errors.InvalidUrlException;
import ar.edu.utn.frba.dds.georef.errors.RequestFailException;
import ar.edu.utn.frba.dds.georef.response.GetDepartamentosResponse;
import ar.edu.utn.frba.dds.georef.response.GetMunicipiosResponse;
import ar.edu.utn.frba.dds.georef.response.GetProvinciasResponse;
import ar.edu.utn.frba.dds.georef.response.GetUbicacionResponse;
import ar.edu.utn.frba.dds.location.CategoriaLocacion;
import ar.edu.utn.frba.dds.location.Centroide;
import ar.edu.utn.frba.dds.location.Localizacion;
import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeoRefAPIClient {
  static final String BASE_URL = "https://apis.datos.gob.ar/georef/api/";
  HttpClient client = HttpClient.newHttpClient();
  Gson gson = new Gson();

  public <T> T MakeGetRequestToApi(String path, T responseType) {
    HttpRequest request;
    try {
      request = HttpRequest.newBuilder()
          .uri(new URI(BASE_URL + path))
          .GET()
          .build();
    } catch (Exception e) {
      throw new InvalidUrlException("Hay caracteres invalidos en la url", e);
    }

    HttpResponse<String> call;
    try {
      call = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (Exception e) {
      throw new RequestFailException("El request fallo inesperadamente", e);
    }

    if (call.statusCode() != 200) {
      throw new InvalidRequestException("El request fue invalido: " + call.body());
    }

    return (T) gson.fromJson(call.body(), responseType.getClass());
  }

  public GetMunicipiosResponse GetMunicipios() {
    return MakeGetRequestToApi("municipios", new GetMunicipiosResponse());
  }

  public GetProvinciasResponse GetProvincias() {
    return MakeGetRequestToApi("provincias", new GetProvinciasResponse());
  }

  public GetDepartamentosResponse GetDepartamentos() {
    return MakeGetRequestToApi("departamentos", new GetDepartamentosResponse());
  }

  public Localizacion CambiarLocacion(Localizacion locacionNueva, float lat, float lon) {
    var response = MakeGetRequestToApi("ubicacion?lon="+lon+"&lat="+lat, new GetUbicacionResponse());
    var ubicacion = response.getUbicacion();
    Localizacion nuevaLocalizacion = locacionNueva.getLocalizacion(ubicacion);
    nuevaLocalizacion.setCentroide(new Centroide(lat, lon));
    return nuevaLocalizacion;
  }
}
