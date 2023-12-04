package ar.edu.utn.frba.dds.model;

import java.util.ArrayList;
import ar.edu.utn.frba.dds.notification.EventManager;
import ar.edu.utn.frba.dds.notification.Notificacion;
import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Comunidades", uniqueConstraints={@UniqueConstraint(columnNames = {"nombre"})})
public class Comunidad {
  private String nombre;
  @ManyToMany
  @JoinTable(name = "miembros")
  private List<Usuario> miembros;
  @ManyToMany
  @JoinTable(name = "administradores")
  private List<Usuario> administradores;
  @OneToMany
  private List<AgrupacionServicio> agrupacionServicios;
  @OneToMany
  private List<Incidente> incidentes;
  @ManyToMany
  private List<Interes> intereses;
  @Transient
  EventManager eventManager = EventManager.getSingletonInstance();

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public Comunidad() {
    this.incidentes = new ArrayList<>();
    this.miembros = new ArrayList<>();
    this.administradores = new ArrayList<>();
    this.intereses = new ArrayList<>();
  }

  public Comunidad(String nombre, List<Usuario> miembros, List<Usuario> administradores, List<AgrupacionServicio> agrupacionServicios, List<Incidente> incidentes, List<Interes> intereses) {
    this.nombre = nombre;
    this.miembros = miembros;
    this.administradores = administradores;
    this.agrupacionServicios = agrupacionServicios;
    this.intereses = intereses;
    this.incidentes = incidentes;
  }

  public List<Incidente> getIncidentes() {
    return incidentes;
  }

  public void setIncidentes(List<Incidente> incidentes) {
    this.incidentes = incidentes;
  }


  public List<Usuario> getMiembros() {
    return miembros;
  }

  public void abrirIncidente(Interes interes){
    var incidente = new Incidente(interes,new Date());
    incidentes.add(incidente);
    eventManager.addNotificacion(new Notificacion(this, incidente, "Apertura", "Se abre el incidente asociado al servicio " + interes.servicio.getNombre()));
  }

  public void abrirIncidente(Incidente incidente) {
    incidentes.add(incidente);
    eventManager.addNotificacion(new Notificacion(this, incidente, "Apertura", "Se abre el incidente asociado al servicio " + incidente.getServicio().getNombre()));
  }

  public void abrirIncidenteSiTieneInteresEn(Interes interes) {
    if (tieneInteres(interes)) {
      abrirIncidente(interes);
    }
  }

  public void cerrarIncidente(Incidente incidenteACerrar){
    Date hoy = incidenteACerrar.cerrar();
    incidentes.stream()
        .filter(incidente -> incidente.equals(incidenteACerrar))
        .forEach(incidente -> incidente.setFechaCierre(hoy));
    eventManager.addNotificacion(new Notificacion(
        this, incidenteACerrar, "Cerrar", "Se cierra el incidente asociado al servicio " + incidenteACerrar.getServicio().getNombre()));
  }

  public boolean tieneInteres(Interes interes){
    return intereses.stream().anyMatch(interes1 -> interes1.esIgualAOtroInteres(interes));
  }

  public boolean tieneIncidente(Incidente incidente){
    return incidentes.contains(incidente);
  }
  public void agregarUsuario(Usuario usuario){
    this.miembros.add(usuario);
  }
  public void agregarInteres(Interes interes){
    this.intereses.add(interes);
  }
  public boolean tieneIncidentesAbiertos() {
    return incidentes.stream().anyMatch(incidente -> incidente.estaAbierto());
  }
  public List<Incidente> getIncidentesAbiertos() {
    return incidentes.stream().filter(incidente -> incidente.estaAbierto()).collect(Collectors.toList());
  }
  public List<Incidente> getIncidentesCerrados() {
    return incidentes.stream().filter(incidente -> incidente.estaCerrado()).collect(Collectors.toList());
  }

  public void setId(Long id) {
    this.id = id;
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

  public List<Usuario> getAdministradores() {
    return administradores;
  }

  public void setAdministradores(List<Usuario> administradores) {
    this.administradores = administradores;
  }

  public List<Interes> getIntereses() {
    return intereses;
  }

  public void setIntereses(List<Interes> intereses) {
    this.intereses = intereses;
  }

  public void setMiembros(List<Usuario> miembros) {
    this.miembros = miembros;
  }

  public List<AgrupacionServicio> getAgrupacionServicios() {
    return agrupacionServicios;
  }

  public void setAgrupacionServicios(List<AgrupacionServicio> agrupacionServicios) {
    this.agrupacionServicios = agrupacionServicios;
  }
}

/*
* Comunidad linea C==>baños, ascensores, etc
* Agus==>baños, escaleras mecanicas
* Kev==>ascensores, escaleras comunes
*
*UsuarioX==>baños
*
* */