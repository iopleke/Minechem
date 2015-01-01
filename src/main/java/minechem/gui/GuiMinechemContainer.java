package minechem.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
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

    public void drawHoveringText(String creativeTab, int mouseX, int mouseY)
    {
        drawCreativeTabHoveringText(creativeTab, mouseX, mouseY);
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
