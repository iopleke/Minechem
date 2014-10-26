package minechem.tileentity.decomposer;

import minechem.gui.GuiContainerTabbed;
import minechem.gui.GuiTabHelp;
import minechem.reference.Resources;
import minechem.utils.MinechemHelper;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class DecomposerGui extends GuiContainerTabbed
{

	public static DecomposerTileEntity ENTITY;
	InventoryPlayer PLAYER_INVENTORY;
	int mouseX = 0;
	int mouseY = 0;
	int guiWidth = 176;
	int guiHeight = 166;

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

		this.mc.renderEngine.bindTexture(Resources.Gui.DECOMPOSER);
		int x = (width - guiWidth) / 2;
		int y = (height - guiHeight) / 2;
		drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);

	}

}
