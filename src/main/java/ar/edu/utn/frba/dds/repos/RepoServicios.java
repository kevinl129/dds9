package ar.edu.utn.frba.dds.repos;

import ar.edu.utn.frba.dds.model.Comunidad;
import ar.edu.utn.frba.dds.model.Entidad;
import ar.edu.utn.frba.dds.model.Incidente;
import ar.edu.utn.frba.dds.model.Servicio;
import ar.edu.utn.frba.dds.model.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;

public class RepoServicios implements WithSimplePersistenceUnit {
  private static RepoServicios repoServicios;

  public static RepoServicios getSingletonInstance() {
    if (repoServicios == null) {
      repoServicios = new RepoServicios();
    }
    return repoServicios;
  }

  public Servicio get(Long id) {
    entityManager().getTransaction().begin();
    Servicio servicio = entityManager().find(Servicio.class, id);
    entityManager().getTransaction().commit();
    return servicio;
  }

  public List<Servicio> getAll(List<Long> ids) {
    List<Servicio> servicios = new ArrayList<>();
    ids.stream().forEach(id -> {
      try {
        entityManager().getTransaction().begin();
        Servicio servicio = entityManager().find(Servicio.class, id);
        entityManager().getTransaction().commit();
        servicios.add(servicio);
      } catch (NoResultException e) {
        System.out.println("No existe el servicio con el id: " + id);
      }
    });
    return servicios;
  }
}
