package minechem.item;

import minechem.common.ModMinechem;
import minechem.utils.Reference;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAtomicManipulator extends Item
{

    public ItemAtomicManipulator(int id)
    {
        super(id);
        setCreativeTab(ModMinechem.CREATIVE_TAB);
        setUnlocalizedName("minechem.itemAtomicManipulator");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        itemIcon = ir.registerIcon(Reference.ATOMIC_MANIPULATOR_TEX);
    }

}
