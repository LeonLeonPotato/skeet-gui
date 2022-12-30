package me.leon.example.settings.rendering;

import me.leon.example.settings.impl.SliderSetting;
import me.leon.skeetgui.gui.Component;
import me.leon.skeetgui.gui.buttons.ModuleComponent;
import me.leon.skeetgui.gui.buttons.sub.ISettingComponent;
import me.leon.skeetgui.gui.utils.MathUtils;
import me.leon.skeetgui.gui.utils.Quad;
import me.leon.skeetgui.gui.utils.RenderUtil;
import me.leon.skeetgui.gui.utils.font.FontUtil;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class SliderSettingRenderer extends ISettingComponent<SliderSetting> {
    private final Quad sliderBar;
    private boolean dragging = false;

    public SliderSettingRenderer(ModuleComponent parent, SliderSetting set) {
        super(parent, set);
        sliderBar = new Quad(16, 0, box.width() - 5, 4);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        box.setXY(parent.getBox().getX(), parent.getBox().getY() + offset);
        sliderBar.setXY(box.getX(), box.getY()).shift(16, 16);

        if(dragging) {
            float progress = (mouseX - sliderBar.getX()) / sliderBar.width();
            progress = (float) MathUtils.clamp(0, 1, progress);

            set.setValue(progress * (set.getMax() - set.getMin()) + set.getMin());
            set.setValue((float) (Math.floor(set.getValue() * 100) / 100f));
        }

        float range = set.getMax() - set.getMin();
        Quad filled = new Quad(0, 0, ((set.getValue() - set.getMin()) / range) * sliderBar.width(), sliderBar.height())
                .shift(sliderBar.getX(), sliderBar.getY());
        FontUtil.drawStringWithShadow(set.getName(), sliderBar.getX(), box.getY() + 9 - (FontUtil.getFontHeight() / 2f), Color.WHITE);
        RenderUtil.drawBox(sliderBar, new Color(40, 40, 40));
        RenderUtil.drawBox(filled, new Color(0xBCF54D));
        String renderText = String.valueOf(set.getValue());

        final float scaleFactor = 0.75f;
        GlStateManager.pushMatrix();
        GlStateManager.translate(filled.getX1() - (FontUtil.getStringWidth(renderText) / (2 + 1 / scaleFactor)), filled.getY1() - (FontUtil.getFontHeight() / (2 + 1 / scaleFactor)), 0);
        GlStateManager.scale(scaleFactor, scaleFactor, 0);
        FontUtil.drawStringWithShadow(renderText, 0, 0, Color.WHITE);
        GlStateManager.popMatrix();
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if(sliderBar.isWithinIncluding(mouseX, mouseY) && button == 0) {
            dragging = true;
        }
        return dragging;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY, int button) {
        return (dragging = false);
    }

    @Override
    public boolean keyTyped(char character, int key) {
        return false;
    }

    @Override
    public float getHeight() {
        return 24;
    }

    @Override
    public Component parent() {
        return null;
    }

    @Override
    public void update(int mouseX, int mouseY) {

    }
}
