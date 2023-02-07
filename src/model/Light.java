package model;

import util.Vec3;

import java.awt.*;

public class Light extends Point {

    protected double diffuseIntensity = 3;
    protected double specularIntensity = 2;
    protected Color color = Color.WHITE;

    public Light(Vec3 origin) {
        super(origin, new Vec3(1, 0, 0), new Vec3(0, 1, 0));
    }

    public double getDiffuseIntensity() {
        return diffuseIntensity;
    }

    public double getSpecularIntensity() {
        return specularIntensity;
    }

    public Color getColor() {
        return color;
    }
}
