package minechem.tileentity.synthesis;

import minechem.Minechem;
import minechem.gui.GuiTabStateControl;
import minechem.utils.MinechemHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class SynthesisTabStateControl extends GuiTabStateControl
{
    public static IIcon noRecipeIcon;
    public static IIcon unpoweredIcon;
    SynthesisTileEntity synthesis;

    enum TabState
    {
        unpowered(MinechemHelper.getLocalString("tab.tooltip.unpowered"), 0xAA0000, Minechem.ICON_NO_ENERGY), powered(MinechemHelper.getLocalString("tab.tooltip.powered"), 0x00CC00, null), norecipe(MinechemHelper.getLocalString("tab.tooltip.norecipe"),
                0xAA0000, Minechem.ICON_NO_RECIPE);
        public String tooltip;
        public int color;
        public IIcon icon;
        private ResourceLocation resource;

        private TabState(String tooltip, int color, ResourceLocation resource)
        {
            this.tooltip = tooltip;
            this.color = color;
            this.resource = resource;
        }
    }

    TabState state;
    private int lastKnownEnergyCost;

    public SynthesisTabStateControl(Gui gui, SynthesisTileEntity synthesis)
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
        } else
        {
            lastKnownEnergyCost = recipe.energyCost();
            // @TODO - if UE is detected, display tab, otherwise have machine always powered
            if (true)
            {
                state = TabState.powered;
            } else
            {
                state = TabState.unpowered;
            }
        }

        this.overlayColor = this.state.color;
    }

    @Override
    public void draw(int x, int y)
    {
        drawBackground(x, y);

        if (this.state.resource != null)
        {
            drawIcon(x + 3, y + 5);
        }

        if (!isFullyOpened())
        {
            return;
        }
    }

    @Override
    public String getTooltip()
    {
        if (state == TabState.unpowered && lastKnownEnergyCost > 0)
        {
            return "Energy Needed: " + lastKnownEnergyCost;
        }

        return this.state.tooltip;
    }

    @Override
    public ResourceLocation getIcon()
    {
        return this.state.resource;
    }

}
