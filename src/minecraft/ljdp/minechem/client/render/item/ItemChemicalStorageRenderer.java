package ljdp.minechem.client.render.item;

import ljdp.minechem.common.tileentity.TileEntityChemicalStorage;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;

public class ItemChemicalStorageRenderer extends ItemMinechemRenderer {

    TileEntityChemicalStorage chemicalStorage;

    public ItemChemicalStorageRenderer() {
        this.chemicalStorage = new TileEntityChemicalStorage();
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        TileEntityRenderer.instance.renderTileEntityAt(this.chemicalStorage, 0.0D, 0.0D, 0.0D, 0.0F);
    }
}
