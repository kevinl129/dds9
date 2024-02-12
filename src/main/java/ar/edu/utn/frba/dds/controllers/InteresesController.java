package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Interes;
import ar.edu.utn.frba.dds.model.Servicio;
import ar.edu.utn.frba.dds.model.Usuario;
import ar.edu.utn.frba.dds.repos.RepoIntereses;
import ar.edu.utn.frba.dds.repos.RepoServicios;
import ar.edu.utn.frba.dds.repos.RepoUsuario;
import javax.swing.text.View;
import spark.Request;
import spark.Response;

public class InteresesController extends GeneralController {
    RepoServicios repoServicios = RepoServicios.getSingletonInstance();
    RepoUsuario repoUsuario = RepoUsuario.getSingletonInstance();
    RepoIntereses repoIntereses = RepoIntereses.getSingletonInstance();

    public View agregar(Request request, Response response) {
        String userId = request.queryParams("userId");
        String serviceId = request.queryParams("service");
        Usuario usuario = repoUsuario.get(Long.parseLong(userId));
        Servicio servicio = repoServicios.get(Long.parseLong(serviceId));
        if (usuario == null || servicio == null) {
            response.status(404);
            return null;
        }
        Interes interes = new Interes(servicio);
        usuario.agregarInteres(interes);
        repoUsuario.add(usuario, interes);
        response.redirect("/");
        return null;
    }

    public View eliminar(Request request, Response response) {
        String interesId = request.params("id");
        String userId = request.queryParams("userId");
        Usuario usuario = repoUsuario.get(Long.parseLong(userId));
        Interes interes = repoIntereses.get(Long.parseLong(interesId));
        if (interes == null || usuario == null) {
            response.status(404);
            return null;
        }
        usuario.removeInteres(interes);
        repoUsuario.add(usuario);
        repoIntereses.eliminate(Long.parseLong(interesId));
        response.redirect("/");
        return null;
    }
}
