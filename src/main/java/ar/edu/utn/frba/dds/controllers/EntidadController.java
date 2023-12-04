package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Entidad;
import ar.edu.utn.frba.dds.model.Incidente;
import ar.edu.utn.frba.dds.ranking.ComparadorRanking;
import ar.edu.utn.frba.dds.ranking.MayorCantidadDeIncidentes;
import ar.edu.utn.frba.dds.ranking.MejorPromedioDeCierre;
import ar.edu.utn.frba.dds.repos.RepoEntidades;
import com.google.gson.Gson;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.text.View;
import spark.ModelAndView;
import spark.Request;
import spark.Response;


public class EntidadController extends GeneralController{
  private RepoEntidades repoEntidades = RepoEntidades.getSingletonInstance();

  public ModelAndView mostrar(Request request, Response response) {
    if (checkUserIsLogged(request, response)) return new ModelAndView(null, "login.html.hbs");
    String entidadName = request.queryParams("entidadName");
    List<Entidad> entidades = repoEntidades.get(entidadName);
    modelo.put("entidades", entidades);
    modelo.put("inputEntidad", entidadName == null ? "" : entidadName);
    return new ModelAndView(modelo, "entidades.html.hbs");
  }

  public ModelAndView mostrarRanking(Request request, Response response) {
    if (checkUserIsLogged(request, response)) return new ModelAndView(null, "login.html.hbs");
    if (!checkUserIsAdmin()) {
      response.redirect("/not-found");
      return new ModelAndView(null, "login.html.hbs");
    }
    String ranking = request.queryParams("ranking");
    List<Entidad> entidades = repoEntidades.getAll();
    if (ranking != null) {
      entidades = switch (ranking) {
        case "promCierre" -> new MayorCantidadDeIncidentes(10).generateRanking(entidades);
        case "cantIncidentes" -> new MejorPromedioDeCierre(10).generateRanking(entidades);
        default -> entidades;
      };
    }
    modelo.put("entidades", entidades);
    modelo.put("ranking", ranking);
    return new ModelAndView(modelo, "entidades-ranking.html.hbs");
  }

  public ModelAndView mostrarEntidad(Request request, Response response) {
    if (checkUserIsLogged(request, response)) return new ModelAndView(null, "login.html.hbs");
    Long id = Long.parseLong(request.params("id"));
    Entidad entidad = repoEntidades.get(id);
    modelo.put("entidad", entidad);
    return new ModelAndView(modelo, "entidad.html.hbs");
  }
  }
