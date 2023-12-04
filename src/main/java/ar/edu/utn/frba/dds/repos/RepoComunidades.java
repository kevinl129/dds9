package ar.edu.utn.frba.dds.repos;

import ar.edu.utn.frba.dds.model.Comunidad;
import ar.edu.utn.frba.dds.model.Entidad;
import ar.edu.utn.frba.dds.model.Establecimiento;
import ar.edu.utn.frba.dds.model.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import javax.persistence.NoResultException;

public class RepoComunidades implements WithSimplePersistenceUnit {
  private static RepoComunidades repoComunidades;

  public static RepoComunidades getSingletonInstance() {
    if (repoComunidades == null) {
      repoComunidades = new RepoComunidades();
    }
    return repoComunidades;
  }

  public Comunidad get(Long id) {
    try {
      entityManager().getTransaction().begin();
      Comunidad comunidad = entityManager().find(Comunidad.class, id);
      entityManager().getTransaction().commit();
      return comunidad;
    } catch (NoResultException e) {
      return null;
    }
  }

  public List<Comunidad> getAll() {
    entityManager().getTransaction().begin();
    List<Comunidad> comunidades = entityManager().createQuery("SELECT c FROM Comunidad c", Comunidad.class).getResultList();
    entityManager().getTransaction().commit();
    return comunidades;
  }

  public void add(Comunidad comunidad) {
    entityManager().getTransaction().begin();
    entityManager().persist(comunidad);
    entityManager().getTransaction().commit();
  }

  public void remove(Comunidad comunidad) {
    entityManager().getTransaction().begin();
    entityManager().remove(comunidad);
    entityManager().getTransaction().commit();
  }

  public void close() {
    entityManager().close();
  }
}
