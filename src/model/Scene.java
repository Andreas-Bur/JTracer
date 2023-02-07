package model;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Scene {
    private final Camera camera;
    private final Light light;
    private final Set<Geometry> geometries;

    private final Color bgColor = Color.GRAY;
    private final double ambientIntensity = 1;

    public Scene(Camera camera, Light light) {
        this.camera = camera;
        this.light = light;
        geometries = new HashSet<>();
    }

    public Camera getCamera() {
        return camera;
    }

    public Light getLight() {
        return light;
    }

    public Set<Geometry> getGeometries() {
        return geometries;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public double getAmbientIntensity() {
        return ambientIntensity;
    }
}
