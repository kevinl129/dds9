package ar.edu.utn.frba.dds.ranking;

import ar.edu.utn.frba.dds.model.Entidad;

public class MejorPromedioDeCierre extends ComparadorRanking{
  public MejorPromedioDeCierre(int limite) {
    super(limite);
  }

  @Override
  public int comparacion(Entidad entidadA, Entidad entidadB) {
    return (int) Math.floor(entidadB.getPromedioCierreIncidentes() - entidadA.getPromedioCierreIncidentes());
  }
}
