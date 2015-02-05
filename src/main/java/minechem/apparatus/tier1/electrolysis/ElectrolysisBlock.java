package minechem.apparatus.tier1.electrolysis;

import minechem.apparatus.prefab.block.BasicBlockContainer;
import minechem.reference.Compendium;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ElectrolysisBlock extends BasicBlockContainer
{
    public ElectrolysisBlock()
    {
        super(Compendium.Naming.electrolysis, Material.glass, Block.soundTypeGlass);

        setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.85F, 0.8F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new ElectrolysisTileEntity();
    }
}
