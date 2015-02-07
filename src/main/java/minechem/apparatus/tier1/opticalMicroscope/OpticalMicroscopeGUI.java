package minechem.apparatus.tier1.opticalMicroscope;

import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.helper.LocalizationHelper;
import minechem.reference.Compendium;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;

/**
 *
 *
 */
public class OpticalMicroscopeGUI extends BasicGuiContainer
{

    public OpticalMicroscopeGUI(InventoryPlayer inventoryPlayer, OpticalMicroscopeTileEntity opticalMicroscope)
    {
        super(new OpticalMicroscopeContainer(inventoryPlayer, opticalMicroscope));
        texture = Compendium.Resource.GUI.opticalMicroscope;
        name = LocalizationHelper.getLocalString("tile.opticalMicroscope.name");
    }

    private void drawMicroscopeOverlay()
    {
        this.zLevel = 600F;
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft + 13, guiTop + 16, 176, 0, 54, 54);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(opacity, mouseX, mouseY);
        drawMicroscopeOverlay();
    }
}
