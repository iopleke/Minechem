package ljdp.minechem.client.render.item;

import ljdp.minechem.client.ModelDecomposer;
import ljdp.minechem.common.utils.ConstantValue;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

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
        switch(type.ordinal()){
        case 0: {
        	GL11.glTranslatef(0.5F, 1.6F, 0.0F);
            GL11.glRotatef(180f, -1f, 0f, 1f);
            return;
        }
        case 1:{
        	GL11.glTranslatef(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180f, 0f, 0f, 1f);
            return;
        }
            
        case 2: {
        	GL11.glTranslatef(0.0F, 0.7f, 0.0F);
            GL11.glRotatef(180f, 0f, 0f, 1f);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            return;
        }
        case 3: {
        	GL11.glTranslatef(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180f, 0f, 0f, 1f);
        }
        }
        model.render(0.0625F);
        GL11.glPopMatrix();
    }

}
