package basiccomponents.common.item;

import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import basiccomponents.common.BasicComponents;

/**
 * An Base Item Class for Basic Components. Do not use this! Make your own!
 * 
 * @author Calclavia
 * 
 */
public class ItemBase extends Item
{
	protected final Icon[] icons = new Icon[256];

	public ItemBase(String name, int id)
	{
		super(BasicComponents.CONFIGURATION.getItem(name, id).getInt(id));
		this.setUnlocalizedName(BasicComponents.TEXTURE_NAME_PREFIX + name);
		this.setNoRepair();
	}
}
