package minechem.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import minechem.Minechem;
import minechem.utils.MinechemHelper;
import minechem.utils.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class OpticalMicroscopeLens extends Item
{
    static final String[] descriptiveNames =
    {
        "item.name.concaveLens", "item.name.convexLens", "item.name.microscopeLens", "item.name.projectorLens"
    };
    @SideOnly(Side.CLIENT)
    private IIcon[] opticalMicroscopeLens;

    public OpticalMicroscopeLens()
    {
        super();
        setUnlocalizedName("opticalMicroscopeLens");
        setCreativeTab(Minechem.CREATIVE_TAB);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i)
    {
        return this.opticalMicroscopeLens[i];
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack)
    {
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
    public void registerIcons(IIconRegister ir)
    {
        this.opticalMicroscopeLens = new IIcon[4];
        this.opticalMicroscopeLens[0] = ir.registerIcon(Reference.LENS1_TEX);
        this.opticalMicroscopeLens[1] = ir.registerIcon(Reference.LENS2_TEX);
        this.opticalMicroscopeLens[2] = ir.registerIcon(Reference.LENS3_TEX);
        this.opticalMicroscopeLens[3] = ir.registerIcon(Reference.LENS4_TEX);
    }

}
