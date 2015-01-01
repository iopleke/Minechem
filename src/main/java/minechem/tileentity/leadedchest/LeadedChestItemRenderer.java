package minechem.tileentity.leadedchest;

import minechem.item.ItemMinechemRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class LeadedChestItemRenderer extends ItemMinechemRenderer
{

    LeadedChestTileEntity leadedChest;

    public LeadedChestItemRenderer()
    {
        this.leadedChest = new LeadedChestTileEntity();
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        TileEntityRendererDispatcher.instance.renderTileEntityAt(this.leadedChest, 0.0D, 0.0D, 0.0D, 0.0F);

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }
}
