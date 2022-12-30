package me.leon.skeetgui.gui.buttons;

import me.leon.skeetgui.gui.ButtonComponent;
import me.leon.skeetgui.gui.Component;
import me.leon.skeetgui.gui.PageComp;
import me.leon.skeetgui.gui.SkeetGUI;
import me.leon.skeetgui.gui.buttons.sub.EnabledVisibleComponent;
import me.leon.skeetgui.gui.buttons.sub.ISettingComponent;
import me.leon.skeetgui.gui.generic.IModule;
import me.leon.skeetgui.gui.generic.ISetting;
import me.leon.skeetgui.gui.utils.Quad;
import me.leon.skeetgui.gui.utils.RenderUtil;
import me.leon.skeetgui.gui.utils.font.FontUtil;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.awt.*;
import java.util.Arrays;

public class ModuleComponent extends ButtonComponent {
    public static final float MODULE_WIDTH = (SkeetGUI.INSIDE.width() - 64 - (SkeetGUI.getConfig().getColumnsPerPage() + 1) * 15) / SkeetGUI.getConfig().getColumnsPerPage();

    public final IModule module;
    private final EnabledVisibleComponent enabledVisibleComponent;

    public ModuleComponent(IModule module, PageComp page, float offset, int column) {
        super(page, offset, column);
        this.module = module;

        try {
            for(ISetting setting : module.getSettings()) {
                Class<? extends ISettingComponent<? extends ISetting>> clazz = setting.getRendererClass();
                // TODO: FIX LMAO
                ISettingComponent<? extends ISetting> settingComponent =
                        (ISettingComponent<? extends ISetting>) clazz.getDeclaredConstructors()[0]
                                .newInstance(this, setting);
                subs.add(settingComponent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            FMLCommonHandler.instance().exitJava(0, true);
        }

        enabledVisibleComponent = new EnabledVisibleComponent(this);
        this.box = new Quad(0, 0, MODULE_WIDTH, getHeight());
    }

    @Override
    public void render(int x, int y) {
        super.render(x, y);
        float length = FontUtil.getStringWidth(module.getName());
        float inc = FontUtil.getFontHeight() / 2f + 1;
        RenderUtil.drawBox(new Quad(box.getX() + 14, box.getY() - inc, box.getX() + 16 + length, box.getY() + inc),
                new Color(16, 16, 16));
        FontUtil.drawStringWithShadow(module.getName(), box.getX() + 15, box.getY() - inc + 2, Color.WHITE);

        float offset = 5;
        enabledVisibleComponent.setOffset(offset);
        enabledVisibleComponent.render(x, y);
        offset += enabledVisibleComponent.getHeight() + 0.5f;

        for(Component comp : subs) {
            if (comp instanceof ISettingComponent) {
                @SuppressWarnings(value = "unchecked")
                ISettingComponent<? extends ISetting> setting = (ISettingComponent<? extends ISetting>) comp;

                setting.setOffset(offset);
                setting.render(x, y);
                offset += setting.getHeight() + 0.5f;
            }
        }
    }

    @Override
    public void update(int x, int y) {
        for(Component b : subs) b.update(x, y);
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        boolean ret = false;
        if(box.isWithinIncluding(mouseX, mouseY)) {
            if (enabledVisibleComponent.mouseClicked(mouseX, mouseY, button)) return true;
            for(Component c : subs) {
                if(c.mouseClicked(mouseX, mouseY, button)) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY, int button) {
        boolean ret = false;
        if (enabledVisibleComponent.mouseReleased(mouseX, mouseY, button)) return true;
        for(Component c : subs) {
            if(c.mouseReleased(mouseX, mouseY, button)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    @Override
    public boolean keyTyped(char character, int key) {
        return false;
    }

    @Override
    public float getHeight() {
        float height = enabledVisibleComponent.getHeight() + 5f;
        for(Component comp : subs) {
            height += comp.getHeight() + 0.5f;
        }
        height += 5;
        return height;
    }

    @Override
    public PageComp parent() {
        return parent;
    }
}
