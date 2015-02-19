package minechem.apparatus.tier1.opticalMicroscope;

import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.chemical.ChemicalBase;
import minechem.chemical.Element;
import minechem.helper.AchievementHelper;
import minechem.helper.LocalizationHelper;
import minechem.Compendium;
import minechem.helper.ResearchHelper;
import minechem.item.chemical.ChemicalItem;
import minechem.registry.ResearchRegistry;
import net.minecraft.client.Minecraft;
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

    public OpticalMicroscopeGUI(InventoryPlayer inventoryPlayer, OpticalMicroscopeTileEntity opticalMicroscope)
    {
        super(new OpticalMicroscopeContainer(inventoryPlayer, opticalMicroscope));
        texture = Compendium.Resource.GUI.opticalMicroscope;
        name = LocalizationHelper.getLocalString("tile.opticalMicroscope.name");
    }

    private void drawMicroscopeOverlay()
    {
        this.zLevel = 600.0F;
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft + 13, guiTop + 16, 176, 0, 54, 54);
    }
    
    private void drawInfo()
    {
        Slot slot = inventorySlots.getSlotFromInventory(((OpticalMicroscopeContainer)inventorySlots).getOpticalMicroscope(), 0);
        if (slot.getHasStack())
        {
            ItemStack itemStack = slot.getStack();
            if (prevStack != itemStack && itemStack.getItem() instanceof ChemicalItem)
            {
                prevStack = itemStack;
                ChemicalBase chemicalBase = ChemicalBase.readFromNBT(itemStack.getTagCompound());
                if (chemicalBase.isElement())
                {
                    AchievementHelper.giveAchievement(getPlayer(), (Element) chemicalBase, getWorld().isRemote);
                    ResearchHelper.addResearch(getPlayer(), chemicalBase.fullName, getWorld().isRemote);
                }
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(opacity, mouseX, mouseY);
        drawMicroscopeOverlay();
        drawInfo();
    }
}
