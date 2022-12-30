package me.leon.skeetgui.gui.buttons.sub;

import me.leon.example.settings.Setting;
import me.leon.skeetgui.gui.Component;
import me.leon.skeetgui.gui.buttons.ModuleComponent;
import me.leon.skeetgui.gui.utils.Quad;

public abstract class ISettingComponent<T extends Setting> extends Component {
    protected final ModuleComponent parent;
    protected T set;
    protected Quad box;
    protected float offset;

    public ISettingComponent(ModuleComponent parent, T set) {
        this.parent = parent;
        this.set = set;
        this.box = new Quad(0, 0, ModuleComponent.MODULE_WIDTH, getHeight());
    }

    public abstract void render(int mouseX, int mouseY);
    public abstract void update(int mouseX, int mouseY);

    public float getOffset() {
        return offset;
    }

    public ISettingComponent<T> setOffset(float offset) {
        this.offset = offset;
        return this;
    }
}
