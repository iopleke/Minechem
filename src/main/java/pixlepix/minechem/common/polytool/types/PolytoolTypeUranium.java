package pixlepix.minechem.common.polytool.types;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.minechem.api.core.EnumElement;
import pixlepix.minechem.common.polytool.PolytoolUpgradeType;

import java.util.Iterator;
import java.util.List;

public class PolytoolTypeUranium extends PolytoolUpgradeType {

    public PolytoolTypeUranium() {
        super();
    }

    @Override
    public float getStrVsBlock(ItemStack itemStack, Block block) {

        return 0;
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target,
                          EntityLivingBase player) {
        List targets = target.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(target.posX - power, target.posY - power, target.posZ - power, target.posX + power, target.posY + power, target.posZ + power));
        Iterator iter = targets.iterator();
        while (iter.hasNext()) {
            EntityLivingBase entity = (EntityLivingBase) iter.next();
            entity.addPotionEffect(new PotionEffect(20, 200, 1));

        }
    }

    @Override
    public void onBlockDestroyed(ItemStack itemStack, World world, int id,
                                 int x, int y, int z, EntityLivingBase entityLiving) {
    }

    @Override
    public EnumElement getElement() {

        return EnumElement.U;
    }

    @Override
    public void onTick() {
    }

    @Override
    public String getDescription() {

        return "Area of Effect wither";
    }

}
