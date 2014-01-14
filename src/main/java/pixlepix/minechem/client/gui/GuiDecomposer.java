package pixlepix.minechem.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import pixlepix.minechem.client.gui.tabs.TabEnergy;
import pixlepix.minechem.client.gui.tabs.TabHelp;
import pixlepix.minechem.client.gui.tabs.TabStateControlDecomposer;
import pixlepix.minechem.common.containers.ContainerDecomposer;
import pixlepix.minechem.common.tileentity.TileEntityDecomposer;
import pixlepix.minechem.common.utils.ConstantValue;
import pixlepix.minechem.common.utils.MinechemHelper;


public class GuiDecomposer extends GuiContainerTabbed {

    TileEntityDecomposer decomposer;
    InventoryPlayer inventoryPlayer;
    int mouseX = 0;
    int mouseY = 0;
    int guiWidth = 176;
    int guiHeight = 166;

    public GuiDecomposer(InventoryPlayer inventoryPlayer, TileEntityDecomposer decomposer) {
        super(new ContainerDecomposer(inventoryPlayer, decomposer));
        this.decomposer = decomposer;
        this.inventoryPlayer = inventoryPlayer;
        addTab(new TabStateControlDecomposer(this, decomposer));
        addTab(new TabEnergy(this, decomposer));
        addTab(new TabHelp(this, MinechemHelper.getLocalString("help.decomposer")));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String info = "Chemical Decomposer";
        int infoWidth = fontRenderer.getStringWidth(info);
        fontRenderer.drawString(info, (guiWidth - infoWidth) / 2, 5, 0xCCCCCC);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.renderEngine.bindTexture(new ResourceLocation(ConstantValue.MOD_ID, ConstantValue.DECOMPOSER_GUI));
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
