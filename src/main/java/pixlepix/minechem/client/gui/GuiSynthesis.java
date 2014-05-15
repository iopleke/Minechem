package pixlepix.minechem.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import pixlepix.minechem.client.gui.tabs.TabEnergySynthesis;
import pixlepix.minechem.client.gui.tabs.TabHelp;
import pixlepix.minechem.client.gui.tabs.TabStateControlSynthesis;
import pixlepix.minechem.common.ModMinechem;
import pixlepix.minechem.common.containers.ContainerSynthesis;
import pixlepix.minechem.common.tileentity.TileEntitySynthesis;
import pixlepix.minechem.common.utils.ConstantValue;
import pixlepix.minechem.common.utils.MinechemHelper;

public class GuiSynthesis extends GuiContainerTabbed {

    int guiWidth = 176;
    int guiHeight = 187;

    public GuiSynthesis(InventoryPlayer inventoryPlayer, TileEntitySynthesis synthesis) {
        super(new ContainerSynthesis(inventoryPlayer, synthesis));
        this.xSize = guiWidth;
        this.ySize = guiHeight;
        addTab(new TabStateControlSynthesis(this, synthesis));
        addTab(new TabEnergySynthesis(this, synthesis));
        addTab(new TabHelp(this, MinechemHelper.getLocalString("help.synthesis")));
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

        this.mc.renderEngine.bindTexture(new ResourceLocation(ModMinechem.ID, ConstantValue.SYNTHESIS_GUI));
        int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;
        drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);
    }

    @Override
    public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w,
                                     int h) {
        // TODO Auto-generated method stub
        return false;
    }

}
