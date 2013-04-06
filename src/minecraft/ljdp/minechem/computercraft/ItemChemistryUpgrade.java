package ljdp.minechem.computercraft;

import ljdp.minechem.common.utils.ConstantValue;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.ComputerCraftAPI;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;

public class ItemChemistryUpgrade extends Item {
    public static Icon icon;

    public ItemChemistryUpgrade(int id) {
        super(id);
        setCreativeTab(ComputerCraftAPI.getCreativeTab());
        setUnlocalizedName("name.chemistryUpgrade");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister ir) {
        iconIndex = ir.registerIcon(ConstantValue.CHEMISTRY_UPGRADE_TEX);
        icon = iconIndex;
    }
}
