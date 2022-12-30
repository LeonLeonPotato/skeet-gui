package me.leon.example.settings.impl;

import me.leon.example.settings.Setting;
import me.leon.skeetgui.gui.buttons.sub.ISettingComponent;
import me.leon.skeetgui.gui.generic.ISetting;

public class ASetting extends Setting {
    public ASetting(String name, String description) {
        super(name, description);
    }

    @Override
    public Class<? extends ISettingComponent<? extends ISetting>> getRendererClass() {
        return null;
    }
}
