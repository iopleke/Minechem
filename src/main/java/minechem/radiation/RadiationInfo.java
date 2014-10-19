package minechem.radiation;

import net.minecraft.item.ItemStack;

public class RadiationInfo
{
	public long decayStarted;
	public long lastDecayUpdate;
	public int radiationDamage;
	public int dimensionID;
	public ItemStack itemstack;
	public RadiationEnum radioactivity;

	public RadiationInfo(ItemStack itemstack, long decayStarted, long lastDecayUpdate, int dimensionID, RadiationEnum radioactivity)
	{
		this.itemstack = itemstack;
		this.decayStarted = decayStarted;
		this.dimensionID = dimensionID;
		this.lastDecayUpdate = lastDecayUpdate;
		this.radioactivity = radioactivity;
	}

	public RadiationInfo(ItemStack itemstack, RadiationEnum radioactivity)
	{
		this.itemstack = itemstack;
		this.radioactivity = radioactivity;
		this.decayStarted = 0;
		this.dimensionID = 0;
		this.lastDecayUpdate = 0;
	}

	public boolean isRadioactive()
	{
		return this.radioactivity != RadiationEnum.stable;
	}
}
