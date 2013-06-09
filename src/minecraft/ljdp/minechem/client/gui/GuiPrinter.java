package ljdp.minechem.client.gui;

import ljdp.minechem.client.gui.tabs.TabEnergy;
import ljdp.minechem.common.containers.ContainerBluePrintPrinter;
import ljdp.minechem.common.tileentity.TileEntityBluePrintPrinter;
import ljdp.minechem.common.utils.ConstantValue;
import ljdp.minechem.common.utils.MinechemHelper;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

public class GuiPrinter extends GuiContainerTabbed {

    int guiWidth = 176;
    int guiHeight = 187;

    public GuiPrinter(InventoryPlayer inventoryPlayer, TileEntityBluePrintPrinter tileEntity) {
        super(new ContainerBluePrintPrinter(inventoryPlayer, tileEntity));
        this.xSize = guiWidth;
        this.ySize = guiHeight;
        addTab(new TabEnergy(this, tileEntity));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String info = MinechemHelper.getLocalString("gui.title.synthesis");
        int infoWidth = fontRenderer.getStringWidth(info);
        fontRenderer.drawString(info, (guiWidth - infoWidth) / 2, 5, 0x000000);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(ConstantValue.SYNTHESIS_GUI);
        int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;
        drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);
    }

}
