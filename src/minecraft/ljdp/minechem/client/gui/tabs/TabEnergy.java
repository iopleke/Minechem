package ljdp.minechem.client.gui.tabs;

import ljdp.minechem.common.MinechemPowerProvider;
import ljdp.minechem.common.utils.MinechemHelper;
import ljdp.minechem.common.utils.RollingAverage;
import buildcraft.api.power.IPowerReceptor;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Icon;

public class TabEnergy extends Tab {
    public static Icon powerIcon;
    // I'm copying the layout for the buildcraft
    // engine tab, because users will be familiar.
    int headerColour = 0xe1c92f;
    int subheaderColour = 0xaaafb8;
    int textColour = 0x000000;
    IPowerReceptor powerReceptor;
    float lastEnergy = 0;
    RollingAverage energyUsageRolling = new RollingAverage(100);

    public TabEnergy(Gui gui, IPowerReceptor powerReceptor) {
        super(gui);
        this.maxWidth = 120;
        this.maxHeight = 100;
        this.powerReceptor = powerReceptor;
        this.overlayColor = 0xFF8800;
    }

    @Override
    public void draw(int x, int y) {
        drawBackground(x, y);
        drawIcon(powerIcon, x + 2, y + 2);
        if (!isFullyOpened())
            return;

        MinechemPowerProvider provider = (MinechemPowerProvider) powerReceptor.getPowerProvider();
        energyUsageRolling.add(provider.getCurrentEnergyUsage());
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.energy"), x + 22, y + 8, headerColour);
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.usage") + ":", x + 22, y + 20, subheaderColour);
        fontRenderer.drawString(String.format("%.1f", energyUsageRolling.getAverage()) + " MJ/t", x + 22, y + 32, textColour);
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.maxUsage") + ":", x + 22, y + 44, subheaderColour);
        fontRenderer.drawString(provider.getMaxEnergyReceived() + " MJ/t", x + 22, y + 56, textColour);
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.stored") + ":", x + 22, y + 68, subheaderColour);
        fontRenderer.drawString(String.format("%.1f", provider.getEnergyStored()) + " MJ", x + 22, y + 80, textColour);
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
