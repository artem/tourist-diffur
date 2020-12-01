package model;

import java.util.ArrayList;
import java.util.List;

public class PointsContainer {
    List<Double> i;
    List<Double> s;
    List<Double> r;
    List<Double> d;
    List<Double> x;
    int size;

    public PointsContainer() {
        this.i = new ArrayList<>();
        this.s = new ArrayList<>();
        this.r = new ArrayList<>();
        this.d = new ArrayList<>();
        this.x = new ArrayList<>();
    }

    public void addCoordinates(double s, double i, double r, double d, double x) {
        this.s.add(s);
        this.i.add(i);
        this.r.add(r);
        this.d.add(d);
        this.x.add(x);
        size++;
    }

    public List<Double> getI() {
        return i;
    }

    public List<Double> getS() {
        return s;
    }

    public List<Double> getR() {
        return r;
    }

    public List<Double> getD() {
        return d;
    }

    public List<Double> getX() {
        return x;
    }

    public int getSize() {
        return size;
    }
}
