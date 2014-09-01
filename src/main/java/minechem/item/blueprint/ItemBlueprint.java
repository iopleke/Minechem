package minechem.item.blueprint;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import minechem.MinechemItemsRegistration;
import minechem.Minechem;
import minechem.utils.MinechemHelper;
import minechem.utils.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBlueprint extends Item
{

    public static final String[] names =
    {
        "item.name.blueprintFusion", "item.name.blueprintFission"
    };

    public ItemBlueprint()
    {
        super();
        setUnlocalizedName("minechem.itemBlueprint");
        setCreativeTab(Minechem.CREATIVE_TAB);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir)
    {
        itemIcon = ir.registerIcon(Reference.BLUEPRINT_TEX);
    }

    public static ItemStack createItemStackFromBlueprint(MinechemBlueprint blueprint)
    {
        return new ItemStack(MinechemItemsRegistration.blueprint, 1, blueprint.id);
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
    public String getItemStackDisplayName(ItemStack itemstack)
    {
        int metadata = itemstack.getItemDamage();
        return MinechemHelper.getLocalString(names[metadata]);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
    {
        for (int i = 0; i < names.length; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

}
