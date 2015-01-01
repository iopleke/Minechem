package minechem.tileentity.leadedchest;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.Minechem;
import minechem.gui.CreativeTabMinechem;
import minechem.reference.Textures;
import minechem.utils.MinechemUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LeadedChestBlock extends BlockContainer
{

    public LeadedChestBlock()
    {
        super(Material.wood);
        this.setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_ITEMS);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setBlockName("leadChest");
    }

    @Override
    public void breakBlock(World world, int xCoord, int yCoord, int zCoord, Block block, int metaData)
    {
        this.dropItems(world, xCoord, yCoord, zCoord);
        super.onBlockDestroyedByPlayer(world, xCoord, yCoord, zCoord, metaData);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i)
    {
        return new LeadedChestTileEntity();
    }

    private void dropItems(World world, int xCoord, int yCoord, int zCoord)
    {

        TileEntity te = world.getTileEntity(xCoord, yCoord, zCoord);
        if (te instanceof IInventory)
        {
            IInventory inventory = (IInventory) te;

            int invSize = inventory.getSizeInventory();
            for (int i = 0; i < invSize; i++)
            {
                MinechemUtil.throwItemStack(world, inventory.getStackInSlot(i), xCoord, yCoord, zCoord);
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
    public void registerBlockIcons(IIconRegister ir)
    {
        blockIcon = ir.registerIcon(Textures.IIcon.LEADEDCHEST);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
}
