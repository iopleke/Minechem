package ljdp.minechem.common.blocks;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.tileentity.TileEntityFusion;
import ljdp.minechem.common.tileentity.TileEntityProxy;
import ljdp.minechem.common.utils.ConstantValue;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockFusion extends BlockMinechemContainer {
    private Icon icon1, icon2;

    public BlockFusion(int id) {
        super(id, Material.iron);
        setCreativeTab(ModMinechem.minechemTab);
        setUnlocalizedName("minechem.blockFusion");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float par7, float par8, float par9) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);;
        if (tileEntity == null || entityPlayer.isSneaking())
            return false;
        entityPlayer.openGui(ModMinechem.instance, 0, world, x, y, z);
        return true;
    }

    @Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks) {
        return;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        if (metadata == 2)
            return new TileEntityFusion();
        else
            return new TileEntityProxy();
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    @Override
    public Icon getBlockTextureFromSideAndMetadata(int par1, int metadata) {
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
        icon1 = ir.registerIcon(ConstantValue.FUSION1_TEX);
        icon2 = ir.registerIcon(ConstantValue.FUSION2_TEX);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 0; i < 2; i++)
            par3List.add(new ItemStack(this.blockID, 1, i));
    }

}
