package me.leon.example;

import me.leon.skeetgui.gui.BackgroundOptions;
import me.leon.skeetgui.gui.generic.ISkeetConfig;
import me.leon.skeetgui.gui.utils.Quad;
import org.lwjgl.input.Keyboard;

public class GenericConfig implements ISkeetConfig {
    @Override
    public int getGuiKey() {
        return Keyboard.KEY_RSHIFT;
    }

    @Override
    public int getCloseGuiKey() {
        return Keyboard.KEY_ESCAPE;
    }

    @Override
    public BackgroundOptions getBackground() {
        return BackgroundOptions.BLUR;
    }

    @Override
    public boolean doesPauseGame() {
        return false;
    }

    @Override
    public boolean useCustomFont() {
        return true;
    }

    @Override
    public int getColumnsPerPage() {
        return 2;
    }

    @Override
    public Quad getBoundingBox() {
        return new Quad(10, 10, 410, 360);
    }
}
