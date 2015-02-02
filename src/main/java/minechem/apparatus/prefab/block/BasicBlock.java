package minechem.apparatus.prefab.block;

import minechem.reference.Compendium;
import minechem.registry.CreativeTabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

/*
 * Extendable class for simple non-container blocks
 */
public abstract class BasicBlock extends Block
{

    /**
     * Unnamed blocks are given a default name
     */
    public BasicBlock()
    {
        this(Compendium.Naming.name + " Basic Block");
    }

    /**
     * Create a basic block with a given name
     *
     * @param blockName unlocalized name of the block
     */
    public BasicBlock(String blockName)
    {
        super(Material.grass);
        setBlockName(blockName);
        setStepSound(Block.soundTypeGrass);
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
        textureName = Compendium.Naming.id + ":" + blockName + "Icon";

    }

    /**
     * Register the block icon from the texture name
     *
     * @param iconRegister
     */
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(textureName);
    }
}
