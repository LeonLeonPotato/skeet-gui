package me.leon.skeetgui.gui.utils;

import net.minecraft.client.gui.ScaledResolution;

public class Quad implements IUtil, Cloneable {
    // x1, y1 = bigger | x, y = smaller
    private float x, y, x1, y1;

    public Quad() {}

    public Quad(ScaledResolution res) {
        x = 0;
        y = 0;
        x1 = res.getScaledWidth();
        y1 = res.getScaledHeight();
    }

    public Quad(float x, float y, float x1, float y1) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
    }

    public Quad(double x, double y, double x1, double y1) {
        this((float) x, (float) y, (float) x1, (float) y1);
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

    public Quad fit(Quad other) {
        x = Math.min(other.x, x);
        x1 = Math.max(other.x1, x1);
        y = Math.min(other.y, y);
        y1 = Math.max(other.y1, y1);
        return this;
    }

    public Quad limit(Quad other) {
        x = Math.max(other.x, x);
        x1 = Math.min(other.x1, x1);
        y = Math.max(other.y, y);
        y1 = Math.min(other.y1, y1);
        return this;
    }

    public Quad fit(float x, float y) {
        this.x = Math.min(x, this.x);
        this.y = Math.min(y, this.y);
        this.x1 = Math.max(x, this.x1);
        this.y1 = Math.max(y, this.y1);
        return this;
    }

    public Quad limit(float x, float y) {
        this.x = Math.max(x, this.x);
        this.y = Math.max(y, this.y);
        this.x1 = Math.min(x, this.x1);
        this.y1 = Math.min(y, this.y1);
        return this;
    }

    public Quad expand(float amount) {
        return shrink(-amount);
    }

    public Quad shrink(float amount) {
        x += amount;
        y += amount;
        x1 -= amount;
        y1 -= amount;
        return this;
    }

    public Quad shrinkX(float amount) {
        x += amount;
        x1 -= amount;
        return this;
    }

    public Quad shrinkY(float amount) {
        y += amount;
        y1 -= amount;
        return this;
    }

    public float[][] iteratePoints() {
        return new float[][] {
                {x, y}, {x, y1}, {x1, y1}, {x1, y}
        };
    }

    public Quad shift(float x, float y) {
        this.x += x;
        this.y += y;
        this.x1 += x;
        this.y1 += y;
        return this;
    }

    public Quad setXY(float x, float y) {
        float w = width(), h = height();
        this.x = x;
        this.y = y;
        this.x1 = x + w;
        this.y1 = y + h;
        return this;
    }

    @Override
    public Quad clone() {
        return new Quad(x, y, x1, y1);
    }

    @Override
    public String toString() {
        return "Quad{" +
                "x=" + x +
                ", y=" + y +
                ", x1=" + x1 +
                ", y1=" + y1 +
                '}';
    }

    public float width() {
        return x1 - x;
    }

    public float height() {
        return y1 - y;
    }

    public float getCenterX() {
        return (x + x1) / 2f;
    }

    public float getCenterY() {
        return (y + y1) / 2f;
    }

    public float getX() {
        return x;
    }

    public Quad setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Quad setY(float y) {
        this.y = y;
        return this;
    }

    public float getX1() {
        return x1;
    }

    public Quad setX1(float x1) {
        this.x1 = x1;
        return this;
    }

    public float getY1() {
        return y1;
    }

    public Quad setY1(float y1) {
        this.y1 = y1;
        return this;
    }
}
