package ljdp.minechem.common;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Loader;

public class MinechemGeneration implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(!Loader.isModLoaded("AtomicScience")){
			if(world.provider.dimensionId==0){
				for(int k=0;k<2;k++){
					int firstBlockXCoord = chunkX + random.nextInt(16);
					int firstBlockYCoord = random.nextInt(30);
					int firstBlockZCoord = chunkZ + random.nextInt(16);
					WorldGenMinable mineable=new WorldGenMinable(MinechemBlocks.uranium.blockID, 4);
					mineable.generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
				}
			}
			
	}
	}

}
