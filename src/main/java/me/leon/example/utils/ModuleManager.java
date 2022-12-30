package me.leon.example.utils;

import com.google.common.reflect.ClassPath;
import me.leon.skeetgui.gui.generic.ICategory;
import me.leon.skeetgui.gui.generic.IModule;
import me.leon.skeetgui.gui.generic.IModuleManager;
import me.leon.skeetgui.gui.generic.ISetting;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager implements IModuleManager {
    private final ArrayList<IModule> modules;

    public ModuleManager() {
        modules = new ArrayList<>();

        // haha, reflections
        try {
//            InputStream st = ClassLoader.getSystemClassLoader().getResourceAsStream("me/leon/example/mods");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(st));
//
//            String name;
//            while ((name = reader.readLine()) != null) {
//                Class<?> clazz = Class.forName("me.leon.example.mods." + name.split("[.]")[0]);
//                IModule mod = (IModule) clazz.newInstance();
//                for(Field field : clazz.getDeclaredFields()) {
//                    if(ISetting.class.isAssignableFrom(field.getType())) {
//                        mod.getSettings().add((ISetting) field.get(mod));
//                    }
//                }
//                modules.add(mod);
//            }
//
//            reader.close(); // close the reader

            final ClassLoader loader = Thread.currentThread().getContextClassLoader();

            for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
                if (info.getName().startsWith("me.leon.example.mods.")) {
                    final Class<?> clazz = info.load();
                    IModule mod = (IModule) clazz.newInstance();
                    for(Field field : clazz.getDeclaredFields()) {
                        if(ISetting.class.isAssignableFrom(field.getType())) {
                            mod.getSettings().add((ISetting) field.get(mod));
                        }
                    }
                    modules.add(mod);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<IModule> getModules() {
        return modules;
    }

    @Override
    public List<IModule> getModulesFromCategory(ICategory category) {
        ArrayList<IModule> toReturn = new ArrayList<>();
        for(IModule m : modules) {
            if(m.getCategory() == category) toReturn.add(m);
        }
        return toReturn;
    }
}
