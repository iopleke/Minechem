package minechem.computercraft;

import minechem.common.ModMinechem;
import minechem.common.utils.Reference;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemChemistryUpgrade extends Item
{
    public static Icon icon;

    public ItemChemistryUpgrade(int id)
    {
        super(id);
        setCreativeTab(ModMinechem.CREATIVE_TAB);
        setUnlocalizedName("name.chemistryUpgrade");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        itemIcon = ir.registerIcon(Reference.CHEMISTRY_UPGRADE_TEX);
        icon = itemIcon;
    }
}
