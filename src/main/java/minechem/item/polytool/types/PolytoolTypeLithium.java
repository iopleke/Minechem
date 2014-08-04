package minechem.item.polytool.types;

import minechem.MinechemItemsGeneration;
import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolItem;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class PolytoolTypeLithium extends PolytoolUpgradeType
{

    public PolytoolTypeLithium()
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
        DamageSource damage = DamageSource.causeMobDamage(target);
        // Never goes below 0
        double damageAmount = Math.ceil(Math.max(0, Math.log10(((PolytoolItem) MinechemItemsGeneration.polytool).getEnergy(itemStack)) - 7));
        System.out.println(damageAmount);
        target.attackEntityFrom(damage, (float) damageAmount);
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving)
    {
    }

    @Override
    public ElementEnum getElement()
    {

        return ElementEnum.Li;
    }

    @Override
    public void onTick()
    {
    }

    @Override
    public String getDescription()
    {

        return "Can charge infinitley for logarithmic bonuses";
    }

}
