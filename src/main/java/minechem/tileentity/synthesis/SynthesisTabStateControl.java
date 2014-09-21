package minechem.tileentity.synthesis;

import minechem.gui.GuiTabStateControl;
import net.minecraft.client.gui.Gui;

public class SynthesisTabStateControl extends GuiTabStateControl
{
    private int lastKnownEnergyCost;

    public SynthesisTabStateControl(Gui gui, SynthesisTileEntity synthesis)
    {
        super(gui);
        this.tileEntity = synthesis;
        this.state = TabState.norecipe;
    }

    @Override
    public void update()
    {
        super.update();
        SynthesisTileEntity synthesis = (SynthesisTileEntity) this.tileEntity;
        SynthesisRecipe recipe = synthesis.getCurrentRecipe();
        if (recipe == null)
        {
            state = TabState.norecipe;
        } else
        {
            lastKnownEnergyCost = recipe.energyCost();
            if (synthesis.hasEnoughPowerForCurrentRecipe())
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
    public String getTooltip()
    {
        if(!isFullyOpened())
        {
            if (state == TabState.unpowered && lastKnownEnergyCost > 0)
            {
                return "Energy Needed: " + lastKnownEnergyCost;
            }
            return this.state.tooltip;
        }
        return null;
    }

}
