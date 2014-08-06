package minechem.block.uranium;

import minechem.ModMinechem;
import minechem.block.BlockSimple;
import minechem.utils.Reference;
import net.minecraft.block.material.Material;

public class BlockUraniumOre extends BlockSimple
{
    public BlockUraniumOre()
    {
        super(Material.iron, Reference.URANIUM_TEX, 1, "Uranium Ore");
        this.setCreativeTab(ModMinechem.CREATIVE_TAB);
        //this.setBlockName("Uranium Ore");
        this.setHardness(4F);
    }
    
    @Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks){
        // set uranium drops here
    };
}
