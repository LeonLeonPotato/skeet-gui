package me.leon.skeetgui.gui;
import me.leon.skeetgui.SkeetMain;
import me.leon.skeetgui.utils.Quad;
import me.leon.skeetgui.utils.SkeetUtils;
import me.leon.skeetgui.utils.font.FontUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

/**
 * @author leon
 *
 * attempt of porting Skeet.cc's GUI into minecraft <br>
 * you can change some variables here to your liking
 *
 * notes:
 * SubSettings are not supported, Phobos-like toggling visibility of options is also not supported
 *
 * @see SkeetMain for listeners
 */
public class SkeetGUI extends GuiScreen {
    // the key to open the gui
    public static int GUI_KEY = Keyboard.KEY_RSHIFT;

    // key to close the gui
    public static int GUI_KEY_CLOSE = Keyboard.KEY_ESCAPE;

    // the background of the gui - args: BLUR, DARKEN, NONE, BOTH
    public static String BACKGROUND = "BLUR";

    // pause the game when opening the gui?
    public static boolean PAUSE_GAME = false;

    public SkeetGUI() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        SkeetUtils.renderSkeetBox(new Quad(10, 10, 400, 400));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void onGuiClosed() {
        switch (BACKGROUND) {
            case "BOTH":
            case "BLUR": {
                mc.entityRenderer.stopUseShader();
            }
        }
    }

    public void onGuiOpened() {
        switch (BACKGROUND) {
            case "BOTH":
            case "BLUR": {
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
        return PAUSE_GAME;
    }
}
