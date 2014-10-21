package minechem.tileentity.multiblock.fission;

import cpw.mods.fml.common.Optional;
import minechem.Minechem;
import minechem.gui.GuiContainerTabbed;
import minechem.gui.GuiTabHelp;
import minechem.utils.MinechemHelper;
import minechem.utils.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FissionGui extends GuiContainerTabbed
{

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

		this.mc.renderEngine.bindTexture(new ResourceLocation(Minechem.ID, Reference.FISSION_GUI));
		int x = (width - guiWidth) / 2;
		int y = (height - guiHeight) / 2;
		// DRAW GUI
		drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);

	}

}
