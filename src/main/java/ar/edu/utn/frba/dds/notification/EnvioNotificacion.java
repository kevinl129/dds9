package ar.edu.utn.frba.dds.notification;

import ar.edu.utn.frba.dds.model.Usuario;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "TipoEnvioNotificacion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type")
public abstract class EnvioNotificacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  public abstract void enviar(Usuario usuario, String evento, String mensaje);
  public abstract String getTipo();
  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
