package minechem.tileentity.microscope;

import minechem.gui.GuiContainerTabbed;
import minechem.gui.GuiTabHelp;
import minechem.reference.Resources;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.decomposer.DecomposerRecipeChance;
import minechem.tileentity.decomposer.DecomposerRecipeHandler;
import minechem.tileentity.decomposer.DecomposerRecipeSelect;
import minechem.tileentity.synthesis.SynthesisRecipe;
import minechem.tileentity.synthesis.SynthesisRecipeHandler;
import minechem.utils.Constants;
import minechem.utils.MinechemHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class MicroscopeGui extends GuiContainerTabbed
{
	int guiWidth = 176;
	int guiHeight = 217;
	int eyepieceX = 25;
	int eyepieceY = 26;
	int inputSlotX = 44;
	int inputSlotY = 45;
	int slideShowTimer = 0;
	int currentSlide = 0;
	public InventoryPlayer inventoryPlayer;
	protected MicroscopeTileEntity microscope;
	MicroscopeGuiSwitch recipeSwitch;
	private boolean isShapedRecipe;

	public MicroscopeGui(InventoryPlayer inventoryPlayer, MicroscopeTileEntity microscope)
	{
		super(new MicroscopeContainer(inventoryPlayer, microscope));
		this.inventoryPlayer = inventoryPlayer;
		this.microscope = microscope;
		this.xSize = guiWidth;
		this.ySize = guiHeight;
		this.itemRenderer = new MicroscopeRenderGUIItem(this);
		this.recipeSwitch = new MicroscopeGuiSwitch(this);
		addTab(new GuiTabHelp(this, MinechemHelper.getLocalString("help.microscope")));
	}

	public boolean isMouseInMicroscope()
	{
		int mouseX = getMouseX();
		int mouseY = getMouseY();
		int x = (width - guiWidth) / 2;
		int y = (height - guiHeight) / 2;
		x += eyepieceX;
		y += eyepieceY;
		int h = 54;
		int w = 54;
		return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
	}

	private void drawMicroscopeOverlay()
	{
		zLevel = 401;
		drawTexturedModalRect(eyepieceX, eyepieceY, 176, 0, 54, 54);
	}

	private void drawUnshapedOverlay()
	{
		zLevel = 0;
		drawTexturedModalRect(97, 26, 176, 70, 54, 54);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		super.drawGuiContainerForegroundLayer(par1, par2);
		String info = MinechemHelper.getLocalString("gui.title.microscope");
		int infoWidth = fontRendererObj.getStringWidth(info);
		fontRendererObj.drawString(info, (guiWidth - infoWidth) / 2, 5, 0x000000);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().renderEngine.bindTexture(Resources.Gui.MICROSCOPE);
		int x = (width - guiWidth) / 2;
		int y = (height - guiHeight) / 2;
		zLevel = 0;

		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0.0F);
		drawTexturedModalRect(0, 0, 0, 0, guiWidth, guiHeight);
		drawMicroscopeOverlay();
		if (!isShapedRecipe)
		{
			drawUnshapedOverlay();
		}
		GL11.glPopMatrix();

		recipeSwitch.setPos(x + 153, y + 26);
		recipeSwitch.draw(mc.renderEngine);

		ItemStack itemstack = microscope.getStackInSlot(0);
		clearRecipeMatrix();
		if (itemstack != null)
		{
			if (recipeSwitch.getState() == 0)
			{
				drawSynthesisRecipe(itemstack, x, y);
			} else
			{
				isShapedRecipe = false;
				drawDecomposerRecipe(itemstack, x, y);
			}
		}
	}

	private void clearRecipeMatrix()
	{
		for (int slot = 2; slot < 2 + 9; slot++)
		{
			this.inventorySlots.putStackInSlot(slot, null);
		}
	}

	private void drawSynthesisRecipe(ItemStack inputstack, int x, int y)
	{
		SynthesisRecipe recipe = SynthesisRecipeHandler.instance.getRecipeFromOutput(inputstack);
		if (recipe != null)
		{
			drawSynthesisRecipeMatrix(recipe, x, y);
			drawSynthesisRecipeCost(recipe, x, y);
		}
	}

	private void drawSynthesisRecipeMatrix(SynthesisRecipe recipe, int x, int y)
	{
		isShapedRecipe = recipe.isShaped();
		ItemStack[] shapedRecipe = MinechemHelper.convertChemicalArrayIntoItemStackArray(recipe.getShapedRecipe());
		int slot = 2;
		for (ItemStack itemstack : shapedRecipe)
		{
			this.inventorySlots.putStackInSlot(slot, itemstack);
			slot++;
			if (slot >= 11)
			{
				break;
			}
		}
	}

	private void drawSynthesisRecipeCost(SynthesisRecipe recipe, int x, int y)
	{
		if (!recipeSwitch.isMoverOver())
		{
			String cost = String.format("%d Energy", recipe.energyCost());
			fontRendererObj.drawString(cost, x + 108, y + 85, 0x000000);
		}
	}

	private void drawDecomposerRecipe(ItemStack inputstack, int x, int y)
	{
		DecomposerRecipe recipe = DecomposerRecipeHandler.instance.getRecipe(inputstack);
		if (recipe != null)
		{
			ArrayList<ItemStack> output = MinechemHelper.convertChemicalsIntoItemStacks(recipe.getOutputRaw());
			if (recipe instanceof DecomposerRecipeSelect)
			{
				drawDecomposerRecipeSelectMatrix(((DecomposerRecipeSelect) recipe).getAllPossibleRecipes(), x, y);
			} else
			{
				drawDecomposerRecipeMatrix(output, x, y);
			}
			drawDecomposerChance(recipe, x, y);
		}
	}

	private void drawDecomposerRecipeMatrix(ArrayList<ItemStack> output, int x, int y)
	{
		int slot = 2;
		for (ItemStack itemstack : output)
		{
			this.inventorySlots.putStackInSlot(slot, itemstack);
			slot++;
			if (slot >= 11)
			{
				break;
			}
		}
	}

	private void drawDecomposerRecipeSelectMatrix(ArrayList<DecomposerRecipe> recipes, int x, int y)
	{
		if (slideShowTimer == Constants.TICKS_PER_SECOND * 8)
		{
			slideShowTimer = 0;
			currentSlide++;
		}

		if (currentSlide == recipes.size())
		{
			currentSlide = 0;
		}

		slideShowTimer++;
		DecomposerRecipe recipe = recipes.get(currentSlide);
		ArrayList<ItemStack> output = MinechemHelper.convertChemicalsIntoItemStacks(recipe.getOutputRaw());
		drawDecomposerRecipeMatrix(output, x, y);
	}

	private void drawDecomposerChance(DecomposerRecipe recipe, int x, int y)
	{
		if (!recipeSwitch.isMoverOver() && recipe instanceof DecomposerRecipeChance)
		{
			DecomposerRecipeChance recipeChance = (DecomposerRecipeChance) recipe;
			int chance = (int) (recipeChance.getChance() * 100);
			String info = String.format("%d%%", chance);
			fontRendererObj.drawString(info, x + 108, y + 85, 0x000000);
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int mouseButton)
	{
		super.mouseClicked(x, y, mouseButton);
		this.recipeSwitch.mouseClicked(x, y, mouseButton);
	}

}
