package minechem.item.polytool.types;

import minechem.api.core.EnumElement;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.energy.IEnergyContainer;
import universalelectricity.api.energy.IEnergyInterface;

public class PolytoolTypeGold extends PolytoolUpgradeType
{

    public PolytoolTypeGold()
    {
        super();
    }

    @Override
    public float getStrVsBlock(ItemStack itemStack, Block block)
    {

        return 0;
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, int id, int x, int y, int z, EntityLivingBase entityLiving)
    {
    }

    @Override
    public EnumElement getElement()
    {

        return EnumElement.Au;
    }

    @Override
    public void onTickFull(ItemStack par1ItemStack, World world, Entity entity, int par4, boolean par5)
    {
        if (world.rand.nextInt(35000) < power)
        {
            world.addWeatherEffect(new EntityLightningBolt(world, entity.posX, entity.posY, entity.posZ));
            if (entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entity;
                for (int i = 0; i < player.inventory.getSizeInventory(); i++)
                {
                    if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof IEnergyContainer)
                    {
                        ((IEnergyInterface) player.inventory.getStackInSlot(i).getItem()).onReceiveEnergy(ForgeDirection.UNKNOWN, 5000000, false);
                    }
                }
            }
        }

    }

    @Override
    public void onTick()
    {
    }

    @Override
    public String getDescription()
    {

        return "Ocasionally creates lightning strikes which chage inventory";
    }

}
