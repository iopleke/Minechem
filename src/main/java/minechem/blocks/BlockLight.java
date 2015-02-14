package minechem.blocks;

import minechem.Compendium;
import minechem.apparatus.prefab.block.SpecialRenderBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockLight extends SpecialRenderBlock
{
    public BlockLight()
    {
        super(Material.circuits);
        setBlockName(Compendium.Naming.light);
        this.setBlockBounds(0.35F, 0.35F, 0.35F, 0.65F, 0.65F, 0.65F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int face, float hitX, float hitY, float hitZ)
    {
        return super.onBlockActivated(world, x, y, z, player, face, hitX, hitY, hitZ);
        //TODO: player hits with an augmented Item to boost light
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        return Math.min(world.getBlockMetadata(x, y, z) + 10, 15);
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
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
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return AxisAlignedBB.getBoundingBox(-0.2D, -0.2D, -0.2D, 0.2D, 0.2D, 0.2D);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
    {
        return null;
    }

    @Override
    public boolean isAir(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public float getExplosionResistance(Entity p_149638_1_)
    {
        return super.getExplosionResistance(p_149638_1_);
    }
}
