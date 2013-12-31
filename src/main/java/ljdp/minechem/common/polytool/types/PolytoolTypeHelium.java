package ljdp.minechem.common.polytool.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import ljdp.minechem.api.core.EnumElement;
import ljdp.minechem.common.polytool.PolytoolUpgradeType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PolytoolTypeHelium extends PolytoolUpgradeType {

	public PolytoolTypeHelium() {
		super();
	}
	public ArrayList targets=new ArrayList();
	@Override
	public float getStrVsBlock(ItemStack itemStack, Block block) {
		
		return 0;
	}

	@Override
	public void hitEntity(ItemStack itemStack, EntityLivingBase target,
			EntityLivingBase player) {
		targets.add(target);
	}
	

	@Override
	public void onBlockDestroyed(ItemStack itemStack, World world, int id,
			int x, int y, int z, EntityLivingBase entityLiving) {
	}

	@Override
	public EnumElement getElement() {
		
		return EnumElement.He;
	}

	@Override
	public void onTick() {
		Iterator iter=targets.iterator();
        while(iter.hasNext()){
        	EntityLivingBase target=(EntityLivingBase) iter.next();
        	target.motionY=Math.min(target.motionY+.03, 1);
        	Random rand=new Random();
        	if(rand.nextInt((int) (100*power))==0){
        		iter.remove();
        	}
        }
	}

	@Override
	public String getDescription() {
		
		return "Hit entities float away";
	}

}
