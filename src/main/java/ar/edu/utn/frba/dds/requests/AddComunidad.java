package ar.edu.utn.frba.dds.requests;

import ar.edu.utn.frba.dds.model.Usuario;
import java.util.List;

public class AddComunidad {
  private String nombre;
  private List<Long> miembros;
  private List<Long> administradores;
  private List<Long> agrupacionServicios;
  private List<Long> incidentes;
  private List<Long> intereses;

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<Long> getIncidentes() {
    return incidentes;
  }

  public void setIncidentes(List<Long> incidentes) {
    this.incidentes = incidentes;
  }

  public List<Long> getIntereses() {
    return intereses;
  }

  public void setIntereses(List<Long> intereses) {
    this.intereses = intereses;
  }

  public List<Long> getAgrupacionServicios() {
    return agrupacionServicios;
  }

  public void setAgrupacionServicios(List<Long> agrupacionServicios) {
    this.agrupacionServicios = agrupacionServicios;
  }

  public List<Long> getAdministradores() {
    return administradores;
  }

  public void setAdministradores(List<Long> administradores) {
    this.administradores = administradores;
  }

  public List<Long> getMiembros() {
    return miembros;
  }

  public void setMiembros(List<Usuario> Long) {
    this.miembros = miembros;
  }
}
