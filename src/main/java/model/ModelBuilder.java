package model;

public class ModelBuilder {
    private float s;
    private float i;
    private float r;
    private final long PEOPLE_AMOUNT;
    private final float alpha;
    private final float beta;
    private final float DELTA;
    private final int TOTAL_DAYS = 365;


    public ModelBuilder(long n, float i, float alpha, float beta) {
        this.PEOPLE_AMOUNT = n;
        this.i = i;
        s = n - i;
        this.r = 0;
        this.alpha = alpha;
        this.beta = beta;
        this.DELTA = (float)Math.log(Math.abs(Math.log(Math.abs(Math.log((double) n)))));
    }

    private float evaluateDifS(float s, float i, float r, float alpha, float beta) {
        return -1 * beta * s * i;
    }

    private float evaluateDifI(float s, float i, float r, float alpha, float beta) {
        return beta * s * i - alpha * i;
    }

    private float evaluateDifR(float s, float i, float r, float alpha, float beta) {
        return alpha * i;
    }

    private float evaluateFunction(float delta, float dif, float func) {
        return (delta * dif + func < 0 ? 0 : delta * dif + func);
    }

    private boolean equal(float r) {
        return  ((float) PEOPLE_AMOUNT - r < Math.log(Math.log(PEOPLE_AMOUNT)));
    }


    public PointsContainer build() {
        PointsContainer pointsContainer = new PointsContainer(20);
        float x = 0;
        pointsContainer.addCoordinates(x, s, i, r);
        while (r <= PEOPLE_AMOUNT && x <= TOTAL_DAYS) {
            float difS = evaluateDifS(s, i, r, alpha, beta);
            float difI = evaluateDifI(s, i, r, alpha, beta);
            float difR = evaluateDifR(s, i, r, alpha, beta);
            s = evaluateFunction(DELTA, difS, s);
            i = evaluateFunction(DELTA, difI, i);
            r = evaluateFunction(DELTA, difR, r);
            if (r >= PEOPLE_AMOUNT || equal(r)) {
                pointsContainer.addCoordinates(x, 0, 0, PEOPLE_AMOUNT);
                break;
            }
            x += DELTA;
            pointsContainer.addCoordinates(x, s, i, r);
        }
        return pointsContainer;
    }


}
