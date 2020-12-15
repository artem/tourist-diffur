package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class ModelBuilder {

    // dynamic parameters
    private List<ParameterPair> alphaList;
    private List<ParameterPair> betaList;
    private List<ParameterPair> muList;

    private double alpha;
    private double beta;
    private double mu;
    private int alphaPosition = 0;
    private int betaPosition = 0;
    private int muPosition = 0;

    private final double DELTA = 1;
    private int totalDays = 365;
    private long peopleAmount;


    // for alpha beta mu dynamic changing
    public static class ParameterPair implements Comparable<ParameterPair> {
        private final long time;
        private final double value;

        public ParameterPair(long time, double value) {
            this.time = time;
            this.value = value;
        }

        public long getTime() {
            return time;
        }

        public double getValue() {
            return value;
        }

        @Override
        public int compareTo(ParameterPair parameterPair) {
            return (time > parameterPair.getTime() ? 1 : -1);
        }
    }

    public ModelBuilder(long peopleAmount) {
        this.peopleAmount = peopleAmount;
        //this.r = 0;
        this.alphaList = List.of();
        this.betaList = List.of();
        this.muList = List.of();
    }

    // Set and sort by time
    public void setAlphaList(List<ParameterPair> alphaList) {
        this.alphaList = alphaList;
        alphaPosition = -1;
        Collections.sort(this.alphaList);
    }

    public void setBetaList(List<ParameterPair> betaList) {
        this.betaList = betaList;
        betaPosition = -1;
        Collections.sort(this.betaList);
    }

    public void setMuList(List<ParameterPair> muList) {
        this.muList = muList;
        muPosition = -1;
        Collections.sort(this.muList);
    }

    private boolean hasNextAlpha() {
        return (alphaPosition + 1 < alphaList.size());
    }

    private boolean hasNextBeta() {
        return (betaPosition + 1 < betaList.size());
    }

    private boolean hasNextMu() {
        return (muPosition + 1 < muList.size());
    }

    //Not move the pointer
    private ParameterPair peekNextAlpha() {
        assert(alphaPosition + 1 < alphaList.size());
        return alphaList.get(alphaPosition + 1);
    }

    private ParameterPair peekNextBeta() {
        assert(betaPosition + 1 < betaList.size());
        return betaList.get(betaPosition + 1);
    }

    private ParameterPair peekNextMu() {
        assert(muPosition + 1 < muList.size());
        return muList.get(muPosition + 1);
    }

    //Move the pointer
    private ParameterPair moveNextAlpha() {
        assert(alphaPosition + 1 < alphaList.size());
        alphaPosition++;
        return alphaList.get(alphaPosition);
    }

    private ParameterPair moveNextBeta() {
        assert(betaPosition + 1 < betaList.size());
        betaPosition++;
        return betaList.get(betaPosition);
    }

    private ParameterPair moveNextMu() {
        assert(muPosition + 1 < muList.size());
        muPosition++;
        return muList.get(muPosition);

    }

    private double evaluateDifS(double s, double i, double r, double alpha, double beta) {
        return -1 * beta * s * i / peopleAmount;
    }

    private double evaluateDifI(double s, double i, double r, double alpha, double beta) {
        return beta * s * i / peopleAmount - alpha * i - mu * i;
    }

    private double evaluateDifR(double s, double i, double r, double alpha, double beta) {
        return alpha * i;
    }

    private double evaluateDifD(double r, double i, double mu) {
        return mu  * i;
    }

    /* private double evaluateDifAlive(double r, double mu) {
        return (1 - mu) * r;
    } */

    private double evaluateFunction(double delta, double dif, double func) {
        return (delta * dif + func < 0 ? 0 : delta * dif + func);
    }

    private void changeParameterByTime(double curTime) {
        if (hasNextAlpha()) {
            ParameterPair parameterPair = peekNextAlpha();
            if (curTime >= parameterPair.getTime()) {
                alpha = moveNextAlpha().getValue();
            }
        }
        if (hasNextBeta()) {
            ParameterPair parameterPair = peekNextBeta();
            if (curTime >= parameterPair.getTime()) {
                beta = moveNextBeta().getValue();
            }
        }
        if (hasNextMu()) {
            ParameterPair parameterPair = peekNextMu();
            if (curTime >= parameterPair.getTime()) {
                mu = moveNextMu().getValue();
            }
        }
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public void setPeopleAmount(long peopleAmount) {
        this.peopleAmount = peopleAmount;
    }

    public boolean parse(String s) {
        StringTokenizer st = new StringTokenizer(s);
        List<ParameterPair> a = new ArrayList<>();
        List<ParameterPair> b = new ArrayList<>();
        List<ParameterPair> mu = new ArrayList<>();

        while (st.countTokens() >= 3) {
            String coeff = st.nextToken();
            ParameterPair pair;
            try {
                pair = new ParameterPair(Integer.parseInt(st.nextToken()), Double.parseDouble(st.nextToken()));
            } catch (Exception e) {
                return false;
            }

            switch (coeff) {
                case "a":
                    a.add(pair);
                    break;
                case "b":
                    b.add(pair);
                    break;
                case "m":
                case "mu":
                    mu.add(pair);
                    break;
                default:
                    return false;
            }
        }
        setAlphaList(a);
        setBetaList(b);
        setMuList(mu);

        return true;
    }

    public PointsContainer build() {
        PointsContainer pointsContainer = new PointsContainer();
        assert(alphaList != null && alphaList.size() > 0);
        assert(betaList != null && betaList.size() > 0);
        assert(muList != null && muList.size() > 0);
        alphaPosition = -1;
        betaPosition = -1;
        muPosition = -1;
        double x = 0;
        double r = 0;
        double i = 1;
        double d = 0;
        double a = 0;
        double s = peopleAmount - i;
       //System.out.println(peopleAmount);
        while (x < totalDays) {
            changeParameterByTime(x);
            pointsContainer.addCoordinates(s, i, r, d, x);
            double difS = evaluateDifS(s, i, r, alpha, beta);
            double difI = evaluateDifI(s, i, r, alpha, beta);
            double difR = evaluateDifR(s, i, r, alpha, beta);
            double difD = evaluateDifD(r, i, mu);
            //double difA = evaluateDifAlive(r, mu);
            s = evaluateFunction(DELTA, difS, s);
            i = evaluateFunction(DELTA, difI, i);
            //a 0 0
            //b 0 0.275
            //m 0 0
            r = evaluateFunction(DELTA, difR, r);

            //System.out.println("i " + x + " " + i);
            //System.out.println("r " + x + " " + r);
            d = evaluateFunction(DELTA, difD, d);
            //a = evaluateFunction(DELTA, difA, a);
            if (i >= peopleAmount) {
                i = peopleAmount;
            }
            if (r >= peopleAmount) {
                r = peopleAmount;
            }
            if (s >= peopleAmount) {
                s = peopleAmount;
            }
            if (d >= peopleAmount) {
                d = peopleAmount;
            }
            x += DELTA;
        }
        return pointsContainer;
    }
}
