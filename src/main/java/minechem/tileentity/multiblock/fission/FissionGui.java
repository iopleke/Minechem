package minechem.tileentity.multiblock.fission;

import minechem.gui.GuiContainerTabbed;
import minechem.gui.GuiTabHelp;
import minechem.reference.Reference;
import minechem.reference.Textures;
import minechem.utils.MinechemHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FissionGui extends GuiContainerTabbed
{

	private static final ResourceLocation resourceLocationFissionGUI = new ResourceLocation(Reference.ID, Textures.FISSION_GUI);
	int guiWidth = 176;
	int guiHeight = 166;

	public FissionGui(Container par1Container)
	{
		super(par1Container);
	}

	public FissionGui(InventoryPlayer inventoryPlayer, FissionTileEntity fission)
	{
		super(new FissionContainer(inventoryPlayer, fission));
        addTab(new FissionTabStateControl(this, fission));
		addTab(new GuiTabHelp(this, MinechemHelper.getLocalString("help.fission")));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.drawGuiContainerForegroundLayer(par1, par2);
		String info = MinechemHelper.getLocalString("block.fissionReactor.name");
		int infoWidth = fontRendererObj.getStringWidth(info);
		fontRendererObj.drawString(info, (guiWidth - infoWidth) / 2, 5, 0x000000);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.renderEngine.bindTexture(resourceLocationFissionGUI);
		int x = (width - guiWidth) / 2;
		int y = (height - guiHeight) / 2;
		// DRAW GUI
		drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);

	}

}
