package me.leon.skeetgui.gui.buttons.sub;

import me.leon.skeetgui.gui.Component;
import me.leon.skeetgui.gui.buttons.ModuleComponent;
import me.leon.skeetgui.gui.utils.Quad;
import me.leon.skeetgui.gui.utils.RenderUtil;
import me.leon.skeetgui.gui.utils.font.FontUtil;

import java.awt.*;

public class EnabledVisibleComponent extends Component {
    private float offset;
    private final ModuleComponent parent;

    private final Quad enabledBox;
    private final Quad visibleBox;
    private final Quad enabledClickBox;
    private final Quad visibleClickBox;

    public EnabledVisibleComponent(ModuleComponent parent) {
        this.parent = parent;
        enabledBox = new Quad(0, 0, ModuleComponent.MODULE_WIDTH, 16);
        visibleBox = new Quad(0, 0, ModuleComponent.MODULE_WIDTH, 16);
        enabledClickBox = new Quad(5, 5, 11, 11);
        visibleClickBox = new Quad(5, 5, 11, 11);
    }

    @Override
    public void render(int x, int y) {
        enabledBox.setXY(parent.getBox().getX(), parent.getBox().getY() + offset);
        visibleBox.setXY(enabledBox.getX(), enabledBox.getY1() + 0.5f);
        enabledClickBox.setXY(enabledBox.getX() + 5, enabledBox.getY() + 5);
        visibleClickBox.setXY(visibleBox.getX() + 5, visibleBox.getY() + 5);

        renderBox("Enabled", enabledBox, enabledClickBox, parent.module.isEnabled());
        renderBox("Visible", visibleBox, visibleClickBox, parent.module.isVisible());
    }

    @Override
    public void update(int x, int y) {

    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if(enabledBox.isWithinIncluding(mouseX, mouseY) && button == 0) {
            parent.module.setEnabled(!parent.module.isEnabled());
            return true;
        } else if(visibleClickBox.isWithinIncluding(mouseX, mouseY) && button == 0) {
            parent.module.setVisible(!parent.module.isVisible());
            return true;
        } else return false;
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
        return 32;
    }

    @Override
    public Component parent() {
        return parent;
    }

    private void renderBox(String st, Quad fit, Quad click, boolean drawGreen) {
        click.setXY(fit.getX() + 5, fit.getY() + 5);
        RenderUtil.drawBox(click, new Color(40, 40, 40));
        RenderUtil.drawOutline(click, new Color(0, 0, 0), 0.5f);
        FontUtil.drawStringWithShadow(st, fit.getX() + 16, fit.getY() + 9 - (FontUtil.getFontHeight() / 2f), Color.WHITE);
        if(drawGreen)
            RenderUtil.drawBox(click.clone().shrink(0.5f), new Color(0xBCF54D));
    }


    public float getOffset() {
        return offset;
    }

    public EnabledVisibleComponent setOffset(float offset) {
        this.offset = offset;
        return this;
    }
}
