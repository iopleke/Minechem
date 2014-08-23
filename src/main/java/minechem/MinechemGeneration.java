package minechem;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class MinechemGeneration implements IWorldGenerator
{

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if (Settings.WorldGenOre)
        {
            if (world.provider.isSurfaceWorld())
            {
                for (int k = 0; k <= Settings.UraniumOreDensity; k++)
                {
                    int firstBlockXCoord = (16 * chunkX) + random.nextInt(16);
                    int firstBlockYCoord = random.nextInt(50);
                    int firstBlockZCoord = (16 * chunkZ) + random.nextInt(16);
                    int oreCount = random.nextInt(Settings.UraniumOreClusterSize + 10);

                    (new WorldGenMinable(MinechemBlocksGeneration.uranium.blockID, oreCount)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
                    if (Settings.DebugMode)
                    {
                        System.out.println("Minechem generated Uranium generated at:");
                        System.out.println("X :" + firstBlockXCoord);
                        System.out.println("Y :" + firstBlockYCoord);
                        System.out.println("Z :" + firstBlockZCoord);
                    }
                }
            }
        }

    }
}
