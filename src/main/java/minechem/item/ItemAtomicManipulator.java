package minechem.item;

import java.util.List;

import minechem.MinechemItemsRegistration;
import minechem.computercraft.MinechemCCItemsRegistration;
import minechem.computercraft.ChemicalTurtleUpgrade;
import minechem.gui.CreativeTabMinechem;
import minechem.reference.Textures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAtomicManipulator extends Item
{

	public ItemAtomicManipulator()
	{
		setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_ITEMS);
		setUnlocalizedName("itemAtomicManipulator");
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon(Textures.IIcon.ATOMIC_MANIPULATOR);
	}

	@SuppressWarnings("unchecked")
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		subItems.add(new ItemStack(item, 1, 0));
		if (Loader.isModLoaded("ComputerCraft")) MinechemCCItemsRegistration.chemicalUpgrade.addTurtlesToCreative(subItems);
	}
	
	public IIcon getIcon()
	{
		return itemIcon;
	}
	
}
