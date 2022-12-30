package me.leon.skeetgui.gui;

import net.minecraft.client.Minecraft;

public abstract class Component {
    protected final static Minecraft mc = Minecraft.getMinecraft();

    public abstract void render(int x, int y);
    public abstract void update(int x, int y);

    public abstract boolean mouseClicked(final int mouseX, final int mouseY, final int button);
    public abstract boolean mouseReleased(final int mouseX, final int mouseY, final int button);
    public abstract boolean keyTyped(final char character, final int key);

    public abstract float getHeight();

    public abstract Component parent();
    public String description() { return ""; }

//    protected void drawRect(int x, int y, int x1, int y1, Color color) {
//        RenderUtil.drawBox(new Quad(x, y, x1, y1), color);
//    }
//    protected void drawRect(float x, float y, float x1, float y1, Color color) {
//        RenderUtil.drawBox(new Quad(x, y, x1, y1), color);
//    }
}
