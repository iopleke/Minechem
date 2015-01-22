package minechem.apparatus.prefab.block;

import java.util.ArrayList;
import minechem.helper.ItemHelper;
import minechem.reference.Compendium;
import minechem.registry.CreativeTabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/*
 * Defines basic properties of a simple block, such as the creative tab, the
 * texture location, and the block name
 *
 * @author jakimfett
 */
public abstract class ContainerBlock extends BlockContainer
{

    public ContainerBlock()
    {
        super(Material.grass);
        setBlockName(Compendium.Naming.name + " Basic Block");
        setStepSound(Block.soundTypeGrass);
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
        textureName = Compendium.Naming.id + ":basicBlockIcon";
    }

    public ContainerBlock(String blockName)
    {
        super(Material.grass);
        setBlockName(blockName);
        setStepSound(Block.soundTypeGrass);
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
        textureName = Compendium.Naming.id + ":" + blockName + "Icon";

    }

    public ContainerBlock(String blockName, Material material, Block.SoundType sound)
    {
        super(material);
        setBlockName(blockName);
        setStepSound(sound);
        setCreativeTab(CreativeTabRegistry.TAB_PRIMARY);
        textureName = Compendium.Naming.id + ":" + blockName + "Icon";

    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    @Override
    public abstract boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ);

    public abstract void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks);

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metaData)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null)
        {
            ArrayList<ItemStack> droppedStacks = new ArrayList<ItemStack>();
            addStacksDroppedOnBlockBreak(tileEntity, droppedStacks);
            for (ItemStack itemstack : droppedStacks)
            {
                ItemHelper.throwItemStack(world, itemstack, x, y, z);
            }
            super.breakBlock(world, x, y, z, block, metaData);
        }

    }
}
