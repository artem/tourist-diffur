package BruteforceParameters;

import model.ModelBuilder;
import model.PointsContainer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteforceParametersFinder {
    public static void main(String[] args) {
        ModelBuilder modelBuilder = new ModelBuilder(25_000_000);

        modelBuilder.setTotalDays(100);
        double minI  = Double.MAX_VALUE;
        double al = 0;
        double b = 0;
        double m = 0;
        double minD  = Double.MAX_VALUE;
        for (double alpha = 0d; alpha <= 1; alpha += 0.001d) {
            for (double beta = 0d; beta <= 10; beta += 0.01d) {
                    double mu = 1 - alpha;
                    modelBuilder.setAlphaList(new ArrayList<ModelBuilder.ParameterPair>(Arrays.asList(new ModelBuilder.ParameterPair(0, alpha))));
                    modelBuilder.setBetaList(Arrays.asList(new ModelBuilder.ParameterPair(0, beta)));
                    modelBuilder.setMuList(Arrays.asList(new ModelBuilder.ParameterPair(0, mu)));
                    modelBuilder.setTotalDays(190);
                    PointsContainer container = modelBuilder.build();
                    double sumI30 = container.getI().get(30);
                    double sumI60 = container.getI().get(60);
                    double sumI90 = container.getI().get(90);
                    double sumD30 = container.getD().get(30);
                    double sumD60 = container.getD().get(60);
                    double sumD90 = container.getD().get(90);
                    double sumI180 = container.getI().get(180);
                    if (/*(sumI30 - 142)*(sumI30 - 142) +
                            (sumI60 - 1832)*(sumI60 - 1832)
                               + (sumI60 - 6600)*(sumI60 - 6600)  +  (sumI180 - 20800) * (sumI180 - 20800) <= minI &&*/ (sumD30 - 6)*(sumD30 - 6) + (sumD60 - 61) * (sumD60 - 61) + (sumD90 - 191) * (sumD90 - 191) <= minD) {
                        minI = (sumI30 - 142)*(sumI30 - 142) +
                                (sumI60 - 1832)*(sumI60 - 1832)
                                + (sumI90 - 6600)*(sumI90 - 6600) + (sumI180 - 20800) * (sumI180 - 20800);
                        if (minI - 4177355.7821954256 < 0.001d) {
                            System.out.println("dffdf");
                        }
                        minD = (sumD30 - 6)*(sumD30 - 6) + (sumD60 - 61) * (sumD60 - 61) + (sumD90 - 191) * (sumD90 - 191);
                        al = alpha;
                        b = beta;

                        m = mu;
                    }
            }
        }
        System.out.println(al);
        System.out.println(b);
        System.out.println(m);
        System.out.println(minI);
    }
}

