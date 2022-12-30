package me.leon.example.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ProjectionManager {
    private static final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private static final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
    private static final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
    private static final FloatBuffer coords = GLAllocation.createDirectFloatBuffer(3);

    public ProjectionManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void renderHook(RenderWorldLastEvent ignored) {
        GlStateManager.getFloat(GL11.GL_PROJECTION_MATRIX, projection);
        GlStateManager.getFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
        GlStateManager.glGetInteger(GL11.GL_VIEWPORT, viewport);
    }

    public static Vec3d project(Vec3d vec) {
        // the amount of documentation on this is disgustingly little
        // nonexistent, even
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int scaleFactor = scaledResolution.getScaleFactor();
        double scaling = scaleFactor / Math.pow(scaleFactor, 2.0D);

        GLU.gluProject(
                (float) vec.x, (float) vec.y, (float) vec.z,
                modelview, projection, viewport,
                coords);

        return new Vec3d(
                coords.get(0) * scaling,
                scaledResolution.getScaledHeight() - coords.get(1) * scaling,
                0);
    }
}
