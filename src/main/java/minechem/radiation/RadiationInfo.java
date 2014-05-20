package minechem.radiation;

import net.minecraft.item.ItemStack;

public class RadiationInfo
{

    public long radiationLife;
    public long lastRadiationUpdate;
    public int radiationDamage;
    public int dimensionID;
    public ItemStack itemstack;
    public RadiationEnum radioactivity;

    public RadiationInfo(ItemStack itemstack, long radiationLife, long lastRadiationUpdate, int dimensionID, RadiationEnum radioactivity)
    {
        this.itemstack = itemstack;
        this.radiationLife = radiationLife;
        this.dimensionID = dimensionID;
        this.lastRadiationUpdate = lastRadiationUpdate;
        this.radioactivity = radioactivity;
    }

    public RadiationInfo(ItemStack itemstack, RadiationEnum radioactivity)
    {
        this.itemstack = itemstack;
        this.radioactivity = radioactivity;
        this.radiationLife = 0;
        this.dimensionID = 0;
        this.lastRadiationUpdate = 0;
    }

    public boolean isRadioactive()
    {
        return this.radioactivity != RadiationEnum.stable;
    }
}
