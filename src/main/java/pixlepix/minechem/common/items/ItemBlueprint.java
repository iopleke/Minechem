package pixlepix.minechem.common.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pixlepix.minechem.common.MinechemItems;
import pixlepix.minechem.common.ModMinechem;
import pixlepix.minechem.common.blueprint.MinechemBlueprint;
import pixlepix.minechem.common.utils.ConstantValue;
import pixlepix.minechem.common.utils.MinechemHelper;

import java.util.List;

public class ItemBlueprint extends Item {

    public static final String[] names = {"item.name.blueprintFusion", "item.name.blueprintFission"};

    public ItemBlueprint(int id) {
        super(id);
        setUnlocalizedName("minechem.itemBlueprint");
        setCreativeTab(ModMinechem.CREATIVE_TAB);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir) {
        itemIcon = ir.registerIcon(ConstantValue.BLUEPRINT_TEX);
    }

    public static ItemStack createItemStackFromBlueprint(MinechemBlueprint blueprint) {
        return new ItemStack(MinechemItems.blueprint, 1, blueprint.id);
    }

    public MinechemBlueprint getBlueprint(ItemStack itemstack) {
        int metadata = itemstack.getItemDamage();
        return MinechemBlueprint.blueprints.get(metadata);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer entityPlayer, List list, boolean par4) {
        MinechemBlueprint blueprint = getBlueprint(itemstack);
        if (blueprint != null) {
            String dimensions = String.format("%d x %d x %d", blueprint.xSize, blueprint.ySize, blueprint.zSize);
            list.add(dimensions);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + names[itemstack.getItemDamage()];
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack) {
        int metadata = itemstack.getItemDamage();
        return MinechemHelper.getLocalString(names[metadata]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int id, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < names.length; i++) {
            list.add(new ItemStack(id, 1, i));
        }
    }

}
