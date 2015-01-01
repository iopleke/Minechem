package minechem.block;

import minechem.gui.CreativeTabMinechem;
import minechem.reference.Textures;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockUraniumOre extends Block
{
    public BlockUraniumOre()
    {
        super(Material.iron);
        this.setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_ITEMS);
        this.setBlockName("oreUranium");
        this.setHardness(4F);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(Textures.IIcon.URANIUM);
    }
}
