package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.AgrupacionServicio;
import ar.edu.utn.frba.dds.model.Servicio;
import ar.edu.utn.frba.dds.repos.RepoAgrupacionesServicios;
import ar.edu.utn.frba.dds.repos.RepoServicios;
import ar.edu.utn.frba.dds.requests.AddAgrupacionServicios;
import com.google.gson.Gson;
import java.util.List;
import spark.Request;
import spark.Response;

public class AgrupacionServiciosController {
    RepoAgrupacionesServicios repoAgrupacionesServicios = RepoAgrupacionesServicios.getSingletonInstance();
    RepoServicios repoServicios = RepoServicios.getSingletonInstance();

    Gson gson;
    public AgrupacionServiciosController(Gson gson) {
        this.gson = gson;
    }

    public List<AgrupacionServicio> getAll(Request request, Response response) {
        List<AgrupacionServicio> agrupacionServicios = repoAgrupacionesServicios.getAll();
        return agrupacionServicios;
    }

    public String add(Request request, Response response) {
        AddAgrupacionServicios newAgrupacionServicio = gson.fromJson(request.body(), AddAgrupacionServicios.class);
        List<Servicio> servicios = repoServicios.getAll(newAgrupacionServicio.getServicios());
        AgrupacionServicio agrupacionServicio = new AgrupacionServicio(newAgrupacionServicio.getNombre(), servicios);
        repoAgrupacionesServicios.add(agrupacionServicio);
        response.status(201);
        return "Created!";
    }
}
