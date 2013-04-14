package ljdp.minechem.client.render;

import ljdp.minechem.common.CommonProxy;
import ljdp.minechem.common.entity.EntityTableOfElements;
import ljdp.minechem.common.utils.ConstantValue;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;

public class RenderTableOfElements extends Render {

	private float rotation = 0.0f;

	public void renderPic(EntityTableOfElements entity, double x, double y, double z, float rotation, float k) 
	{	
		// setting proper lighting
		// round down the coords
		int fX = MathHelper.floor_double(entity.posX);
        int fY = MathHelper.floor_double(entity.posY + (double)(y / 16.0F));
        int fZ = MathHelper.floor_double(entity.posZ);

		if (entity.hangingDirection == 2)
        {
            x -= 0.5 * entity.art.sizeX;
            z += 0.025;
            fX = MathHelper.floor_double(entity.posX + (double)(x / 16.0F));
        }

        if (entity.hangingDirection == 1)
        {
            z += 0.5 * entity.art.sizeX;
            x += 0.025;
            fZ = MathHelper.floor_double(entity.posZ - (double)(x / 16.0F));
        }

        if (entity.hangingDirection == 0)
        {
        	x += 0.5 * entity.art.sizeX;
        	z -= 0.025;
        	fX = MathHelper.floor_double(entity.posX - (double)(x / 16.0F));
        }

        if (entity.hangingDirection == 3)
        {
        	z -= 0.5 * entity.art.sizeX;
        	x -= 0.025;
        	fZ = MathHelper.floor_double(entity.posZ + (double)(x / 16.0F));
        }
        
        y -= 0.5 * entity.art.sizeY;
        
        // get the proper light value
        int light = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(fX, fY, fZ, 0);
        // get the x bit
        int lightX = light % 65536;
        // get the y bit
        int lightY = light / 65536;
        // set the lightmap texture coords
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lightX, (float)lightY);
        // ensure gl color is white
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        Tessellator tessellator = Tessellator.instance;
			GL11.glPushMatrix();
	        GL11.glTranslated(x, y, z);
	        GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);

	        float var11 = 0.0625F;
	        GL11.glScalef(var11, var11, var11);

	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_LIGHTING);

	        tessellator.startDrawing(GL11.GL_QUADS);

	        GL11.glColor3f(0.7734375f, 0.7734375f, 0.7734375f);
	        tessellator.addVertex(0, 0, 0);
	        tessellator.addVertex(0, entity.art.sizeY * 16, 0);
	        tessellator.addVertex(entity.art.sizeX * 16, entity.art.sizeY * 16, 0);
	        tessellator.addVertex(entity.art.sizeX * 16, 0, 0);

	        tessellator.draw();

	        GL11.glColor3f(1.0f, 1.0f, 1.0f);

	        GL11.glEnable(GL11.GL_TEXTURE_2D);

	        GL11.glPushMatrix();

	        GL11.glTranslated(((entity.art.sizeX * 16 / 2) - 8), (entity.art.sizeY * 16 / 2) - 8, -0.01);

	        GL11.glTranslated(8, 8, 0);
	        GL11.glRotatef(this.rotation+=3.0f, 0.0f, 0.0f, 1.0f);
	        GL11.glScaled(0.5, 0.5, 0.5);

	        FMLClientHandler.instance().getClient().renderEngine.bindTexture(ConstantValue.table_HEX);

	        tessellator.startDrawing(GL11.GL_QUADS);

	        tessellator.addVertexWithUV(-8, -8, 0, 1, 1);
	        tessellator.addVertexWithUV(-8, 8, 0, 1, 0);
	        tessellator.addVertexWithUV(8, 8, 0, 0, 0);
	        tessellator.addVertexWithUV(8, -8, 0, 0, 1);

	        tessellator.draw();

	        GL11.glEnable(GL11.GL_LIGHTING);

	        GL11.glPopMatrix();

	        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	        GL11.glPopMatrix();
						
			GL11.glPushMatrix();
	        GL11.glTranslated(x, y, z);
	        GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
	        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	        GL11.glEnable(GL11.GL_LIGHTING);

	        float var111 = 0.0625F;
	        GL11.glScalef(var111, var111, var111);

	        tessellator.startDrawing(GL11.GL_QUADS);

	        tessellator.addVertexWithUV(0, 0, 0, 1, 1);
	        tessellator.addVertexWithUV(0, entity.art.sizeX * 16, 0, 1, 0);
	        tessellator.addVertexWithUV(entity.art.sizeX* 16, entity.art.sizeY * 16, 0, 0, 0);
	        tessellator.addVertexWithUV(entity.art.sizeX * 16, 0, 0, 0, 1);

	        tessellator.draw();

	        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	        GL11.glPopMatrix();
		}
	

	public void doRender(Entity entity, double x, double y, double z, float j, float k) 
	{
		this.renderPic((EntityTableOfElements)entity, x, y, z, j, k);
	}
}
