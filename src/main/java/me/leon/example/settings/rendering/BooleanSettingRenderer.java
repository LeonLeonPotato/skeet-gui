package me.leon.example.settings.rendering;

import me.leon.example.settings.impl.BooleanSetting;
import me.leon.skeetgui.gui.buttons.ModuleComponent;
import me.leon.skeetgui.gui.buttons.sub.ISettingComponent;
import me.leon.skeetgui.gui.utils.Quad;
import me.leon.skeetgui.gui.utils.RenderUtil;
import me.leon.skeetgui.gui.utils.font.FontUtil;

import java.awt.*;

public class BooleanSettingRenderer extends ISettingComponent<BooleanSetting> {
    private final Quad button;

    public BooleanSettingRenderer(ModuleComponent parent, BooleanSetting set) {
        super(parent, set);
        button = new Quad(5, 5, 11, 11);
    }

    // TODO: implement descriptions
    @Override
    public void render(int mouseX, int mouseY) {
        box.setXY(parent.getBox().getX(), parent.getBox().getY() + offset);
        button.setXY(box.getX() + 5, box.getY() + 5);
        //RenderUtil.drawBox(box, RenderUtil.alpha(100, Color.WHITE)); //debug
        RenderUtil.drawBox(button, new Color(40, 40, 40));
        RenderUtil.drawOutline(button, new Color(0, 0, 0), 0.5f);
        FontUtil.drawStringWithShadow(set.getName(), box.getX() + 16, box.getY() + 9 - (FontUtil.getFontHeight() / 2f), Color.WHITE);
        if(set.isEnabled()) {
            RenderUtil.drawBox(button.clone().shrink(0.5f), new Color(0xBCF54D));
        }
    }

    @Override
    public void update(int mouseX, int mouseY) {

    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if(this.button.isWithinIncluding(mouseX, mouseY) && button == 0) {
            set.toggle();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY, int button) {
        return false;
    }

    @Override
    public boolean keyTyped(char character, int key) {
        return false;
    }

    @Override
    public float getHeight() {
        return 16;
    }

    @Override
    public ModuleComponent parent() {
        return parent;
    }
}
