package model;

import util.ColorUtil;
import util.Material;
import util.Vec3;

import java.awt.*;

public class Plane extends Geometry {

    protected double width;
    protected double height;

    public Plane(Vec3 origin, Vec3 forward, Vec3 up, Material material, double width, double height) {
        super(origin, forward, up, material);
        this.width = width;
        this.height = height;
    }

    @Override
    public Vec3 getIntersection(Vec3 camera, Vec3 ray) {
        ray = ray.normalize();
        if (forward.dotProd(ray) == 0) {
            return null;
        }
        double t = (forward.dotProd(origin) - forward.dotProd(camera)) / forward.dotProd(ray);
        if (t < 0) {
            return null;
        }
        var intersection = camera.add(ray.mult(t));
        var local = toLocalCoordinates(intersection);
        if (Math.abs(local.z()) > width / 2 || Math.abs(local.y()) > height / 2) {
            return null;
        }
        return intersection;
    }

    @Override
    public Color getAmbientColor(Vec3 pos) {
        if ((Math.floor(pos.x()) + Math.floor(pos.z())) % 2 == 0) {
            return material.getAmbientColor();
        } else {
            return ColorUtil.mult(Color.WHITE, material.ambientStrength());
        }
    }

    @Override
    public Color getDiffuseColor(Vec3 pos) {
        if ((Math.floor(pos.x()) + Math.floor(pos.z())) % 2 == 0) {
            return material.getDiffuseColor();
        } else {
            return ColorUtil.mult(Color.WHITE, material.diffuseStrength());
        }
    }

    @Override
    public Vec3 getNormal(Vec3 pos) {
        return forward.normalize();
    }
}
