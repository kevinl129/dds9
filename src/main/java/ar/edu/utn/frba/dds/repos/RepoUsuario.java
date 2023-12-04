package ar.edu.utn.frba.dds.repos;

import ar.edu.utn.frba.dds.location.Localizacion;
import ar.edu.utn.frba.dds.model.*;
import ar.edu.utn.frba.dds.notification.EnvioNotificacion;
import ar.edu.utn.frba.dds.notification.EnvioNotificacionMail;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.NoResultException;
import org.apache.commons.codec.digest.DigestUtils;

public class RepoUsuario implements WithSimplePersistenceUnit {
  private static RepoUsuario repoUsuario;

  public static RepoUsuario getSingletonInstance() {
    if (repoUsuario == null) {
      repoUsuario = new RepoUsuario();
    }
    return repoUsuario;
  }

  public List<Usuario> getAll(List<Long> ids) {
    List<Usuario> usuarios = new ArrayList<>();
    ids.stream().forEach(id -> {
      try {
        entityManager().getTransaction().begin();
        Usuario usuario = entityManager().find(Usuario.class, id);
        entityManager().getTransaction().commit();
        usuarios.add(usuario);
      } catch (NoResultException e) {
        System.out.println("No existe el usuario con el id: " + id);
      }
    });
    return usuarios;
  }

  public List<Usuario> getAll() {
    entityManager().getTransaction().begin();

    List<Usuario> usuarios = entityManager().createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();

    entityManager().getTransaction().commit();
    return usuarios;
  }

  public List<Usuario> get(String name) {
    entityManager().getTransaction().begin();

    List<Usuario> usuarios;
    if (name == null) {
      usuarios = entityManager().createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    } else {
      usuarios = entityManager().createQuery("SELECT u FROM Usuario u WHERE lower(u.nombre) LIKE lower(:name)", Usuario.class)
              .setParameter("name", "%"+name+"%")
              .getResultList();
    }

    entityManager().getTransaction().commit();
    return usuarios;
  }

  public Usuario get(String email, String password) {
    try {
      entityManager().getTransaction().begin();
      Usuario usuario = entityManager().createQuery("SELECT u FROM Usuario u WHERE u.contrasenia = :password AND u.email = :email", Usuario.class)
          .setParameter("email", email)
          .setParameter("password", DigestUtils.sha256Hex(password))
          .getSingleResult();
      entityManager().getTransaction().commit();
      return usuario;
    } catch (NoResultException e) {
      return null;
    }
  }

  public Usuario get(Long id) {
    try {
      entityManager().getTransaction().begin();
      Usuario usuario = entityManager().find(Usuario.class, id);
      entityManager().getTransaction().commit();
      return usuario;
    } catch (NoResultException e) {
      return null;
    }
  }

  public Stream<Usuario> getAllInteresadosEn(Interes interes) {
    entityManager().getTransaction().begin();

    List<Usuario> usuarios = entityManager().createQuery("SELECT u FROM Usuario u JOIN u.intereses i WHERE i = :interes", Usuario.class)
        .setParameter("interes", interes)
        .getResultList();

    entityManager().getTransaction().commit();
    return usuarios.stream();
  }

  public void add(Usuario usuario) {
    entityManager().getTransaction().begin();
    entityManager().persist(usuario.getFormaEnvioNotificacion());
    entityManager().persist(usuario);
    entityManager().getTransaction().commit();
  }

  public void add(Usuario usuario, Interes interes) {
    entityManager().getTransaction().begin();
    entityManager().persist(interes);
    entityManager().persist(usuario);
    entityManager().getTransaction().commit();
  }

  public void add(Usuario usuario, Rango rango) {
    entityManager().getTransaction().begin();
    entityManager().persist(rango);
    entityManager().persist(usuario);
    entityManager().getTransaction().commit();
  }

  public void remove(Usuario usuario) {
    entityManager().getTransaction().begin();

    usuario = entityManager().merge(usuario);
    entityManager().remove(usuario);

    entityManager().getTransaction().commit();
  }

  public void remove(Long id) {
    entityManager().getTransaction().begin();

    Usuario usuario = entityManager().find(Usuario.class, id);
    entityManager().remove(usuario);

    entityManager().getTransaction().commit();
  }

  public void close() {
    entityManager().close();
  }
}
