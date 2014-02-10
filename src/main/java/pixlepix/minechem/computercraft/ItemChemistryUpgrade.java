package pixlepix.minechem.computercraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import pixlepix.minechem.common.ModMinechem;
import pixlepix.minechem.common.utils.ConstantValue;

public class ItemChemistryUpgrade extends Item {
    public static Icon icon;

    public ItemChemistryUpgrade(int id) {
        super(id);
        setCreativeTab(ModMinechem.minechemTab);
        setUnlocalizedName("name.chemistryUpgrade");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        itemIcon = ir.registerIcon(ConstantValue.CHEMISTRY_UPGRADE_TEX);
        icon = itemIcon;
    }
}
