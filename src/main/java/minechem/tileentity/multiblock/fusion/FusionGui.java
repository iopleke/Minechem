package minechem.tileentity.multiblock.fusion;

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

public class FusionGui extends GuiContainerTabbed
{

	private static final ResourceLocation resourceLocationFissionGUI = new ResourceLocation(Minechem.ID, Reference.FUSION_GUI);
	static float increaseRate = .2F;
	static float decreaseRate = .4F;

	FusionTileEntity fusion;
	static int guiWidth = 176;
	static int guiHeight = 187;
	int storedEnergy;
	int maxEnergy;

	public FusionGui(InventoryPlayer inventoryPlayer, FusionTileEntity fusion)
	{
		super(new FusionContainer(inventoryPlayer, fusion));
		this.fusion = fusion;
		this.xSize = guiWidth;
		this.ySize = guiHeight;
		this.storedEnergy = this.fusion.getEnergyStored();
		this.maxEnergy = fusion.getMaxEnergyStored();
		addTab(new GuiTabHelp(this, MinechemHelper.getLocalString("help.fusion")));
		addTab(new FusionTabStateControl(this, fusion));
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
		updateEnergy();

		this.mc.renderEngine.bindTexture(resourceLocationFissionGUI);
		int x = (width - guiWidth) / 2;
		int y = (height - guiHeight) / 2;
		// DRAW GUI
		drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);

		// DRAW ENERGY BAR OVERLAY
		drawEnergyBarOverlay(x, y);
	}

	public void drawEnergyBarOverlay(int x, int y)
	{
		// @TODO - calculate energybar width based on machine state and energy
		drawTexturedModalRect(x + 8, y + 38, 0, 187, this.fusion.getPowerRemainingScaled(160), 3);

	}

	private void updateEnergy()
	{
		this.storedEnergy = this.fusion.getEnergyStored();
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
