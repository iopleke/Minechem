package minechem.item;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;

public interface IOverlay
{
    public void renderOverlay(FontRenderer fontRenderer, TextureManager textureManager, ItemStack itemStack, int left, int top, float z);
}
