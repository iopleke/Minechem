package minechem.common.items;

import java.util.List;

import minechem.common.MinechemItems;
import minechem.common.ModMinechem;
import minechem.common.blueprint.MinechemBlueprint;
import minechem.common.utils.ConstantValue;
import minechem.common.utils.MinechemHelper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlueprint extends Item
{

    public static final String[] names =
    { "item.name.blueprintFusion", "item.name.blueprintFission" };

    public ItemBlueprint(int id)
    {
        super(id);
        setUnlocalizedName("minechem.itemBlueprint");
        setCreativeTab(ModMinechem.CREATIVE_TAB);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        itemIcon = ir.registerIcon(ConstantValue.BLUEPRINT_TEX);
    }

    public static ItemStack createItemStackFromBlueprint(MinechemBlueprint blueprint)
    {
        return new ItemStack(MinechemItems.blueprint, 1, blueprint.id);
    }

    public MinechemBlueprint getBlueprint(ItemStack itemstack)
    {
        int metadata = itemstack.getItemDamage();
        return MinechemBlueprint.blueprints.get(metadata);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer entityPlayer, List list, boolean par4)
    {
        MinechemBlueprint blueprint = getBlueprint(itemstack);
        if (blueprint != null)
        {
            String dimensions = String.format("%d x %d x %d", blueprint.xSize, blueprint.ySize, blueprint.zSize);
            list.add(dimensions);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return getUnlocalizedName() + "." + names[itemstack.getItemDamage()];
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
        int metadata = itemstack.getItemDamage();
        return MinechemHelper.getLocalString(names[metadata]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int id, CreativeTabs creativeTabs, List list)
    {
        for (int i = 0; i < names.length; i++)
        {
            list.add(new ItemStack(id, 1, i));
        }
    }

}
