package me.leon.example.mods;

import me.leon.example.Category;
import me.leon.example.Module;
import me.leon.example.settings.impl.BooleanSetting;
import me.leon.example.settings.impl.SliderSetting;
import me.leon.example.utils.ProjectionManager;
import me.leon.skeetgui.gui.utils.Quad;
import me.leon.skeetgui.gui.utils.RenderUtil;
import me.leon.skeetgui.gui.utils.font.FontUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.LinkedList;

public class ExampleModule extends Module {
    public static final BooleanSetting boolSetting
            = new BooleanSetting("Example boolean setting", "Example setting description", true);

    public static final SliderSetting sliderSetting
            = new SliderSetting("Example slider setting", "Example setting description", false, 0, 10, 5);

    public ExampleModule() {
        super("ExampleModule", "Example module description", Category.COMBAT);
    }

    private final LinkedList<Entity> entitiesToRender = new LinkedList<>();

    @Override
    public void onLoad() {
    }

    @Override
    public void onTick() {
    }

    @SubscribeEvent
    public void worldRenderHook(RenderWorldLastEvent event) {
        Vec3d interpolatedPos = getInterpolatedPos(mc.player, mc.getRenderPartialTicks());
        Frustum f = new Frustum();
        f.setPosition(interpolatedPos.x, interpolatedPos.y + mc.player.getEyeHeight(), interpolatedPos.z);

        entitiesToRender.clear();
        for(Entity e : mc.world.loadedEntityList) {
            if(e == mc.player) continue;
            if(f.isBoundingBoxInFrustum(e.getRenderBoundingBox())) {
                entitiesToRender.add(e);
            }
        }
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if(nullCheck()) return;

        Vec3d interpolatedPos = getInterpolatedPos(mc.player, mc.getRenderPartialTicks());

        for(Entity e : entitiesToRender) {
            double minX = 9999, maxX = -9999, minY = 9999, maxY = -9999;
            AxisAlignedBB bb = e.getRenderBoundingBox()
                    .offset(-e.posX, -e.posY, -e.posZ)
                    .offset(getInterpolatedPos(e, mc.getRenderPartialTicks()));
            ArrayList<Vec3d> vectors = new ArrayList<>(8);
            vectors.add(new Vec3d(bb.minX, bb.minY, bb.minZ));
            vectors.add(new Vec3d(bb.minX, bb.minY, bb.maxZ));
            vectors.add(new Vec3d(bb.minX, bb.maxY, bb.minZ));
            vectors.add(new Vec3d(bb.minX, bb.maxY, bb.maxZ));
            vectors.add(new Vec3d(bb.maxX, bb.minY, bb.minZ));
            vectors.add(new Vec3d(bb.maxX, bb.minY, bb.maxZ));
            vectors.add(new Vec3d(bb.maxX, bb.maxY, bb.minZ));
            vectors.add(new Vec3d(bb.maxX, bb.maxY, bb.maxZ));

            for(Vec3d v : vectors) {
                Vec3d projected = ProjectionManager.project(v.subtract(interpolatedPos));
                minX = Math.min(projected.x, minX);
                maxX = Math.max(projected.x, maxX);
                minY = Math.min(projected.y, minY);
                maxY = Math.max(projected.y, maxY);
            }

            RenderUtil.drawOutline(new Quad(minX, minY, maxX, maxY), Color.WHITE, sliderSetting.getValue());
        }
    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;
        mc.player.sendChatMessage("Example module enabled!");
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;
        mc.player.sendChatMessage("Example module disabled!");
    }
}
