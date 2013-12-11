package ljdp.minechem.common.items;

import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.utils.ConstantValue;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAtomicManipulator extends Item {

    public ItemAtomicManipulator(int id) {
        super(id);
        setCreativeTab(ModMinechem.minechemTab);
        setUnlocalizedName("minechem.itemAtomicManipulator");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        itemIcon = ir.registerIcon(ConstantValue.ATOMIC_MANIPULATOR_TEX);
    }

}
