package ljdp.minechem.client;

import java.util.EnumSet;

import org.lwjgl.opengl.GL11;

import ljdp.minechem.common.MinechemBlocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements ITickHandler {

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {}

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        if (type.contains(TickType.RENDER))
            renderOverlays((Float) tickData[0]);
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.RENDER, TickType.CLIENT);
    }

    @Override
    public String getLabel() {
        return null;
    }

    private void renderOverlays(float parialTickTime) {
        Minecraft mc = FMLClientHandler.instance().getClient();
        if (mc.renderViewEntity != null && mc.gameSettings.thirdPersonView == 0 && !mc.renderViewEntity.isPlayerSleeping()
                && mc.thePlayer.isInsideOfMaterial(MinechemBlocks.materialGas)) {
            renderWarpedTextureOverlay(mc, "/misc/water.png");
        }
    }

    /**
     * Renders a texture that warps around based on the direction the player is looking. Texture needs to be bound before being called. Used for the water
     * overlay. Args: parialTickTime
     */
    private void renderWarpedTextureOverlay(Minecraft mc, String texture) {
        int overlayTexture = mc.renderEngine.getTexture(texture);
        double tile = 4.0F;
        double yaw = -mc.thePlayer.rotationYaw / 64.0F;
        double pitch = mc.thePlayer.rotationPitch / 64.0F;
        double left = 0;
        double top = 0;
        double right = mc.displayWidth;
        double bot = mc.displayHeight;
        double z = -1;
        Tessellator ts = Tessellator.instance;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, overlayTexture);
        GL11.glPushMatrix();

        ts.startDrawingQuads();
        ts.addVertexWithUV(left, bot, z, tile + yaw, tile + pitch);
        ts.addVertexWithUV(right, bot, z, yaw, tile + pitch);
        ts.addVertexWithUV(right, top, z, yaw, pitch);
        ts.addVertexWithUV(left, top, z, tile + yaw, pitch);
        ts.draw();

        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }

}
