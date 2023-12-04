package ar.edu.utn.frba.dds.repos;

import ar.edu.utn.frba.dds.model.AgrupacionServicio;
import ar.edu.utn.frba.dds.model.Comunidad;
import ar.edu.utn.frba.dds.model.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;

public class RepoAgrupacionesServicios implements WithSimplePersistenceUnit {
  private static RepoAgrupacionesServicios repoAgrupacionesServicios;

  public static RepoAgrupacionesServicios getSingletonInstance() {
    if (repoAgrupacionesServicios == null) {
      repoAgrupacionesServicios = new RepoAgrupacionesServicios();
    }
    return repoAgrupacionesServicios;
  }

  public AgrupacionServicio get(Long id) {
    try {
      entityManager().getTransaction().begin();
      AgrupacionServicio agrupacionServicio = entityManager().find(AgrupacionServicio.class, id);
      entityManager().getTransaction().commit();
      return agrupacionServicio;
    } catch (NoResultException e) {
      return null;
    }
  }

  public List<AgrupacionServicio> getAll() {
    entityManager().getTransaction().begin();
    List<AgrupacionServicio> agrupacionServicios = entityManager().createQuery("SELECT a FROM AgrupacionServicio a", AgrupacionServicio.class).getResultList();
    entityManager().getTransaction().commit();
    return agrupacionServicios;
  }

  public List<AgrupacionServicio> getAll(List<Long> ids) {
    List<AgrupacionServicio> agrupacionServicios = new ArrayList<>();
    ids.stream().forEach(id -> {
      try {
        entityManager().getTransaction().begin();
        AgrupacionServicio agrupacionServicio = entityManager().find(AgrupacionServicio.class, id);
        entityManager().getTransaction().commit();
        agrupacionServicios.add(agrupacionServicio);
      } catch (NoResultException e) {
        System.out.println("No existe la agrupacion con el id: " + id);
      }
    });
    return agrupacionServicios;
  }

  public void add(AgrupacionServicio agrupacionServicio) {
    entityManager().getTransaction().begin();
    entityManager().persist(agrupacionServicio);
    entityManager().getTransaction().commit();
  }

  public void remove(AgrupacionServicio agrupacionServicio) {
    entityManager().getTransaction().begin();
    entityManager().remove(agrupacionServicio);
    entityManager().getTransaction().commit();
  }

  public void close() {
    entityManager().close();
  }
}
