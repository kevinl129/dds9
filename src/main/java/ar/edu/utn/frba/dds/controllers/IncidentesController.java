package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Entidad;
import ar.edu.utn.frba.dds.model.Establecimiento;
import ar.edu.utn.frba.dds.model.Incidente;
import ar.edu.utn.frba.dds.model.Servicio;
import ar.edu.utn.frba.dds.repos.RepoEntidades;
import ar.edu.utn.frba.dds.repos.RepoIncidentes;
import ar.edu.utn.frba.dds.repos.RepoServicios;
import ar.edu.utn.frba.dds.requests.AddAgrupacionServicios;
import ar.edu.utn.frba.dds.requests.OpenIncidente;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import javax.swing.text.View;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IncidentesController extends GeneralController {
  private RepoIncidentes repoIncidentes = RepoIncidentes.getSingletonInstance();
  private RepoEntidades repoEntidades = RepoEntidades.getSingletonInstance();
  private RepoServicios repoServicios = RepoServicios.getSingletonInstance();
  Gson gson;
  public IncidentesController(Gson gson) {
    this.gson = gson;
  }
  public ModelAndView mostrar(Request request, Response response) {
    if (checkUserIsLogged(request, response)) return new ModelAndView(null, "login.html.hbs");
    String nombreServicioCreado = request.queryParams("created");
    if (nombreServicioCreado != null) {
      addNotification("Incidente abierto", "Se ha generado un incidente para el servicio " + nombreServicioCreado + " correctamente!");
    }
    String nombreServicioCerrado = request.queryParams("closed");
    if (nombreServicioCerrado != null) {
      addNotification("Incidente cerrado", "Se ha cerrado el incidente del servicio " + nombreServicioCerrado + " correctamente!");
    }
    String serviceName = request.queryParams("serviceName");
    if (serviceName != null) {
      modelo.put("inputService", serviceName);
    } else {
      serviceName = "";
    }
    String typeChecked = request.queryParams("typeChecked");
    Boolean isOpenChecked = true, isCloseChecked = false, isAllChecked = false;
    if (typeChecked != null) {
      switch (typeChecked) {
        case "all":
          isAllChecked = true;
          break;
        case "close":
          isCloseChecked = true;
          break;
        default:
          isOpenChecked = true;
          break;
      }
    }
    modelo.put("isOpenChecked", isOpenChecked ? "checked" : "");
    modelo.put("isCloseChecked", isCloseChecked ? "checked" : "");
    modelo.put("isAllChecked", isAllChecked ? "checked" : "");
    List<Incidente> incidentes = repoIncidentes.getAll(isCloseChecked, isAllChecked, serviceName);
    modelo.put("incidentes", incidentes);
    return new ModelAndView(modelo, "incidentes.html.hbs");
  }

  public ModelAndView reportar(Request request, Response response) {
    if (checkUserIsLogged(request, response)) return new ModelAndView(null, "login.html.hbs");
    addSelectService(request);
    return new ModelAndView(modelo, "incidentes-reportar.html.hbs");
  }

  public View abrir(Request request, Response response) {
    if (checkUserIsLogged(request, response)) return null;
    OpenIncidente openIncidente = gson.fromJson(request.body(), OpenIncidente.class);
    Servicio servicio = repoServicios.get(openIncidente.getService());
    if (servicio != null) {
      Incidente incidente = new Incidente(servicio, openIncidente.getObservacion());
      repoIncidentes.abrir(incidente);
      try {
        response.redirect("/incidentes?created="+ URLEncoder.encode(servicio.getNombre(), "UTF-8"), 303);
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }
      return null;
    }
    response.status(404);
    return null;
  }

  public View cerrar(Request request, Response response) {
    if (checkUserIsLogged(request, response)) return null;
    Long id = Long.parseLong(request.params(":id"));
    Incidente incidente = repoIncidentes.cerrar(id);
    try {
      response.redirect("/incidentes?serviceName=&typeChecked=close&closed="+URLEncoder.encode(incidente.getServicio().getNombre(), "UTF-8"), 303);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return null;
  }
}
