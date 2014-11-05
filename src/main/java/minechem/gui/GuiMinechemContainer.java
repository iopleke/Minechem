package minechem.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.inventory.Container;

@SideOnly(Side.CLIENT)
public abstract class GuiMinechemContainer extends GuiContainer
{
	private final FontRenderer fontRenderer;

	public GuiMinechemContainer(Container container)
	{
		super(container);
		this.inventorySlots = container;
		this.fontRenderer = RenderManager.instance.getFontRenderer();
	}

	protected void drawHoveringText(String creativeTab, int mouseX, int mouseY)
	{
		int tabNameWidth = this.fontRenderer.getStringWidth(creativeTab);
		int mouseXOffset = mouseX + 12;
		int mouseYOffset = mouseY - 12;
		byte eight = 8;
		int var9 = -267386864;
		this.drawGradientRect(mouseXOffset - 3, mouseYOffset - 4, mouseXOffset + tabNameWidth + 3, mouseYOffset - 3, var9, var9);
		this.drawGradientRect(mouseXOffset - 3, mouseYOffset + eight + 3, mouseXOffset + tabNameWidth + 3, mouseYOffset + eight + 4, var9, var9);
		this.drawGradientRect(mouseXOffset - 3, mouseYOffset - 3, mouseXOffset + tabNameWidth + 3, mouseYOffset + eight + 3, var9, var9);
		this.drawGradientRect(mouseXOffset - 4, mouseYOffset - 3, mouseXOffset - 3, mouseYOffset + eight + 3, var9, var9);
		this.drawGradientRect(mouseXOffset + tabNameWidth + 3, mouseYOffset - 3, mouseXOffset + tabNameWidth + 4, mouseYOffset + eight + 3, var9, var9);
		int var10 = 1347420415;
		int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
		this.drawGradientRect(mouseXOffset - 3, mouseYOffset - 3 + 1, mouseXOffset - 3 + 1, mouseYOffset + eight + 3 - 1, var10, var11);
		this.drawGradientRect(mouseXOffset + tabNameWidth + 2, mouseYOffset - 3 + 1, mouseXOffset + tabNameWidth + 3, mouseYOffset + eight + 3 - 1, var10, var11);
		this.drawGradientRect(mouseXOffset - 3, mouseYOffset - 3, mouseXOffset + tabNameWidth + 3, mouseYOffset - 3 + 1, var10, var10);
		this.drawGradientRect(mouseXOffset - 3, mouseYOffset + eight + 2, mouseXOffset + tabNameWidth + 3, mouseYOffset + eight + 3, var11, var11);
		this.fontRenderer.drawStringWithShadow(creativeTab, mouseXOffset, mouseYOffset, -1);
	}

	public int getXSize()
	{
		return this.xSize;
	}

	public int getYSize()
	{
		return this.ySize;
	}

    public int getGuiTop()
    {
        return guiTop;
    }

    public int getGuiLeft()
    {
        return guiLeft;
    }

}
