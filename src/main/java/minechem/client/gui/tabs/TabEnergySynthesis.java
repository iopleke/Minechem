package minechem.client.gui.tabs;

import minechem.api.recipe.SynthesisRecipe;
import minechem.common.tileentity.TileEntitySynthesis;
import minechem.common.utils.MinechemHelper;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.common.ForgeDirection;

public class TabEnergySynthesis extends TabEnergy
{

    TileEntitySynthesis ENTITY;

    public TabEnergySynthesis(Gui gui, TileEntitySynthesis synthesis)
    {
        super(gui, synthesis);
        this.ENTITY = synthesis;
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

        int energyCost = 0;
        SynthesisRecipe recipe = ENTITY.getCurrentRecipe();
        if (recipe != null)
        {
            energyCost = recipe.energyCost();
        }

        // Title.
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.energy"), x + 22, y + 8, headerColour);

        // Current amount of energy in the machine.
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.stored") + ":", x + 22, y + 68, subheaderColour);
        fontRenderer.drawString(String.valueOf(ENTITY.getEnergy(ForgeDirection.UNKNOWN)), x + 22, y + 80, textColour);

        // Total amount of energy needed to create this compound.
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.activationEnergy") + ":", x + 22, y + 20, subheaderColour);
        fontRenderer.drawString(String.valueOf(energyCost), x + 22, y + 32, textColour);

        // Last known amount of energy being used.
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.maxUsage") + ":", x + 22, y + 44, subheaderColour);
        fontRenderer.drawString(String.valueOf(ENTITY.getPowerRemainingScaled(100)) + "%", x + 22, y + 56, textColour);
    }

    @Override
    public String getTooltip()
    {
        if (!isOpen())
        {
            return ENTITY.getPowerRemainingScaled(100) + "%";
        }
        else
            return null;
    }

}
