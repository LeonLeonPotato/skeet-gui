package me.leon.skeetgui.gui;

import me.leon.skeetgui.gui.utils.Quad;
import me.leon.skeetgui.gui.utils.RenderUtil;

import java.awt.*;
import java.util.ArrayList;

public abstract class ButtonComponent extends Component {
    public final ArrayList<Component> subs;
    protected final PageComp parent;
    protected float offset;
    protected int column;

    protected Quad box;

    public ButtonComponent(PageComp page, float offset, int column) {
        this.parent = page;
        this.offset = offset;
        this.column = column;

        this.subs = new ArrayList<>();
    }

    @Override
    public void render(int x, int y) {
        box.setXY(SkeetGUI.INSIDE.getX(), SkeetGUI.INSIDE.getY()).shift(64 + column * 15, 15);
        RenderUtil.drawOutline(box, Color.WHITE, 0.5f);
    }

    @Override
    public Component parent() {
        return parent;
    }

    public Quad getBox() {
        return box;
    }
}
