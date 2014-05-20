package minechem.gui;

import minechem.ModMinechem;
import minechem.tileentity.prefab.MinechemTileEntity;
import minechem.utils.MinechemHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;

public class GuiTabEnergy extends GuiTab
{
    public static Icon powerIcon;

    // I'm copying the layout for the buildcraft
    // engine tab, because users will be familiar.
    int headerColour = 0xe1c92f;
    int subheaderColour = 0xaaafb8;
    int textColour = 0x000000;

    // Switched from reference to MinechemPowerProvider for 1.6 move to UE
    MinechemTileEntity TILE_ENTITY;

    public GuiTabEnergy(Gui gui, MinechemTileEntity energy)
    {
        super(gui);
        this.maxWidth = 120;
        this.maxHeight = 100;
        this.TILE_ENTITY = energy;
        this.overlayColor = 0xFF8800;
    }

    @Override
    public void draw(int x, int y)
    {
        drawBackground(x, y);
        drawIcon(x + 2, y + 2);
        if (!isFullyOpened())
        {
            return;
        }

        // Energy Tab
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.energy"), x + 22, y + 8, headerColour);

        // Amount of power stored.
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.usage") + ":", x + 22, y + 20, subheaderColour);
        fontRenderer.drawString(String.valueOf(TILE_ENTITY.getEnergy(ForgeDirection.UNKNOWN)), x + 22, y + 32, textColour);

        // Total capacity for operation.
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.maxUsage") + ":", x + 22, y + 44, subheaderColour);
        fontRenderer.drawString(String.valueOf(TILE_ENTITY.getEnergyCapacity(ForgeDirection.UNKNOWN)), x + 22, y + 56, textColour);

        // Percentage of Energy Stored
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.stored") + ":", x + 22, y + 68, subheaderColour);
        fontRenderer.drawString(TILE_ENTITY.getPowerRemainingScaled(100) + "%", x + 22, y + 80, textColour);
    }

    @Override
    public String getTooltip()
    {
        if (!isOpen())
        {
            return TILE_ENTITY.getPowerRemainingScaled(100) + "%";
        }
        else
        {
            return null;
        }
    }

    @Override
    public ResourceLocation getIcon()
    {
        return ModMinechem.ICON_ENERGY;
    }

}
