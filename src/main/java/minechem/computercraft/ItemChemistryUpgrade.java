package minechem.computercraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.common.ModMinechem;
import minechem.common.utils.ConstantValue;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;

public class ItemChemistryUpgrade extends Item {
    public static Icon icon;

    public ItemChemistryUpgrade(int id) {
        super(id);
        setCreativeTab(ModMinechem.CREATIVE_TAB);
        setUnlocalizedName("name.chemistryUpgrade");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        itemIcon = ir.registerIcon(ConstantValue.CHEMISTRY_UPGRADE_TEX);
        icon = itemIcon;
    }
}
