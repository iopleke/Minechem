package ljdp.minechem.client.render;

import ljdp.minechem.common.entity.EntityTableOfElements;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

public class RenderTableOfElements extends Render {

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		renderTableOfElements((EntityTableOfElements)entity, d, d1, d2, f, f1);
	}
	
	private void blitTexture(EntityTableOfElements entityTable, int i, int j, int k, int l)
    {
		Double d1 = 64.0D;
		Double d2 = 32.0D;
		calculateBrightness(entityTable, 64.0F, 32.0F);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(d1, -d2, 1.0D, 0.0D, 1.0D);
	    tessellator.addVertexWithUV(-d1, -d2, 0.9D, 1.0D, 1.0D);
	    tessellator.addVertexWithUV(-d1, d2, 0.9D, 1.0D, 0.0D);
	    tessellator.addVertexWithUV(d1, d2, 0.9D, 0.0D, 0.0D);
	    tessellator.draw();
    }
	
	public void renderTableOfElements(EntityTableOfElements entityTable, double d, double d1, double d2, float f, float f1)
    {
        //rand.setSeed(187L);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(f, 0.0F, 1.0F, 0.0F);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        loadTexture("/minechem/tableofelements.png");
        float f2 = 0.0625F;
        GL11.glScalef(f2, f2, f2);
        blitTexture(entityTable, entityTable.tableSizeX, entityTable.tableSizeY, 0, 0);
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
    }
	
	private void calculateBrightness(EntityTableOfElements entityTable, float f, float f1)
    {
        int i = MathHelper.floor_double(entityTable.posX);
        int j = MathHelper.floor_double(entityTable.posY + (double)(f1 / 16F));
        int k = MathHelper.floor_double(entityTable.posZ);
        if (entityTable.direction == 0)
        {
            i = MathHelper.floor_double(entityTable.posX + (double)(f / 16F));
        }
        if (entityTable.direction == 1)
        {
            k = MathHelper.floor_double(entityTable.posZ - (double)(f / 16F));
        }
        if (entityTable.direction == 2)
        {
            i = MathHelper.floor_double(entityTable.posX - (double)(f / 16F));
        }
        if (entityTable.direction == 3)
        {
            k = MathHelper.floor_double(entityTable.posZ + (double)(f / 16F));
        }
        int l = renderManager.worldObj.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int i1 = l % 0x10000;
        int j1 = l / 0x10000;
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }

}
