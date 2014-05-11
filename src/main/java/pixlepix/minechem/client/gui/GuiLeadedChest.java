package pixlepix.minechem.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import pixlepix.minechem.common.containers.ContainerLeadedChest;
import pixlepix.minechem.common.tileentity.TileEntityLeadedChest;
import pixlepix.minechem.common.utils.ConstantValue;
import pixlepix.minechem.common.utils.MinechemHelper;

public class GuiLeadedChest extends GuiContainer {

    int guiWidth = 176;
    int guiHeight = 217;

    public GuiLeadedChest(InventoryPlayer inventoryPlayer, TileEntityLeadedChest te) {
        super(new ContainerLeadedChest(inventoryPlayer, te));
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

        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ConstantValue.MOD_ID, ConstantValue.MICROSCOPE_GUI));
        int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;
        zLevel = 0;

        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        drawTexturedModalRect(0, 0, 0, 0, guiWidth, guiHeight);
    }

}
