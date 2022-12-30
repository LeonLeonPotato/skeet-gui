package me.leon.skeetgui.gui.utils;

import java.awt.*;

public class SkeetUtils {
    /**
     * renders the classic Skeet Box
     * @param quad the quad
     * @return the inside of the box
     */
    public static Quad renderSkeetBox(Quad quad) {
        quad = quad.clone();
        RenderUtil.drawBox(quad, new Color(25, 26, 26, 255));
        quad.shrink(0.5f);
        RenderUtil.drawBox(quad, new Color(45, 45, 45, 255));
        quad.shrink(0.5f);
        RenderUtil.drawBox(quad, new Color(51, 51, 51, 255));
        quad.shrink(0.5f);
        RenderUtil.drawBox(quad, new Color(40, 40, 40, 255));
        quad.shrink(1.5f);
        RenderUtil.drawBox(quad, new Color(51, 51, 51, 255));
        quad.shrink(0.5f);
        RenderUtil.drawBox(quad, new Color(45, 45, 45, 255));
        quad.shrink(0.5f);
        RenderUtil.drawBox(quad, new Color(16, 16, 16, 255));

        final Quad rainbow = new Quad(quad.getX(), quad.getY(), quad.getX1(), quad.getY() + 0.5f);
        final Quad rainbow0 = new Quad(quad.getX(), quad.getY() + 0.5f, quad.getX1(), quad.getY() + 1.0f);
        RenderUtil.drawRainbowX(rainbow, 193f, 1f, 0.87f, 1f, 1f, 255);
        RenderUtil.drawRainbowX(rainbow0, 193f, 1f, 0.5f, 1f, 1f, 255);

        quad.setY(quad.getY() + 1);

        return quad;
    }
}
