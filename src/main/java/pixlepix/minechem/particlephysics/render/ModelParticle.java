package pixlepix.particlephysics.common.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelParticle extends ModelBase
{
	//This is broken
	//Don't ask
	//Thanks to minalien's tutorial
    private IModelCustom model;
     
    public ModelParticle()
    {
        model = AdvancedModelLoader.loadModel("/assets/particlephysics/particle.obj");
    }
    private void render()
    {
        model.renderAll();
    }
    
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        // Push a blank matrix onto the stack
        GL11.glPushMatrix();
     
        // Move the object into the correct position on the block (because the OBJ's origin is the center of the object)
        //GL11.glTranslatef((float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f);
     
        // Scale our object to about half-size in all directions (the OBJ file is a little large)
        GL11.glScalef(0.5f, 0.5f, 0.5f);
     
        // Bind the texture, so that OpenGL properly textures our block.
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation("particlephysics","textures/entity/coal.png"));
     
        // Render the object, using modelTutBox.renderAll();
        this.render();
     
        // Pop this matrix from the stack.
        GL11.glPopMatrix();
    }
}