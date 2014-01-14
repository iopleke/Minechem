package pixlepix.particlephysics.common.helper;

import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

public interface IBlock {
	
	public Class<TileEntity> getTileEntityClass();
	
	public void addRecipe();
	
	
	
	public String getName();
	public boolean hasItemBlock();
	public Class getItemBlock();
	public boolean inCreativeTab();
	

}
