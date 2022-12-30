package me.leon.example;

import me.leon.example.utils.ModuleManager;
import me.leon.example.utils.ProjectionManager;
import me.leon.skeetgui.gui.SkeetGUI;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

/**
 * Main class, for loading in the GUI
 * Can be replaced with your own main class, however it does require you to init the objects listed here.
 */
@Mod(modid = SkeetMain.MODID, name = SkeetMain.NAME, version = SkeetMain.VERSION)
public class SkeetMain {
    public static final String MODID = "skeetgui";
    public static final String NAME = "SkeetGUI";
    public static final String VERSION = "1.0";

    public static Logger logger;

    public static SkeetGUI skeetGUI;
    public static SkeetMain INSTANCE;

    public static ModuleManager moduleManager;
    public static ProjectionManager projectionManager;

    @Mod.EventHandler
    public void onInit(FMLPreInitializationEvent event) {
        INSTANCE = new SkeetMain();
        MinecraftForge.EVENT_BUS.register(this);

        logger = LogManager.getLogger(NAME);

        SkeetGUI.setCategories(Category.values());
        SkeetGUI.setConfig(new GenericConfig());
        SkeetGUI.setModules(moduleManager = new ModuleManager());

        skeetGUI = new SkeetGUI("Arial");

        projectionManager = new ProjectionManager();
    }

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        if(mc.player != null && mc.world != null) {
            if(Keyboard.isCreated()) {
                if(Keyboard.getEventKeyState()) {
                    final int key = Keyboard.getEventKey();
                    if(key == SkeetGUI.config.getGuiKey() && mc.currentScreen != skeetGUI) {
                        mc.displayGuiScreen(skeetGUI);
                        skeetGUI.onGuiOpened();
                    } else if(key == SkeetGUI.config.getCloseGuiKey() && mc.currentScreen == skeetGUI) {
                        mc.displayGuiScreen(null);
                    }
                }
            }
        }
    }

    public SkeetMain() { }
}
