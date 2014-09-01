package minechem.tileentity.multiblock.fusion;

import minechem.Minechem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class FusionItemBlock extends ItemBlock
{

    private static final String[] names =
    { "Fusion Wall", "Tungsten Plating", "Fusion Core" };

    public FusionItemBlock(Block block)
    {
		//TODO: Find matching block
	    super(block);
        setHasSubtypes(true);
        setUnlocalizedName("minechem.itemBlockFusion");
    }

    @Override
    public int getMetadata(int damageValue)
    {
        return damageValue;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return super.getUnlocalizedName(itemstack) + names[itemstack.getItemDamage()];
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemstack)
    {
        return names[itemstack.getItemDamage()];
    }

}
