package model;

import util.Vec3;

public abstract class Point {
    protected Vec3 origin;
    protected Vec3 forward;
    protected Vec3 up;
    public Point(Vec3 origin, Vec3 forward, Vec3 up) {
        this.origin = origin;
        this.forward = forward;
        this.up = up;
    }

    public Vec3 getOrigin() {
        return origin;
    }

    public void setOrigin(Vec3 origin) {
        this.origin = origin;
    }

    public Vec3 getForward() {
        return forward;
    }

    public void setForward(Vec3 forward) {
        this.forward = forward;
    }

    public Vec3 getUp() {
        return up;
    }

    public void setUp(Vec3 up) {
        this.up = up;
    }

    public Vec3 getRight() {
        return forward.crossProd(up);
    }
}
