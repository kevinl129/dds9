package ar.edu.utn.frba.dds.repos;

import ar.edu.utn.frba.dds.model.Interes;
import ar.edu.utn.frba.dds.model.Rango;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.NoResultException;

public class RepoRangos implements WithSimplePersistenceUnit {
  private static RepoRangos repoRangos;

  public static RepoRangos getSingletonInstance() {
    if (repoRangos == null) {
      repoRangos = new RepoRangos();
    }
    return repoRangos;
  }

  public void add(Rango rango) {
    entityManager().getTransaction().begin();
    entityManager().persist(rango);
    entityManager().getTransaction().commit();
  }

  public Rango get(Long id) {
    try {
      entityManager().getTransaction().begin();
      Rango rango = entityManager().find(Rango.class, id);
      entityManager().getTransaction().commit();
      return rango;
    } catch (NoResultException e) {
      return null;
    }
  }

  public void eliminate(Long id) {
    entityManager().getTransaction().begin();
    Rango rango = entityManager().find(Rango.class, id);
    entityManager().remove(rango);
    entityManager().getTransaction().commit();
  }
}
