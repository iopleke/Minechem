package ljdp.minechem.common.polytool.types;

import java.util.Random;

import ljdp.minechem.api.core.EnumElement;
import ljdp.minechem.common.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeCarbon extends PolytoolUpgradeType {

	public PolytoolTypeCarbon() {
		super();
	}

	@Override
	public float getStrVsBlock(ItemStack itemStack, Block block) {

		return 0;
	}

	@Override
	public void hitEntity(ItemStack itemStack, EntityLivingBase target,
			EntityLivingBase player) {
	}

	@Override
	public void onBlockDestroyed(ItemStack itemStack, World world, int id, int x, int y, int z, EntityLivingBase entityLiving) {
		Random rand=new Random();
		if(!world.isRemote){
			int bonus=(int) (rand.nextDouble()*Math.log(this.power));
			if(id==Block.oreCoal.blockID){
				world.spawnEntityInWorld(new EntityItem(world, x+rand.nextDouble(), y+rand.nextDouble(), z+rand.nextDouble(), new ItemStack(Item.coal.itemID,bonus, 0)));
			}
			if(id==Block.oreDiamond.blockID){
				world.spawnEntityInWorld(new EntityItem(world, x+rand.nextDouble(), y+rand.nextDouble(), z+rand.nextDouble(), new ItemStack(Item.diamond.itemID,bonus, 0)));
			}
		}
	}

	@Override
	public EnumElement getElement() {

		return EnumElement.C;
	}

	@Override
	public void onTick() {
	}

}
