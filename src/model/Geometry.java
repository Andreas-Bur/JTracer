package model;

import util.Material;
import util.Vec3;

import java.awt.*;

public abstract class Geometry extends Point {

    protected Material material;

    public Geometry(Vec3 origin, Vec3 forward, Vec3 up, Material material) {
        super(origin, forward, up);
        this.material = material;
    }

    public abstract Vec3 getIntersection(Vec3 origin, Vec3 ray);

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Color getAmbientColor(Vec3 pos) {
        return material.getAmbientColor();
    }

    public Color getDiffuseColor(Vec3 pos) {
        return material.getDiffuseColor();
    }

    public Vec3 getNormal(Vec3 pos) {
        return pos.sub(origin).normalize();
    }

    public Vec3 toLocalCoordinates(Vec3 vec) {
        var v = vec.sub(origin);
        var right = getRight();

        double x = forward.x() * v.x() + up.x() * v.y() + right.x() * v.z();
        double y = forward.y() * v.x() + up.y() * v.y() + right.y() * v.z();
        double z = forward.z() * v.x() + up.z() * v.y() + right.z() * v.z();
        return new Vec3(x, y, z);
    }
}
