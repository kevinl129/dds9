package ar.edu.utn.frba.dds.repos;

import ar.edu.utn.frba.dds.model.Entidad;
import ar.edu.utn.frba.dds.model.Establecimiento;

import ar.edu.utn.frba.dds.model.Interes;
import ar.edu.utn.frba.dds.model.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.NoResultException;

public class RepoEstablecimientos implements WithSimplePersistenceUnit {
  private static RepoEstablecimientos repoEstablecimientos;

  public static RepoEstablecimientos getSingletonInstance() {
    if (repoEstablecimientos == null) {
      repoEstablecimientos = new RepoEstablecimientos();
    }
    return repoEstablecimientos;
  }

  public Establecimiento get(Long id) {
    try {
      entityManager().getTransaction().begin();
      Establecimiento establecimiento = entityManager().find(Establecimiento.class, id);
      Entidad entidad = entityManager().createQuery("SELECT e FROM Entidad e WHERE :establecimiento MEMBER OF e.establecimientos", Entidad.class)
          .setParameter("establecimiento", establecimiento)
          .getSingleResult();
      establecimiento.setEntidad(entidad);
      return establecimiento;
    } catch (NoResultException e) {
      return null;
    }
  }
  public void close() {
    entityManager().close();
  }
}
