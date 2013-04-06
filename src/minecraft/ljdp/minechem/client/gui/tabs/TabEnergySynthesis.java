package ljdp.minechem.client.gui.tabs;

import ljdp.minechem.api.recipe.SynthesisRecipe;
import ljdp.minechem.common.MinechemPowerProvider;
import ljdp.minechem.common.tileentity.TileEntitySynthesis;
import ljdp.minechem.common.utils.MinechemHelper;
import net.minecraft.client.gui.Gui;
import buildcraft.api.power.IPowerReceptor;

public class TabEnergySynthesis extends TabEnergy {

    TileEntitySynthesis synthesis;

    public TabEnergySynthesis(Gui gui, TileEntitySynthesis synthesis) {
        super(gui, (IPowerReceptor) synthesis);
        this.synthesis = synthesis;
    }

    @Override
    public void draw(int x, int y) {
        drawBackground(x, y);
        drawIcon(powerIcon, x + 2, y + 2);
        if (!isFullyOpened())
            return;

        MinechemPowerProvider provider = (MinechemPowerProvider) powerReceptor.getPowerProvider();
        int energyCost = 0;
        SynthesisRecipe recipe = synthesis.getCurrentRecipe();
        if (recipe != null)
            energyCost = recipe.energyCost();

        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.energy"), x + 22, y + 8, headerColour);

        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.stored") + ":", x + 22, y + 68, subheaderColour);
        fontRenderer.drawString(String.format("%.1f", provider.getEnergyStored()) + " MJ", x + 22, y + 80, textColour);

        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.activationEnergy") + ":", x + 22, y + 20, subheaderColour);
        fontRenderer.drawString(energyCost + " MJ", x + 22, y + 32, textColour);

        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.maxUsage") + ":", x + 22, y + 44, subheaderColour);
        fontRenderer.drawString(provider.getMaxEnergyReceived() + " MJ/t", x + 22, y + 56, textColour);
    }

    @Override
    public String getTooltip() {
        if (!isOpen()) {
            MinechemPowerProvider provider = (MinechemPowerProvider) powerReceptor.getPowerProvider();
            return String.format("%.1f", provider.getCurrentEnergyUsage()) + " MJ/t";
        } else
            return null;
    }

}
