package me.leon.skeetgui.gui.generic;

import me.leon.skeetgui.gui.buttons.sub.ISettingComponent;

public interface ISetting {
    String getName();
    String getDescription();

    IModule getModule();
    Class<? extends ISettingComponent<? extends ISetting>> getRendererClass();
}
