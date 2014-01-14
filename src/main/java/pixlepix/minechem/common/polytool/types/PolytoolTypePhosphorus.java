package pixlepix.minechem.common.polytool.types;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import pixlepix.minechem.api.core.EnumElement;
import pixlepix.minechem.common.polytool.PolytoolUpgradeType;

public class PolytoolTypePhosphorus extends PolytoolUpgradeType {

    public PolytoolTypePhosphorus() {
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
        if (target.worldObj.isRemote) {
            if (Block.blocksList[id].isFlammable(world, x, y, z, 0, ForgeDirection.UP)) {
                for (int i = (int) (x - power); i < x + power; i++) {
                    for (int j = (int) (y - power); j < y + power; j++) {
                        for (int k = (int) (z - power); k < z + power; k++) {
                            if (world.getBlockId(i, j, k) == 0) {
                                world.setBlock(i, j, k, Block.fire.blockID);
                            }
                        }
                    }
                }
            }

        }
    }

    @Override
    public EnumElement getElement() {

        return EnumElement.P;
    }

    @Override
    public void onTick() {
    }

    @Override
    public String getDescription() {

        return "Sets nearby blocks on fire";
    }

}
