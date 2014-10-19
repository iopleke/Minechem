package minechem.tileentity.leadedchest;

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

public class LeadedChestGui extends GuiContainerTabbed
{

	int guiWidth = 176;
	int guiHeight = 217;
	LeadedChestTileEntity leadedchest;

	public LeadedChestGui(InventoryPlayer inventoryPlayer, LeadedChestTileEntity leadedChest)
	{
		super(new LeadedChestContainer(inventoryPlayer, leadedChest));
		this.leadedchest = leadedChest;
		addTab(new GuiTabHelp(this, MinechemHelper.getLocalString("help.leadChest")));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.drawGuiContainerForegroundLayer(par1, par2);
		String info = MinechemHelper.getLocalString("gui.title.leadedchest");
		int infoWidth = this.fontRendererObj.getStringWidth(info);
		this.fontRendererObj.drawString(info, (guiWidth - infoWidth) / 2, 5, 0x000000);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.renderEngine.bindTexture(new ResourceLocation(Minechem.ID, Reference.LEADED_CHEST_GUI));

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0.0F);
		GL11.glPopMatrix();
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
