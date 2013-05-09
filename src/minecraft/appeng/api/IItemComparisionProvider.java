package appeng.api;

import net.minecraft.item.ItemStack;

public interface IItemComparisionProvider {
	
	IItemComparison getComparison( ItemStack is );
	
	public boolean canHandle( ItemStack stack);
	
}
