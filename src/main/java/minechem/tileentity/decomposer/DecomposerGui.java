package minechem.tileentity.decomposer;

import cpw.mods.fml.common.Optional;
import minechem.Minechem;
import minechem.gui.GuiContainerTabbed;
import minechem.gui.GuiTabHelp;
import minechem.utils.MinechemHelper;
import minechem.utils.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class DecomposerGui extends GuiContainerTabbed
{

    public static DecomposerTileEntity ENTITY;
    InventoryPlayer PLAYER_INVENTORY;
    int mouseX = 0;
    int mouseY = 0;
    int guiWidth = 176;
    int guiHeight = 166;
    public static ResourceLocation TEXTURE = new ResourceLocation(Minechem.ID, Reference.DECOMPOSER_GUI);

    public DecomposerGui(InventoryPlayer inventoryPlayer, DecomposerTileEntity decomposer)
    {
        super(new DecomposerContainer(inventoryPlayer, decomposer));
        DecomposerGui.ENTITY = decomposer;
        this.PLAYER_INVENTORY = inventoryPlayer;
        addTab(new DecomposerTabStateControl(this, decomposer));
        addTab(new GuiTabHelp(this, MinechemHelper.getLocalString("help.decomposer")));

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String info = MinechemHelper.getLocalString("gui.title.decomposer");
        int infoWidth = fontRendererObj.getStringWidth(info);
        fontRendererObj.drawString(info, (guiWidth - infoWidth) / 2, 5, 0x000000);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.renderEngine.bindTexture(TEXTURE);
        int x = (width - guiWidth) / 2;
        int y = (height - guiHeight) / 2;
        drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);

    }

    @Override
    public boolean hideItemPanelSlot(GuiContainer gui, int x, int y, int w, int h)
    {
        return false;
    }

    @Optional.Method(modid = "NotEnoughItems")
    @Override
    public Iterable<Integer> getItemSpawnSlots(GuiContainer gc, ItemStack is)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
