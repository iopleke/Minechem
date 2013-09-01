package ljdp.minechem.client.gui;

import ljdp.minechem.client.gui.tabs.TabEnergySynthesis;
import ljdp.minechem.client.gui.tabs.TabHelp;
import ljdp.minechem.client.gui.tabs.TabStateControlSynthesis;
import ljdp.minechem.common.containers.ContainerSynthesis;
import ljdp.minechem.common.tileentity.TileEntitySynthesis;
import ljdp.minechem.common.utils.ConstantValue;
import ljdp.minechem.common.utils.MinechemHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

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

        this.mc.renderEngine.func_110577_a(new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.SYNTHESIS_GUI));
        int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;
        drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);
    }

}
