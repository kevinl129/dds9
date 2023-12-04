package ar.edu.utn.frba.dds.ranking;

import ar.edu.utn.frba.dds.CSVManagement.CSVProducer;
import ar.edu.utn.frba.dds.repos.RepoEntidades;

import java.util.Arrays;
import java.util.List;

public class MainRanking {
  private static RepoEntidades repoEntidades = RepoEntidades.getSingletonInstance();
  public static void main(String[] args) {
    CSVProducer csvProducer = new CSVProducer();
    // Cada semana se corre la siguiente funcion
    List<ComparadorRanking> comparadorRankingList = Arrays.asList(
        new MayorCantidadDeIncidentes(10),
        new MejorPromedioDeCierre(10));
    var entidades = repoEntidades.getAll();
    comparadorRankingList.forEach(comparadorRanking -> {
      var ranking = comparadorRanking.generateRanking(entidades);
      csvProducer.WriteCsv(ranking, null);
    });
  }
}
