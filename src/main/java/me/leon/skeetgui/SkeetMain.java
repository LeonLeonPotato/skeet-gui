package me.leon.skeetgui;

import me.leon.skeetgui.gui.SkeetGUI;
import me.leon.skeetgui.utils.font.CFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@Mod(modid = SkeetMain.MODID, name = SkeetMain.NAME, version = SkeetMain.VERSION)
public class SkeetMain {
    public static final String MODID = "skeetgui";
    public static final String NAME = "SkeetGUI";
    public static final String VERSION = "1.0";

    public static Logger logger;

    public static CFontRenderer fontRenderer;
    public static SkeetGUI skeetGUI;
    public static SkeetMain INSTANCE;

    @Mod.EventHandler
    public void onInit(FMLPreInitializationEvent event) {
        INSTANCE = new SkeetMain();
        MinecraftForge.EVENT_BUS.register(this);

        logger = LogManager.getLogger(NAME);

        fontRenderer = new CFontRenderer(new Font("Verdana", Font.PLAIN, 12), true, true);
        skeetGUI = new SkeetGUI();
    }

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        if(mc.player != null && mc.world != null) {
            if(Keyboard.isCreated()) {
                if(Keyboard.getEventKeyState()) {
                    final int key = Keyboard.getEventKey();
                    if(key == SkeetGUI.GUI_KEY && mc.currentScreen != skeetGUI) {
                        mc.displayGuiScreen(skeetGUI);
                        skeetGUI.onGuiOpened();
                    } else if(key == SkeetGUI.GUI_KEY_CLOSE && mc.currentScreen == skeetGUI) {
                        mc.displayGuiScreen(null);
                    }
                }
            }
        }
    }

    public SkeetMain() { }
}
