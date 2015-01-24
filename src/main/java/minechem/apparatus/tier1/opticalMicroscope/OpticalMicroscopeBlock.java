package minechem.apparatus.tier1.opticalMicroscope;

import java.util.ArrayList;
import minechem.apparatus.prefab.block.BasicBlockContainer;
import minechem.reference.Compendium;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
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

    @Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks)
    {
        if (tileEntity instanceof OpticalMicroscopeTileEntity)
        {
            OpticalMicroscopeTileEntity opticalMicroscope = (OpticalMicroscopeTileEntity) tileEntity;

        }
    }
}
