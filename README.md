# SkeetGUI for Minecraft 1.12.2



## About
**What is this project?**
This project aims to port a certain CS:GO GUI into Minecraft version 1.12.2. It allows the user to enable / disable certain modules and change the settings in them.

**Where can I use this project?**
This project is mainly aimed to be used to make PVP clients, however it can be used anywhere.

**Why should I use SkeetGUI instead of other GUIs?**
This GUI provides a unique page based look, instead of other dropdown style menus commonly found in clients nowadays.

## Installation

Currently, you will have to manually copy the [skeetgui](link) folder over to your project. A maven dependency is coming soon.

## Usage
1. Create a class implementing [ISkeetConfig](link) as provided below.
Note that the "colored" and "both" options are not yet implemented
```java
public class GenericConfig implements ISkeetConfig {  
  @Override  
  public int getGuiKey() {  
        return Keyboard.KEY_RSHIFT;  
  }  
  
  @Override  
  public int getCloseGuiKey() {  
        return Keyboard.KEY_ESCAPE;  
  }  
  
  @Override  
  public BackgroundOptions getBackground() {  
        return BackgroundOptions.BLUR;  
  }  
  
  @Override  
  public boolean doesPauseGame() {  
        return false;  
  }  
  
  @Override  
  public boolean useCustomFont() {  
        return true;  
  }  
  
  @Override  
  public int getColumnsPerPage() {  
        return 2;  
  }  
  
  @Override  
  public Quad getBoundingBox() {  
        return new Quad(10, 10, 410, 360);  
  }  
}
```
2. In your category class, implement [ICategory](link). You need to provide a ResourceLocation for the icon used in the GUI.
3. In your module manager, implement [IModuleManager](link). You need to override `getModulesFromCategory(ICategory c)`.
##