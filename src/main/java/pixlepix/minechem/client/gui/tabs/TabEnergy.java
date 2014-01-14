package pixlepix.minechem.client.gui.tabs;

import pixlepix.minechem.common.ModMinechem;
import pixlepix.minechem.common.tileentity.MinechemTileEntity;
import pixlepix.minechem.common.utils.MinechemHelper;
import pixlepix.minechem.common.utils.RollingAverage;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class TabEnergy extends Tab {
	
	
	
    public static Icon powerIcon;
    // I'm copying the layout for the buildcraft
    // engine tab, because users will be familiar.
    int headerColour = 0xe1c92f;
    int subheaderColour = 0xaaafb8;
    int textColour = 0x000000;
    //Switched from refrence to MinechemPowerProvider for 1.6 move to UE
    MinechemTileEntity energy;
    float lastEnergy = 0;
    RollingAverage energyUsageRolling = new RollingAverage(100);

    public TabEnergy(Gui gui, MinechemTileEntity energy) {
        super(gui);
        this.maxWidth = 120;
        this.maxHeight = 100;
        this.energy=energy;
        this.overlayColor = 0xFF8800;
    }

    @Override
    public void draw(int x, int y) {
        drawBackground(x, y);
        drawIcon(x + 2, y + 2);
        if (!isFullyOpened())
            return;
        //TODO Convert Power Values
        energyUsageRolling.add(energy.getRequest());
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.energy"), x + 22, y + 8, headerColour);
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.usage") + ":", x + 22, y + 20, subheaderColour);
        fontRenderer.drawString(String.format("%.1f", energyUsageRolling.getAverage()) + " MJ/t", x + 22, y + 32, textColour);
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.maxUsage") + ":", x + 22, y + 44, subheaderColour);
        //Arbitrary direction
        //Shouldn't matter for any machine
        fontRenderer.drawString(energy.getRequest() + " RF/t", x + 22, y + 56, textColour);
        fontRenderer.drawStringWithShadow(MinechemHelper.getLocalString("tab.title.stored") + ":", x + 22, y + 68, subheaderColour);
        fontRenderer.drawString(String.format("%d", energy.getEnergyStored()) + " RF", x + 22, y + 80, textColour);
    }

    @Override
    public String getTooltip() {
        if (!isOpen()) {
            return String.format("%.1f", energy.getRequest()) + " MJ/t";
        } else
            return null;
    }

	@Override
	public ResourceLocation getIcon() {
		// TODO Auto-generated method stub
		return ModMinechem.ICON_ENERGY;
	}

}
