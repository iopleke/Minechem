package minechem.fluid;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import minechem.item.MinechemChemicalType;
import minechem.item.bucket.MinechemBucketHandler;
import minechem.item.molecule.MoleculeEnum;
import minechem.utils.LogHelper;

public class ExplosiveFluidHandler {
	
	private static ExplosiveFluidHandler instance;

    public static ExplosiveFluidHandler getInstance()
    {
        if (instance == null)
        {
            instance = new ExplosiveFluidHandler();
        }
        return instance;
    }
    
    private Map<MinechemChemicalType, Float> explosiveFluids=new LinkedHashMap<MinechemChemicalType, Float>();
    private Set<Block> fireSource=new LinkedHashSet<Block>();
    
    public ExplosiveFluidHandler() {
		init();
	}
    
    public boolean existingFireSource(Block block) {
		return fireSource.contains(block);
	}

	public void addFireSource(Block block) {
		fireSource.add(block);
		LogHelper.debug("Added fire source block:"+block);
	}

	public void removeFireSource(Block block) {
		fireSource.remove(block);
		LogHelper.debug("Removed fire source block:"+block);
	}

	public void addExplosiveFluid(MinechemChemicalType type,float level){
    	explosiveFluids.put(type, level);
    	LogHelper.debug("Added explosive fluid:"+type);
    }
    
    public void removeExplosiveFluid(MinechemChemicalType type){
    	explosiveFluids.remove(type);
    	LogHelper.debug("Removed explosive fluid:"+type);
    }
    
    public float getExplosiveFluid(MinechemChemicalType type){
    	Float level=explosiveFluids.get(type);
    	if (level==null){
    		return Float.NaN;
    	}else{
    		return level;
    	}
    }
    
    private void init(){
    	addFireSource(Blocks.fire);
    	addFireSource(Blocks.lava);
    	addFireSource(Blocks.flowing_lava);
    	
    	addExplosiveFluid(MoleculeEnum.tnt, 2f);
    }
}
