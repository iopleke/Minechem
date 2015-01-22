package minechem.apparatus;

import minechem.reference.Compendium;
import minechem.registry.CreativeTabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

/*
 * Defines basic properties of a simple block, such as the creative tab, the
 * texture location, and the block name
 *
 * @author jakimfett
 */
public abstract class BasicBlock extends Block
{

    public BasicBlock()
    {
        super(Material.grass);
        setBlockName(Compendium.Naming.name + " Basic Block");
        setStepSound(Block.soundTypeGrass);
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
        textureName = Compendium.Naming.id + ":basicBlockIcon";
    }

    public BasicBlock(String blockName)
    {
        super(Material.grass);
        setBlockName(blockName);
        setStepSound(Block.soundTypeGrass);
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
        textureName = Compendium.Naming.id + ":" + blockName + "Icon";

    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(textureName);
    }
}
