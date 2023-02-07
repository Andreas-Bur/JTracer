package util;

import java.awt.*;

public class ColorUtil {
    public static Color mult(Color c, double i) {
        int r = (int) Math.max(0, Math.min(255, c.getRed() * i));
        int g = (int) Math.max(0, Math.min(255, c.getGreen() * i));
        int b = (int) Math.max(0, Math.min(255, c.getBlue() * i));
        return new Color(r, g, b);
    }

    public static Color add(Color x, Color y) {
        int r = Math.min(255, x.getRed() + y.getRed());
        int g = Math.min(255, x.getGreen() + y.getGreen());
        int b = Math.min(255, x.getBlue() + y.getBlue());
        return new Color(r, g, b);
    }
}
