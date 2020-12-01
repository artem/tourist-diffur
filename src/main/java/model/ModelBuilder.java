package model;

public class ModelBuilder {
    private double s;
    private long i;
    //private double r;
    private double d;
    private long PEOPLE_AMOUNT;
    private double alpha;
    private double beta;
    private double mu;
    private final double DELTA;
    private final int TOTAL_DAYS = 365;


    public ModelBuilder(long n, long i, double alpha, double beta, double mu) {
        if (n < i) {
            throw new IllegalStateException("n < i");
        }

        this.PEOPLE_AMOUNT = n;
        this.i = i;
        this.mu = mu;
        s = n - i;
        //this.r = 0;
        this.alpha = alpha;
        this.beta = beta;
        // We don't actually care about dynamic delta
        // Plotter scales the data according to the windows size
        // Settle with 1 since 0.5 introduces jitter
        this.DELTA = 1;
    }

    public ModelBuilder setN(long n) {
        if (n < i) {
            throw new IllegalStateException("n < i");
        }
        this.PEOPLE_AMOUNT = n;
        s = n - i;

        return this;
    }

    public ModelBuilder setI(long i) {
        if (PEOPLE_AMOUNT < i) {
            throw new IllegalStateException("n < i");
        }
        this.i = i;
        s = PEOPLE_AMOUNT - i;

        return this;
    }

    public ModelBuilder setAlpha(double alpha) {
        this.alpha = alpha;

        return this;
    }

    public ModelBuilder setBeta(double beta) {
        this.beta = beta;

        return this;
    }

    public ModelBuilder setMu(double mu) {
        this.mu = mu;

        return this;
    }

    private double evaluateDifS(double s, double i, double r, double alpha, double beta) {
        return -1 * beta * s * i / PEOPLE_AMOUNT;
    }

    private double evaluateDifI(double s, double i, double r, double alpha, double beta) {
        return beta * s * i / PEOPLE_AMOUNT - alpha * i;
    }

    private double evaluateDifR(double s, double i, double r, double alpha, double beta) {
        return alpha * i / PEOPLE_AMOUNT;
    }

    private double evaluateDifD(double r, double mu) {
        return mu * r;
    }

    private double evaluateFunction(double delta, double dif, double func) {
        return (delta * dif + func < 0 ? 0 : delta * dif + func);
    }

    private boolean equal(double r) {
        return  ((double) PEOPLE_AMOUNT - r < Math.log(Math.log(PEOPLE_AMOUNT)));
    }


    public PointsContainer build() {
        PointsContainer pointsContainer = new PointsContainer(TOTAL_DAYS);
        double x = 0;

        double r = 0;
        double i = this.i;
        double s = this.s;
        double d = this.d;

        while (x < TOTAL_DAYS) {
            pointsContainer.addCoordinates(x, s, i, r, d);
            double difS = evaluateDifS(s, i, r, alpha, beta);
            double difI = evaluateDifI(s, i, r, alpha, beta);
            double difR = evaluateDifR(s, i, r, alpha, beta);
            double difD = evaluateDifD(r, mu);
            s = evaluateFunction(DELTA, difS, s);
            i = evaluateFunction(DELTA, difI, i);
            r = evaluateFunction(DELTA, difR, r);
            d = evaluateFunction(DELTA, difD, d);
            if (r >= PEOPLE_AMOUNT || d >= PEOPLE_AMOUNT) {
                pointsContainer.addCoordinates(x, 0, 0, PEOPLE_AMOUNT, d);
                break;
            }
            x += DELTA;
        }
        return pointsContainer;
    }
}
