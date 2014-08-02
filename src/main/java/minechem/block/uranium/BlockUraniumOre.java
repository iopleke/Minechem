package minechem.block.uranium;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.ModMinechem;
import minechem.utils.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockUraniumOre extends Block
{
    public BlockUraniumOre()
    {
        super(Material.iron);
        this.setCreativeTab(ModMinechem.CREATIVE_TAB);
        //this.setBlockName("Uranium Ore");
        this.setHardness(4F);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(Reference.URANIUM_TEX);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return this.blockIcon;
    }
}
