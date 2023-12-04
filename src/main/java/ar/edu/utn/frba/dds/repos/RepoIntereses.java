package ar.edu.utn.frba.dds.repos;

import ar.edu.utn.frba.dds.model.Incidente;
import ar.edu.utn.frba.dds.model.Interes;
import ar.edu.utn.frba.dds.model.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;

public class RepoIntereses implements WithSimplePersistenceUnit {
  private static RepoIntereses repoIntereses;

  public static RepoIntereses getSingletonInstance() {
    if (repoIntereses == null) {
      repoIntereses = new RepoIntereses();
    }
    return repoIntereses;
  }

  public void add(Interes interes) {
    entityManager().getTransaction().begin();
    entityManager().persist(interes);
    entityManager().getTransaction().commit();
  }

  public List<Interes> getAll(List<Long> ids) {
    List<Interes> intereses = new ArrayList<>();
    ids.stream().forEach(id -> {
      try {
        entityManager().getTransaction().begin();
        Interes interes = entityManager().find(Interes.class, id);
        entityManager().getTransaction().commit();
        intereses.add(interes);
      } catch (NoResultException e) {
        System.out.println("No existe el incidente con el id: " + id);
      }
    });
    return intereses;
  }

  public Interes get(Long id) {
    try {
      entityManager().getTransaction().begin();
      Interes interes = entityManager().find(Interes.class, id);
      entityManager().getTransaction().commit();
      return interes;
    } catch (NoResultException e) {
      return null;
    }
  }

  public void eliminate(Long id) {
    entityManager().getTransaction().begin();
    Interes interes = entityManager().find(Interes.class, id);
    entityManager().remove(interes);
    entityManager().getTransaction().commit();
  }
}
