package me.leon.skeetgui.gui.generic;

import java.util.ArrayList;
import java.util.List;

public interface IModuleManager {
    List<IModule> getModules();
    List<IModule> getModulesFromCategory(ICategory category);
}
