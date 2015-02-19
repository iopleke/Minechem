package minechem.apparatus.prefab.block;

import minechem.Compendium;
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
        this(blockName, Material.grass, Block.soundTypeGrass);
    }

    public BasicBlock(String blockName, Material material)
    {
        this(blockName, material, material == Material.cloth ? Block.soundTypeCloth : material == Material.wood ? Block.soundTypeWood : material == Material.glass ? Block.soundTypeGlass : material == Material.iron ? Block.soundTypeMetal : Block.soundTypeGrass);
    }

    public BasicBlock(String blockName, Material material, SoundType soundType)
    {
        super(material);
        setBlockName(blockName);
        setStepSound(soundType);
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
