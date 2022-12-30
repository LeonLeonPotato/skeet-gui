package me.leon.skeetgui.gui.utils.font;

import me.leon.skeetgui.gui.SkeetGUI;
import me.leon.skeetgui.gui.utils.IUtil;

import java.awt.*;

public class FontUtil implements IUtil {
    public static float drawStringWithShadow(String text, double x, double y, Color color) {
        return drawStringWithShadow(SkeetGUI.getConfig().useCustomFont(), text, x, y, color);
    }

    public static float drawStringWithShadow(boolean customFont, String text, double x, double y, Color color) {
        if (customFont) {
            return SkeetGUI.fontRenderer.drawStringWithShadow(text, x, y, color);
        } else {
            return mc.fontRenderer.drawStringWithShadow(text, (int) x, (int) y, color.getRGB());
        }
    }

    public static int getStringWidth(boolean customFont, String string) {
        if (customFont) {
            return SkeetGUI.fontRenderer.getStringWidth(string);
        } else {
            return mc.fontRenderer.getStringWidth(string);
        }
    }

    public static int getFontHeight(boolean customFont) {
        if (customFont) {
            return SkeetGUI.fontRenderer.getHeight();
        } else {
            return mc.fontRenderer.FONT_HEIGHT;
        }
    }

    public static int getStringWidth(String string) {
        return getStringWidth(SkeetGUI.getConfig().useCustomFont(), string);
    }

    public static int getFontHeight() {
        return getFontHeight(SkeetGUI.getConfig().useCustomFont());
    }
}
