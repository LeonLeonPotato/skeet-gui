package me.leon.skeetgui.gui.generic;

import me.leon.skeetgui.gui.BackgroundOptions;
import me.leon.skeetgui.gui.utils.Quad;

public interface ISkeetConfig {
    int getGuiKey();
    int getCloseGuiKey();
    int getColumnsPerPage();
    boolean doesPauseGame();
    boolean useCustomFont();
    BackgroundOptions getBackground();
    Quad getBoundingBox();
}
