package minechem.tileentity.chemicalstorage;

import minechem.ModMinechem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ChemicalStorageBlock extends BlockChest
{

    public ChemicalStorageBlock()
    {
        super(0);
        setBlockName("minechem.blockChemicalStorage");
        setCreativeTab(ModMinechem.CREATIVE_TAB);
        setHardness(2.5F);
        setStepSound(Block.soundTypeWood);
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World par1World, int i)
    {
        return new ChemicalStorageTileEntity();
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new ChemicalStorageTileEntity();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float par7, float par8, float par9)
    {
        if (!world.isRemote)
        {
            entityPlayer.addChatMessage(new ChatComponentText("Please pick up all Chemical Storage Chests and convert them to Leaded Chests using a crafting grid"));
        }
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (world.isRemote)
        {
            return true;
        }
        if (tileEntity instanceof ChemicalStorageTileEntity)
        {
            entityPlayer.openGui(ModMinechem.INSTANCE, 0, world, x, y, z);
            return true;
        }
        return false;
    }

    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        par1World.setTileEntity(par2, par3, par4, this.createTileEntity(par1World, par1World.getBlockMetadata(par2, par3, par4)));
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase el, ItemStack is)
    {
        if (!world.isRemote && el instanceof EntityPlayer)
        {
            EntityPlayer entityPlayer = (EntityPlayer) el;
            entityPlayer.addChatMessage(new ChatComponentText("The Chemical Storage Chest was replaced by the Leaded Chest."));
            entityPlayer.addChatMessage(new ChatComponentText("Please pick up all Chemical Storage Chests and convert them to Leaded Chests using a crafting grid"));
        }
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

}
