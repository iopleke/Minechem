package minechem.tileentity.multiblock.fusion;

import minechem.Settings;
import minechem.gui.GuiTabStateControl;
import net.minecraft.client.gui.Gui;

public class FusionTabStateControl extends GuiTabStateControl
{
	 private int lastKnownEnergyCost = 0;

	 public FusionTabStateControl(Gui gui, FusionTileEntity fusion)
	 {
		 super(gui);
		 this.tileEntity = fusion;
		 this.state = TabState.norecipe;
	 }

	 @Override
	 public void update()
	 {
	 super.update();
		 if (this.tileEntity.inventory[0] == null || this.tileEntity.inventory[1] == null)
		 {
			 state = TabState.norecipe;
		 }
		 else
		 {
			 lastKnownEnergyCost = (this.tileEntity.inventory[1].getItemDamage() + this.tileEntity.inventory[0].getItemDamage() + 2) * Settings.fusionMultiplier;
			 if (this.tileEntity.getEnergyNeeded() < this.tileEntity.getEnergyStored())
			 {
				 state = TabState.powered;
			 }
			 else
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
