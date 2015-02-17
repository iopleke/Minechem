package minechem.blocks;

import minechem.Compendium;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRedstone extends Block
{
    public static final Material myAir = new MaterialTransparent(MapColor.airColor);

    public BlockRedstone()
    {
        super(myAir);
        this.setBlockName(Compendium.Naming.redstone);
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public boolean canCollideCheck(int meta, boolean boat)
    {
        return false;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float dropChance, int fortune) {}

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        world.scheduleBlockUpdate(x, y, z, this, 1);
        world.notifyBlocksOfNeighborChange(x, y, z, this);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (world.getBlock(x,y,z) == this)
        {
            int meta = world.getBlockMetadata(x,y,z);
            if (meta == 0)
            {
                world.setBlockToAir(x, y, z);
                world.notifyBlocksOfNeighborChange(x, y, z, this);
            }
            else
            {
                world.setBlockMetadataWithNotify(x, y, z, meta - 1, 4);
            }
            world.scheduleBlockUpdate(x, y, z, this, 1);
        }
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side)
    {
        return 15;
    }

    @Override
    public boolean canHarvestBlock(EntityPlayer player, int meta)
    {
        return false;
    }
}
