package minechem.item;

import java.util.List;

import minechem.ModMinechem;
import minechem.utils.MinechemHelper;
import minechem.utils.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLens extends Item
{
    static final String[] descriptiveNames =
    { "item.name.concavelens", "item.name.convexlens", "item.name.microscopelens", "item.name.projectorlens" };
    @SideOnly(Side.CLIENT)
	private final IIcon[] icons = new IIcon[4];

    public ItemLens()
    {
        super();
        setUnlocalizedName("minechem.itemLens");
        setCreativeTab(ModMinechem.CREATIVE_TAB);
        setHasSubtypes(true);
    }

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		int metadata = itemStack.getItemDamage();
		return MinechemHelper.getLocalString(descriptiveNames[metadata]);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(item, 1, 0));
        par3List.add(new ItemStack(item, 1, 1));
        par3List.add(new ItemStack(item, 1, 2));
        par3List.add(new ItemStack(item, 1, 3));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i)
    {
        return icons[i];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir)
    {
        icons[0] = ir.registerIcon(Reference.LENS1_TEX);
        icons[1] = ir.registerIcon(Reference.LENS2_TEX);
        icons[2] = ir.registerIcon(Reference.LENS3_TEX);
        icons[3] = ir.registerIcon(Reference.LENS4_TEX);
    }

}
