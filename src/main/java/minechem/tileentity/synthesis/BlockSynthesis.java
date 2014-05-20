package minechem.tileentity.synthesis;

import java.util.ArrayList;

import minechem.CommonProxy;
import minechem.ModMinechem;
import minechem.block.BlockMinechemContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/** Chemical Synthesizer block. Its associated TileEntitySynthesis's inventory inventory has many specialized slots, including some "ghost" slots whose contents don't really exist and shouldn't be able to be extracted or dumped when the block is broken. See
 * {@link minechem.tileentity.synthesis.TileEntitySynthesis} for details of the inventory slots. */
public class BlockSynthesis extends BlockMinechemContainer
{

    public BlockSynthesis(int par1)
    {
        super(par1, Material.iron);
        setUnlocalizedName("minechem.blockSynthesis");
        setCreativeTab(ModMinechem.CREATIVE_TAB);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase el, ItemStack is)
    {
        super.onBlockPlacedBy(world, x, y, z, el, is);
        int facing = MathHelper.floor_double(el.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, facing, 2);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity == null || entityPlayer.isSneaking())
            return false;
        entityPlayer.openGui(ModMinechem.INSTANCE, 0, world, x, y, z);
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World var1)
    {
        return new TileEntitySynthesis();
    }

    @Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList itemStacks)
    {
        TileEntitySynthesis synthesizer = (TileEntitySynthesis) tileEntity;
        for (int slot : TileEntitySynthesis.kRealSlots)
        {
            if (synthesizer.isRealItemSlot(slot))
            {
                ItemStack itemstack = synthesizer.getStackInSlot(slot);
                if (itemstack != null)
                {
                    itemStacks.add(itemstack);
                }
            }
        }
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return CommonProxy.RENDER_ID;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

}
