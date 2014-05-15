package pixlepix.minechem.common.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.minechem.api.BaseParticle;
import pixlepix.minechem.common.MinechemBlocks;

import java.util.ArrayList;
import java.util.List;

public class PolarizedGlass extends BasicComplexBlock
{

    public PolarizedGlass(int itemID)
    {
        super(itemID);
    }

    @Override
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        if (par7Entity instanceof BaseParticle)
        {
            return;
        }
        AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);

        if (axisalignedbb1 != null && par5AxisAlignedBB.intersectsWith(axisalignedbb1))
        {
            par6List.add(axisalignedbb1);
        }
    }

    @Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks)
    {

    }

    @Override
    public String getFront()
    {
        // TODO Auto-generated method stub
        return "PolarizedGlass";
    }

    @Override
    public boolean hasModel()
    {
        return true;
    }

    @Override
    public String getTop()
    {
        // TODO Auto-generated method stub
        return "PolarizedGlass";
    }

    @Override
    public Class getTileEntityClass()
    {
        return null;
    }

    @Override
    public void addRecipe()
    {
        GameRegistry.addRecipe(new ItemStack(this), " R ", "RGR", " R ", 'R', new ItemStack(Item.redstone), 'G', new ItemStack(Block.glass));

    }

    @Override
    public String getName()
    {
        return "Polarized Glass";
    }

    @Override
    public boolean hasItemBlock()
    {
        return false;
    }

    @Override
    public Class getItemBlock()
    {
        return null;

    }

    @Override
    public boolean topSidedTextures()
    {
        return false;
    }

}
