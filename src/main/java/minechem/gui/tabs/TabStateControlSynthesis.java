package minechem.gui.tabs;

import minechem.ModMinechem;
import minechem.tileentity.synthesis.SynthesisRecipe;
import minechem.tileentity.synthesis.TileEntitySynthesis;
import minechem.utils.MinechemHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;

public class TabStateControlSynthesis extends TabStateControl
{
    public static Icon noRecipeIcon, unpoweredIcon;
    TileEntitySynthesis synthesis;

    enum TabState
    {

        unpowered(MinechemHelper.getLocalString("tab.tooltip.unpowered"), 0xAA0000, ModMinechem.ICON_NO_ENERGY), powered(MinechemHelper.getLocalString("tab.tooltip.powered"), 0x00CC00, null), norecipe(MinechemHelper.getLocalString("tab.tooltip.norecipe"),
                0xAA0000, ModMinechem.ICON_NO_RECIPE);
        public String tooltip;
        public int color;
        public Icon icon;
        private ResourceLocation resource;

        private TabState(String tooltip, int color, ResourceLocation resource)
        {
            this.tooltip = tooltip;
            this.color = color;
            this.resource = resource;
        }
    }

    TabState state;

    public TabStateControlSynthesis(Gui gui, TileEntitySynthesis synthesis)
    {
        super(gui);
        this.synthesis = synthesis;
        this.state = TabState.norecipe;
        this.minWidth = 16 + 9;
        this.minHeight = 16 + 10;
        this.maxHeight = this.minHeight;
        this.maxWidth = this.minWidth;
    }

    @Override
    public void update()
    {
        super.update();
        SynthesisRecipe recipe = synthesis.getCurrentRecipe();
        if (recipe == null)
        {
            state = TabState.norecipe;
        }
        else
        {
            int energyCost = recipe.energyCost();
            if (synthesis.getEnergy(ForgeDirection.UNKNOWN) >= energyCost)
                state = TabState.powered;
            else
                state = TabState.unpowered;
        }

        this.overlayColor = this.state.color;
    }

    @Override
    public void draw(int x, int y)
    {
        drawBackground(x, y);
        if (this.state.resource != null)
            drawIcon(x + 3, y + 5);
        if (!isFullyOpened())
            return;
    }

    @Override
    public String getTooltip()
    {
        return this.state.tooltip;
    }

    @Override
    public ResourceLocation getIcon()
    {
        return this.state.resource;
    }

}
