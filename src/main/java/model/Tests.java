package model;

import java.util.ArrayList;
import java.util.List;

public class Tests {
    public static void main(String[] args) {
        ArrayList<ModelBuilder.ParameterPair> alphaList = new ArrayList<>();
        alphaList.add(new ModelBuilder.ParameterPair(0, 0.00001d));
        List<ModelBuilder.ParameterPair> betaList = new ArrayList<>();
        betaList.add(new ModelBuilder.ParameterPair(0, 0.1d));
        List<ModelBuilder.ParameterPair> muList = new ArrayList<>();
        muList.add(new ModelBuilder.ParameterPair(0, 0.01d));
        ModelBuilder modelBuilder = new ModelBuilder(7_000_000_000L);
        modelBuilder.setAlphaList(alphaList);
        modelBuilder.setBetaList(betaList);
        modelBuilder.setMuList(muList);
        PointsContainer pointsContainer = modelBuilder.build();
    }
}
