package ljdp.minechem.client.render.item;

import ljdp.minechem.client.ModelMicroscope;
import ljdp.minechem.common.utils.ConstantValue;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class ItemMicroscopeRenderer extends ItemMinechemRenderer {

    private ModelMicroscope modelMicroscope;

    public ItemMicroscopeRenderer() {
        modelMicroscope = new ModelMicroscope();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
    	switch(type){
        case EQUIPPED: {
        return true;
        }
        case EQUIPPED_FIRST_PERSON:{
        	return true;
        }
            
        case INVENTORY:{
        	return true;
        }
            
        case ENTITY: {
        	return true;
        }
            
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
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        String texture = ConstantValue.MICROSCOPE_MODEL;
        GL11.glPushMatrix();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, FMLClientHandler.instance().getClient().renderEngine.getTexture(texture));
        switch(type){
        case EQUIPPED:
        	GL11.glTranslatef(0.5F, 1.6F, 0.0F);
            GL11.glRotatef(180f, -1f, 0f, 1f);
            break;
        }
        switch(type){
        case EQUIPPED_FIRST_PERSON:
        	GL11.glTranslatef(0.5F, 1.6F, 0.0F);
            GL11.glRotatef(180f, -1f, 0f, 1f);
            break;
        }
        switch(type){
        case INVENTORY:
        	GL11.glTranslatef(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180f, 0f, 0f, 1f);
            break;
        }switch(type){
        case ENTITY:
        	GL11.glTranslatef(0.0F, 0.7f, 0.0F);
            GL11.glRotatef(180f, 0f, 0f, 1f);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            break;
        }
        modelMicroscope.render(0.0625F);
        GL11.glPopMatrix();
    }

}
