package minechem.tileentity.multiblock.fusion;

import minechem.gui.GuiTabStateControl;
import minechem.item.element.ElementItem;
import net.minecraft.client.gui.Gui;

public class FusionTabStateControl extends GuiTabStateControl
{
	private int lastKnownEnergyCost = 0;

	public FusionTabStateControl(Gui gui, FusionTileEntity fusion)
	{
		super(gui);
		this.tileEntity = (FusionTileEntity) fusion;
		this.state = TabState.norecipe;
	}

	@Override
	public void update()
	{
		super.update();
		if (this.tileEntity instanceof FusionTileEntity)
		{
			if (this.tileEntity.inventory[0] != null && this.tileEntity.inventory[1] != null)
			{
				if (this.tileEntity.inventory[0].getItem() instanceof ElementItem && this.tileEntity.inventory[1].getItem() instanceof ElementItem)
				{
					lastKnownEnergyCost = this.tileEntity.getEnergyNeeded();
					if (lastKnownEnergyCost <= this.tileEntity.getEnergyStored())
					{
						if (((FusionTileEntity) this.tileEntity).canOutput())
						{
							state = TabState.powered;
						} else
						{
							state = TabState.jammed;
						}
					} else
					{
						state = TabState.unpowered;
					}
				} else
				{
					this.state = TabState.norecipe;
				}
			} else
			{
				this.state = TabState.norecipe;
			}
			this.overlayColor = this.state.color;
		}
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
