package minechem.tileentity.chemicalstorage;

import minechem.ModMinechem;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockChemicalStorage extends BlockChest
{

    public BlockChemicalStorage(int id)
    {
        super(id, 0);
        setUnlocalizedName("minechem.blockChemicalStorage");
        setCreativeTab(ModMinechem.CREATIVE_TAB);
        setHardness(2.5F);
        setStepSound(soundWoodFootstep);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float par7, float par8, float par9)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (world.isRemote)
            return true;
        if (tileEntity instanceof TileEntityChemicalStorage)
        {
            entityPlayer.openGui(ModMinechem.INSTANCE, 0, world, x, y, z);
            return true;
        }
        return false;
    }

    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        par1World.setBlockTileEntity(par2, par3, par4, this.createTileEntity(par1World, par1World.getBlockMetadata(par2, par3, par4)));
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase el, ItemStack is)
    {
        byte facing = 0;
        int facingI = MathHelper.floor_double(el.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        if (facingI == 0)
        {
            facing = 2;
        }

        if (facingI == 1)
        {
            facing = 5;
        }

        if (facingI == 2)
        {
            facing = 3;
        }

        if (facingI == 3)
        {
            facing = 4;
        }
        world.setBlockMetadataWithNotify(x, y, z, facing, 2);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileEntityChemicalStorage();
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityChemicalStorage();
    }

}
