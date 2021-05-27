package me.leon.skeetgui.utils.font;

import me.leon.skeetgui.SkeetMain;
import me.leon.skeetgui.utils.IUtil;

import java.awt.*;

public class FontUtil implements IUtil {

    public static float drawStringWithShadow(boolean customFont, String text, int x, int y, Color color) {
        if (customFont) {
            return SkeetMain.fontRenderer.drawStringWithShadow(text, x, y, color);
        } else {
            return mc.fontRenderer.drawStringWithShadow(text, x, y, color.getRGB());
        }
    }

    public static int getStringWidth(boolean customFont, String string) {
        if (customFont) {
            return SkeetMain.fontRenderer.getStringWidth(string);
        } else {
            return mc.fontRenderer.getStringWidth(string);
        }
    }

    public static int getFontHeight(boolean customFont) {
        if (customFont) {
            return SkeetMain.fontRenderer.getHeight();
        } else {
            return mc.fontRenderer.FONT_HEIGHT;
        }
    }
}
