package minechem.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * To be implemented by containers and armour.
 *
 * @author lukeperkin
 */
public interface IRadiationShield
{

    /**
     * Return a number from 0 to 1 to reduce radiation damage. Multiple IRadationShields will sum their reductionFactors.
     *
     * @param baseDamage the base damage that we want to reduce.
     * @param itemstack if armour implements this it's the armour stack.
     * @param player the player we're harming.
     * @return a float where n >= 0 && n <= 1
     */
    public float getRadiationReductionFactor(int baseDamage, ItemStack itemstack, EntityPlayer player);

}
