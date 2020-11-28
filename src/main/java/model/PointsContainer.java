package model;

public class PointsContainer {
    float[] x;
    float[] i;
    float[] s;
    float[] r;
    int size;

    public PointsContainer(int size) {
        this.x = new float[size];
        this.i = new float[size];
        this.s = new float[size];
        this.r = new float[size];
    }

    public void addCoordinates(float x, float s, float i, float r) {
        incCapacity();
        this.x[size] = x;
        this.i[size] = i;
        this.s[size] = s;
        this.r[size] = r;
        size++;
    }

    private void incCapacity() {
        if (size == this.x.length) {
            float[] newX = new float[this.x.length + (this.x.length / 2)];
            float[] newI = new float[this.x.length + (this.x.length / 2)];
            float[] newS = new float[this.x.length + (this.x.length / 2)];
            float[] newR = new float[this.x.length + (this.x.length / 2)];
            System.arraycopy(this.x,0, newX, 0, size);
            System.arraycopy(this.i, 0, newI, 0, size);
            System.arraycopy(this.r, 0, newR, 0, size);
            System.arraycopy(this.s, 0, newS, 0, size);
            this.x = newX;
            this.i = newI;
            this.r = newR;
            this.s = newS;
        }
    }

    public float[] getX() {
        return x;
    }

    public float[] getI() {
        return i;
    }

    public float[] getS() {
        return s;
    }

    public float[] getR() {
        return r;
    }

    public int getSize() {
        return size;
    }
}
