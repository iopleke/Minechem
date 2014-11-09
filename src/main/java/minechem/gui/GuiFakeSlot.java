package minechem.gui;

import cpw.mods.fml.client.FMLClientHandler;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiFakeSlot extends Gui
{
	private Minecraft mc;
    private GuiContainerTabbed parentContainer;
    private EntityPlayer player;
    private int parentWidth, parentHeight;
    private int xPos, yPos;
    private int xOffset = 0;
    private int yOffset = 0;
    private int width = 16;
    private int height = 16;
    private ItemStack itemstack;
    private static RenderItem renderItem = new RenderItem();

	public GuiFakeSlot(GuiContainerTabbed parentContainer, EntityPlayer player)
	{
		this.parentContainer = parentContainer;
		this.parentWidth = parentContainer.getXSize();
		this.parentHeight = parentContainer.getYSize();
		this.player = player;
		this.mc = FMLClientHandler.instance().getClient();
	}

	private int mouseX()
	{
		return parentContainer.mouseX;
	}

	private int mouseY()
	{
		return parentContainer.mouseY;
	}

	public void setItemStack(ItemStack itemstack)
	{
		this.itemstack = itemstack;
	}

	public ItemStack getItemStack()
	{
		return this.itemstack;
	}

	public void setXPos(int x)
	{
		this.xPos = x;
	}

	public void setYPos(int y)
	{
		this.yPos = y;
	}

	public void setXOffset(int x)
	{
		this.xOffset = x;
	}

	public void setYOffset(int y)
	{
		this.yOffset = y;
	}

	public int getXPos()
	{
		return this.xPos + this.xOffset;
	}

	public int getYPos()
	{
		return this.yPos + this.yOffset;
	}

	public boolean getMouseIsOver()
	{
		int mx = mouseX();
		int my = mouseY();
		int x = getXPos();
		int y = getYPos();
		return mx >= x && mx <= x + this.width && my >= y && my <= y + this.height;
	}

	public void draw()
	{
		this.zLevel = 0.0F;
		GL11.glPushMatrix();
		GL11.glTranslatef(getXPos(), yPos, 0);

		if (getMouseIsOver())
		{
			drawBackgroundHighlight();
		}
		if (itemstack != null)
		{
			drawItemStack(itemstack);
		}

		GL11.glPopMatrix();
	}

	public void drawTooltip(int x, int y)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		if (itemstack != null && getMouseIsOver())
		{
			drawItemStackTooltip(itemstack);
		}
		GL11.glPopMatrix();
	}

	private void drawItemStack(ItemStack itemstack)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
		RenderHelper.enableGUIStandardItemLighting();
		this.zLevel = 100.0F;
		renderItem.zLevel = 100.0F;
		renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, itemstack, 0, 0);
		renderItem.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, itemstack, 0, 0);
		this.zLevel = 0.0F;
		renderItem.zLevel = 0.0F;
	}

	private void drawItemStackTooltip(ItemStack itemstack)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		List<String> lines = itemstack.getTooltip(this.player, mc.gameSettings.advancedItemTooltips);
		int x = 0;
		int y = 0;
		int lineSpacing = 10;
		int maxLineWidth = 0;
		for (String line : lines)
		{
			int lineWidth = mc.fontRenderer.getStringWidth(line);
			if (lineWidth > maxLineWidth)
			{
				maxLineWidth = lineWidth;
			}
		}

		int bkX = x - 3;
		int bkY = y - 3;
		int tooltipWidth = maxLineWidth + 4;
		int tooltipHeight = (lines.size() * lineSpacing) + 4;
		int backgroundColor;
		backgroundColor = 0xAA000088;
		drawGradientRect(bkX - 1, bkY - 1, bkX + tooltipWidth + 1, bkY + tooltipHeight + 1, backgroundColor, backgroundColor);
		backgroundColor = 0xCC000000;
		drawGradientRect(bkX, bkY, bkX + tooltipWidth, bkY + tooltipHeight, backgroundColor, backgroundColor);

		for (int i = 0; i < lines.size(); i++)
		{
			int tx = x;
			int ty = y + (i * 10);
			String tooltip = lines.get(i);
			if (i == 0)
			{
				tooltip = itemstack.getRarity().rarityColor + tooltip;
			}
			mc.fontRenderer.drawStringWithShadow(tooltip, tx, ty, 0xFFFFFFFF);
		}
	}

	private void drawBackgroundHighlight()
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		int color4 = 0x44000000;
		drawGradientRect(0, 0, width, height, color4, color4);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

}
