package minechem.compatibility.IGW.tabs;

import minechem.Compendium;
import minechem.helper.LogHelper;
import minechem.registry.BlockRegistry;
import net.minecraft.item.ItemStack;

public class MainMinechemTab extends BaseTab {

	public MainMinechemTab() {
		name = "Minechem";
		icon = new ItemStack(BlockRegistry.opticalMicroscope);
		pages = new Page[] {Page.WELCOME, Page.FIRST_STEPS};
		addPages();
	}
	
}
