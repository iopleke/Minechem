package minechem.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.gui.CreativeTabMinechem;
import minechem.reference.Textures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemAtomicManipulator extends Item
{

	public ItemAtomicManipulator()
	{
		setCreativeTab(CreativeTabMinechem.CREATIVE_TAB_ITEMS);
		setUnlocalizedName("itemAtomicManipulator");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon(Textures.IIcon.ATOMIC_MANIPULATOR);
	}

}
