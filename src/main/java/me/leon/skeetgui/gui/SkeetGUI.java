package me.leon.skeetgui.gui;

import me.leon.example.Category;
import me.leon.example.SkeetMain;
import me.leon.skeetgui.gui.generic.ICategory;
import me.leon.skeetgui.gui.generic.IModuleManager;
import me.leon.skeetgui.gui.generic.ISkeetConfig;
import me.leon.skeetgui.gui.utils.Quad;
import me.leon.skeetgui.gui.utils.RenderUtil;
import me.leon.skeetgui.gui.utils.SkeetUtils;
import me.leon.skeetgui.gui.utils.font.CFontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author leon
 *
 * attempt of porting Skeet.cc's GUI into minecraft <br>
 * you can change some variables here to your liking
 * notes:
 * SubSettings are not supported, Phobos-like toggling visibility of options is also not supported
 *
 * @see SkeetMain for listeners
 * @see me.leon.skeetgui.gui.generic.ISkeetConfig for configuration
 */
public class SkeetGUI extends GuiScreen {
    private static boolean CREATED = false;
    public static ICategory[] categories;
    public static ISkeetConfig config;
    public static IModuleManager modules;
    public static CFontRenderer fontRenderer;

    public static Quad OUTSIDE;
    public static Quad INSIDE; // the inside of the box, for convenience

    public static ArrayList<PageComp> pages;
    public static PageComp selectedPage;

    private static float dragX, dragY;
    private static boolean dragging = false;

    public SkeetGUI(String font) {
        if(categories == null || config == null || modules == null)
            throw new RuntimeException("Please set the constants first! Refer to the examples for instructions.");
        if(CREATED)
            throw new RuntimeException("Instance already created!");

        MinecraftForge.EVENT_BUS.register(this);
        fontRenderer = new CFontRenderer(new Font(font, Font.PLAIN, 18), true, true);

        OUTSIDE = config.getBoundingBox();
        INSIDE = OUTSIDE.clone()
                .shrink(4);
        INSIDE.setY(INSIDE.getY() + 1);

        pages = new ArrayList<>();
        int off = 10;
        for (Category c : Category.values()) {
            PageComp p = new PageComp(c, off);
            pages.add(p);
            off += p.getHeight() + 0.5f;
        }

        selectedPage = pages.get(0);
        CREATED = true;
    }

    public static ICategory[] getCategories() {
        return categories;
    }

    public static void setCategories(ICategory[] categories) {
        SkeetGUI.categories = categories;
    }

    public static ISkeetConfig getConfig() {
        return config;
    }

    public static void setConfig(ISkeetConfig config) {
        SkeetGUI.config = config;
    }

    public static boolean isCREATED() {
        return CREATED;
    }

    public static IModuleManager getModules() {
        return modules;
    }

    public static void setModules(IModuleManager modules) {
        SkeetGUI.modules = modules;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if(dragging)
            OUTSIDE.setXY(mouseX - dragX, mouseY - dragY);

        INSIDE = SkeetUtils.renderSkeetBox(OUTSIDE);
        for(PageComp p : pages) {
            p.update(mouseX, mouseY);
        }
        for(PageComp p : pages) {
            p.render(mouseX, mouseY);
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(INSIDE.getX(), 0, 0);
        RenderUtil.drawLine(64, INSIDE.getY(), 64, selectedPage.getClickableBox().getY(), 0.5f, Color.WHITE);
        RenderUtil.drawLine(0, selectedPage.getClickableBox().getY(), 64, selectedPage.getClickableBox().getY(), 0.5f, Color.WHITE);
        RenderUtil.drawLine(0, selectedPage.getClickableBox().getY(), 0, selectedPage.getClickableBox().getY1(), 0.5f, Color.WHITE);
        RenderUtil.drawLine(0, selectedPage.getClickableBox().getY1(), 64, selectedPage.getClickableBox().getY1(), 0.5f, Color.WHITE);
        RenderUtil.drawLine(64, selectedPage.getClickableBox().getY1(), 64, INSIDE.getY1(), 0.5f, Color.WHITE);
        GlStateManager.popMatrix();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        boolean broken = false;
        for(PageComp page : pages) {
            if(page.mouseClicked(mouseX, mouseY, mouseButton)) {
                broken = true;
                break;
            }
        }
        if(!broken) {
            if(OUTSIDE.isWithinIncluding(mouseX, mouseY)) {
                dragging = true;
                dragX = mouseX - OUTSIDE.getX();
                dragY = mouseY - OUTSIDE.getY();
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = false;
        for(PageComp page : pages) {
            if(page.mouseReleased(mouseX, mouseY, state)) {
                break;
            }
        }
    }

    @Override
    public void onGuiClosed() {
        switch (config.getBackground()) {
            case BOTH:
            case BLUR: {
                mc.entityRenderer.stopUseShader();
            }
        }
    }

    public void onGuiOpened() {
        switch (config.getBackground()) {
            case BOTH:
            case BLUR: {
                mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
            }
        }
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return config.doesPauseGame();
    }
}
