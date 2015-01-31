package minechem.registry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.reference.Compendium;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabRegistry
{
    public static CreativeTabs TAB_PRIMARY = new CreativeTab(Compendium.Naming.name, Items.fermented_spider_eye);

    /**
     * Better implementation of the CreativeTab which allows {@link net.minecraft.item.ItemStack} to be passed
     * Thus making it possible to add metaData to the Item
     */
    private static class CreativeTab extends CreativeTabs
    {
        private ItemStack iconItemStack;

        public CreativeTab(String label, Item iconItem)
        {
            this(label, new ItemStack(iconItem));
        }

        public CreativeTab(String label, Item iconItem, int meta)
        {
            this(label, new ItemStack(iconItem, 1, meta));
        }

        public CreativeTab(String label, ItemStack iconItemStack)
        {
            super(label);
            this.iconItemStack = iconItemStack;
        }

        @SideOnly(Side.CLIENT)
        @Override
        public Item getTabIconItem()
        {
            return iconItemStack.getItem();
        }

        @SideOnly(Side.CLIENT)
        @Override
        public ItemStack getIconItemStack()
        {
            return this.iconItemStack;
        }
    }
}
