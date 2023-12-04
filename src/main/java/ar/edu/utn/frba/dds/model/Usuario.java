package ar.edu.utn.frba.dds.model;

import ar.edu.utn.frba.dds.location.Localizacion;
import ar.edu.utn.frba.dds.notification.EnvioNotificacion;
import ar.edu.utn.frba.dds.notification.EnvioNotificacionWpp;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import org.apache.commons.codec.digest.DigestUtils;

@Entity
@Table(name = "Usuarios", uniqueConstraints={@UniqueConstraint(columnNames = {"email"})})
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  private String nombre;
  private String apellido;
  private String email;
  private String telefono;
  private Boolean admin;
  String contrasenia;
  @Transient
  private Localizacion ubicacion;
  @OneToMany
  List<Interes> intereses;
  @ManyToMany
  List<Entidad> entidades;
  @OneToMany
  List<Rango> rangoNotificaciones;
  @ManyToMany
  List<Comunidad> comunidades;
  @ManyToOne
  private EnvioNotificacion formaEnvioNotificacion;

  public Usuario() {
    this.intereses = new ArrayList<>();
  }

  public List<Comunidad> getComunidades() {
    return comunidades;
  }

  public void setFormaEnvioNotificacion(EnvioNotificacion envioNotificacion) {
    this.formaEnvioNotificacion = envioNotificacion;
  }

  public EnvioNotificacion getFormaEnvioNotificacion() {
    return formaEnvioNotificacion;
  }
  public Usuario(String nombre, String apellido, String contrasenia, String telefono, String email, Boolean admin) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.contrasenia = DigestUtils.sha256Hex(contrasenia);
    this.telefono = telefono;
    this.email = email;
    this.intereses = new ArrayList<>();
    this.entidades = new ArrayList<>();
    this.comunidades = new ArrayList<>();
    this.rangoNotificaciones = new ArrayList<>();
    this.formaEnvioNotificacion = new EnvioNotificacionWpp();
    this.admin = admin;
  }

  public List<Rango> getRango() {
    return rangoNotificaciones;
  }

  public boolean estaInteresadoEn(Interes otroInteres) {
    return intereses.stream().anyMatch(interes -> interes.esIgualAOtroInteres(otroInteres));
  }
  public String getNombre() {
    return nombre;
  }

  public void notificar(String evento, String mensaje) {
    formaEnvioNotificacion.enviar(this, evento, mensaje);
  }

  public void addRangoNotificaciones(Rango rango) {
    rangoNotificaciones.add(rango);
  }

  public List<Rango> getRangoNotificaciones() {
    return rangoNotificaciones;
  }
  public void removeRangoNotificaciones(Rango rango) {
    rangoNotificaciones = rangoNotificaciones.stream().filter(x -> x.getId() != rango.getId()).collect(Collectors.toList());
  }
  public void removeInteres(Interes interes) {
    intereses = intereses.stream().filter(x -> x.id != interes.id).collect(Collectors.toList());
  }

  public boolean quiereNotificacion(Calendar hoy) {
    return getRangoNotificaciones().stream().anyMatch(rango -> rango.estaEnElRango(hoy));
  }

  public List<Interes> getIntereses() {
    return intereses;
  }

  public String getContrasenia() {
    return contrasenia;
  }

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
     this.email = email;
  }
  public void setContrasenia(String contrasenia) {
    this.contrasenia = DigestUtils.sha256Hex(contrasenia);
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public void setComunidades(List<Comunidad> comunidades) {
    this.comunidades = comunidades;
  }

  public boolean tieneInteresEnServicio(Servicio servicio) {
    return getIntereses().stream().anyMatch(interes -> interes.getServicio().equals(servicio));
  }

  public void abrirIncidente(Interes interes){
    comunidades.stream().forEach(comunidad -> comunidad.abrirIncidenteSiTieneInteresEn(interes));
  }

  public void cerrarIncidente(Incidente incidente){
    this.comunidades.stream()
        .filter(comunidad -> comunidad.tieneIncidente(incidente))
        .forEach(comunidad -> comunidad.cerrarIncidente(incidente));
  }

  public Stream<Incidente> incidentesAbiertosDeMisComunidades() {
    return getComunidades().stream()
        // .filter(comunidad -> comunidad.tieneIncidentesAbiertos())
        .map(comunidad -> comunidad.getIncidentesAbiertos())
        .flatMap(List::stream);
  }

  public boolean estaCercaDe(Incidente incidente) {
    return incidente.estaCercaDe(ubicacion);
  }
  public void agregarComunidad(Comunidad comunidad){
    this.comunidades.add(comunidad);
  }
  public void agregarInteres(Interes interes){
    this.intereses.add(interes);
  }

  public void setId(Long id) {
    this.id = id;
  }
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }
  public String getTelefono() { return this.telefono; }
  public Long getId() {
    return id;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public Boolean getAdmin() {
    return admin;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }

  public void setUbicacion(Localizacion ubicacion) {
    this.ubicacion = ubicacion;
  }
}
