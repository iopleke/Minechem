package minechem.render;

import org.lwjgl.opengl.GL11;
import cpw.mods.fml.client.FMLClientHandler;
import minechem.potion.PotionInjector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

//Thanks to thepers for teaching me rendering - Mandrake
public class EffectsRenderer {

    public static void renderEffects()
    {
        Minecraft mc = FMLClientHandler.instance().getClient();
//        if (mc.isSingleplayer())
//        {
            EntityPlayer player = mc.thePlayer;
            if (player instanceof EntityPlayer && player.isPotionActive(PotionInjector.atropineHigh))
            {
                PotionEffect DHigh = player.getActivePotionEffect(PotionInjector.atropineHigh);
                int Multiplier = DHigh.getAmplifier();
                RenderDelirium(5 * Multiplier + 5);
            }
//        }
    }


    /* // this is all unused code for a WIP gas system private void renderOverlays(float parialTickTime) { Minecraft mc = FMLClientHandler.instance().getClient(); if (mc.renderViewEntity != null && mc.gameSettings.thirdPersonView == 0 &&
     * !mc.renderViewEntity.isPlayerSleeping() && mc.thePlayer.isInsideOfMaterial(MinechemBlocks.materialGas)) { renderWarpedTextureOverlay(mc, new ResourceLocation(ModMinechem.ID,"/misc/water.png")); } }
     * 
     * 
     * // Renders a texture that warps around based on the direction the player is looking. Texture needs to be bound before being called. Used for the water // overlay. Args: parialTickTime
     * 
     * private void renderWarpedTextureOverlay(Minecraft mc, ResourceLocation texture) { int overlayTexture = mc.renderEngine.func_110581_b(texture).func_110552_b(); double tile = 4.0F; double yaw = -mc.thePlayer.rotationYaw / 64.0F; double pitch =
     * mc.thePlayer.rotationPitch / 64.0F; double left = 0; double top = 0; double right = mc.displayWidth; double bot = mc.displayHeight; double z = -1; Tessellator ts = Tessellator.instance;
     * 
     * GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F); GL11.glDisable(GL11.GL_ALPHA_TEST); GL11.glEnable(GL11.GL_BLEND); GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); GL11.glBindTexture(GL11.GL_TEXTURE_2D, overlayTexture); GL11.glPushMatrix();
     * 
     * ts.startDrawingQuads(); ts.addVertexWithUV(left, bot, z, tile + yaw, tile + pitch); ts.addVertexWithUV(right, bot, z, yaw, tile + pitch); ts.addVertexWithUV(right, top, z, yaw, pitch); ts.addVertexWithUV(left, top, z, tile + yaw, pitch); ts.draw();
     * 
     * GL11.glPopMatrix(); GL11.glDisable(GL11.GL_BLEND); GL11.glEnable(GL11.GL_ALPHA_TEST); } */
    public static void RenderDelirium(int markiplier)
    {
        ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        int width = scale.getScaledWidth();
        int height = scale.getScaledHeight();
        Gui gui = new Gui();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        int color = (int) (220.0F * markiplier - 150) << 24 | 1052704;
        Gui.drawRect(0, 0, width, height, color);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

    }
}
