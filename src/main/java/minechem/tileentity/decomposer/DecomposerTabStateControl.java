package minechem.tileentity.decomposer;

import minechem.Settings;
import minechem.gui.GuiTabStateControl;
import minechem.tileentity.decomposer.DecomposerTileEntity.State;
import net.minecraft.client.gui.Gui;

public class DecomposerTabStateControl extends GuiTabStateControl
{
	public DecomposerTabStateControl(Gui gui, DecomposerTileEntity decomposer)
	{
		super(gui);
		this.tileEntity = decomposer;
	}

	@Override
	public void update()
	{
		super.update();
		DecomposerTileEntity decomposer = (DecomposerTileEntity) this.tileEntity;
		State state = decomposer.getState();
		if (!Settings.powerUseEnabled || decomposer.getEnergyStored() > Settings.decompositionCost)
		{
			this.state = TabState.powered;
		} else if (state == State.jammed)
		{
			this.state = TabState.jammed;
		} else
		{
			this.state = TabState.unpowered;
		}

		this.overlayColor = this.state.color;
	}

}
