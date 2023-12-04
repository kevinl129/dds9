package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Establecimiento;
import ar.edu.utn.frba.dds.repos.RepoEstablecimientos;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EstablecimientosController extends GeneralController {
  private RepoEstablecimientos repoEstablecimientos = RepoEstablecimientos.getSingletonInstance();

  public ModelAndView mostrar(Request request, Response response) {
    if (checkUserIsLogged(request, response)) return new ModelAndView(null, "login.html.hbs");
    Long id = Long.parseLong(request.params("id"));
    Establecimiento establecimiento = repoEstablecimientos.get(id);
    if (establecimiento == null) {
      modelo.put("entity", "establecimiento");
      return new ModelAndView(modelo, "no-entity.html.hbs");
    }
    modelo.put("establecimiento", establecimiento);
    return new ModelAndView(modelo, "establecimiento.html.hbs");
  }
}
