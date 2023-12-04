package ar.edu.utn.frba.dds.db;

import ar.edu.utn.frba.dds.model.Entidad;
import ar.edu.utn.frba.dds.model.Interes;
import ar.edu.utn.frba.dds.model.Servicio;
import ar.edu.utn.frba.dds.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Stream;
import ar.edu.utn.frba.dds.repos.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class RepoUsuarioTest {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private RepoUsuario repoUsuario;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("simple-persistence-unit");
        entityManager = entityManagerFactory.createEntityManager();
        repoUsuario = RepoUsuario.getSingletonInstance();
    }

    @AfterEach
    public void tearDown() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    @Disabled
    public void testAddAndRemoveUsuario() {
        Usuario usuario = new Usuario();

        entityManager.getTransaction().begin();
        repoUsuario.add(usuario);
        entityManager.getTransaction().commit();

        List<Usuario> usuarios = repoUsuario.getAll();
        Assertions.assertTrue(usuarios.contains(usuario));

        entityManager.getTransaction().begin();
        repoUsuario.remove(usuario);
        entityManager.getTransaction().commit();

        usuarios = repoUsuario.getAll();
        Assertions.assertFalse(usuarios.contains(usuario));
    }

    @Test
    @Disabled
    public void testGetAllInteresadosEn() {
        Interes interes = new Interes(new Entidad(), new Servicio());

        Usuario usuario1 = new Usuario();
        usuario1.agregarInteres(interes);

        Usuario usuario2 = new Usuario();
        usuario2.agregarInteres(interes);

        entityManager.getTransaction().begin();
        repoUsuario.add(usuario1);
        repoUsuario.add(usuario2);
        entityManager.getTransaction().commit();

        Stream<Usuario> usuariosInteresados = repoUsuario.getAllInteresadosEn(interes);
        Assertions.assertNotEquals(null, usuariosInteresados);
        Assertions.assertEquals(2, usuariosInteresados.count());
    }
}

