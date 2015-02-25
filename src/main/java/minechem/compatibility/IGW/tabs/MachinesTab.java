package minechem.compatibility.IGW.tabs;

import minechem.registry.BlockRegistry;
import net.minecraft.item.ItemStack;

public class MachinesTab extends BaseTab {

	public MachinesTab() {
		name = "Minechem Machines";
		icon = new ItemStack(BlockRegistry.centrifugeBlock);
		pages = new Page[] {Page.OPTICAL_MICROSCOPE, Page.CENTRIFUGE, Page.ELECTRIC_CRUCIBLE};
		addPages();
	}
}
