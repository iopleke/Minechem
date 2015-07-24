package minechem.radiation;

import minechem.item.MinechemChemicalType;
import minechem.utils.MinechemUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RadiationInfo implements Cloneable
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

    @Override
    public RadiationInfo clone()
    {
        return new RadiationInfo(itemstack.copy(), decayStarted, lastDecayUpdate, dimensionID, radioactivity);
    }

    public long getLeftTime(long now)
    {
        return (radioactivity.getLife() - now) + decayStarted;
    }

    public void setLeftTime(long now, long leftTime)
    {
        decayStarted = (now + leftTime) - radioactivity.getLife();
    }

    public static RadiationInfo initiateRadioactivity(ItemStack chemicalItem, World world)
    {
        RadiationEnum radioactivity = getRadioactivity(chemicalItem);
        int dimensionID = world.provider.dimensionId;
        long lastUpdate = world.getTotalWorldTime();
        RadiationInfo info = new RadiationInfo(chemicalItem, lastUpdate, lastUpdate, dimensionID, radioactivity);
        setRadiationInfo(info, chemicalItem);
        return info;
    }

    public static RadiationInfo getRadiationInfo(ItemStack chemicalItem, World world)
    {
        RadiationEnum radioactivity = getRadioactivity(chemicalItem);
        if (radioactivity == RadiationEnum.stable)
        {
            return new RadiationInfo(chemicalItem, radioactivity);
        }
        NBTTagCompound stackTag = chemicalItem.getTagCompound();
        if (stackTag == null)
        {
            return RadiationInfo.initiateRadioactivity(chemicalItem, world);
        }
        int dimensionID = stackTag.getInteger("dimensionID");
        long lastUpdate = stackTag.getLong("lastUpdate");
        long decayStart = stackTag.getLong("decayStart");
        RadiationInfo info = new RadiationInfo(chemicalItem, decayStart, lastUpdate, dimensionID, radioactivity);
        return info;
    }

    public static RadiationEnum getRadioactivity(ItemStack itemstack)
    {
        MinechemChemicalType chemical = MinechemUtil.getChemical(itemstack);
        if (chemical == null)
        {
            return RadiationEnum.stable;
        }
        return chemical.radioactivity();
    }

    public static void setRadiationInfo(RadiationInfo radiationInfo, ItemStack itemStack)
    {
        if (getRadioactivity(itemStack) == RadiationEnum.stable)
        {
            return;
        }

        NBTTagCompound stackTag = itemStack.getTagCompound();
        if (stackTag == null)
        {
            stackTag = new NBTTagCompound();
        }
        stackTag.setLong("lastUpdate", radiationInfo.lastDecayUpdate);
        stackTag.setLong("decayStart", radiationInfo.decayStarted);
        stackTag.setInteger("dimensionID", radiationInfo.dimensionID);
        itemStack.setTagCompound(stackTag);
    }
}
