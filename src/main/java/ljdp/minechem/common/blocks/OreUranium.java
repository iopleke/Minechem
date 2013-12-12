package ljdp.minechem.common.blocks;

import java.util.ArrayList;
import java.util.List;

import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.tileentity.TileEntityFission;
import ljdp.minechem.common.tileentity.TileEntityFusion;
import ljdp.minechem.common.tileentity.TileEntityProxy;
import ljdp.minechem.common.utils.ConstantValue;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class OreUranium extends Block {
    private Icon icon1, icon2;

    public OreUranium(int id) {
        super(id, Material.iron);
        setCreativeTab(ModMinechem.minechemTab);
        setUnlocalizedName("minechem.uraniumOre");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        blockIcon = ir.registerIcon(ConstantValue.URANIUM_TEX);
    }
}
