package minechem.item.polytool.types;

import java.util.ArrayList;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolUpgradeType;
import minechem.utils.CoordTuple;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class PolytoolTypeIron extends PolytoolUpgradeType
{

    public PolytoolTypeIron()
    {
        super();
    }

    @Override
    public float getStrVsBlock(ItemStack itemStack, Block block)
    {

        return 0;
    }

    @Override
    public void hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase player)
    {

    }

	@Override
    public void onBlockDestroyed(ItemStack itemStack, World world, Block id, int x1, int y1, int z1, EntityLivingBase entityLiving)
    {
        ArrayList<CoordTuple> queue = new ArrayList<CoordTuple>(100);
        if (id == Blocks.coal_ore || id == Blocks.diamond_ore || id == Blocks.emerald_ore || id == Blocks.gold_ore || id == Blocks.iron_ore || id == Blocks.lapis_ore || id == Blocks.quartz_ore
                || id == Blocks.redstone_ore|| OreDictionary.getOreName(OreDictionary.getOreID(new ItemStack(itemStack.getItem(), 1, 0))).contains("ore"))
        {

            int toMine = (int) power;
            queue.add(new CoordTuple(x1, y1, z1));
            while (!queue.isEmpty())
            {
                CoordTuple coord = queue.remove(0);
                int x = coord.x;
                int y = coord.y;
                int z = coord.z;
                for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
                {
                    if (world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == id)
                    {
                        world.setBlockToAir(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
                        queue.add(new CoordTuple(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ));
                        toMine--;
                        if (toMine <= 0)
                        {
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    public ElementEnum getElement()
    {

        return ElementEnum.Fe;
    }

    @Override
    public void onTick()
    {
    }

    @Override
    public String getDescription()
    {

        return "Mines deposits of ores";
    }

}
