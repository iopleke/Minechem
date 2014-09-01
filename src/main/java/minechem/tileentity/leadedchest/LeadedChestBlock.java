package minechem.tileentity.leadedchest;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import minechem.Minechem;
import minechem.utils.Reference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LeadedChestBlock extends BlockContainer
{
    public LeadedChestBlock()
    {
        super(Material.wood);
        this.setCreativeTab(Minechem.CREATIVE_TAB);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setBlockName("container.leadedchest");
    }

	@Override
	public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_) {
		this.dropItems(p_149664_1_, p_149664_2_,  p_149664_3_, p_149664_4_);
		super.onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_);
	}

    @Override
    public TileEntity createNewTileEntity(World world, int i)
    {
        return new LeadedChestTileEntity();
    }

    private void dropItems(World world, int xCoord, int yCoord, int zCoord)
    {
        Random rand = new Random();

        TileEntity te = world.getTileEntity(xCoord, yCoord, zCoord);
        if (te instanceof IInventory)
        {
            IInventory inventory = (IInventory) te;

            int invSize = inventory.getSizeInventory();
            for (int i = 0; i < invSize; i++)
            {
                ItemStack item = inventory.getStackInSlot(i);

                if (item != null && item.stackSize > 0)
                {
                    float randomX = rand.nextFloat() * 0.8F + 0.1F;
                    float randomY = rand.nextFloat() * 0.8F + 0.1F;
                    float randomZ = rand.nextFloat() * 0.8F + 0.1F;

                    EntityItem entityItem = new EntityItem(world, xCoord, yCoord, zCoord, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));

                    if (item.hasTagCompound())
                    {
                        entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                    }

                    float factor = 0.05F;

                    entityItem.motionX = rand.nextGaussian() * factor;
                    entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                    entityItem.motionZ = rand.nextGaussian() * factor;

                    world.spawnEntityInWorld(entityItem);
                    item.stackSize = 0;
                }
            }
        }
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int xCoord, int yCoord, int zCoord, EntityPlayer player, int metadata, float par7, float par8, float par9)
    {
        if (!world.isRemote)
        {
            LeadedChestTileEntity leadedChest = (LeadedChestTileEntity) world.getTileEntity(xCoord, yCoord, zCoord);
            if (leadedChest == null || player.isSneaking())
            {
                return false;
            }
            player.openGui(Minechem.INSTANCE, 0, world, xCoord, yCoord, zCoord);
        }
        return true;
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

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister ir) {
		blockIcon = ir.registerIcon(Reference.LEADEDCHEST_TEX);
	}

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
}
