package me.leon.example.settings.impl;

import me.leon.example.settings.Setting;
import me.leon.example.settings.rendering.SliderSettingRenderer;
import me.leon.skeetgui.gui.buttons.sub.ISettingComponent;
import me.leon.skeetgui.gui.generic.ISetting;

public class SliderSetting extends Setting {
    private final float min, max;
    private final boolean onlyInt;
    private float value;

    public SliderSetting(String name, String description, boolean onlyInt, float min, float max, float value) {
        super(name, description);
        this.min = min;
        this.max = max;
        this.onlyInt = onlyInt;
        this.value = value;
    }

    @Override
    public Class<? extends ISettingComponent<? extends ISetting>> getRendererClass() {
        return SliderSettingRenderer.class;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public boolean isOnlyInt() {
        return onlyInt;
    }

    public float getValue() {
        return value;
    }

    public SliderSetting setValue(float value) {
        this.value = value;
        return this;
    }
}
