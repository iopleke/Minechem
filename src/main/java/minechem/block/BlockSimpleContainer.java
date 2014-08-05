package minechem.block;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.Block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockSimpleContainer extends BlockContainer
{

    private Random random = new Random();

    protected BlockSimpleContainer(Material material)
    {
        super(material);
        setHardness(2F);
        setResistance(50F);
    }

    public abstract void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks);

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metaData)
    {
        this.random = new Random();
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null)
        {
            ArrayList<ItemStack> droppedStacks = new ArrayList<ItemStack>();
            addStacksDroppedOnBlockBreak(tileEntity, droppedStacks);
            for (ItemStack itemstack : droppedStacks)
            {
                float randomX = random.nextFloat() * 0.8F + 0.1F;
                float randomY = random.nextFloat() * 0.8F + 0.1F;
                float randomZ = random.nextFloat() * 0.8F + 0.1F;
                while (itemstack.stackSize > 0)
                {
                    int randomN = random.nextInt(21) + 10;
                    if (randomN > itemstack.stackSize)
                    {
                        randomN = itemstack.stackSize;
                    }
                    itemstack.stackSize -= randomN;
                    ItemStack droppedStack = new ItemStack(itemstack.getItem(), randomN, itemstack.getItemDamage());
                    // Copy NBT tag data, needed to preserve Chemist Journal
                    // contents (for example).
                    if (itemstack.hasTagCompound())
                    {
                        droppedStack.setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                    }

                    EntityItem droppedEntityItem = new EntityItem(world, x + randomX, y + randomY, z + randomZ, droppedStack);
                    world.spawnEntityInWorld(droppedEntityItem);
                }
            }
            super.breakBlock(world, x, y, z, block, metaData);
        }

    }
}
