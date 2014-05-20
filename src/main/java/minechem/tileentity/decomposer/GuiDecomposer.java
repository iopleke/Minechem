package minechem.tileentity.decomposer;

import minechem.ModMinechem;
import minechem.gui.GuiContainerTabbed;
import minechem.gui.tabs.TabEnergy;
import minechem.gui.tabs.TabHelp;
import minechem.gui.tabs.TabStateControlDecomposer;
import minechem.utils.MinechemHelper;
import minechem.utils.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiDecomposer extends GuiContainerTabbed
{

    public static TileEntityDecomposer ENTITY;
    InventoryPlayer PLAYER_INVENTORY;
    int mouseX = 0;
    int mouseY = 0;
    int guiWidth = 176;
    int guiHeight = 166;
    public static ResourceLocation TEXTURE = new ResourceLocation(ModMinechem.ID, Reference.DECOMPOSER_GUI);

    public GuiDecomposer(InventoryPlayer inventoryPlayer, TileEntityDecomposer decomposer)
    {
        super(new ContainerDecomposer(inventoryPlayer, decomposer));
        GuiDecomposer.ENTITY = decomposer;
        this.PLAYER_INVENTORY = inventoryPlayer;
        addTab(new TabStateControlDecomposer(this, decomposer));
        addTab(new TabEnergy(this, decomposer));
        addTab(new TabHelp(this, MinechemHelper.getLocalString("help.decomposer")));

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        super.drawGuiContainerForegroundLayer(par1, par2);
        String info = MinechemHelper.getLocalString("gui.title.decomposer");
        int infoWidth = fontRenderer.getStringWidth(info);
        fontRenderer.drawString(info, (guiWidth - infoWidth) / 2, 5, 0xCCCCCC);
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

}
