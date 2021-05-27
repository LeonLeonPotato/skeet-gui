package me.leon.skeetgui.utils;

import net.minecraft.client.gui.ScaledResolution;

public class Quad implements IUtil {
    // x1, y1 = bigger | x, y = smaller
    private float x, y, x1, y1;

    public Quad() {}

    public Quad(float x, float y, float x1, float y1) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
    }

    public boolean isWithinIncluding(float x, float y) {
        return x >= this.x && x <= this.x1 && y >= this.y && y <= this.y1;
    }

    public boolean isWithin(float x, float y) {
        return x > this.x && x < this.x1 && y > this.y && y < this.y1;
    }

    public boolean insideScreen() {
        return insideScreenX() && insideScreenY();
    }

    public boolean insideScreenX() {
        final ScaledResolution res = new ScaledResolution(mc);

        return (x >= 0 && x1 <= res.getScaledWidth());
    }

    public boolean insideScreenY() {
        final ScaledResolution res = new ScaledResolution(mc);

        return (y >= 0 && y <= res.getScaledHeight());
    }

    public void shrink(float amount) {
        setX(getX() + amount);
        setY(getY() + amount);
        setX1(getX1() - amount);
        setY1(getY1() - amount);
    }

    public Quad clone() {
        return new Quad(x, y, x1, y1);
    }

    public float width() {
        return x1 - x;
    }

    public float height() {
        return y1 - y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }
}
