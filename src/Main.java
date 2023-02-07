import model.*;
import util.Material;
import util.Vec3;

import javax.swing.*;
import java.awt.*;

public class Main {

    private Scene setupScene() {
        Camera camera = new Camera(new Vec3(-5, 3, 0), new Vec3(1, -0.5, 0), new Vec3(0.5, 1, 0), 90, 1);
        Light light = new Light(new Vec3(-3, 3, 3));

        Material material1 = new Material(new Color(200, 20, 20), 0.3, 0.2, 0.3, 20);
        Material material2 = new Material(new Color(150, 150, 20), 0.3, 0.4, 0.6, 40);
        Material material3 = new Material(Color.DARK_GRAY, 0.3, 0.2, 0.3, 30);

        Sphere sphere1 = new Sphere(new Vec3(0, 0, 0), new Vec3(1, 0, 0), new Vec3(0, 1, 0), 1, material1);
        Sphere sphere2 = new Sphere(new Vec3(1, 0, 1), 1.7, material2);

        Plane plane1 = new Plane(new Vec3(0, -2, 0), new Vec3(0, 1, 0), new Vec3(1, 0, 0), material3, 12, 160);

        Scene scene = new Scene(camera, light);
        scene.getGeometries().add(sphere2);
        scene.getGeometries().add(sphere1);
        scene.getGeometries().add(plane1);
        return scene;
    }

    public Main() {
        var scene = setupScene();
        var renderer = new Renderer(scene);

        var image = renderer.render(1080, 720);

        var imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(image));

        var frame = new JFrame();
        frame.getContentPane().add(imageLabel);
        frame.setSize(1080, 720);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Main();
    }
}