package me.leon.skeetgui.gui;

public abstract class Component {
    protected abstract void updateComponent();
    protected abstract void render();

    protected abstract boolean mouseClicked(final int mouseX, final int mouseY, final int button);
    protected abstract boolean mouseReleased(final int mouseX, final int mouseY, final int button);
    protected abstract boolean keyTyped(final char character, final int key);

}
