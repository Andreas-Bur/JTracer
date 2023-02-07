package util;

import java.awt.*;

public record Material(Color baseColor, double ambientStrength, double diffuseStrength, double specularStrength,
                       double shininess) {

    public Material(Color color) {
        this(color, 0.3, 0.3, 0.5, 20);
    }

    public Color getAmbientColor() {
        return ColorUtil.mult(baseColor, ambientStrength);
    }

    public Color getDiffuseColor() {
        return ColorUtil.mult(baseColor, diffuseStrength);
    }

}
