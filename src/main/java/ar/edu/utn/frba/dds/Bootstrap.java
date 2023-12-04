package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.CSVManagement.TipoOrganizacion;
import ar.edu.utn.frba.dds.location.Centroide;
import ar.edu.utn.frba.dds.location.Provincia;
import ar.edu.utn.frba.dds.model.Entidad;
import ar.edu.utn.frba.dds.model.Establecimiento;
import ar.edu.utn.frba.dds.model.Incidente;
import ar.edu.utn.frba.dds.model.Rango;
import ar.edu.utn.frba.dds.model.Servicio;
import ar.edu.utn.frba.dds.model.TipoEstablecimiento;
import ar.edu.utn.frba.dds.model.Usuario;
import ar.edu.utn.frba.dds.notification.EnvioNotificacion;
import ar.edu.utn.frba.dds.notification.EnvioNotificacionMail;
import ar.edu.utn.frba.dds.notification.EnvioNotificacionWpp;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Bootstrap implements WithSimplePersistenceUnit {
  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void addSomeBasicEntities() {
    // --- Provincia ---
    Provincia provincia = new Provincia();
    provincia.setId("06");
    provincia.setCentroide(new Centroide(-34.6144934119689f,-58.4458563545429f));
    provincia.setNombre("BSAS");
    provincia.setNombre_largo("Buenos Aires");
    entityManager().persist(provincia);
    // --- Servicio 1 ---
    Servicio servicio1 = new Servicio();
    servicio1.setLocalizacion(provincia);
    servicio1.setNombre("Baño");
    entityManager().persist(servicio1);
    // --- Servicio 2 ---
    Servicio servicio2 = new Servicio();
    servicio2.setLocalizacion(provincia);
    servicio2.setNombre("Escalera");
    entityManager().persist(servicio2);
    // --- Servicio 3 ---
    Servicio servicio3 = new Servicio();
    servicio3.setLocalizacion(provincia);
    servicio3.setNombre("Ascensor");
    entityManager().persist(servicio3);
    // --- Servicio 4 ---
    Servicio servicio4 = new Servicio();
    servicio4.setLocalizacion(provincia);
    servicio4.setNombre("Baño Hombres");
    entityManager().persist(servicio4);
    // --- Establecimiento ---
    Establecimiento establecimiento = new Establecimiento();
    establecimiento.setTipo(TipoEstablecimiento.ESTACION);
    establecimiento.setNombre("Medrano");
    establecimiento.setServicios(Arrays.asList(servicio1, servicio2));
    establecimiento.setUbicacion(provincia);
    entityManager().persist(establecimiento);

    // --- Establecimiento 2 ---
    Establecimiento establecimientoAux = new Establecimiento();
    establecimientoAux.setTipo(TipoEstablecimiento.ESTACION);
    establecimientoAux.setNombre("Medrano2");
    establecimientoAux.setServicios(Arrays.asList(servicio3, servicio4));
    establecimientoAux.setUbicacion(provincia);
    entityManager().persist(establecimientoAux);
    // --- Entidad ---
    Entidad entidad = new Entidad(
        "Linea B",
        TipoOrganizacion.ENTIDAD_PRESTADORA,
        "elitvak@frba.utn.edu.ar",
        provincia,
        Arrays.asList(establecimiento));
    entidad.setIncidentes(new ArrayList<Incidente>());
    entityManager().persist(entidad);
    //Entidad 2
    Entidad entidadAux = new Entidad(
        "Linea A",
        TipoOrganizacion.ENTIDAD_PRESTADORA,
        "kevinl12344@frba.utn.edu.ar",
        provincia,
        Arrays.asList(establecimientoAux));
    entidad.setIncidentes(new ArrayList<Incidente>());
    entityManager().persist(entidadAux);
  }

  public void addUsers() {
    Usuario admin = new Usuario("admin", "admin", "admin", "telefono", "admin", true);
    EnvioNotificacion envioNotificacion = new EnvioNotificacionWpp();
    Rango rango1 = new Rango(Calendar.getInstance(), Calendar.getInstance());
    Rango rango2 = new Rango(Calendar.getInstance(), Calendar.getInstance());
    entityManager().persist(rango1);
    entityManager().persist(rango2);
    admin.addRangoNotificaciones(rango1);
    admin.addRangoNotificaciones(rango2);
    admin.setFormaEnvioNotificacion(envioNotificacion);
    entityManager().persist(admin.getFormaEnvioNotificacion());
    entityManager().persist(admin);

    Usuario user = new Usuario("user", "user", "user", "telefono", "user", false);
    EnvioNotificacion envioNotificacion2 = new EnvioNotificacionMail();
    Rango rango3 = new Rango(Calendar.getInstance(), Calendar.getInstance());
    Rango rango4 = new Rango(Calendar.getInstance(), Calendar.getInstance());
    entityManager().persist(rango3);
    entityManager().persist(rango4);
    user.addRangoNotificaciones(rango3);
    user.addRangoNotificaciones(rango4);
    user.setFormaEnvioNotificacion(envioNotificacion2);
    entityManager().persist(user.getFormaEnvioNotificacion());
    entityManager().persist(user);
  }

  public void run() {
    withTransaction(() -> {
      //addSomeBasicEntities();
      //addUsers();
    });
  }
}