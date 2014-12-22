package minechem.gui;

import cpw.mods.fml.client.FMLClientHandler;
import minechem.utils.MinechemUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import org.lwjgl.opengl.GL11;

public abstract class GuiToggleSwitch
{
	public class ToggleButton
	{
		public int u;
		public int v;
		public String tooltip;
	}

	protected int numStates;
	protected int state = 0;
	protected int zLevel = 100;
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	protected int mouseX;
	protected int mouseY;
	protected Minecraft mc;
	protected GuiContainerTabbed container;
	protected ResourceLocation texture;
	protected HashMap<Integer, ToggleButton> buttons = new HashMap<Integer, ToggleButton>();

	public GuiToggleSwitch()
	{
		this.mc = FMLClientHandler.instance().getClient();
	}

	public void draw(TextureManager renderEngine)
	{
		renderEngine.bindTexture(texture);
		ToggleButton button = buttons.get(state);
		drawTexturedModalRect(x, y, button.u, button.v, width, height);
		String tooltip = MinechemUtil.getLocalString(button.tooltip);
		int cx = (container.width - container.getXSize()) / 2;
		int cy = (container.height - container.getYSize()) / 2;
		int tooltipWidth = mc.fontRenderer.getStringWidth(tooltip);

		if (isMoverOver())
		{
            GL11.glDisable(GL11.GL_DEPTH_TEST);
			container.drawHoveringText(tooltip, cx + 77 - (tooltipWidth / 2), cy + 100);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
	}

	public void setPos(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void mouseClicked(int x, int y, int mouseButton)
	{
		if (isMoverOver())
		{
			onClicked();
		}
	}

	public boolean isMoverOver()
	{
		mouseX = container.getMouseX();
		mouseY = container.getMouseY();
		if (mouseX > this.x && mouseX < this.x + width && mouseY > this.y && mouseY < this.y + height)
		{
			return true;
		}
		return false;
	}

	public int getState()
	{
		return this.state;
	}

	private void onClicked()
	{
		this.state++;
		if (this.state == this.numStates)
		{
			this.state = 0;
		}
	}

	/**
	 * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
	 */
	public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		float var7 = 0.00390625F;
		float var8 = 0.00390625F;
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV(par1 + 0, par2 + par6, this.zLevel, (par3 + 0) * var7, (par4 + par6) * var8);
		var9.addVertexWithUV(par1 + par5, par2 + par6, this.zLevel, (par3 + par5) * var7, (par4 + par6) * var8);
		var9.addVertexWithUV(par1 + par5, par2 + 0, this.zLevel, (par3 + par5) * var7, (par4 + 0) * var8);
		var9.addVertexWithUV(par1 + 0, par2 + 0, this.zLevel, (par3 + 0) * var7, (par4 + 0) * var8);
		var9.draw();
	}

}
