package minechem.tileentity.multiblock.fusion;

import minechem.Minechem;
import minechem.gui.GuiContainerTabbed;
import minechem.gui.GuiTabHelp;
import minechem.utils.MinechemHelper;
import minechem.utils.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class FusionGui extends GuiContainerTabbed
{

    static float increaseRate = .2F;
    static float decreaseRate = .4F;

    FusionTileEntity fusion;
    int guiWidth = 176;
    int guiHeight = 187;
    int targetEnergy = 0;

    public FusionGui(InventoryPlayer inventoryPlayer, FusionTileEntity fusion)
    {
        super(new FusionContainer(inventoryPlayer, fusion));
        this.fusion = fusion;
        this.xSize = guiWidth;
        this.ySize = guiHeight;
        addTab(new GuiTabHelp(this, MinechemHelper.getLocalString("help.fusion")));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String info = MinechemHelper.getLocalString("block.fusionReactor.name");
        int infoWidth = fontRendererObj.getStringWidth(info);
        fontRendererObj.drawString(info, (guiWidth - infoWidth) / 2, 5, 0x000000);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.renderEngine.bindTexture(new ResourceLocation(Minechem.ID, Reference.FUSION_GUI));
        int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;
        // DRAW GUI
        drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);

        // DRAW ENERGY BAR
        updateEnergy();

        // @TODO - calculate energybar width based on machine state and energy
        drawTexturedModalRect(x + 8, y + 38, 0, 192, 10, 3);

        // DRAW ENERGY BAR OVERLAY
        drawEnergyBarOverlay();
    }

    public void drawEnergyBarOverlay()
    {

        int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;
        drawTexturedModalRect(x + 7, y + 37, 0, 187, 162, 5);

    }

    private void updateEnergy()
    {
        targetEnergy = fusion.output;
        // @TODO - calculate energy 
    }

    @Override
    public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h)
    {
        return false;
    }

}
