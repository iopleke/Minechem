package minechem.fluid;

import minechem.Settings;
import minechem.tick.ChemicalExplosionHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class MinechemFluidBlock extends BlockFluidClassic {

	public MinechemFluidBlock(Fluid fluid, Material material) {
		super(fluid, material);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock){
		super.onNeighborBlockChange(world, x, y, z, neighborBlock);
		
		if (Settings.explosionFluidMeetFluid){
			boolean exploded=false;
			
			exploded|=checkToExplode(world,x+1,y,z);
			exploded|=checkToExplode(world,x,y+1,z);
			exploded|=checkToExplode(world,x,y,z+1);
			exploded|=checkToExplode(world,x-1,y,z);
			exploded|=checkToExplode(world,x,y-1,z);
			exploded|=checkToExplode(world,x,y,z-1);
			
			if (exploded){
				world.setBlockToAir(x, y, z);
			}
		}
	}
	
	private boolean checkToExplode(World world,int x,int y,int z){
		return ChemicalExplosionHandler.checkToExplode(this, world.getBlock(x, y, z), world, x, y, z);
	}
}
