package minechem.tileentity.multiblock.fusion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import minechem.Minechem;
import minechem.block.BlockSimpleContainer;
import minechem.gui.CreativeTabMinechem;
import minechem.reference.Textures;
import minechem.tileentity.multiblock.MultiBlockTileEntity;
import minechem.tileentity.multiblock.fission.FissionTileEntity;
import minechem.tileentity.prefab.TileEntityProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class FusionBlock extends BlockSimpleContainer
{
    private IIcon icon1, icon2;

    public FusionBlock()
    {
        super(Material.iron);
        setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_ITEMS);
        setBlockName("fusionWall");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float par7, float par8, float par9)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity == null)
        {
            return false;
        }
        if (!world.isRemote)
        {
            entityPlayer.openGui(Minechem.INSTANCE, 0, world, x, y, z);
        }
        return true;
    }

    @Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks)
    {
        // Should not drop blocks if this is a proxy
        if (tileEntity instanceof MultiBlockTileEntity && tileEntity instanceof IInventory)
        {
            IInventory inv = (IInventory) tileEntity;
            for (int i = 0; i < inv.getSizeInventory(); i++)
            {
                if (inv.getStackInSlot(i) != null)
                {
                    itemStacks.add(inv.getStackInSlot(i));
                }
            }
        }
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        if (metadata == 2)
        {
            return new FusionTileEntity();
        }
        if (metadata == 3)
        {
            return new FissionTileEntity();
        } else
        {
            return new TileEntityProxy();
        }
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
    public IIcon getIcon(int par1, int metadata)
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
    public void registerBlockIcons(IIconRegister ir)
    {
        blockIcon = ir.registerIcon(Textures.IIcon.DEFAULT);
        icon1 = ir.registerIcon(Textures.IIcon.FUSION1);
        icon2 = ir.registerIcon(Textures.IIcon.FUSION2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < 2; i++)
        {
            par3List.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metaData)
    {
        super.breakBlock(world, x, y, z, block, metaData);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i)
    {
        return new TileEntityProxy();
    }

}
