package ljdp.minechem.client.gui.tabs;

import ljdp.minechem.common.gates.MinechemTriggers;
import ljdp.minechem.common.tileentity.TileEntityDecomposer;
import ljdp.minechem.common.tileentity.TileEntityDecomposer.State;
import ljdp.minechem.common.utils.MinechemHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Icon;

public class TabStateControlDecomposer extends TabStateControl {
    private TileEntityDecomposer decomposer;

    enum TabState {
        jammed(MinechemHelper.getLocalString("tab.tooltip.jammed"), 0xAA0000, MinechemTriggers.outputJammed.icon),
        noBottles(MinechemHelper.getLocalString("tab.tooltip.nobottles"), 0xAA0000, MinechemTriggers.noTestTubes.icon),
        powered(MinechemHelper.getLocalString("tab.tooltip.powered"), 0x00CC00, null),
        unpowered(MinechemHelper.getLocalString("tab.tooltip.unpowered"), 0xAA0000, unpoweredIcon);
        public String tooltip;
        public int color;
        public Icon icon;

        private TabState(String tooltip, int color, Icon iconIndex) {
            this.tooltip = tooltip;
            this.color = color;
            this.icon = iconIndex;
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
        else if (decomposer.getEnergyStored() > decomposer.getMinEnergyNeeded() || decomposer.getEnergyUsage() > 0)
            this.state = TabState.powered;
        else
            this.state = TabState.unpowered;

        this.overlayColor = this.state.color;
    }

    @Override
    public void draw(int x, int y) {
        drawBackground(x, y);
        if (this.state.icon != null)
            drawIcon(this.state.icon, x + 3, y + 5);
        if (!isFullyOpened())
            return;
    }

    @Override
    public String getTooltip() {
        return this.state.tooltip;
    }

}
