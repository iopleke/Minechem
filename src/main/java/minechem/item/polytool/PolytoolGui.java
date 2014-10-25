package minechem.item.polytool;

import minechem.MinechemItemsRegistration;
import minechem.gui.GuiContainerTabbed;
import minechem.gui.GuiTabHelp;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementGuiHelper;
import minechem.utils.MinechemHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class PolytoolGui extends GuiContainerTabbed
{
	private static final ResourceLocation texture = new ResourceLocation("minechem", "textures/gui/polytool.png");
	private static final ItemStack polytoolItem = new ItemStack(MinechemItemsRegistration.polytool);
	private static final Random rand=new Random();
	public ArrayList<ElementGuiHelper> elements = new ArrayList();
	long renders;
	ItemStack polytool;
	InventoryPlayer player;
	boolean shouldUpdate;

	public PolytoolGui(PolytoolContainer polytoolContainer)
	{
		super(polytoolContainer);
		xSize = 176;
		ySize = 218;
		this.polytool = polytoolContainer.player.getCurrentItem();
		this.player = polytoolContainer.player;
		this.shouldUpdate = true;
		ItemStack stack = polytoolContainer.player.getCurrentItem();
		if (stack.getItem() instanceof PolytoolItem)
		{
			ArrayList<PolytoolUpgradeType> upgrades = PolytoolItem.getUpgrades(stack);
			Iterator<PolytoolUpgradeType> iter = upgrades.iterator();
			while (iter.hasNext())
			{
				PolytoolUpgradeType upgrade = iter.next();
				ElementEnum element = upgrade.getElement();
				for (int i = 0; i < upgrade.power; i++)
				{

					elements.add(new ElementGuiHelper(1 + rand.nextInt(2), rand.nextDouble() * Math.PI * 2, element));
				}

			}
		}
		addTab(new GuiTabHelp(this, MinechemHelper.getLocalString("help.polytool")));

	}

	public void drawItemStack(ItemStack par1ItemStack, int par2, int par3, String par4Str)
	{
		par2 += guiLeft;
		par3 += guiTop;

		// Copied from GuiContainer
		GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		this.zLevel = 3;
		itemRender.zLevel = 200.0F;
		FontRenderer font = null;
		if (par1ItemStack != null)
		{
			font = par1ItemStack.getItem().getFontRenderer(par1ItemStack);
		}
		if (font == null)
		{
			font = fontRendererObj;
		}
		itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3);
		itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3 - (8), par4Str);
		this.zLevel = 3;
		itemRender.zLevel = 0.0F;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		renders++;

		Iterator renderIter = elements.iterator();
		while (renderIter.hasNext())
		{
			((ElementGuiHelper) renderIter.next()).draw(this, renders);
		}

		drawItemStack(polytoolItem, 80, 42, "");
		String localizedName;

		localizedName = MinechemHelper.getLocalString("gui.polytool.sword");
		if (localizedName.isEmpty() || localizedName == "gui.polytool.sword")
		{
			localizedName = "Sword";
		}
		fontRendererObj.drawString(localizedName + ": " + PolytoolItem.instance.getSwordStr(polytool), guiLeft + 10, guiTop + 80, 0x404040);

		localizedName = MinechemHelper.getLocalString("gui.polytool.ores");
		if (localizedName.isEmpty() || localizedName == "gui.polytool.ores")
		{
			localizedName = "Ores";
		}
		fontRendererObj.drawString(localizedName + ": " + PolytoolItem.instance.getPickaxeStr(polytool), guiLeft + 10, guiTop + 90, 0x404040);

		localizedName = MinechemHelper.getLocalString("gui.polytool.stone");
		if (localizedName.isEmpty() || localizedName == "gui.polytool.stone")
		{
			localizedName = "Stone";
		}
		fontRendererObj.drawString(localizedName + ": " + PolytoolItem.instance.getStoneStr(polytool), guiLeft + 10, guiTop + 100, 0x404040);

		localizedName = MinechemHelper.getLocalString("gui.polytool.axe");
		if (localizedName.isEmpty() || localizedName == "gui.polytool.axe")
		{
			localizedName = "Axe";
		}
		fontRendererObj.drawString(localizedName + ": " + PolytoolItem.instance.getAxeStr(polytool), guiLeft + 10, guiTop + 110, 0x404040);

		localizedName = MinechemHelper.getLocalString("gui.polytool.shovel");
		if (localizedName.isEmpty() || localizedName == "gui.polytool.shovel")
		{
			localizedName = "Shovel";
		}
		fontRendererObj.drawString(localizedName + ": " + PolytoolItem.instance.getShovelStr(polytool), guiLeft + 10, guiTop + 120, 0x404040);
	}

	public void addUpgrade(PolytoolUpgradeType upgrade)
	{
		for (int i = 0; i < upgrade.power; i++)
		{
			elements.add(new ElementGuiHelper(1 + rand.nextInt(2), rand.nextDouble() * Math.PI * 2, upgrade.getElement()));
			this.shouldUpdate = true;
		}
	}
}
