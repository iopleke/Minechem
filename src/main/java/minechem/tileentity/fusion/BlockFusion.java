package minechem.tileentity.fusion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import minechem.ModMinechem;
import minechem.block.BlockMinechemContainer;
import minechem.tileentity.fission.TileEntityFission;
import minechem.tileentity.ghostblock.TileEntityMultiBlock;
import minechem.tileentity.prefab.TileEntityProxy;
import minechem.utils.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFusion extends BlockMinechemContainer
{
    private Icon icon1, icon2;

    public BlockFusion(int id)
    {
        super(id, Material.iron);
        setCreativeTab(ModMinechem.CREATIVE_TAB);
        setUnlocalizedName("minechem.blockFusion");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float par7, float par8, float par9)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        // if(tileEntity instanceof TileEntityProxy){
        // TileEntityProxy proxy=(TileEntityProxy) tileEntity;
        // if(proxy.manager!=null){
        // this.onBlockActivated(world, proxy.manager.xCoord,proxy.manager.yCoord,proxy.manager.zCoord, entityPlayer, side, par7, par8, par9);
        // }
        // return true;
        // }
        if (tileEntity == null || entityPlayer.isSneaking())
            return false;
        if (!world.isRemote)
        {
            entityPlayer.openGui(ModMinechem.INSTANCE, 0, world, x, y, z);
        }
        return true;
    }

    @Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks)
    {
        // Should not drop blocks if this is a proxy
        if (tileEntity instanceof TileEntityMultiBlock && tileEntity instanceof IInventory)
        {
            IInventory inv = (IInventory) tileEntity;
            for (int i = 0; i < inv.getSizeInventory(); i++)
            {
                itemStacks.add(inv.getStackInSlot(i));
            }
        }
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        if (metadata == 2)
            return new TileEntityFusion();
        if (metadata == 3)
            return new TileEntityFission();
        else
            return new TileEntityProxy();
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
    }

    // Do not drop if this is a reactor core
    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        return meta < 2 ? 1 : 0;
    }

    @Override
    public Icon getIcon(int par1, int metadata)
    {
        switch (metadata)
        {
        case 0:
            return icon1;
        case 1:
            return icon2;
        }
        return blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        blockIcon = ir.registerIcon(Reference.DEFAULT_TEX);
        icon1 = ir.registerIcon(Reference.FUSION1_TEX);
        icon2 = ir.registerIcon(Reference.FUSION2_TEX);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < 2; i++)
            par3List.add(new ItemStack(this.blockID, 1, i));
    }

    //
    @Override
    public void breakBlock(World world, int x, int y, int z, int oldBlockId, int oldMetadata)
    {
        super.breakBlock(world, x, y, z, oldBlockId, oldMetadata);

        /* if (oldMetadata < 2) { if (world.getBlockTileEntity(x, y, z) instanceof TileEntityProxy) { TileEntityProxy tileEntity = (TileEntityProxy) world.getBlockTileEntity(x, y, z);
         * 
         * world.destroyBlock(tileEntity.getManager().xCoord, tileEntity.getManager().yCoord, tileEntity.getManager().zCoord, true); } } */

    }

    @Override
    public boolean hasTileEntity()
    {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityProxy();
    }

}
