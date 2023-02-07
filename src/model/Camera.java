package model;

import util.Vec3;

public class Camera extends Point {
    private double fov;
    private double imagePlaneDist;

    public Camera(Vec3 origin, Vec3 forward, Vec3 up, int fov, double imagePlaneDist) {
        super(origin, forward, up);
        this.fov = fov;
        this.imagePlaneDist = imagePlaneDist;
    }

    public double getFov() {
        return fov;
    }

    public void setFov(double fov) {
        this.fov = fov;
    }

    public double getImagePlaneDist() {
        return imagePlaneDist;
    }

    public void setImagePlaneDist(double imagePlaneDist) {
        this.imagePlaneDist = imagePlaneDist;
    }
}
