package ar.edu.utn.frba.dds.ranking;

import ar.edu.utn.frba.dds.model.Entidad;

public class MayorCantidadDeIncidentes extends ComparadorRanking{
  public MayorCantidadDeIncidentes(int limite) {
    super(limite);
  }

  @Override
  public int comparacion(Entidad entidadA, Entidad entidadB) {
    return entidadB.getCantidadIncidentesEnLaSemana() - entidadA.getCantidadIncidentesEnLaSemana();
  }
}
