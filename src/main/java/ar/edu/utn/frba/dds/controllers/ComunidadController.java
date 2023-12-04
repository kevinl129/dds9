package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.AgrupacionServicio;
import ar.edu.utn.frba.dds.model.Comunidad;
import ar.edu.utn.frba.dds.model.Incidente;
import ar.edu.utn.frba.dds.model.Interes;
import ar.edu.utn.frba.dds.model.Servicio;
import ar.edu.utn.frba.dds.model.Usuario;
import ar.edu.utn.frba.dds.repos.RepoAgrupacionesServicios;
import ar.edu.utn.frba.dds.repos.RepoComunidades;
import ar.edu.utn.frba.dds.repos.RepoIncidentes;
import ar.edu.utn.frba.dds.repos.RepoIntereses;
import ar.edu.utn.frba.dds.repos.RepoServicios;
import ar.edu.utn.frba.dds.repos.RepoUsuario;
import ar.edu.utn.frba.dds.requests.AbrirIncidente;
import ar.edu.utn.frba.dds.requests.AddComunidad;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import spark.Request;
import spark.Response;

public class ComunidadController {
    RepoComunidades repoComunidades = RepoComunidades.getSingletonInstance();
    RepoIncidentes repoIncidentes = RepoIncidentes.getSingletonInstance();
    RepoIntereses repoIntereses = RepoIntereses.getSingletonInstance();
    RepoUsuario repoUsuario = RepoUsuario.getSingletonInstance();
    RepoAgrupacionesServicios repoAgrupacionServicio = RepoAgrupacionesServicios.getSingletonInstance();
    RepoServicios repoServicios = RepoServicios.getSingletonInstance();

    Gson gson;
    public ComunidadController(Gson gson) {
        this.gson = gson;
    }

    private Boolean isAdmin(Request request, Response response) {
        String authorization = request.headers("Authorization");
        if (authorization == null || authorization.trim().equals("")) {
            return false;
        }
        String[] splittedUserData = authorization.split(" ");
        if (splittedUserData.length < 2) {
            return false;
        }
        String userData = splittedUserData[1];
        try {
            byte[] decodedBase64 = Base64.getDecoder().decode(userData);
            userData = new String(decodedBase64);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        splittedUserData = userData.split(":");
        if (splittedUserData.length != 2) {
            return false;
        }
        Usuario usuario = repoUsuario.get(splittedUserData[0], splittedUserData[1]);
        if (usuario != null) {
            return usuario.getAdmin();
        }
        return false;
    }

    public List<Comunidad> getAll(Request request, Response response) {
        Boolean isAdmin = isAdmin(request, response);
        if (!isAdmin) {
            response.status(401);
            return null;
        }
        List<Comunidad> comunidades = repoComunidades.getAll();
        return comunidades;
    }

    public Comunidad get(Request request, Response response) {
        Boolean isAdmin = isAdmin(request, response);
        if (!isAdmin) {
            response.status(401);
            return null;
        }
        Long id = Long.parseLong(request.params("id"));
        Comunidad comunidad = repoComunidades.get(id);
        if (comunidad == null) response.status(400);
        return comunidad;
    }

    public String delete(Request request, Response response) {
        Boolean isAdmin = isAdmin(request, response);
        if (!isAdmin) {
            response.status(401);
            return null;
        }
        Long id = Long.parseLong(request.params("id"));
        Comunidad comunidad = repoComunidades.get(id);
        if (comunidad == null) {
            response.status(400);
            return "Id de comunidad desconocida";
        }
        repoComunidades.remove(comunidad);
        return "Deleted!";
    }

    public String add(Request request, Response response) {
        Boolean isAdmin = isAdmin(request, response);
        if (!isAdmin) {
            response.status(401);
            return null;
        }
        AddComunidad newComunidad = gson.fromJson(request.body(), AddComunidad.class);
        if (newComunidad.getNombre() == null) {
            response.status(400);
            return "No se ha enviado un nombre";
        }
        List<Usuario> miembros = newComunidad.getMiembros() != null ? repoUsuario.getAll(newComunidad.getMiembros()) : new ArrayList<>();
        List<Usuario> administradores = newComunidad.getAdministradores() != null ? repoUsuario.getAll(newComunidad.getAdministradores()) : new ArrayList<>();
        List<Incidente> incidentes = newComunidad.getIncidentes() != null ? repoIncidentes.getAll(newComunidad.getIncidentes()) : new ArrayList<>();
        List<Interes> intereses = newComunidad.getIntereses() != null ? repoIntereses.getAll(newComunidad.getIntereses()) : new ArrayList<>();
        List<AgrupacionServicio> agrupacionServicios = newComunidad.getAgrupacionServicios() != null ?  repoAgrupacionServicio.getAll(newComunidad.getAgrupacionServicios()) : new ArrayList<>();

        Comunidad comunidad = new Comunidad(
            newComunidad.getNombre(),
            miembros,
            administradores,
            agrupacionServicios,
            incidentes,
            intereses
        );
        repoComunidades.add(comunidad);
        response.status(201);
        return "Created!";
    }

    public String update(Request request, Response response) {
        Boolean isAdmin = isAdmin(request, response);
        if (!isAdmin) {
            response.status(401);
            return null;
        }
        Long id = Long.parseLong(request.params("id"));
        Comunidad comunidad = repoComunidades.get(id);
        if (comunidad == null) {
            response.status(400);
            return "No se conoce comunidad con ese Id";
        }
        AddComunidad newComunidad = gson.fromJson(request.body(), AddComunidad.class);
        if (newComunidad.getNombre() != null && !newComunidad.getNombre().trim().equals("")) {
            comunidad.setNombre(newComunidad.getNombre());
        }
        if (newComunidad.getMiembros() != null) {
            List<Usuario> miembros = repoUsuario.getAll(newComunidad.getMiembros());
            comunidad.setMiembros(miembros);
        }
        if (newComunidad.getAdministradores() != null) {
            List<Usuario> administradores = repoUsuario.getAll(newComunidad.getAdministradores());
            comunidad.setAdministradores(administradores);
        }
        if (newComunidad.getIncidentes() != null) {
            List<Incidente> incidentes = repoIncidentes.getAll(newComunidad.getIncidentes());
            comunidad.setIncidentes(incidentes);
        }
        if (newComunidad.getIntereses() != null) {
            List<Interes> intereses = repoIntereses.getAll(newComunidad.getIntereses());
            comunidad.setIntereses(intereses);
        }
        if (newComunidad.getAgrupacionServicios() != null) {
            List<AgrupacionServicio> agrupacionServicios = repoAgrupacionServicio.getAll(newComunidad.getAgrupacionServicios());
            comunidad.setAgrupacionServicios(agrupacionServicios);
        }

        repoComunidades.add(comunidad);
        response.status(200);
        return "Updated!";
    }

    public String incidente(Request request, Response response) {
        Boolean isAdmin = isAdmin(request, response);
        if (!isAdmin) {
            response.status(401);
            return null;
        }
        Long id = Long.parseLong(request.params("id"));
        Comunidad comunidad = repoComunidades.get(id);
        if (comunidad == null) {
            response.status(400);
            return "No se conoce comunidad con ese Id";
        }
        Servicio servicio = repoServicios.get(Long.parseLong(request.params("servicio")));
        if (servicio != null) {
            AbrirIncidente abrirIncidente = gson.fromJson(request.body(), AbrirIncidente.class);
            Incidente incidente = new Incidente(servicio, abrirIncidente.getObservacion());
            repoIncidentes.abrir(incidente);
            comunidad.abrirIncidente(incidente);
            response.status(200);
            return "Abierto el incidente!";
        } else {
            response.status(400);
            return null;
        }
    }
}
