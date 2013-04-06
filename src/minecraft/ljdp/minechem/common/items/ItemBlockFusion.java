package ljdp.minechem.common.items;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockFusion extends ItemBlock {

    private static final String[] names = { "Fusion Wall", "Tungsten Plating", "Fusion Core" };

    public ItemBlockFusion(int par1) {
        super(par1);
        setHasSubtypes(true);
        setUnlocalizedName("minechem.itemBlockFusion");
    }

    @Override
    public int getMetadata(int damageValue) {
        return damageValue;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return super.getUnlocalizedName(itemstack) + names[itemstack.getItemDamage()];
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack) {
        return names[itemstack.getItemDamage()];
    }

}
