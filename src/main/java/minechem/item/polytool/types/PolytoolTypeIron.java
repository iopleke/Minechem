package minechem.item.polytool.types;

import java.util.ArrayList;

import minechem.api.core.EnumElement;
import minechem.item.polytool.PolytoolUpgradeType;
import minechem.utils.CoordTuple;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
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
    public void onBlockDestroyed(ItemStack itemStack, World world, int id, int x1, int y1, int z1, EntityLivingBase entityLiving)
    {
        ArrayList<CoordTuple> queue = new ArrayList<CoordTuple>(100);
        if (id == Block.oreCoal.blockID || id == Block.oreDiamond.blockID || id == Block.oreEmerald.blockID || id == Block.oreGold.blockID || id == Block.oreIron.blockID || id == Block.oreLapis.blockID || id == Block.oreNetherQuartz.blockID
                || id == Block.oreRedstone.blockID || OreDictionary.getOreName(OreDictionary.getOreID(new ItemStack(id, 1, 0))).contains("ore"))
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
                    if (world.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == id)
                    {
                        world.destroyBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, true);
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
    public EnumElement getElement()
    {

        return EnumElement.Fe;
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
