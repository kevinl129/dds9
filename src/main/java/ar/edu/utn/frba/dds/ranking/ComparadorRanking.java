package ar.edu.utn.frba.dds.ranking;

import ar.edu.utn.frba.dds.model.Entidad;
import ar.edu.utn.frba.dds.repos.RepoEntidades;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ComparadorRanking {
  public ComparadorRanking(int limiteRanking) {
    this.limiteRanking = limiteRanking;
  }
  public int limiteRanking;
  public abstract int comparacion(Entidad entidadA, Entidad entidadB);
  public List<Entidad> generateRanking(List<Entidad> entidades) {
    entidades.sort((entidadA, entidadB) -> comparacion(entidadA, entidadB));
    return entidades.stream().limit(limiteRanking).collect(Collectors.toList());
  }
}
