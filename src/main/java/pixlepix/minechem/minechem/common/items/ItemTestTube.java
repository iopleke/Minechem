package pixlepix.minechem.minechem.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import pixlepix.minechem.minechem.common.ModMinechem;
import pixlepix.minechem.minechem.common.utils.ConstantValue;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemTestTube extends Item {

    public ItemTestTube(int id) {
        super(id);
        setUnlocalizedName("minechem.itemTestTube");
        setCreativeTab(ModMinechem.minechemTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        itemIcon = ir.registerIcon(ConstantValue.TESTTUBE_TEX);
    }

}
