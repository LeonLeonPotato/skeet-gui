package me.leon.example.settings.impl;

import me.leon.example.settings.Setting;
import me.leon.example.settings.rendering.BooleanSettingRenderer;
import me.leon.skeetgui.gui.buttons.sub.ISettingComponent;
import me.leon.skeetgui.gui.generic.ISetting;

public class BooleanSetting extends Setting {
    private boolean value;

    public BooleanSetting(String name, String description, boolean value) {
        super(name, description);
        this.value = value;
    }

    public void toggle() {
        value = !value;
    }

    public boolean isEnabled() {
        return value;
    }

    public BooleanSetting setValue(boolean value) {
        this.value = value;
        return this;
    }

    @Override
    public Class<? extends ISettingComponent<? extends ISetting>> getRendererClass() {
        return BooleanSettingRenderer.class;
    }
}
