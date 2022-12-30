package me.leon.skeetgui.gui.utils;

import me.leon.skeetgui.gui.SkeetGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPackedDepthStencil;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil implements IUtil {
    private static final Tessellator tessellator = Tessellator.getInstance();
    private static final BufferBuilder builder = tessellator.getBuffer();

    public static void drawBox(Quad quad, Color color) {
        setup();
        final double x = Math.min(quad.getX(), quad.getX1());
        final double y = Math.min(quad.getY(), quad.getY1());
        final double x1 = Math.max(quad.getX(), quad.getX1());
        final double y1 = Math.max(quad.getY(), quad.getY1());
        builder.begin(GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        vertex(x1, y, color);
        vertex(x, y, color);
        vertex(x, y1, color);
        vertex(x1, y1, color);
        tessellator.draw();
        end();
    }

    public static void drawLine(double x, double y, double x1, double y1, float width, Color color) {
        setup();
        GlStateManager.glLineWidth(width);
        builder.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        vertex(x, y, color);
        vertex(x1, y1, color);
        tessellator.draw();
        end();
    }

    public static void drawOutline(Quad quad, Color color, float width) {
        setup();
        final double x = Math.min(quad.getX(), quad.getX1());
        final double y = Math.min(quad.getY(), quad.getY1());
        final double x1 = Math.max(quad.getX(), quad.getX1());
        final double y1 = Math.max(quad.getY(), quad.getY1());
        GlStateManager.glLineWidth(width);
        builder.begin(GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);
        vertex(x1, y, color);
        vertex(x, y, color);
        vertex(x, y1, color);
        vertex(x1, y1, color);
        tessellator.draw();
        end();
    }

    public static void drawRainbowX(Quad quad, float hue, float sat, float bright, float speed, int alpha) {
        drawRainbowX(quad, hue, sat, bright, speed, 0.5f, alpha);
    }

    public static void drawRainbowX(Quad quad, float hue, float sat, float bright, float speed, float pixelSpeed, int alpha) {
        final Rainbow rainbow = new Rainbow();
        rainbow.update(hue);
        for(float a = quad.getX() + pixelSpeed; a <= quad.getX1(); a += pixelSpeed) {
            rainbow.update(speed);
            final Color color = rainbow.getColor(0, sat, bright);
            drawBox(new Quad(a - pixelSpeed, quad.getY(), a, quad.getY1()), alpha(alpha, color));
        }
    }

    public static void renderTexture(Quad quad, ResourceLocation loc, Color color) {
        final double x = Math.min(quad.getX(), quad.getX1());
        final double y = Math.min(quad.getY(), quad.getY1());
        final double x1 = Math.max(quad.getX(), quad.getX1());
        final double y1 = Math.max(quad.getY(), quad.getY1());
        mc.renderEngine.bindTexture(loc);
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        builder.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        builder.pos(x, y1, 0.0D).tex(0, 1).endVertex();
        builder.pos(x1, y1, 0.0D).tex(1, 1).endVertex();
        builder.pos(x1, y, 0.0D).tex(1, 0).endVertex();
        builder.pos(x, y, 0.0D).tex(0, 0).endVertex();
        tessellator.draw();
    }

    public static void scissor(int x, int y, int x1, int y1) {
        glPushAttrib(GL_SCISSOR_BIT);
        glEnable(GL_SCISSOR_TEST);
        final ScaledResolution res = new ScaledResolution(mc);
        y += 1;
        glScissor(x * res.getScaleFactor(), (res.getScaledHeight() - y1) * res.getScaleFactor(), (x1 - x) * res.getScaleFactor(), (y1 - y) * res.getScaleFactor());
    }

    public static void scissor(Quad quad) {
        glPushAttrib(GL_SCISSOR_BIT);
        glEnable(GL_SCISSOR_TEST);
        final ScaledResolution res = new ScaledResolution(mc);
        quad = quad.clone().setY(quad.getY() + 1);
        glScissor((int) quad.getX() * res.getScaleFactor(), (res.getScaledHeight() - (int) quad.getY()) * res.getScaleFactor(), (int) quad.width() * res.getScaleFactor(), (int) quad.height() * res.getScaleFactor());
    }

    public static void restoreScissor() {
        glPopAttrib();
        glDisable(GL_SCISSOR_TEST);
    }

    public static void stencil(double x, double y, double x1, double y1) {
        stencil(x, y, x1, y1, INSIDE);
    }

    public static void stencil(double x, double y, double x1, double y1, int mode) {
        stencil(mode, new Quad(x, y, x1, y1));
    }

    public static void stencil(int mode, Quad... quads) {
        checkSetupFBO();
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        glClear(GL_STENCIL_BUFFER_BIT);
        glClearStencil(0xF);
        glStencilMask(0xF);
        glEnable(GL_STENCIL_TEST);

        glStencilFunc(GL_NEVER, 1, 0xF);
        glStencilOp(GL_REPLACE, GL_REPLACE, GL_REPLACE);

        for(Quad quad : quads) {
            drawBox(quad, new Color(0, 0, 0));
        }

        glStencilMask(0x0);
        glStencilFunc(mode == INSIDE ? GL_NOTEQUAL : GL_EQUAL, 1, 0xF);
        glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
    }

    public static void restoreStencil() {
        glDisable(GL_STENCIL_TEST);
        glPopAttrib();
    }

    private static void checkSetupFBO()
    {
        Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();

        if (fbo.depthBuffer > -1)
        {
            setupFBO(fbo);
            fbo.depthBuffer = -1;
        }
    }

    private static void setupFBO(Framebuffer fbo)
    {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT, mc.displayWidth, mc.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencil_depth_buffer_ID);
    }

    public static final int INSIDE = 0;
    public static final int OUTSIDE = 1;


    public static Color alpha(int alpha, Color color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }



    private static void vertex(double x, double y, Color c) {
        builder.pos(x, y, 0.0D).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
    }

    private static void setup() {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    }

    private static void setupGradient() {
        glPushMatrix();

        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_ALPHA);
        glDisable(GL_DEPTH_TEST);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        glDisable(GL_CULL_FACE);
        GlStateManager.shadeModel(GL_SMOOTH);
    }

    private static void endGradient() {
        glEnable(GL_DEPTH_TEST);
        GlStateManager.shadeModel(GL_FLAT);
        glDisable(GL_BLEND);
        glEnable(GL_CULL_FACE);
        glEnable(GL_ALPHA);
        glEnable(GL_TEXTURE_2D);

        glPopMatrix();
    }

    private static void end() {
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }
}
