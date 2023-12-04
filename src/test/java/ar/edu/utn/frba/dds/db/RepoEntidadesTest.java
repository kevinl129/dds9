package ar.edu.utn.frba.dds.db;
import ar.edu.utn.frba.dds.model.Entidad;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ar.edu.utn.frba.dds.repos.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class RepoEntidadesTest {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private RepoEntidades repoEntidades;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("simple-persistence-unit");
        entityManager = entityManagerFactory.createEntityManager();
        repoEntidades = RepoEntidades.getSingletonInstance();;
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
    public void testAddAndRemoveEntidad() {
        Entidad entidad = new Entidad();

        entityManager.getTransaction().begin();
        repoEntidades.add(entidad);
        entityManager.getTransaction().commit();

        List<Entidad> entidades = repoEntidades.getAll();
        assertTrue(entidades.contains(entidad));

        entityManager.getTransaction().begin();
        repoEntidades.remove(entidad);
        entityManager.getTransaction().commit();

        entidades = repoEntidades.getAll();
        assertTrue(!entidades.contains(entidad));
    }

    @Test
    public void testGetAll() {
        Entidad entidad1 = new Entidad();
        Entidad entidad2 = new Entidad();

        entityManager.getTransaction().begin();
        repoEntidades.add(entidad1);
        repoEntidades.add(entidad2);
        entityManager.getTransaction().commit();

        List<Entidad> entidades = repoEntidades.getAll();
        Assertions.assertEquals(2, entidades.size());
    }
}
