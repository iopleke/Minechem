package ljdp.minechem.common;

import ljdp.minechem.api.core.EnumRadioactivity;
import net.minecraft.item.ItemStack;

public class RadiationInfo {

    public long radiationLife;
    public long lastRadiationUpdate;
    public int radiationDamage;
    public int dimensionID;
    public ItemStack itemstack;
    public EnumRadioactivity radioactivity;

    public RadiationInfo(ItemStack itemstack, long radiationLife, long lastRadiationUpdate, int dimensionID, EnumRadioactivity radioactivity) {
        this.itemstack = itemstack;
        this.radiationLife = radiationLife;
        this.dimensionID = dimensionID;
        this.lastRadiationUpdate = lastRadiationUpdate;
        this.radioactivity = radioactivity;
    }

    public RadiationInfo(ItemStack itemstack, EnumRadioactivity radioactivity) {
        this.itemstack = itemstack;
        this.radioactivity = radioactivity;
        this.radiationLife = 0;
        this.dimensionID = 0;
        this.lastRadiationUpdate = 0;
    }

    public boolean isRadioactive() {
        return this.radioactivity != EnumRadioactivity.stable;
    }
}
