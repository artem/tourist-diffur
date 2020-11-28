package model;

public class Tests {
    public static void main(String[] args) {
        PointsContainer container = new ModelBuilder(5_000_000_000L, 1, 0.000005f, 0.0000000001f).build();
    }
}
