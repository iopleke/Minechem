package minechem.compatibility.IGW.tabs;

import minechem.registry.ItemRegistry;
import net.minecraft.item.ItemStack;

public class Features extends BaseTab {

	public Features() {
		name = "Minechem Features";
		icon = new ItemStack(ItemRegistry.chemicalItem);
		pages = new Page[] {Page.FOOD_SPIKING};
		addPages();
	}
}
