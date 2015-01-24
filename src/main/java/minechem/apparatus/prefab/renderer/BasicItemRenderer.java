package minechem.apparatus.prefab.renderer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class BasicItemRenderer implements IItemRenderer
{
    TileEntitySpecialRenderer tesr;
    private final TileEntity tileEntity;

    public BasicItemRenderer(TileEntitySpecialRenderer tesr, TileEntity tileEntity)
    {
        this.tesr = tesr;
        this.tileEntity = tileEntity;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        if (type == IItemRenderer.ItemRenderType.ENTITY)
        {
            GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
        }
        this.tesr.renderTileEntityAt(this.tileEntity, 0.0D, 0.0D, 0.0D, 0.0625F);
    }
}
