package minechem.apparatus.tier1.opticalMicroscope;

import minechem.apparatus.prefab.block.BasicBlockContainer;
import minechem.Compendium;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class OpticalMicroscopeBlock extends BasicBlockContainer
{
    public OpticalMicroscopeBlock()
    {
        super(Compendium.Naming.opticalMicroscope, Material.iron, Block.soundTypeMetal);
        setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 1.0F, 0.8F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new OpticalMicroscopeTileEntity();
    }

}
