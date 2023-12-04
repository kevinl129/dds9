package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.NotificactionTemplate;
import ar.edu.utn.frba.dds.location.Localizacion;
import ar.edu.utn.frba.dds.model.Entidad;
import ar.edu.utn.frba.dds.model.Establecimiento;
import ar.edu.utn.frba.dds.model.Incidente;
import ar.edu.utn.frba.dds.model.Servicio;
import ar.edu.utn.frba.dds.model.Usuario;
import ar.edu.utn.frba.dds.repos.RepoEntidades;
import ar.edu.utn.frba.dds.repos.RepoIncidentes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController extends GeneralController {
  RepoEntidades repoEntidades = RepoEntidades.getSingletonInstance();
  RepoIncidentes repoIncidentes = RepoIncidentes.getSingletonInstance();

  public ModelAndView home(Request request, Response response) {
      if (checkUserIsLogged(request, response)) return new ModelAndView(null, "login.html.hbs");
      addSelectService(request);
      Usuario loggedUser = (Usuario) modelo.get("loggedUser");
      loggedUser.setUbicacion(Localizacion.randomLocalizacion());
      List<Incidente> incidentes = repoIncidentes.getIncidentesInteresantesParaElUsuario(loggedUser);
      List<NotificactionTemplate> notificaciones = new ArrayList<>();
      incidentes.forEach(i -> {
        if (loggedUser.estaCercaDe(i)) {
          Entidad entidad = RepoEntidades.getSingletonInstance().getEntidadQueTieneElServicio(i.getServicio());
          List<Establecimiento> establecimientos = entidad
              .getEstablecimientos().stream().filter(est -> est.getServicios().stream().anyMatch(x-> x.getId() == i.getServicio().getId())).toList();
          notificaciones.add(new NotificactionTemplate("Cerca tuyo se encuentra un incidente!", "Hay un incidente en  " + entidad.getNombre() + ", en el establecimiento " + establecimientos.get(0).getNombre() + ", en su servicio " + i.getServicio().getNombre() + ", el cual se encuentra cerca tuyo."));
        }
      });
      modelo.put("notificaciones", notificaciones);
      modelo.put("incidentes", incidentes);
      return new ModelAndView(modelo,"home.html.hbs");
    }
}
