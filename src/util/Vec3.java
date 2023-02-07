package util;

public record Vec3(double x, double y, double z) {
    public Vec3 add(Vec3 a) {
        return new Vec3(x + a.x(), y + a.y(), z + a.z());
    }

    public Vec3 sub(Vec3 a) {
        return new Vec3(x - a.x(), y - a.y(), z - a.z());
    }

    public Vec3 mult(double d) {
        return new Vec3(x * d, y * d, z * d);
    }

    public Vec3 div(double d) {
        return new Vec3(x / d, y / d, z / d);
    }

    public Vec3 crossProd(Vec3 v) {
        double a = y * v.z() - z * v.y();
        double b = z * v.x() - x * v.z();
        double c = x * v.y() - y * v.x();
        return new Vec3(a, b, c);
    }

    public double dotProd(Vec3 v) {
        return x * v.x() + y * v.y() + z * v.z();
    }

    public Vec3 normalize() {
        double length = length();
        return this.div(length);
    }

    public double length() {
        return Math.sqrt(this.dotProd(this));
    }

}
