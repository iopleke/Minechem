package ljdp.minechem.common.blocks;

import java.util.Random;

import ljdp.minechem.common.CommonProxy;
import ljdp.minechem.common.tileentity.TileEntityGhostBlock;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGhostBlock extends BlockContainer {

    public BlockGhostBlock(int id) {
        super(id, Material.iron);
        setUnlocalizedName("Unnamed");
        setLightValue(0.5F);
        setHardness(1000F);
        setResistance(1000F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float par7, float par8, float par9) {
        super.onBlockActivated(world, x, y, z, entityPlayer, side, par7, par8, par9);

        if (world.isRemote)
            return true;

        if (entityPlayer.getDistanceSq((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D) > 64.0D)
            return true;

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityGhostBlock) {
            TileEntityGhostBlock ghostBlock = (TileEntityGhostBlock) tileEntity;
            ItemStack blockAsStack = ghostBlock.getBlockAsItemStack();
            if (playerIsHoldingItem(entityPlayer, blockAsStack)) {
                world.setBlock(x, y, z, blockAsStack.getItem().itemID, blockAsStack.getItemDamage(), 3);
                if (!entityPlayer.capabilities.isCreativeMode)
                    entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
                return true;
            }
        }
        return false;
    }

    private boolean playerIsHoldingItem(EntityPlayer entityPlayer, ItemStack itemstack) {
        ItemStack helditem = entityPlayer.inventory.getCurrentItem();
        return helditem != null && helditem.itemID == itemstack.itemID
                && (helditem.getItemDamage() == itemstack.getItemDamage() || itemstack.getItemDamage() == -1);
    }

    /**
     * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
     */
    public boolean canCollideCheck(int par1, boolean par2) {
        return true;
    }

    @Override
    public int damageDropped(int par1) {
        return par1;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        // Todo
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given coordinates. Args: blockAccess, x, y, z, side
     */
    @Override
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
        return var6 == this.blockID ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return CommonProxy.CUSTOM_RENDER_ID;
    }

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {}

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(World var1) {
        return new TileEntityGhostBlock();
    }
}
