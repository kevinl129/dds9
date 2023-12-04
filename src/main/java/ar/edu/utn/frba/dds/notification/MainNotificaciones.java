package ar.edu.utn.frba.dds.notification;

import ar.edu.utn.frba.dds.model.Usuario;
import ar.edu.utn.frba.dds.repos.RepoUsuario;

public class MainNotificaciones {
  public static void NotificarUsuarioPorCercania(Usuario usuario) {
    var incidentes = usuario.incidentesAbiertosDeMisComunidades();
    incidentes.forEach(incidente -> {
      if (usuario.estaCercaDe(incidente))
        usuario.notificar("Cercania a Incidente", "Se esta cerca de un incidente en " + incidente.getEntidad().getNombre());
    });
  }

  public static void NotificarAsincronicamente() {
    EventManager eventManager = EventManager.getSingletonInstance();
    eventManager.notificar();
  }

  public static void main(String[] args) {
    // Cada minuto se corre
    RepoUsuario repoUsuario = RepoUsuario.getSingletonInstance();
    var usuarios = repoUsuario.getAll();
    usuarios.stream().forEach(usuario -> NotificarUsuarioPorCercania(usuario));
    NotificarAsincronicamente();
  }
}
