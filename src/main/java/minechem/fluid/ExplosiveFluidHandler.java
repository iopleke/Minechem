package minechem.fluid;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import minechem.item.MinechemChemicalType;
import minechem.item.molecule.MoleculeEnum;
import minechem.utils.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ExplosiveFluidHandler
{

    private static ExplosiveFluidHandler instance;

    public static ExplosiveFluidHandler getInstance()
    {
        if (instance == null)
        {
            instance = new ExplosiveFluidHandler();
        }
        return instance;
    }

    private Map<MinechemChemicalType, Float> explosionLevels = new LinkedHashMap<MinechemChemicalType, Float>();
    private Set<Block> fireSource = new LinkedHashSet<Block>();

    public ExplosiveFluidHandler()
    {
        init();
    }

    public boolean isFireSource(Block block)
    {
        return fireSource.contains(block);
    }

    public void addFireSource(Block block)
    {
        fireSource.add(block);
        LogHelper.debug("Added fire source block:" + block);
    }

    public void removeFireSource(Block block)
    {
        fireSource.remove(block);
        LogHelper.debug("Removed fire source block:" + block);
    }

    public void addExplosiveFluid(MinechemChemicalType type, float level)
    {
        explosionLevels.put(type, level);
        LogHelper.debug("Added explosive fluid:" + type);
    }

    public void removeExplosiveFluid(MinechemChemicalType type)
    {
        explosionLevels.remove(type);
        LogHelper.debug("Removed explosive fluid:" + type);
    }

    public float getExplosionLevel(MinechemChemicalType type)
    {
        Float level = explosionLevels.get(type);
        if (level == null)
        {
            return Float.NaN;
        } else
        {
            return level;
        }
    }

    private void init()
    {
        addFireSource(Blocks.fire);
        addFireSource(Blocks.lava);
        addFireSource(Blocks.flowing_lava);

        addExplosiveFluid(MoleculeEnum.tnt, 2f);
    }
}
