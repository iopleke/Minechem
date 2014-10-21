package minechem.tileentity.multiblock.fission;

import minechem.Settings;
import minechem.gui.GuiTabStateControl;
import net.minecraft.client.gui.Gui;

public class FissionTabStateControl extends GuiTabStateControl
{
	private int lastKnownEnergyCost = 0;

	public FissionTabStateControl(Gui gui, FissionTileEntity fission)
	{
		super(gui);
		this.tileEntity = fission;
		this.state = TabState.norecipe;
	}

	@Override
	public void update()
	{
		super.update();
		if (this.tileEntity.inventory[0] == null)
		{
			state = TabState.norecipe;
		} else
		{
			lastKnownEnergyCost = (this.tileEntity.inventory[0].getItemDamage() + 1) * Settings.fissionMultiplier;
            if (((FissionTileEntity)this.tileEntity).inputIsFissionable())
            {
                if (this.tileEntity.getEnergyNeeded() < this.tileEntity.getEnergyStored())
                {
                    state = TabState.powered;
                } else
                {
                    state = TabState.unpowered;
                }
            }
            else
            {
                state = TabState.norecipe;
            }
		}
		this.overlayColor = this.state.color;
	}

	@Override
	public String getTooltip()
	{
		if (!isFullyOpened())
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
