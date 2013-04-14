package ljdp.minechem.client.render.item;

import ljdp.minechem.client.ModelDecomposer;
import ljdp.minechem.common.utils.ConstantValue;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;

public class ItemDecomposerRenderer extends ItemMinechemRenderer {

    private ModelDecomposer model;

    public ItemDecomposerRenderer() {
        model = new ModelDecomposer();
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        String texture = ConstantValue.DECOMPOSER_MODEL_ON;
        GL11.glPushMatrix();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, FMLClientHandler.instance().getClient().renderEngine.getTexture(texture));
        if (type == ItemRenderType.ENTITY) {
            GL11.glTranslatef(0.0F, 1.4F, 0.0F);
            GL11.glRotatef(180f, 0f, 0f, 1f);
        } else if (type == ItemRenderType.EQUIPPED) {
            GL11.glTranslatef(0.5F, 1.6F, 0.0F);
            GL11.glRotatef(180f, -1f, 0f, 1f);
        } else {
            GL11.glTranslatef(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180f, 0f, 0f, 1f);
        }
        model.render(0.0625F);
        GL11.glPopMatrix();
    }

}
