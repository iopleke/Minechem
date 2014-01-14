package pixlepix.minechem.minechem.client.gui.tabs;

import pixlepix.minechem.minechem.common.ModMinechem;
import pixlepix.minechem.minechem.common.tileentity.TileEntityDecomposer;
import pixlepix.minechem.minechem.common.tileentity.TileEntityDecomposer.State;
import pixlepix.minechem.minechem.common.utils.MinechemHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class TabStateControlDecomposer extends TabStateControl {
    private TileEntityDecomposer decomposer;

    enum TabState {

        jammed(MinechemHelper.getLocalString("tab.tooltip.jammed"), 0xAA0000, ModMinechem.ICON_JAMMED),
        noBottles(MinechemHelper.getLocalString("tab.tooltip.nobottles"), 0xAA0000, ModMinechem.ICON_NO_BOTTLES),
        powered(MinechemHelper.getLocalString("tab.tooltip.powered"), 0x00CC00, null),
        unpowered(MinechemHelper.getLocalString("tab.tooltip.unpowered"), 0xAA0000, ModMinechem.ICON_NO_ENERGY);
        public String tooltip;
        public int color;
        public Icon icon;
        public ResourceLocation resource;

        private TabState(String tooltip, int color, ResourceLocation resource) {
            this.tooltip = tooltip;
            this.color = color;
            this.resource=resource;
        }
    }

    TabState state;

    public TabStateControlDecomposer(Gui gui, TileEntityDecomposer decomposer) {
        super(gui);
        this.decomposer = decomposer;
        this.state = TabState.unpowered;
        this.minWidth = 16 + 9;
        this.minHeight = 16 + 10;
        this.maxHeight = this.minHeight;
        this.maxWidth = this.minWidth;
    }

    @Override
    public void update() {
        super.update();
        State state = decomposer.getState();
        if (state == State.kProcessJammed)
            this.state = TabState.jammed;
        else if (decomposer.getState() == State.kProcessNoBottles)
            this.state = TabState.noBottles;
        else if (decomposer.getEnergyStored() > decomposer.getMinEnergyNeeded() || decomposer.getRequest() > 0)
            this.state = TabState.powered;
        else
            this.state = TabState.unpowered;

        this.overlayColor = this.state.color;
    }

    @Override
    public void draw(int x, int y) {
        drawBackground(x, y);
        if (this.state.resource != null)
            drawIcon(x + 3, y + 5);
        if (!isFullyOpened())
            return;
    }

    @Override
    public String getTooltip() {
        return this.state.tooltip;
    }

	@Override
	public ResourceLocation getIcon() {
		// TODO Auto-generated method stub
		return this.state.resource;
	}

}
