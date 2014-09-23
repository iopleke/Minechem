package minechem.item;

import minechem.Minechem;
import minechem.utils.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAtomicManipulator extends Item
{

    public ItemAtomicManipulator()
    {
        setCreativeTab(Minechem.CREATIVE_TAB_ITEMS);
        setUnlocalizedName("itemAtomicManipulator");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir)
    {
        itemIcon = ir.registerIcon(Reference.ATOMIC_MANIPULATOR_TEX);
    }

}
