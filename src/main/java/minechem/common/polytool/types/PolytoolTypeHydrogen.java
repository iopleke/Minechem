package minechem.common.polytool.types;

import minechem.api.core.EnumElement;
import minechem.common.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeHydrogen extends PolytoolUpgradeType {

    public PolytoolTypeHydrogen() {
        super();
    }

    @Override
    public float getStrVsBlock(ItemStack itemStack, Block block) {

        return 0;
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target,
                          EntityLivingBase player) {
        if (!target.worldObj.isRemote && target instanceof EntityLiving && target.isBurning()) {
            target.worldObj.createExplosion(target, target.posX, target.posY, target.posZ, power, true);
        }
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, int id,
                                 int x, int y, int z, EntityLivingBase entityLiving) {
    }

    @Override
    public EnumElement getElement() {

        return EnumElement.H;
    }

    @Override
    public void onTick() {
    }

    @Override
    public String getDescription() {

        return "Creates explosion if hit entity is on fire";
    }

}
