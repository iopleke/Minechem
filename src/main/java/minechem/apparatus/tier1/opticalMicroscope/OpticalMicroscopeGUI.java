package minechem.apparatus.tier1.opticalMicroscope;

import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.chemical.ChemicalBase;
import minechem.chemical.Element;
import minechem.chemical.Molecule;
import minechem.helper.AchievementHelper;
import minechem.helper.LocalizationHelper;
import minechem.Compendium;
import minechem.helper.ResearchHelper;
import minechem.helper.StringHelper;
import minechem.item.chemical.ChemicalItem;
import minechem.proxy.client.render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 *
 *
 */
public class OpticalMicroscopeGUI extends BasicGuiContainer
{
    
    private ItemStack prevStack;
    protected OpticalMicroscopeTileEntity opticalMicroscope;
    protected static final int eyePieceX = 13;
    protected static final int eyePieceY = 16;
    protected static final int eyePieceW = 54;
    protected static final int eyePieceH = 54;
    private RenderItem renderItem;

    public OpticalMicroscopeGUI(InventoryPlayer inventoryPlayer, OpticalMicroscopeTileEntity opticalMicroscope)
    {
        super(new OpticalMicroscopeContainer(inventoryPlayer, opticalMicroscope));
        this.opticalMicroscope = opticalMicroscope;
        texture = Compendium.Resource.GUI.opticalMicroscope;
        name = LocalizationHelper.getLocalString("tile.opticalMicroscope.name");
        renderItem = new MicroscopeRenderItem(this);
    }

    public boolean isMouseInMicroscope()
    {
        return mouseX >= eyePieceX && mouseX <= eyePieceX + eyePieceW && mouseY >= eyePieceY && mouseY <= eyePieceY + eyePieceH;
    }

    private void drawMicroscopeOverlay()
    {
        RenderHelper.resetOpenGLColour();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        float z = this.zLevel;
        this.zLevel = 600.0F;
        drawTexturedModalRect(eyePieceX, eyePieceY, 176, 0, eyePieceH, eyePieceW);
        this.zLevel -= 10.0F;
        drawTexturedModalRect(eyePieceX, eyePieceY, 176, eyePieceH, eyePieceH, eyePieceW);
        this.zLevel = z;
    }
    
    private void drawInfo()
    {
        Slot slot = inventorySlots.getSlotFromInventory(opticalMicroscope, 0);
        if (slot.getHasStack())
        {
            ItemStack itemStack = slot.getStack();
            if (itemStack.getItem() instanceof ChemicalItem)
            {
                ChemicalBase chemicalBase = ChemicalBase.readFromNBT(itemStack.getTagCompound());
                fontRendererObj.drawString(chemicalBase.fullName, eyePieceX + eyePieceH + 5, eyePieceY, 0);
                fontRendererObj.drawString("Formula:", eyePieceX + eyePieceH + 5, eyePieceY +10, 0);
                fontRendererObj.drawString(chemicalBase.getFormula(), eyePieceX + eyePieceH + 5, eyePieceY +20, 0);

                if (!chemicalBase.isElement())
                {
                    RenderHelper.drawTexturedRectUV(eyePieceX + eyePieceW + 50, eyePieceY + 5, 0, 0, 0, 200, 200, 60, 60, ((Molecule)chemicalBase).getStructureResource());
                }

                if (prevStack != itemStack)
                {
                    prevStack = itemStack;
                    if (chemicalBase.isElement())
                    {
                        AchievementHelper.giveAchievement(getPlayer(), (Element) chemicalBase, getWorld().isRemote);
                    }
                    ResearchHelper.addResearch(getPlayer(), chemicalBase.fullName, getWorld().isRemote);
                }
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int i1)
    {
        super.drawGuiContainerForegroundLayer(i, i1);
        drawMicroscopeOverlay();
        drawInfo();
    }

    @Override
    public void drawScreen(int x, int y, float z)
    {
        super.drawScreen(x, y, z);
        renderItem.renderItemAndEffectIntoGUI(fontRendererObj, this.mc.getTextureManager(), opticalMicroscope.getStackInSlot(0), x, y);
        renderItem.renderItemAndEffectIntoGUI(fontRendererObj, this.mc.getTextureManager(), getContainer().getInventoryPlayer().getItemStack(), x, y);
    }
}
