package ljdp.minechem.client.gui.tabs;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.Icon;
import ljdp.minechem.api.recipe.SynthesisRecipe;
import ljdp.minechem.common.MinechemPowerProvider;
import ljdp.minechem.common.tileentity.TileEntitySynthesis;
import ljdp.minechem.common.utils.MinechemHelper;

public class TabStateControlSynthesis extends TabStateControl {
    public static Icon noRecipeIcon, unpoweredIcon;
    TileEntitySynthesis synthesis;

    enum TabState {
        norecipe(MinechemHelper.getLocalString("tab.tooltip.norecipe"), 0xAA0000, noRecipeIcon),
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

    public TabStateControlSynthesis(Gui gui, TileEntitySynthesis synthesis) {
        super(gui);
        this.synthesis = synthesis;
        this.state = TabState.norecipe;
        this.minWidth = 16 + 9;
        this.minHeight = 16 + 10;
        this.maxHeight = this.minHeight;
        this.maxWidth = this.minWidth;
    }

    @Override
    public void update() {
        super.update();
        SynthesisRecipe recipe = synthesis.getCurrentRecipe();
        MinechemPowerProvider provider = (MinechemPowerProvider) synthesis.getPowerProvider();
        if (recipe == null) {
            state = TabState.norecipe;
        } else {
            int energyCost = recipe.energyCost();
            if (provider.getEnergyStored() >= energyCost)
                state = TabState.powered;
            else
                state = TabState.unpowered;
        }

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
