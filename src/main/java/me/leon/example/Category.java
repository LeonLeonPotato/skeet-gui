package me.leon.example;

import me.leon.skeetgui.gui.generic.ICategory;
import net.minecraft.util.ResourceLocation;

/**
 * Example category class to make the example work
 * To be repleaced with your own, however does need the icon path to each page
 */
public enum Category implements ICategory {
    COMBAT("sub_librator_icon.png"),
    MOVEMENT("sub_pioneer_icon.png"),
    PLAYER("sub_fortress_icon.png");

    private final String path;
    Category(String path) {
        this.path = path;
    }

    @Override
    public ResourceLocation getPath() {
        return new ResourceLocation("skeetgui", "icons/" + path);
    }
}
