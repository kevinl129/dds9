package ar.edu.utn.frba.dds.repos;

import ar.edu.utn.frba.dds.model.AgrupacionServicio;
import ar.edu.utn.frba.dds.model.Incidente;
import ar.edu.utn.frba.dds.model.Interes;
import ar.edu.utn.frba.dds.model.Servicio;
import ar.edu.utn.frba.dds.model.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.NoResultException;

public class RepoIncidentes implements WithSimplePersistenceUnit {
  private static RepoIncidentes repoIncidentes;

  public static RepoIncidentes getSingletonInstance() {
    if (repoIncidentes == null) {
      repoIncidentes = new RepoIncidentes();
    }
    return repoIncidentes;
  }

  public void abrir(Incidente incidente) {
    entityManager().getTransaction().begin();
    entityManager().persist(incidente.getInteres());
    entityManager().persist(incidente);
    entityManager().getTransaction().commit();
  }

  public Incidente cerrar(Long id) {
    entityManager().getTransaction().begin();
    Incidente incidente = entityManager().find(Incidente.class, id);
    incidente.cerrar();
    entityManager().persist(incidente);
    entityManager().getTransaction().commit();
    return incidente;
  }

  public List<Incidente> getAll(List<Long> ids) {
    List<Incidente> incidentes = new ArrayList<>();
    ids.stream().forEach(id -> {
      try {
        entityManager().getTransaction().begin();
        Incidente incidente = entityManager().find(Incidente.class, id);
        entityManager().getTransaction().commit();
        incidentes.add(incidente);
      } catch (NoResultException e) {
        System.out.println("No existe el incidente con el id: " + id);
      }
    });
    return incidentes;
  }

  public List<Incidente> getAll(Boolean closeChecked, Boolean allChecked, String serviceName) {
    entityManager().getTransaction().begin();
    String closeValidation = "WHERE i.fechaCierre != NULL";
    String openValidation = "WHERE i.fechaCierre = NULL";
    String checkedValidation;
    List<Incidente> incidentes;
    if (serviceName == null || serviceName.trim().equals("")) {
      if (closeChecked) {
        checkedValidation = closeValidation;
      } else if (allChecked) {
        checkedValidation = "";
      } else {
        checkedValidation = openValidation;
      }
      incidentes = entityManager().createQuery("SELECT i FROM Incidente i " + checkedValidation, Incidente.class).getResultList();
    } else {
      String serviceNameValidation = "i.interes != NULL AND lower(i.interes.servicio.nombre) LIKE lower(:serviceName)";
      if (closeChecked) {
        checkedValidation = closeValidation + " AND ";
      } else if (allChecked) {
        checkedValidation = "WHERE ";
      } else {
        checkedValidation = openValidation + " AND ";
      }
      incidentes = entityManager().createQuery("SELECT i FROM Incidente i " + checkedValidation + serviceNameValidation, Incidente.class)
          .setParameter("serviceName", "%"+serviceName+"%")
          .getResultList();
    }
    entityManager().getTransaction().commit();
    return incidentes;
  }

  public List<Incidente> getIncidentesInteresantesParaElUsuario(Usuario usuario) {
    entityManager().getTransaction().begin();
    List<Incidente> incidentes = entityManager().createQuery("SELECT i FROM Incidente i WHERE i.fechaCierre = NULL AND i.interes.servicio IN :servicios", Incidente.class)
        .setParameter("servicios", usuario.getIntereses().stream().map(i -> i.getServicio()).collect(Collectors.toList()))
        .getResultList();
    entityManager().getTransaction().commit();
    return incidentes;
  }
}
