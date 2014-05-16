package minechem.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.common.tileentity.TileEntityGhostBlock;
import minechem.common.utils.ConstantValue;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockGhostBlock extends BlockContainer {

    public BlockGhostBlock(int id) {
        super(id, Material.iron);
        setUnlocalizedName("block.minechemGhostBlock");
        setLightValue(0.5F);
        setHardness(1000F);
        setResistance(1000F);
    }

    public Icon icon1;
    public Icon icon2;

    @Override
    public Icon getIcon(int par1, int metadata) {
        switch (metadata) {
            case 0:
                return icon1;
            case 1:
                return icon2;
        }
        return blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        blockIcon = ir.registerIcon(ConstantValue.DEFAULT_TEX);
        icon1 = ir.registerIcon(ConstantValue.BLUEPRINT1_TEX);
        icon2 = ir.registerIcon(ConstantValue.BLUEPRINT2_TEX);
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

                world.setBlock(x, y, z, blockAsStack.getItem().itemID, 0, 0);
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
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
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
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(World var1) {
        return new TileEntityGhostBlock();
    }

	@Override
	public boolean isBlockReplaceable(World world, int x, int y, int z) {
		return true;
	}
}
