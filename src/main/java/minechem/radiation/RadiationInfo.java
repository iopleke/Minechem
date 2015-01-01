package minechem.radiation;

import minechem.MinechemItemsRegistration;
import minechem.item.bucket.MinechemBucketItem;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.MoleculeEnum;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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

    public static RadiationEnum getRadioactivity(ItemStack itemstack)
    {
        int id = itemstack.getItemDamage();
        Item item = itemstack.getItem();
        if (item == MinechemItemsRegistration.element)
        {
            return id != 0 ? ElementEnum.getByID(id).radioactivity() : RadiationEnum.stable;
        } else if (item == MinechemItemsRegistration.molecule)
        {
            if (id >= MoleculeEnum.molecules.size() || MoleculeEnum.molecules.get(id) == null)
            {
                return RadiationEnum.stable;
            }
            return MoleculeEnum.molecules.get(id).radioactivity();
        } else if (item instanceof MinechemBucketItem)
        {
            return ((MinechemBucketItem) item).chemical.radioactivity();
        }
        return RadiationEnum.stable;
    }

    public static void setRadiationInfo(RadiationInfo radiationInfo, ItemStack itemStack)
    {
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
