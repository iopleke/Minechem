package minechem.block.uranium;

import java.util.ArrayList;
import minechem.Minechem;
import minechem.block.BlockSimple;
import minechem.utils.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

public class BlockUraniumOre extends BlockSimple
{
    public BlockUraniumOre()
    {
        super(Material.iron, Reference.URANIUM_TEX, 1, "Uranium Ore");
        this.setCreativeTab(Minechem.CREATIVE_TAB);
        this.setBlockName("oreUranium");
        this.setHardness(4F);
    }
    
    @Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks){
        // set uranium drops here
    };
}
