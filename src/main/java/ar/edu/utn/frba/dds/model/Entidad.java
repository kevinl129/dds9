package ar.edu.utn.frba.dds.model;

import ar.edu.utn.frba.dds.CSVManagement.TipoOrganizacion;
import ar.edu.utn.frba.dds.location.Localizacion;
import ar.edu.utn.frba.dds.repos.RepoUsuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Entidades")
public class Entidad {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Transient
  RepoUsuario repoUsuario = RepoUsuario.getSingletonInstance();
  private String nombre;
  @Enumerated(EnumType.STRING)
  private TipoOrganizacion tipo;
  private String mailReporte;
  public Entidad(String nombre, TipoOrganizacion tipo, String email, Localizacion localizacion, List<Establecimiento> establecimientos) {
    this.nombre = nombre;
    this.tipo = tipo;
    this.mailReporte = email;
    this.localizacion = localizacion;
    this.establecimientos = establecimientos;
    this.incidentes = new ArrayList<>();
  }

  public Entidad(String nombre, TipoOrganizacion tipo, String email) {
    this.nombre = nombre;
    this.tipo = tipo;
    this.mailReporte = email;
    this.establecimientos = new ArrayList<>();
    this.incidentes = new ArrayList<>();
  }
  @OneToMany
  private List<Establecimiento> establecimientos;
  @OneToOne
  private Localizacion localizacion;
  @Transient
  private List<Incidente> incidentes;

  public Entidad() {}

  public String getEmail() {
    return mailReporte;
  }
  public Long getId() {
    return id;
  }
  public String getNombre() {
    return nombre;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public TipoOrganizacion getTipo() {
    return tipo;
  }
  public List<Establecimiento> getEstablecimientos() {
    return establecimientos;
  }
  public Localizacion getLocalizacion() {
    return localizacion;
  }

  public void setLocalizacion(Localizacion ubicacion) {
    this.localizacion = ubicacion;
  }
  public float getPromedioCierreIncidentes() {
    var total = incidentes.stream().filter(incidente -> incidente.estaCerrado() && incidente.estaEnLaSemanaActual()).mapToLong(Incidente::getDiferenciaEntreCierreYCreacion).sum();
    if (incidentes.isEmpty()) {
      return 0;
    }
    return total / incidentes.size();
  }

  public int getCantidadIncidentesEnLaSemana() {
     var incidentesEnLaSemana = incidentes.stream()
        .filter(incidente -> incidente.estaEnLaSemanaActual() && incidente.tieneMasDe24Horas())
        .toList();
     return incidentesEnLaSemana.size();
  }

  public Establecimiento getPrimeraEstacion(){
    return establecimientos.size() == 0 ? establecimientos.get(0) : null;
  }
  public Establecimiento getUltimaEstacion(){
    int cantidadEstablecimientos = establecimientos.size();
    return cantidadEstablecimientos != 0 ? establecimientos.get(cantidadEstablecimientos - 1) : null;
  }

  // Hacer lo mismo para donde se use el new Date() o tema con fechas, para testing!
  public void abrirIncidente(Servicio servicio) {
    abrirIncidente(servicio, new Date());
  }

  public void abrirIncidente(Servicio servicio, Date fecha) {
    var interes = new Interes(this, servicio);
    incidentes.add(new Incidente(interes, fecha));
    repoUsuario.getAllInteresadosEn(interes)
        .forEach(usuario -> usuario.notificar("Incidente", "La entidad" + getNombre() + "abrio un incidente en " + servicio.getNombre()));
  }

  public void setIncidentes(List<Incidente> incidentes) {
    this.incidentes = incidentes;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
