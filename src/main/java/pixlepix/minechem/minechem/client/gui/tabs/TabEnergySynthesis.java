package pixlepix.minechem.minechem.client.gui.tabs;

import pixlepix.minechem.minechem.api.recipe.SynthesisRecipe;
import pixlepix.minechem.minechem.common.tileentity.MinechemTileEntity;
import pixlepix.minechem.minechem.common.tileentity.TileEntitySynthesis;
import pixlepix.minechem.minechem.common.utils.MinechemHelper;
import net.minecraft.client.gui.Gui;

public class TabEnergySynthesis extends TabEnergy {

    TileEntitySynthesis synthesis;

    public TabEnergySynthesis(Gui gui, TileEntitySynthesis synthesis) {
        super(gui, (MinechemTileEntity) synthesis);
        this.synthesis = synthesis;
    }

    @Override
    public void draw(int x, int y) {
        drawBackground(x, y);
        drawIcon(x + 2, y + 2);
        if (!isFullyOpened())
            return;

        int energyCost = 0;
        SynthesisRecipe recipe = synthesis.getCurrentRecipe();
        if (recipe != null)
            energyCost = recipe.energyCost();

        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.energy"), x + 22, y + 8, headerColour);

        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.stored") + ":", x + 22, y + 68, subheaderColour);
        fontRenderer.drawString(String.format("%d", energy.getEnergyStored()) + " MJ", x + 22, y + 80, textColour);

        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.activationEnergy") + ":", x + 22, y + 20, subheaderColour);
        fontRenderer.drawString(energyCost + " MJ", x + 22, y + 32, textColour);

        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.maxUsage") + ":", x + 22, y + 44, subheaderColour);
        fontRenderer.drawString(energy.getRequest() + " MJ/t", x + 22, y + 56, textColour);
    }

    @Override
    public String getTooltip() {
        if (!isOpen()) {
            return String.format("%.1f", energy.getRequest()) + " MJ/t";
        } else
            return null;
    }

}
