package pixlepix.minechem.common.blocks;

import java.util.Random;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import pixlepix.minechem.common.ModMinechem;
import pixlepix.minechem.common.tileentity.TileEntityLeadedChest;

public class BlockLeadedChest extends BlockContainer {

    public BlockLeadedChest(int id) {
        super(id, Material.wood);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setUnlocalizedName("container.leadedchest");
        this.setCreativeTab(ModMinechem.minechemTab);
    }

    @Override
    public boolean onBlockActivated(World world, int xCoord, int yCoord, int zCoord, EntityPlayer player, int metadata, float par7, float par8, float par9) {
        if (!world.isRemote) {
            TileEntityLeadedChest leadedchest = (TileEntityLeadedChest) world.getBlockTileEntity(xCoord, yCoord, zCoord);
            if (leadedchest == null || player.isSneaking()) {
                return false;
            }
            player.openGui(ModMinechem.instance, 0, world, xCoord, yCoord, zCoord);
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int xCoord, int yCoord, int zCoord, int par5, int par6) {
        this.dropItems(world, xCoord, yCoord, zCoord);
        super.breakBlock(world, xCoord, yCoord, zCoord, par5, par6);
    }

    private void dropItems(World world, int xCoord, int yCoord, int zCoord) {
        Random rand = new Random();

        TileEntity te = world.getBlockTileEntity(xCoord, yCoord, zCoord);
        if (te instanceof IInventory) {
            IInventory inventory = (IInventory) te;

            int invSize = inventory.getSizeInventory();
            for (int i = 0; i < invSize; i++) {
                ItemStack item = inventory.getStackInSlot(i);

                if (item != null && item.stackSize > 0) {
                    float randomX = rand.nextFloat() * 0.8F + 0.1F;
                    float randomY = rand.nextFloat() * 0.8F + 0.1F;
                    float randomZ = rand.nextFloat() * 0.8F + 0.1F;

                    EntityItem entityItem = new EntityItem(world, xCoord, yCoord, zCoord, new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

                    if (item.hasTagCompound()) {
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
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityLeadedChest();
    }
    
    @Override
    public int getRenderType(){
        return -1;
    }
    
    @Override
    public boolean isOpaqueCube(){
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase el, ItemStack is) {
        byte facing = 0;
        int facingI = MathHelper.floor_double((double) (el.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (facingI == 0) {
            facing = 2;
        }

        if (facingI == 1) {
            facing = 5;
        }

        if (facingI == 2) {
            facing = 3;
        }

        if (facingI == 3) {
            facing = 4;
        }
        world.setBlockMetadataWithNotify(x, y, z, facing, 2);
    }
}
