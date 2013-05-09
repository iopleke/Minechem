package appeng.api;

import net.minecraft.item.ItemStack;

public interface ISpecialComparisonRegistry {

	IItemComparison getSpecialComparion( ItemStack stack );
	
	public void addComparisonProvider( IItemComparisionProvider prov );
	
}
