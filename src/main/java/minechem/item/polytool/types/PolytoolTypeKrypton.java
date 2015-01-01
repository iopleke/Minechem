package minechem.item.polytool.types;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class PolytoolTypeKrypton extends PolytoolUpgradeType
{

    public PolytoolTypeKrypton()
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
        if (target instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) target;
            if (entityPlayer.getTotalArmorValue() > 23)
            {
                if (entityPlayer.attackEntityFrom(DamageSource.outOfWorld, power))
                    ;
            }
        }
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving)
    {
    }

    @Override
    public ElementEnum getElement()
    {

        return ElementEnum.Kr;
    }

    @Override
    public void onTick()
    {
    }

    @Override
    public String getDescription()
    {

        return "Does extra damage to heavily armored players";
    }

}
