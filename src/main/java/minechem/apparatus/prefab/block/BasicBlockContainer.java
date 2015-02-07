package minechem.apparatus.prefab.block;

import java.util.ArrayList;
import minechem.Minechem;
import minechem.helper.ItemHelper;
import minechem.proxy.CommonProxy;
import minechem.reference.Compendium;
import minechem.registry.CreativeTabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Extendable class for simple container blocks
 */
public abstract class BasicBlockContainer extends BlockContainer
{

    /**
     * Unnamed blocks are given a default name
     */
    public BasicBlockContainer()
    {
        this(Compendium.Naming.name + " Basic Block");
    }

    /**
     * Create a basic block with a given name
     *
     * @param blockName unlocalized name of the block
     */
    public BasicBlockContainer(String blockName)
    {
        this(blockName, Material.grass, Block.soundTypeGrass);
    }

    /**
     * Create a basic block with a given name, material, and sound
     *
     * @param blockName unlocalized name of the block
     * @param material  Material type
     * @param sound     Block sound type
     */
    public BasicBlockContainer(String blockName, Material material, Block.SoundType sound)
    {
        super(material);
        setBlockName(blockName);
        setStepSound(sound);
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
        textureName = Compendium.Texture.prefix + blockName + "Icon";
    }

    /**
     * Define what stacks get dropped when the block is broken, defaults to nothing
     *
     * @param tileEntity
     * @param itemStacks
     */
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks)
    {
    }

    /**
     * Called when the block is broken
     *
     * @param world    the world object
     * @param x        world X coordinate of broken block
     * @param y        world Y coordinate of broken block
     * @param z        world Z coordinate of broken block
     * @param block    the block being broken
     * @param metaData block metadata value
     */
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metaData)
    {
        if (block instanceof BasicBlockContainer)
        {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity != null)
            {
                ArrayList<ItemStack> droppedStacks = new ArrayList<ItemStack>();

                if (dropInventory())
                {
                    if (tileEntity instanceof IInventory)
                    {
                        IInventory inventory = (IInventory) tileEntity;
                        for (int i = 0; i < inventory.getSizeInventory(); i++)
                        {
                            ItemStack stack = inventory.getStackInSlot(i);
                            if (stack != null)
                            {
                                droppedStacks.add(stack);
                            }
                        }
                    }
                }

                addStacksDroppedOnBlockBreak(tileEntity, droppedStacks);
                for (ItemStack itemstack : droppedStacks)
                {
                    ItemHelper.throwItemStack(world, itemstack, x, y, z);
                }
                super.breakBlock(world, x, y, z, block, metaData);
            }
        }
    }

    /**
     * Abstract method, to be overridden by child classes
     *
     * @param world the world object
     * @param meta  block metadata
     * @return the newly created TileEntity
     */
    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    /**
     * Override to allow inventory dropping to be toggled
     *
     * @return boolean true
     */
    public boolean dropInventory()
    {
        return true;
    }

    /**
     * Get the render type
     *
     * @return render ID from the CommonProxy
     */
    @Override
    public int getRenderType()
    {
        return CommonProxy.RENDER_ID;
    }

    /**
     * Disable opaque cube rendering
     *
     * @return false
     */
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * Open the GUI on block activation
     *
     * @param world  the game world object
     * @param x      the x coordinate of the block being activated
     * @param y      the y coordinate of the block being activated
     * @param z      the z coordinate of the block being activated
     * @param player the entityplayer object
     * @param side   which side was hit
     * @param hitX   on the side that was hit, the x coordinate
     * @param hitY   on the side that was hit, the y coordinate
     * @param hitZ   on the side that was hit, the z coordinate
     * @return boolean does the block get activated
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && !player.isSneaking())
        {
            if (!world.isRemote)
            {
                player.openGui(Minechem.INSTANCE, 0, world, x, y, z);
            }
            return true;
        }

        return false;
    }

    /**
     * Set block metadata for model rotation
     *
     * @param world        the world object
     * @param x            world X coordinate of placed block
     * @param y            world Y coordinate of placed block
     * @param z            world Z coordinate of placed block
     * @param livingEntity the entity that placed the block
     * @param itemStack    ItemStack object used to place the block
     */
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase livingEntity, ItemStack itemStack)
    {
        super.onBlockPlacedBy(world, x, y, z, livingEntity, itemStack);
        int facing = MathHelper.floor_double(livingEntity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, facing, 2);
    }

    /**
     * Disable normal block rendering
     *
     * @return false
     */
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
}
