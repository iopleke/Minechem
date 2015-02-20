package minechem.apparatus.tier1.electricCrucible;

import minechem.Compendium;
import minechem.apparatus.prefab.block.BasicBlockContainer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 *
 * @author jakimfett
 */
public class ElectricCrucibleBlock extends BasicBlockContainer
{
    public ElectricCrucibleBlock()
    {
        super(Compendium.Naming.electricCrucible, Material.anvil, Block.soundTypeMetal);

        setBlockBounds(0.12F, 0F, 0.12F, 0.88F, 0.93F, 0.88F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new ElectricCrucibleTileEntity();
    }
}
