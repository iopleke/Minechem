package minechem.tileentity.chemicalstorage;

import minechem.item.ItemMinechemRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ItemChemicalStorageRenderer extends ItemMinechemRenderer
{

    TileEntityChemicalStorage chemicalStorage;

    public ItemChemicalStorageRenderer()
    {
        this.chemicalStorage = new TileEntityChemicalStorage();
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        TileEntityRenderer.instance.renderTileEntityAt(this.chemicalStorage, 0.0D, 0.0D, 0.0D, 0.0F);

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }
}
