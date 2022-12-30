package me.leon.example;

import me.leon.skeetgui.gui.generic.IModule;
import me.leon.skeetgui.gui.generic.ISetting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;

public abstract class Module implements IModule {
    protected static final Minecraft mc = Minecraft.getMinecraft();

    private final String name;
    private final String description;
    private final Category category;
    private final ArrayList<ISetting> settings;

    private boolean enabled, visible = true;

    public Module(String name, String desc, Category category) {
        this.name = name;
        this.description = desc;
        this.category = category;
        this.settings = new ArrayList<>();
    }

    public abstract void onLoad();
    public abstract void onTick();
    public abstract void onEnable();
    public abstract void onDisable();

    protected boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    @Override
    public ArrayList<ISetting> getSettings() {
        return settings;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if(enabled) {
            MinecraftForge.EVENT_BUS.register(this);
            onEnable();
        } else {
            MinecraftForge.EVENT_BUS.unregister(this);
            onDisable();
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
