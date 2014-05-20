package minechem.tileentity.multiblock;

import minechem.ModMinechem;
import minechem.gui.GuiMinechemContainer;
import minechem.utils.MinechemHelper;
import minechem.utils.Reference;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiFission extends GuiMinechemContainer
{

    int guiWidth = 176;
    int guiHeight = 166;

    public GuiFission(Container par1Container)
    {
        super(par1Container);
    }

    public GuiFission(InventoryPlayer inventoryPlayer, TileEntityFission fission)
    {
        super(new ContainerFission(inventoryPlayer, fission));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String info = MinechemHelper.getLocalString("Fission");
        int infoWidth = fontRenderer.getStringWidth(info);
        fontRenderer.drawString(info, (width - infoWidth) / 2, 5, 0xFFFFFF);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.renderEngine.bindTexture(new ResourceLocation(ModMinechem.ID, Reference.FISSION_GUI));
        int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;
        // DRAW GUI
        drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);

    }

}