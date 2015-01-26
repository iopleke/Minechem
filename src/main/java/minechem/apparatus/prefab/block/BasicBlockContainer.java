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
 * Defines basic properties of a simple block, such as the creative tab, the texture location, and the block name
 *
 * @author jakimfett
 */
public abstract class BasicBlockContainer extends BlockContainer
{

    public BasicBlockContainer()
    {
        super(Material.grass);
        setBlockName(Compendium.Naming.name + " Basic Block");
        setStepSound(Block.soundTypeGrass);
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
        textureName = Compendium.Naming.id + ":basicBlockIcon";
    }

    public BasicBlockContainer(String blockName)
    {
        super(Material.grass);
        setBlockName(blockName);
        setStepSound(Block.soundTypeGrass);
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
        textureName = Compendium.Naming.id + ":" + blockName + "Icon";

    }

    public BasicBlockContainer(String blockName, Material material, Block.SoundType sound)
    {
        super(material);
        setBlockName(blockName);
        setStepSound(sound);
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
        textureName = Compendium.Texture.prefix + blockName + "Icon";

    }

    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks)
    {
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metaData)
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

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    public boolean dropInventory()
    {
        return true;
    }

    @Override
    public int getRenderType()
    {
        return CommonProxy.RENDER_ID;
    }

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

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase el, ItemStack is)
    {
        super.onBlockPlacedBy(world, x, y, z, el, is);
        int facing = MathHelper.floor_double(el.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, facing, 2);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
}
