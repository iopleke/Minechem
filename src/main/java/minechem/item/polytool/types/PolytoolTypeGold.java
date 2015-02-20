package minechem.item.polytool.types;

import cofh.api.energy.IEnergyContainerItem;
import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeGold extends PolytoolUpgradeType
{
    private static boolean canCharge;

    static
    {
        try
        {
            Class.forName("cofh.api.energy.IEnergyContainerItem");
            canCharge = true;
        } catch (ClassNotFoundException e)
        {
        }
    }

    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.Au;
    }

    @Override
    public void onTickFull(ItemStack par1ItemStack, World world, Entity entity, int par4, boolean par5)
    {
        if (!world.isRemote && world.rand.nextInt(35000) < power)
        {
            world.addWeatherEffect(new EntityLightningBolt(world, entity.posX, entity.posY, entity.posZ));
            if (canCharge)
            {
                if (entity instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer)entity;
                    for (int i = 0; i < player.inventory.getSizeInventory(); i++)
                    {
                        ItemStack stack = player.inventory.getStackInSlot(i);
                        if (stack != null && stack.getItem() instanceof IEnergyContainerItem)
                        {
                            ((IEnergyContainerItem)stack.getItem()).receiveEnergy(stack, 5000000, false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getDescription()
    {
        return "Occasionally creates lightning strikes which charge inventory";
    }

}
