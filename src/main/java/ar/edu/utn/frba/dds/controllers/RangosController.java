package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Interes;
import ar.edu.utn.frba.dds.model.Rango;
import ar.edu.utn.frba.dds.model.Servicio;
import ar.edu.utn.frba.dds.model.Usuario;
import ar.edu.utn.frba.dds.repos.RepoIntereses;
import ar.edu.utn.frba.dds.repos.RepoRangos;
import ar.edu.utn.frba.dds.repos.RepoServicios;
import ar.edu.utn.frba.dds.repos.RepoUsuario;
import java.sql.Date;
import java.util.Calendar;
import javax.swing.text.View;
import spark.Request;
import spark.Response;

public class RangosController extends GeneralController {
    RepoUsuario repoUsuario = RepoUsuario.getSingletonInstance();
    RepoRangos repoRangos = RepoRangos.getSingletonInstance();

    public View agregar(Request request, Response response) {
        String userId = request.queryParams("userId");
        Usuario usuario = repoUsuario.get(Long.parseLong(userId));
        if (usuario == null) {
            response.status(404);
            return null;
        }
        String startDate = request.queryParams("startdate");
        String endDate = request.queryParams("enddate");
        Rango rango = new Rango(startDate, endDate);
        if (!rango.esRangoValido()) {
            response.redirect("/usuarios/" + userId + "/notificaciones?errorRango=1");
            response.status(400);
            return null;
        }
        usuario.addRangoNotificaciones(rango);
        repoUsuario.add(usuario, rango);
        response.redirect("/usuarios/" + userId + "/notificaciones");
        return null;
    }

    public View eliminar(Request request, Response response) {
        String rangoId = request.params("id");
        String userId = request.queryParams("userId");
        Usuario usuario = repoUsuario.get(Long.parseLong(userId));
        Rango rango = repoRangos.get(Long.parseLong(rangoId));
        if (rango == null || usuario == null) {
            response.status(404);
            return null;
        }
        usuario.removeRangoNotificaciones(rango);
        repoUsuario.add(usuario);
        repoRangos.eliminate(Long.parseLong(rangoId));
        response.redirect("/usuarios/" + userId + "/notificaciones");
        return null;
    }
}
