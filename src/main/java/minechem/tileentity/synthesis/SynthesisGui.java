package minechem.tileentity.synthesis;

import minechem.gui.GuiContainerTabbed;
import minechem.gui.GuiTabHelp;
import minechem.reference.Reference;
import minechem.reference.Textures;
import minechem.utils.MinechemHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class SynthesisGui extends GuiContainerTabbed
{

	private static final ResourceLocation resourceLocationSynthesisGUI = new ResourceLocation(Reference.ID, Textures.SYNTHESIS_GUI);
	int guiWidth = 176;
	int guiHeight = 187;

	public SynthesisGui(InventoryPlayer inventoryPlayer, SynthesisTileEntity synthesis)
	{
		super(new SynthesisContainer(inventoryPlayer, synthesis));
		this.xSize = guiWidth;
		this.ySize = guiHeight;
		addTab(new SynthesisTabStateControl(this, synthesis));
		addTab(new GuiTabHelp(this, MinechemHelper.getLocalString("help.synthesis")));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.drawGuiContainerForegroundLayer(par1, par2);
		String info = MinechemHelper.getLocalString("gui.title.synthesis");
		int infoWidth = fontRendererObj.getStringWidth(info);
		fontRendererObj.drawString(info, (guiWidth - infoWidth) / 2, 5, 0x000000);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.renderEngine.bindTexture(resourceLocationSynthesisGUI);
		int x = (width - guiWidth) / 2;
		int y = (height - guiHeight) / 2;
		drawTexturedModalRect(x, y, 0, 0, guiWidth, guiHeight);
	}

}
