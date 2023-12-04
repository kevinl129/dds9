package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Entidad;
import ar.edu.utn.frba.dds.model.Establecimiento;
import ar.edu.utn.frba.dds.model.Usuario;
import ar.edu.utn.frba.dds.repos.RepoEntidades;
import ar.edu.utn.frba.dds.repos.RepoUsuario;
import java.util.List;
import java.util.stream.Collectors;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class GeneralController {
    public boolean isUserLoggedIn(Request request) {
        return request.session().attribute("currentUser") != null;
    }
    public boolean checkUserIsLogged(Request request, Response response) {
        if (!isUserLoggedIn(request)) {
            response.redirect("/login?path="+request.pathInfo());
            return true;
        }
        modelo.put("anio", LocalDate.now().getYear());
        Long id = request.session().attribute("currentUser");
        Usuario usuario = RepoUsuario.getSingletonInstance().get(id);
        modelo.put("loggedUser", usuario);
        return false;
    }

    public boolean checkUserIsAdmin() {
        Usuario usuario = (Usuario) modelo.get("loggedUser");
        return usuario.getAdmin();
    }

    public boolean checkLoggedUserHasId(Long id) {
        Usuario usuario = (Usuario) modelo.get("loggedUser");
        return usuario.getId() == id;
    }

    public void addNotification(String titulo, String body) {
        modelo.put("notificacion", body);
        modelo.put("notificacion_titulo", titulo);
    }

    public void addSelectService(Request request) {
        List<Entidad> entidades = RepoEntidades.getSingletonInstance().getAll();
        modelo.put("entidades", entidades);
        String entidadId = request.queryParams("entidad");
        if (entidadId != null) {
            Entidad entidad = RepoEntidades.getSingletonInstance().get(Long.parseLong(entidadId));
            modelo.put("entidad", entidad);
            String establecimientoId = request.queryParams("establecimiento");
            if (establecimientoId != null) {
                List<Establecimiento> establecimientos = entidad.getEstablecimientos().stream().filter(x -> x.getId() == Long.parseLong(establecimientoId)).collect(Collectors.toList());
                modelo.put("establecimiento", establecimientos.get(0));
            }
        }
    }
    public static Map<String, Object> modelo = new HashMap<>();
}
