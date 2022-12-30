package me.leon.skeetgui.gui;

import me.leon.example.SkeetMain;
import me.leon.skeetgui.gui.buttons.ModuleComponent;
import me.leon.skeetgui.gui.generic.ICategory;
import me.leon.skeetgui.gui.generic.IModule;
import me.leon.skeetgui.gui.utils.MathUtils;
import me.leon.skeetgui.gui.utils.Quad;
import me.leon.skeetgui.gui.utils.RenderUtil;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;

public class PageComp extends Component {
    public final ICategory category;
    private final ResourceLocation iconLocation;

    // TODO: Implement scrolling
    private float scroll;
    private final float offset;
    private final Quad clickableBox;
    public final ArrayList<ButtonComponent> buttons = new ArrayList<>();

    public PageComp(ICategory category, float offset) {
        this.category = category;
        this.iconLocation = category.getPath();
        this.offset = offset;

        clickableBox = new Quad(0, 0, 64, 64);

        float[] offsets = new float[SkeetGUI.getConfig().getColumnsPerPage()];
        for(IModule m : SkeetGUI.getModules().getModulesFromCategory(category)) {
            int min = MathUtils.argMin(offsets);
            ButtonComponent c = new ModuleComponent(m, this, offsets[min], 1);
            buttons.add(c);
            offsets[min] += c.getHeight() + 15;
        }
    }

    @Override
    public void render(int x, int y) {
        clickableBox.setXY(SkeetGUI.INSIDE.getX(), SkeetGUI.INSIDE.getY()).shift(0, offset);

        RenderUtil.renderTexture(clickableBox, iconLocation, getColor(x, y));

        if(SkeetGUI.selectedPage == this) {
            RenderUtil.stencil(RenderUtil.OUTSIDE, SkeetGUI.INSIDE.clone().setX(SkeetGUI.INSIDE.getX() + 64));
            for(ButtonComponent b : buttons) b.render(x, y);
            RenderUtil.restoreStencil();
        }
    }

    @Override
    public void update(int x, int y) {
        if(SkeetGUI.selectedPage == this) {
            for(ButtonComponent b : buttons) b.update(x, y);
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if(clickableBox.isWithin(mouseX, mouseY) && button == 0) {
            SkeetGUI.selectedPage = this;
            return true;
        } else {
            if (SkeetGUI.selectedPage == this) {
                boolean broken = false;
                for (ButtonComponent b : buttons) {
                    if (b.mouseClicked(mouseX, mouseY, button)) {
                        broken = true;
                        break;
                    }
                }
                return broken;
            }
            return false;
        }
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY, int button) {
        boolean broken = false;
        for (ButtonComponent b : buttons) {
            if(b.mouseReleased(mouseX, mouseY, button)) {
                broken = true;
                break;
            }
        }
        return broken;
    }

    private Color getColor(int x, int y) {
        Color c;
        if(clickableBox.isWithin(x, y)) {
            if(SkeetGUI.selectedPage == this)
                c = Color.WHITE;
            else
                c = new Color(230, 230, 230);
        } else {
            if(SkeetGUI.selectedPage == this)
                c = new Color(200, 200, 200);
            else
                c = new Color(170, 170, 170);
        }
        return c;
    }

    @Override
    public boolean keyTyped(char character, int key) {
        return false;
    }

    @Override
    public float getHeight() {
        return 64f;
    }

    @Override
    public Component parent() {
        return null;
    }

    public Quad getClickableBox() {
        return clickableBox;
    }
}
