package ljdp.minechem.common.blocks;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ljdp.minechem.common.CommonProxy;
import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.tileentity.TileEntityMicroscope;
import ljdp.minechem.common.utils.ConstantValue;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockMicroscope extends BlockMinechemContainer {
    private Icon front;

    public BlockMicroscope(int par1) {
        super(par1, Material.iron);
        setCreativeTab(ModMinechem.minechemTab);
        setUnlocalizedName("minechem.blockMicroscope");
        setLightValue(0.5F);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving el, ItemStack is) {
        super.onBlockPlacedBy(world, x, y, z, el, is);
        int facing = MathHelper.floor_double((double) (el.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, facing, 2);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity == null || entityPlayer.isSneaking())
            return false;
        entityPlayer.openGui(ModMinechem.instance, 0, world, x, y, z);
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World var1) {
        return new TileEntityMicroscope();
    }

    @Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks) {
        ItemStack inputStack = ((TileEntityMicroscope) tileEntity).getStackInSlot(0);
        ItemStack journal = ((TileEntityMicroscope) tileEntity).getStackInSlot(1);
        if (inputStack != null)
            itemStacks.add(inputStack);
        if (journal != null)
           itemStacks.add(journal);
        return;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        blockIcon = ir.registerIcon(ConstantValue.MICROSCOPE_TEX);
        front = ir.registerIcon(ConstantValue.MICROSCOPE_FRONT_TEX);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return CommonProxy.CUSTOM_RENDER_ID;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

}
