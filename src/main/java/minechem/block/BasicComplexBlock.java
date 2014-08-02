package minechem.block;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import minechem.ModMinechem;
import minechem.network.server.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class BasicComplexBlock extends Block implements IBlock
{

    public IIcon connectorIcon;
    public IIcon topIcon;
    static int blockIdIncrement;
    public String textureBase = "minechem:";

    public abstract String getFront();

    public abstract String getTop();

    @Override
    public boolean renderAsNormalBlock()
    {
        return true;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean hasModel()
    {
        return false;
    }

    public BasicComplexBlock(Material material)
    {
        super(material);

        this.setCreativeTab(ModMinechem.CREATIVE_TAB);

    }

    @Override
    public boolean inCreativeTab()
    {
        return true;
    }

    public abstract void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks);

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metaData)
    {
        Random random = new Random();
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null)
        {
            ArrayList<ItemStack> droppedStacks = new ArrayList<ItemStack>();
            addStacksDroppedOnBlockBreak(tileEntity, droppedStacks);
            for (ItemStack itemstack : droppedStacks)
            {
                float randomX = random.nextFloat() * 0.8F + 0.1F;
                float randomY = random.nextFloat() * 0.8F + 0.1F;
                float randomZ = random.nextFloat() * 0.8F + 0.1F;
                while (itemstack.stackSize > 0)
                {
                    int randomN = random.nextInt(21) + 10;
                    if (randomN > itemstack.stackSize)
                    {
                        randomN = itemstack.stackSize;
                    }
                    itemstack.stackSize -= randomN;
                    ItemStack droppedStack = new ItemStack(itemstack.getItem(), randomN, itemstack.getItemDamage());
                    // Copy NBT tag data, needed to preserve Chemist Journal
                    // contents (for example).
                    if (itemstack.hasTagCompound())
                    {
                        droppedStack.setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                    }

                    EntityItem droppedEntityItem = new EntityItem(world, x + randomX, y + randomY, z + randomZ, droppedStack);
                    world.spawnEntityInWorld(droppedEntityItem);
                }
            }
            super.breakBlock(world, x, y, z, block, metaData);
        }

    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderType()
    {
        if (this.hasModel())
        {
            return CommonProxy.RENDER_ID;
        }
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {

        blockIcon = par1IconRegister.registerIcon(textureBase + this.getFront());
        topIcon = par1IconRegister.registerIcon(textureBase + this.getTop());
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (topSidedTextures())
        {

            if (side == 1 || side == 0)
            {
                return topIcon;
            }
        }
        return blockIcon;

    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
    {

        int angle = MathHelper.floor_double((par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int change = 0;

        switch (angle)
        {
            case 0:
                change = 1;
                break;
            case 1:
                change = 2;
                break;
            case 2:
                change = 0;
                break;
            case 3:
                change = 3;
                break;
        }
        world.setBlockMetadataWithNotify(x, y, z, change, 2);
        world.notifyBlocksOfNeighborChange(x, y, z, this);
    }

    @Override
    public TileEntity createTileEntity(World var1, int metadata)
    {
        try
        {
            return getTileEntityClass().newInstance();
        } catch (Throwable e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Error while creating tile entity");
        return null;

    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return getTileEntityClass() != null;
    }

    @Override
    public void addRecipe()
    {

    }

    @Override
    public String getName()
    {
        // TODO Auto-generated method stub
        return "BasicComplexBlock";
    }

    @Override
    public boolean hasItemBlock()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Class getItemBlock()
    {
        // TODO Auto-generated method stub

        return ItemBlock.class;
    }

    public boolean topSidedTextures()
    {
        return true;
    }

    @Override
    public Class<TileEntity> getTileEntityClass()
    {
        return null;
    }

    public boolean isPlantMaterial()
    {
        return false;
    }

}
