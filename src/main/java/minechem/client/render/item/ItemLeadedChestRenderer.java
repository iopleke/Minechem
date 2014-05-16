package minechem.client.render.item;

import minechem.common.tileentity.TileEntityLeadedChest;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ItemLeadedChestRenderer extends ItemMinechemRenderer {

    TileEntityLeadedChest leadedChest;

    public ItemLeadedChestRenderer() {
        this.leadedChest = new TileEntityLeadedChest();
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        TileEntityRenderer.instance.renderTileEntityAt(this.leadedChest, 0.0D, 0.0D, 0.0D, 0.0F);

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }
}
