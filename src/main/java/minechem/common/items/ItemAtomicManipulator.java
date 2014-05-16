package minechem.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.common.ModMinechem;
import minechem.common.utils.ConstantValue;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemAtomicManipulator extends Item {

    public ItemAtomicManipulator(int id) {
        super(id);
        setCreativeTab(ModMinechem.CREATIVE_TAB);
        setUnlocalizedName("minechem.itemAtomicManipulator");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        itemIcon = ir.registerIcon(ConstantValue.ATOMIC_MANIPULATOR_TEX);
    }

}
