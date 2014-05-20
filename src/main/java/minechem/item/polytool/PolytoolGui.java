package minechem.item.polytool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import minechem.MinechemItemsGeneration;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementGuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class PolytoolGui extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation("minechem", "textures/gui/polytool.png");
    public ArrayList<ElementGuiHelper> elements = new ArrayList();
    long renders;
    ItemStack polytool;
    InventoryPlayer player;

    public PolytoolGui(PolytoolContainer par1Container)
    {
        super(par1Container);
        xSize = 176;
        ySize = 218;
        this.polytool = par1Container.player.getCurrentItem();
        this.player = par1Container.player;
        ItemStack stack = par1Container.player.getCurrentItem();
        if (stack.getItem() instanceof PolytoolItem)
        {
            ArrayList<PolytoolUpgradeType> upgrades = PolytoolItem.getUpgrades(stack);
            Iterator<PolytoolUpgradeType> iter = upgrades.iterator();
            Random rand = new Random();
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
    }

    public void drawItemStack(ItemStack par1ItemStack, int par2, int par3, String par4Str)
    {
        par2 += guiLeft;
        par3 += guiTop;

        // Copied from GuiContainer
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRenderer.zLevel = 200.0F;
        FontRenderer font = null;
        if (par1ItemStack != null)
            font = par1ItemStack.getItem().getFontRenderer(par1ItemStack);
        if (font == null)
            font = fontRenderer;
        itemRenderer.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3);
        itemRenderer.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3 - (8), par4Str);
        this.zLevel = 0.0F;
        itemRenderer.zLevel = 0.0F;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        renders++;
        Iterator iter = elements.iterator();
        while (iter.hasNext())
        {
            ((ElementGuiHelper) iter.next()).draw(this, renders);
        }

        drawItemStack(new ItemStack(MinechemItemsGeneration.polytool), 80, 42, "");
        fontRenderer.drawString("Sword: " + PolytoolItem.instance.getSwordStr(polytool), guiLeft + 10, guiTop + 80, 0x404040);
        fontRenderer.drawString("Ores: " + PolytoolItem.instance.getPickaxeStr(polytool), guiLeft + 10, guiTop + 90, 0x404040);
        fontRenderer.drawString("Stone: " + PolytoolItem.instance.getStoneStr(polytool), guiLeft + 10, guiTop + 100, 0x404040);
        fontRenderer.drawString("Axe: " + PolytoolItem.instance.getAxeStr(polytool), guiLeft + 10, guiTop + 110, 0x404040);
        fontRenderer.drawString("Shovel: " + PolytoolItem.instance.getShovelStr(polytool), guiLeft + 10, guiTop + 120, 0x404040);

    }

    public void addUpgrade(PolytoolUpgradeType upgrade)
    {
        Random rand = new Random();
        for (int i = 0; i < upgrade.power; i++)
        {
            elements.add(new ElementGuiHelper(1 + rand.nextInt(2), rand.nextDouble() * Math.PI * 2, upgrade.getElement()));
        }
    }

}
