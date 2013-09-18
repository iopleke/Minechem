package ljdp.minechem.client;

import java.util.EnumSet;
import java.util.List;

import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.common.MinechemItems;
import ljdp.minechem.common.PotionInjector;
import ljdp.minechem.common.utils.ConstantValue;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.client.gui.Gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

// Thanks to thepers for teaching me rendering - Mandrake
public class TickHandler implements ITickHandler {
	public void transmuteWaterToPortal(World world, int dx, int dy, int dz) {
		int px = dx;
		int pz = dz;
		
		if (world.getBlockMaterial(px - 1, dy, pz) == Material.water) {
			px--;
		}
		if (world.getBlockMaterial(px, dy, pz - 1) == Material.water) {
			pz--;
		}
		
		world.setBlock(px + 0, dy, pz + 0, 1, 0, 2);
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		Minecraft mc = FMLClientHandler.instance().getClient();
		if (mc.isSingleplayer()) {
			EntityPlayer player = mc.thePlayer;
			World world = mc.theWorld; // GuiIngame
			if (player != null) {
				if (world != null) {
					
				}
				double rangeToCheck = 32.0D;
				
				List<EntityItem> itemList = world.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(rangeToCheck, rangeToCheck, rangeToCheck));
				for (EntityItem entityItem : itemList) {
					if ((entityItem.getEntityItem().itemID == new ItemStack(MinechemItems.element, 1, EnumMolecule.potassiumNitrate.ordinal()).itemID && (world.isMaterialInBB(entityItem.boundingBox, Material.water)))) {
						world.createExplosion(entityItem, entityItem.posX, entityItem.posY, entityItem.posZ, 0.9F, true);
						int dx = MathHelper.floor_double(entityItem.posX);
						int dy = MathHelper.floor_double(entityItem.posY);
						int dz = MathHelper.floor_double(entityItem.posZ);
						transmuteWaterToPortal(world, dx, dy, dz);
						return;
					}
				}
			}
		}
		
	}
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		
	}
	
	public static void renderEffects() {
		Minecraft mc = FMLClientHandler.instance().getClient();
		if (mc.isSingleplayer()) {
			EntityPlayer player = mc.thePlayer;
			if (player instanceof EntityPlayer && player.isPotionActive(PotionInjector.atropineHigh)) {
				PotionEffect DHigh = player.getActivePotionEffect(PotionInjector.atropineHigh);
				int Multiplier = DHigh.getAmplifier();
				if (Multiplier == 1 ){ // II
				RenderDelirium(10);} 
				else if (Multiplier == 2 ){ // III
					RenderDelirium(15);
				}
					else if (Multiplier == 3){ // IV
						RenderDelirium(20);	
					}
					else if (Multiplier == 4){ // V
						RenderDelirium(25);	
					}
					else { // preform a level V effect if effect is greater than level V 
					RenderDelirium(25);
					}
					}
				}
				}	
	
	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}
	
	@Override
	public String getLabel() {
		return null;
	}
	
	/*
	 * // this is all unused code for a WIP gas system private void
	 * renderOverlays(float parialTickTime) { Minecraft mc =
	 * FMLClientHandler.instance().getClient(); if (mc.renderViewEntity != null
	 * && mc.gameSettings.thirdPersonView == 0 &&
	 * !mc.renderViewEntity.isPlayerSleeping() &&
	 * mc.thePlayer.isInsideOfMaterial(MinechemBlocks.materialGas)) {
	 * renderWarpedTextureOverlay(mc, new
	 * ResourceLocation(ConstantValue.MOD_ID,"/misc/water.png")); } }
	 * 
	 * 
	 * // Renders a texture that warps around based on the direction the player
	 * is looking. Texture needs to be bound before being called. Used for the
	 * water // overlay. Args: parialTickTime
	 * 
	 * private void renderWarpedTextureOverlay(Minecraft mc, ResourceLocation
	 * texture) { int overlayTexture =
	 * mc.renderEngine.func_110581_b(texture).func_110552_b(); double tile =
	 * 4.0F; double yaw = -mc.thePlayer.rotationYaw / 64.0F; double pitch =
	 * mc.thePlayer.rotationPitch / 64.0F; double left = 0; double top = 0;
	 * double right = mc.displayWidth; double bot = mc.displayHeight; double z =
	 * -1; Tessellator ts = Tessellator.instance;
	 * 
	 * GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
	 * GL11.glDisable(GL11.GL_ALPHA_TEST); GL11.glEnable(GL11.GL_BLEND);
	 * GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, overlayTexture);
	 * GL11.glPushMatrix();
	 * 
	 * ts.startDrawingQuads(); ts.addVertexWithUV(left, bot, z, tile + yaw, tile
	 * + pitch); ts.addVertexWithUV(right, bot, z, yaw, tile + pitch);
	 * ts.addVertexWithUV(right, top, z, yaw, pitch); ts.addVertexWithUV(left,
	 * top, z, tile + yaw, pitch); ts.draw();
	 * 
	 * GL11.glPopMatrix(); GL11.glDisable(GL11.GL_BLEND);
	 * GL11.glEnable(GL11.GL_ALPHA_TEST); }
	 */
	public static void RenderDelirium(int markiplier) { 
		ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		int width = scale.getScaledWidth();
		int height = scale.getScaledHeight();
		Gui gui = new Gui();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		int color = (int) (220.0F * markiplier - 150) << 24 | 1052704;
		gui.drawRect(0, 0, width, height, color);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
	}
}
