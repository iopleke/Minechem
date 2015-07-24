package minechem.item;

import minechem.gui.CreativeTabMinechem;
import minechem.radiation.RadiationEnum;
import minechem.radiation.RadiationInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ChemicalTubeItem extends Item
{

    public ChemicalTubeItem()
    {
        setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_ELEMENTS);
        setHasSubtypes(true);
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player)
    {
        super.onCreated(itemStack, world, player);
        if ((RadiationInfo.getRadioactivity(itemStack) != RadiationEnum.stable) && (itemStack.stackTagCompound == null))
        {
            RadiationInfo.setRadiationInfo(new RadiationInfo(itemStack, world.getTotalWorldTime(), world.getTotalWorldTime(), world.provider.dimensionId, RadiationInfo.getRadioactivity(itemStack)), itemStack);
        }
    }

}
