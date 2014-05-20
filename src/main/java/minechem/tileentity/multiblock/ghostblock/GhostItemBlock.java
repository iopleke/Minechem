package minechem.tileentity.multiblock.ghostblock;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class GhostItemBlock extends ItemBlock
{
    private final static String[] subNames =
    { "white", "orange", "magenta", "lightBlue", "yellow", "lightGreen", "pink", "darkGrey", "lightGrey", "cyan", "purple", "blue", "brown", "green", "red", "black" };

    public GhostItemBlock(int id)
    {
        super(id);
        setUnlocalizedName("itemGhostBlock");
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damageValue)
    {
        return damageValue;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        if (itemstack.getItemDamage() < GhostItemBlock.subNames.length)
        {
            return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
        }
        return "white";
    }

}
