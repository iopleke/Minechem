package minechem.apparatus.tier1.opticalMicroscope;

import minechem.apparatus.prefab.block.BasicBlockContainer;
import minechem.reference.Compendium;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class OpticalMicroscopeBlock extends BasicBlockContainer
{
    public OpticalMicroscopeBlock()
    {
        super(Compendium.Naming.opticalMicroscope, Material.iron, Block.soundTypeMetal);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new OpticalMicroscopeTileEntity();
    }

}
