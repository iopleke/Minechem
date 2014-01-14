package pixlepix.minechem.minechem.common.blocks;

import java.util.ArrayList;

import pixlepix.minechem.minechem.common.CommonProxy;
import pixlepix.minechem.minechem.common.ModMinechem;
import pixlepix.minechem.minechem.common.tileentity.TileEntityDecomposer;
import pixlepix.minechem.minechem.common.utils.ConstantValue;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecomposer extends BlockMinechemContainer {
    private Icon front;

    public BlockDecomposer(int id) {
        super(id, Material.iron);
        setUnlocalizedName("blockChemicalDecomposer");
        setCreativeTab(ModMinechem.minechemTab);
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
        return new TileEntityDecomposer();
    }

    @Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks) {
        TileEntityDecomposer decomposer = (TileEntityDecomposer) tileEntity;
        for (int slot = 0; slot < decomposer.getSizeInventory(); slot++) {
            ItemStack itemstack = decomposer.getStackInSlot(slot);
            if (itemstack != null) {
                itemStacks.add(itemstack);
            }
        }
        return;
    }


    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        blockIcon = ir.registerIcon(ConstantValue.DECOMPOSER_TEX);
        front = ir.registerIcon(ConstantValue.DECOMPOSER_FRONT_TEX);
        
        //Yes, this is hacky
        ModMinechem.instance.textureHook(ir);
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
