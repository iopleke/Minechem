package pixlepix.particlephysics.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.api.IParticleBouncer;
import pixlepix.particlephysics.common.helper.BasicComplexBlock;
import cpw.mods.fml.common.registry.GameRegistry;

public class ControlGlass extends BasicComplexBlock implements IParticleBouncer {

	
	
	public ControlGlass() {
		super(1181); 
	}
	public ControlGlass(int i) {
		super(i);
	}
	@Override
	public String getFront() {
		// TODO Auto-generated method stub
		return "ControlGlass";
	}
	@Override
	public boolean hasModel(){
		return true;
	}
	@Override
	public String getTop() {
		// TODO Auto-generated method stub
		return "ControlGlass";
	}

	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
		if (par7Entity instanceof BaseParticle&&par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)){
			return;
		}
        AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);

        if (axisalignedbb1 != null && par5AxisAlignedBB.intersectsWith(axisalignedbb1))
        {
            par6List.add(axisalignedbb1);
        }
    }
	@Override
	public Class getTileEntityClass() {
		return null;
	}

	@Override
	public void addRecipe() {
		GameRegistry.addRecipe(new ItemStack(this),"R R"," G ","R R",'R',new ItemStack(Item.redstone),'G',new ItemStack(Block.glass));
		
	}

	@Override
	public String getName() {
		return "Control Glass";
	}

	@Override
	public boolean hasItemBlock() {
		return false;
	}

	@Override
	public Class getItemBlock() {
		return null;
		
	}
	@Override
	public boolean topSidedTextures(){
		return false;
	}
	@Override
	public boolean canBounce(World world, int x, int y, int z,
			BaseParticle particle) {
		return !(world.isBlockIndirectlyGettingPowered(x,y,z));
	}

	

}
