package ljdp.minechem.client.render.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class ItemMinechemRenderer implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        switch(type){
        case EQUIPPED:
            return true;
        }
        switch(type){
        case EQUIPPED_FIRST_PERSON:
            return true;
        }
        switch(type){
        case INVENTORY:
            return true;
        }switch(type){
        case ENTITY:
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        if (helper == ItemRendererHelper.INVENTORY_BLOCK)
            return true;
        if (helper == ItemRendererHelper.ENTITY_BOBBING)
            return true;
        if (helper == ItemRendererHelper.ENTITY_ROTATION)
            return true;
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {}

}
