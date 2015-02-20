package minechem.item.polytool.types;

import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.Loader;
import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeGold extends PolytoolUpgradeType
{
    @Override
    public ElementEnum getElement()
    {
        return ElementEnum.Au;
    }

    @Override
    public void onTickFull(ItemStack par1ItemStack, World world, Entity entity, int par4, boolean par5)
    {
        if (world.rand.nextInt(35000) < power)
        {
            world.addWeatherEffect(new EntityLightningBolt(world, entity.posX, entity.posY, entity.posZ));
            if (entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer)entity;
                for (int i = 0; i < player.inventory.getSizeInventory(); i++)
                {
                    if (Loader.isModLoaded("CoFHAPI|energy"))
                    {
                        ItemStack stack = player.inventory.getStackInSlot(i);
                        if (stack != null && stack.getItem() instanceof IEnergyContainerItem)
                        {
                            ((IEnergyContainerItem)stack.getItem()).receiveEnergy(stack, 5000000, true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getDescription()
    {
        return "Ocasionally creates lightning strikes which charge inventory";
    }

}
