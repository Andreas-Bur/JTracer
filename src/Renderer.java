import model.Geometry;
import model.Light;
import model.Scene;
import util.ColorUtil;
import util.Intersection;
import util.Vec3;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class Renderer {
    private Scene scene;

    private static final double SHADOW_BIAS = 1e-10;

    public Renderer(Scene scene) {
        this.scene = scene;
    }

    public BufferedImage render(int width, int height) {
        var camera = scene.getCamera();
        var imgPlaneWidth = Math.tan(camera.getFov() / 2 * Math.PI / 180) * camera.getImagePlaneDist() * 2;
        var pixelSize = imgPlaneWidth / width;
        var imgPlaneHeight = height * pixelSize;

        var topLeft = camera.getForward()
                .add(camera.getUp().normalize().mult(imgPlaneHeight / 2))
                .add(camera.getRight().normalize().mult(-imgPlaneWidth / 2));

        var rightUnit = camera.getRight().normalize().mult(pixelSize);
        var downUnit = camera.getUp().normalize().mult(-pixelSize);

        var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                var color = getColor(x, y, camera.getOrigin(), topLeft, rightUnit, downUnit);
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }

    private Color getColor(int x, int y, Vec3 origin, Vec3 topLeft, Vec3 rightUnit, Vec3 downUnit) {
        var rays = getRays(x, y, topLeft, rightUnit, downUnit, 3);
        double r = 0;
        double g = 0;
        double b = 0;
        for (var ray : rays) {
            var c = getColor(origin, ray);
            r += c.getRed();
            g += c.getGreen();
            b += c.getBlue();
        }
        int ri = (int) (r / rays.size());
        int gi = (int) (g / rays.size());
        int bi = (int) (b / rays.size());
        return new Color(ri, gi, bi);
    }

    private Color getColor(Vec3 origin, Vec3 ray) {
        var intersection = getFirstIntersection(origin, ray);
        if (intersection == null) {
            return scene.getBgColor();
        }

        var color = getAmbientColor(intersection);
        if (isLit(intersection, scene.getLight())) {
            color = ColorUtil.add(color, getDiffuseColor(intersection));
            color = ColorUtil.add(color, getSpecularColor(intersection));
        }

        return color;
    }

    private Color getAmbientColor(Intersection intersection) {
        var color = intersection.geometry().getAmbientColor(intersection.pos());
        return ColorUtil.mult(color, scene.getAmbientIntensity());
    }

    private Color getDiffuseColor(Intersection intersection) {
        var normal = intersection.geometry().getNormal(intersection.pos());
        var light = scene.getLight().getOrigin().sub(intersection.pos()).normalize();

        var prod = light.dotProd(normal) * scene.getLight().getDiffuseIntensity();
        var diffuseColor = intersection.geometry().getDiffuseColor(intersection.pos());

        return ColorUtil.mult(diffuseColor, prod);
    }

    private Color getSpecularColor(Intersection intersection) {
        var normal = intersection.geometry().getNormal(intersection.pos());
        var light = scene.getLight().getOrigin().sub(intersection.pos()).normalize();
        var view = scene.getCamera().getOrigin().sub(intersection.pos()).normalize();

        var refFactor = 2 * light.dotProd(normal);
        var reflection = normal.mult(refFactor).sub(light).normalize();

        var specularStrength = intersection.geometry().getMaterial().specularStrength();
        var shininess = intersection.geometry().getMaterial().shininess();

        var prod = specularStrength * Math.pow(reflection.dotProd(view), shininess) * scene.getLight().getSpecularIntensity();

        var specularColor = ColorUtil.mult(scene.getLight().getColor(), prod);

        return specularColor;
    }

    private Intersection getFirstIntersection(Vec3 camera, Vec3 ray) {
        double zbuffer = Double.POSITIVE_INFINITY;
        Vec3 pos = null;
        Geometry geometry = null;

        for (Geometry g : scene.getGeometries()) {
            var intersection = g.getIntersection(camera, ray);
            if (intersection != null) {
                var distance = intersection.sub(camera).length();
                if (distance < zbuffer) {
                    zbuffer = distance;
                    pos = intersection;
                    geometry = g;
                }
            }
        }
        return geometry == null ? null : new Intersection(pos, geometry);
    }

    private boolean isLit(Intersection i, Light l) {
        var hit = getFirstIntersection(l.getOrigin(), i.pos().sub(l.getOrigin()));
        return hit.pos().sub(i.pos()).length() < SHADOW_BIAS;
    }

    private Set<Vec3> getRays(int x, int y, Vec3 topLeft, Vec3 rightUnit, Vec3 downUnit, int supersampling) {
        var rays = new HashSet<Vec3>();
        var offsets = new double[supersampling];
        for (int i = 0; i < supersampling; i++) {
            offsets[i] = 1.0 / (2 * supersampling) + i * (1.0 / supersampling);
        }
        for (double xOff : offsets) {
            for (double yOff : offsets) {
                rays.add(topLeft.add(rightUnit.mult(x + xOff)).add(downUnit.mult(y + yOff)));
            }
        }
        return rays;
    }
}
