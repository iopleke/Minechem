package minechem.client.gui;

import minechem.client.gui.tabs.TabHelp;
import minechem.common.ModMinechem;
import minechem.common.containers.ContainerLeadedChest;
import minechem.common.tileentity.TileEntityLeadedChest;
import minechem.common.utils.ConstantValue;
import minechem.common.utils.MinechemHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiLeadedChest extends GuiContainerTabbed
{

    int guiWidth = 176;
    int guiHeight = 217;
    TileEntityLeadedChest leadedchest;

    public GuiLeadedChest(InventoryPlayer inventoryPlayer, TileEntityLeadedChest leadedChest)
    {
        super(new ContainerLeadedChest(inventoryPlayer, leadedChest));
        this.leadedchest = leadedChest;
        addTab(new TabHelp(this, MinechemHelper.getLocalString("help.leadedChest")));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String info = MinechemHelper.getLocalString("gui.title.leadedchest");
        int infoWidth = fontRenderer.getStringWidth(info);
        fontRenderer.drawString(info, (guiWidth - infoWidth) / 2, 5, 0xCCCCCC);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.renderEngine.bindTexture(new ResourceLocation(ModMinechem.ID, ConstantValue.LEADED_CHEST_GUI));

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        GL11.glPopMatrix();
    }

    @Override
    public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h)
    {
        return false;
    }

}
