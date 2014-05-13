package pixlepix.minechem.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import pixlepix.minechem.client.gui.tabs.TabHelp;
import pixlepix.minechem.common.containers.ContainerLeadedChest;
import pixlepix.minechem.common.tileentity.TileEntityLeadedChest;
import pixlepix.minechem.common.utils.ConstantValue;
import pixlepix.minechem.common.utils.MinechemHelper;

public class GuiLeadedChest extends GuiContainerTabbed {

    int guiWidth = 176;
    int guiHeight = 217;
    TileEntityLeadedChest leadedchest;

    public GuiLeadedChest(InventoryPlayer inventoryPlayer, TileEntityLeadedChest leadedChest) {
        super(new ContainerLeadedChest(inventoryPlayer, leadedChest));
        this.leadedchest = leadedChest;
        addTab(new TabHelp(this, MinechemHelper.getLocalString("help.leadedChest")));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String info = MinechemHelper.getLocalString("gui.title.leadlinedchest");
        int infoWidth = fontRenderer.getStringWidth(info);
        fontRenderer.drawString(info, (guiWidth - infoWidth) / 2, 5, 0x000000);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.renderEngine.bindTexture(new ResourceLocation(ConstantValue.MOD_ID, ConstantValue.LEADED_CHEST_GUI));

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        GL11.glPopMatrix();
    }

    @Override
    public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h) {
        return false;
    }

}
