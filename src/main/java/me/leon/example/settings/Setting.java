package me.leon.example.settings;

import me.leon.skeetgui.gui.generic.IModule;
import me.leon.skeetgui.gui.generic.ISetting;

public abstract class Setting implements ISetting {
    protected final String name, description;
    protected IModule module;

    public Setting(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public IModule getModule() {
        return module;
    }
}
