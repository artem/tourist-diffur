package model;

public class PointsContainer {
    double[] i;
    double[] s;
    double[] r;
    double[] d;
    int size;

    public PointsContainer(int size) {
        this.i = new double[size];
        this.s = new double[size];
        this.r = new double[size];
        this.d = new double[size];
    }

    public void addCoordinates(double x, double s, double i, double r, double d) {
        incCapacity();
        this.i[size] = i;
        this.s[size] = s;
        this.r[size] = r;
        this.d[size] = d;
        size++;
    }

    private void incCapacity() {
        if (size == this.i.length) {
            double[] newI = new double[size + 1];
            double[] newS = new double[size + 1];
            double[] newR = new double[size + 1];
            double[] newD = new double[size + 1];
            System.arraycopy(this.i, 0, newI, 0, size);
            System.arraycopy(this.r, 0, newR, 0, size);
            System.arraycopy(this.s, 0, newS, 0, size);
            System.arraycopy(this.d, 0, newD, 0, size);
            this.i = newI;
            this.r = newR;
            this.s = newS;
            this.d = newD;
        }
    }

    public double[] getX() {
        return null;
    }

    public double[] getI() {
        return i;
    }

    public double[] getS() {
        return s;
    }

    public double[] getR() {
        return r;
    }

    public double[] getD() {
        return d;
    }

    public int getSize() {
        return size;
    }
}
