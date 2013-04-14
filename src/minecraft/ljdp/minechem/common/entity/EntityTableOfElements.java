package ljdp.minechem.common.entity;

import ljdp.minechem.common.MinechemItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumArt;
import net.minecraft.world.World;

public class EntityTableOfElements extends EntityHanging {
	
	public EnumArt art;
	
	public EntityTableOfElements(World world, int x, int y, int z, int orientation) {
		super(world);
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		this.hangingDirection = orientation;
		this.art = EnumArt.Aztec;
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}

	public boolean canStay() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public int getSizeX() {
		return this.art.sizeX;
	}
	
	public int getSizeY() {
		return this.art.sizeY;
	}

	/**
	 * Function: Get Art Size X.
	 * @return
	 */
	@Override
	public int func_82329_d() {
		return getSizeX();
	}
	
	/**
	 * Function: Get Art Size Y.
	 * @return
	 */
	@Override
	public int func_82330_g() {
		return getSizeY();
	}

	@Override
	public void dropItemStack() {
		this.entityDropItem(new ItemStack(MinechemItems.tableelements), 0.0F);
	}

}
