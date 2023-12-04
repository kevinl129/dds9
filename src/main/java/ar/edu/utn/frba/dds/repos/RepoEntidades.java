package ar.edu.utn.frba.dds.repos;
import ar.edu.utn.frba.dds.model.Entidad;
import ar.edu.utn.frba.dds.model.Incidente;
import ar.edu.utn.frba.dds.model.Servicio;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RepoEntidades {
  private static final String PERSISTENCE_NAME = "simple-persistence-unit";

  private EntityManagerFactory entityManagerFactory;
  private EntityManager entityManager;

  private static RepoEntidades repoEntidades;

  private RepoEntidades() {
    entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
    entityManager = entityManagerFactory.createEntityManager();
  }

  public static RepoEntidades getSingletonInstance() {
    if (repoEntidades == null) {
      repoEntidades = new RepoEntidades();
    }
    return repoEntidades;
  }

  public List<Entidad> getAll() {
    entityManager.getTransaction().begin();

    List<Entidad> entidades = entityManager.createQuery("SELECT e FROM Entidad e", Entidad.class).getResultList();

    entityManager.getTransaction().commit();
    return entidades;
  }

  public List<Entidad> get(String name) {
    entityManager.getTransaction().begin();
    List<Entidad> entidades;
    if (name == null) {
      entidades = entityManager.createQuery("SELECT e FROM Entidad e", Entidad.class).getResultList();
    } else {
      entidades = entityManager.createQuery("SELECT e FROM Entidad e WHERE lower(e.nombre) LIKE lower(:name)", Entidad.class)
              .setParameter("name", "%"+name+"%")
              .getResultList();
    }
    entidades.forEach(entidad -> {
      List<Incidente> incidentes = new ArrayList<>();
      entidad.getEstablecimientos().forEach(establecimiento -> {
        List<Servicio> servicios = establecimiento.getServicios();
        servicios.forEach(servicio -> {
          List<Incidente> incidentesDelServicio = entityManager.createQuery("SELECT i FROM Incidente i WHERE i.interes.servicio.id = :servicioId", Incidente.class)
                  .setParameter("servicioId", servicio.getId())
                  .getResultList();
          incidentes.addAll(incidentesDelServicio);
        });
      });
      entidad.setIncidentes(incidentes);
    });
    entityManager.getTransaction().commit();
    return entidades;
  }

  public void add(Entidad entidad) {
    entityManager.getTransaction().begin();

    entityManager.persist(entidad);

    entityManager.getTransaction().commit();
  }

  public void remove(Entidad entidad) {
    entityManager.getTransaction().begin();

    entidad = entityManager.merge(entidad);
    entityManager.remove(entidad);

    entityManager.getTransaction().commit();
  }

  public void close() {
    entityManager.close();
    entityManagerFactory.close();
  }
  public Entidad get(Long Id) {
    entityManager.getTransaction().begin();

    Entidad entidades = entityManager.find(Entidad.class, Id);

    entityManager.getTransaction().commit();
    return entidades;
  }

  public Entidad getEntidadQueTieneElServicio(Servicio servicio) {
    entityManager.getTransaction().begin();
    Entidad entidad = entityManager.createQuery("SELECT e FROM Entidad e JOIN e.establecimientos est WHERE :servicio MEMBER OF est.servicios", Entidad.class)
        .setParameter("servicio", servicio)
        .getSingleResult();
    entityManager.getTransaction().commit();
    return entidad;
  }
}

