package minechem.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemMinechemRenderer implements IItemRenderer
{

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type.ordinal())
        {
            case 0:
            {
                return true;
            }

            case 1:
            {
                return true;
            }

            case 2:
            {
                return true;
            }
            case 3:
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return helper == ItemRendererHelper.INVENTORY_BLOCK || helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
    }

}
