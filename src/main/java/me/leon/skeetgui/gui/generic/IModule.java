package me.leon.skeetgui.gui.generic;

import java.util.ArrayList;

public interface IModule {
    String getName();
    String getDescription();
    ICategory getCategory();
    ArrayList<ISetting> getSettings();

    boolean isEnabled();
    void setEnabled(boolean enabled);
    boolean isVisible();
    void setVisible(boolean visible);
}
