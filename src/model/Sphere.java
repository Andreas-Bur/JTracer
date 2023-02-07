package model;

import util.Material;
import util.Vec3;

import java.awt.*;

public class Sphere extends Geometry {
    private double radius;

    public Sphere(Vec3 origin) {
        this(origin, 1, new Material(Color.RED));
    }

    public Sphere(Vec3 origin, double radius, Material material) {
        this(origin, new Vec3(1, 0, 0), new Vec3(0, 1, 0), radius, material);
    }

    public Sphere(Vec3 origin, Vec3 forward, Vec3 up, double radius, Material material) {
        super(origin, forward, up, material);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public Vec3 getIntersection(Vec3 camera, Vec3 ray) {
        var diff = camera.sub(origin);
        var normRay = ray.normalize();
        var delta = normRay.dotProd(diff);
        var discriminant = delta * delta - (diff.dotProd(diff) - radius * radius);
        if (discriminant < 0) {
            return null;
        }
        var d = -normRay.dotProd(diff) - Math.sqrt(discriminant);
        return camera.add(normRay.mult(d));
    }

    @Override
    public Color getAmbientColor(Vec3 pos) {
        /*var local = toLocalCoordinates(pos);
        var intensity = (int) (255*Math.abs(local.y()));
        if(local.y() < 0){
            return new Color(intensity, 0, 0);
        }

        return new Color(0, intensity, 0);*/

        return material.getAmbientColor();
    }
}
