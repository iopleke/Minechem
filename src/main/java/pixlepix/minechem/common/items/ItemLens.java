package pixlepix.minechem.common.items;

import java.util.List;

import pixlepix.minechem.common.ModMinechem;
import pixlepix.minechem.common.utils.ConstantValue;
import pixlepix.minechem.common.utils.MinechemHelper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLens extends Item {
    static final String[] descriptiveNames = { "item.name.concavelens", "item.name.convexlens", "item.name.microscopelens", "item.name.projectorlens" };
    private final Icon[] icons = new Icon[4];

    public ItemLens(int id) {
        super(id);
        setUnlocalizedName("minechem.itemLens");
        setCreativeTab(ModMinechem.minechemTab);
        setHasSubtypes(true);
    }

    @Override
    public String getItemDisplayName(ItemStack itemStack) {
        int metadata = itemStack.getItemDamage();
        return MinechemHelper.getLocalString(descriptiveNames[metadata]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int id, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(id, 1, 0));
        par3List.add(new ItemStack(id, 1, 1));
        par3List.add(new ItemStack(id, 1, 2));
        par3List.add(new ItemStack(id, 1, 3));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int i) {
        return icons[i];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        icons[0] = ir.registerIcon(ConstantValue.LENS1_TEX);
        icons[1] = ir.registerIcon(ConstantValue.LENS2_TEX);
        icons[2] = ir.registerIcon(ConstantValue.LENS3_TEX);
        icons[3] = ir.registerIcon(ConstantValue.LENS4_TEX);
    }

}
