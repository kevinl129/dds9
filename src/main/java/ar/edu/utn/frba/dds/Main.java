package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.controllers.*;
import ar.edu.utn.frba.dds.spark.template.handlebars.HandlebarsTemplateEngine;
import com.google.gson.Gson;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.checkerframework.checker.units.qual.C;
import spark.Spark;

public class Main implements WithSimplePersistenceUnit {
    public static void main(String[] args) {
        Bootstrap.main(args);
        new Main().start();
    }

    public void start() {
        System.out.println("Iniciando servidor!");

        Spark.port(80);
        Spark.staticFileLocation("/public");
        Gson gson = new Gson();
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
        LoginController loginController = new LoginController();
        IncidentesController incidentesController = new IncidentesController(gson);
        EntidadController entidadesController = new EntidadController();
        EstablecimientosController establecimientosController = new EstablecimientosController();
        ExceptionController generalController = new ExceptionController();
        UsuariosController usuariosController = new UsuariosController();
        InteresesController interesesController = new InteresesController();
        RangosController rangosController = new RangosController();
        HomeController homeController = new HomeController();
        AgrupacionServiciosController agrupacionServiciosController = new AgrupacionServiciosController(gson);
        ComunidadController comunidadController = new ComunidadController(gson);

        //----creación de usuario
        Spark.get("/", homeController::home, engine);
        Spark.get("/usuarios/crear", usuariosController::verCrearUsuario,engine);
        Spark.post("/usuarios", usuariosController::crear);
        Spark.get("/usuarios", usuariosController::mostrarUsuarios,engine);
        Spark.get("/usuarios/:id", usuariosController::mostrarUsuario, engine);
        Spark.get("/usuarios/:id/notificaciones", usuariosController::notificacionesUsuario, engine);
        Spark.post("/usuarios/:id/eliminar", usuariosController::eliminar);
        Spark.post("/usuarios/:id", usuariosController::editarUsuario);
        Spark.get("/incidentes", incidentesController::mostrar, engine);
        Spark.post("/incidentes", incidentesController::abrir);
        Spark.post("/incidentes/:id", incidentesController::cerrar);
        Spark.get("/establecimientos/:id", establecimientosController::mostrar, engine);
        Spark.get("/incidentes/reportar", incidentesController::reportar, engine);
        Spark.post("/intereses", interesesController::agregar);
        Spark.post("/intereses/:id", interesesController::eliminar);
        Spark.post("/rangos", rangosController::agregar);
        Spark.post("/rangos/:id", rangosController::eliminar);
        Spark.get("/login", loginController::login, engine);
        Spark.post("/logout", loginController::logout);
        Spark.get("/entidades", entidadesController::mostrar, engine);
        Spark.get("/entidades/ranking", entidadesController::mostrarRanking, engine);
        Spark.get("/entidades/:id", entidadesController::mostrarEntidad, engine);
        Spark.get("/error", generalController::internalServerError, engine);
        Spark.get("/not-found", generalController::notFound, engine);

        Spark.get("/servicios/agrupacion", agrupacionServiciosController::getAll, gson::toJson);
        Spark.post("/servicios/agrupacion", agrupacionServiciosController::add, gson::toJson);
        // Comunidades
        // Listado de comunidades
        Spark.get("/comunidades", comunidadController::getAll, gson::toJson);
        // Detalle de comunidades
        Spark.get("/comunidades/:id", comunidadController::get, gson::toJson);
        // Creación de comunidades
        Spark.post("/comunidades", comunidadController::add, gson::toJson);
        // Edición de comunidades
        Spark.put("/comunidades/:id", comunidadController::update, gson::toJson);
        // Añadir incidente
        Spark.post("/comunidades/:id/incidentes/:servicio", comunidadController::incidente, gson::toJson);
        // Eliminación de comunidades
        Spark.delete("/comunidades/:id", comunidadController::delete, gson::toJson);


        // Using Route
        Spark.exception(Exception.class, generalController::exceptionHandler);
        Spark.before((request, response) -> {
            entityManager().clear();
        });
        Spark.after(((request, response) -> {
            entityManager().close();
            GeneralController.modelo.clear();
        }));
    }
}
