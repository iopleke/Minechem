package minechem.compatibility.IGW.tabs;

import net.minecraft.item.ItemStack;
import igwmod.gui.GuiWiki;
import igwmod.gui.tabs.BaseWikiTab;

public class BaseTab extends BaseWikiTab {

	public String name;
	public ItemStack icon;
	public Page[] pages;
	public void addPages() {
		for (Page page : pages) {
			pageEntries.add(page.id);
		}
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public ItemStack renderTabIcon(GuiWiki gui) {
		// TODO Auto-generated method stub
		return icon;
	}

	@Override
	protected String getPageName(String pageEntry) {
		// TODO Auto-generated method stub
		return Page.findById(pageEntry).name;
	}

	@Override
	protected String getPageLocation(String pageEntry) {
		// TODO Auto-generated method stub
		return Page.findById(pageEntry).fileName;
	}

}
