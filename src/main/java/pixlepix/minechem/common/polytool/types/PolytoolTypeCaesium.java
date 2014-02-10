package pixlepix.minechem.common.polytool.types;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import pixlepix.minechem.api.core.EnumElement;
import pixlepix.minechem.common.polytool.PolytoolUpgradeType;

public class PolytoolTypeCaesium extends PolytoolUpgradeType {

    public PolytoolTypeCaesium() {
        super();
    }

    @Override
    public float getStrVsBlock(ItemStack itemStack, Block block) {

        return 0;
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target,
                          EntityLivingBase player) {
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, int id,
                                 int x, int y, int z, EntityLivingBase target) {
        if (id == Block.stone.blockID) {
            target.worldObj.createExplosion(target, target.posX, target.posY, target.posZ, power, true);
        }
    }

    @Override
    public EnumElement getElement() {

        return EnumElement.Cs;
    }

    @Override
    public void onTick() {
    }

    @Override
    public String getDescription() {

        return "Creates explosion when mining stone";
    }

}
