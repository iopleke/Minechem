package ljdp.minechem.client.render;

import ljdp.minechem.common.entity.EntityTableOfElements;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumArt;
import net.minecraft.util.MathHelper;

public class RenderTableOfElements extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float angle, float scale) {
		this.renderPeriodicTable((EntityTableOfElements)entity, x, y, z, angle, scale);
	}
	
	public void renderPeriodicTable(EntityTableOfElements periodicTable, double x, double y, double z, float angle, float scale) {
		GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        this.loadTexture("/art/kz.png");
        EnumArt art = periodicTable.art;
        float artScale = 0.0625F;
        GL11.glScalef(artScale, artScale, artScale);
        this.renderArt(periodicTable, art.sizeX, art.sizeY, art.offsetX, art.offsetY);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
	}

	private void renderArt(EntityTableOfElements periodicTable, int sizeX, int sizeY, int offsetX, int offsetY) {
		float var6 = (float)(-sizeX) / 2.0F;
        float var7 = (float)(-sizeY) / 2.0F;
        float var8 = 0.5F;
        float var9 = 0.75F;
        float var10 = 0.8125F;
        float var11 = 0.0F;
        float var12 = 0.0625F;
        float var13 = 0.75F;
        float var14 = 0.8125F;
        float var15 = 0.001953125F;
        float var16 = 0.001953125F;
        float var17 = 0.7519531F;
        float var18 = 0.7519531F;
        float var19 = 0.0F;
        float var20 = 0.0625F;

        for (int var21 = 0; var21 < sizeX / 16; ++var21)
        {
            for (int var22 = 0; var22 < sizeY / 16; ++var22)
            {
                float var23 = var6 + (float)((var21 + 1) * 16);
                float var24 = var6 + (float)(var21 * 16);
                float var25 = var7 + (float)((var22 + 1) * 16);
                float var26 = var7 + (float)(var22 * 16);
                this.setLighting(periodicTable, (var23 + var24) / 2.0F, (var25 + var26) / 2.0F);
                float var27 = (float)(offsetX + sizeX - var21 * 16) / 256.0F;
                float var28 = (float)(offsetX + sizeX - (var21 + 1) * 16) / 256.0F;
                float var29 = (float)(offsetY + sizeY - var22 * 16) / 256.0F;
                float var30 = (float)(offsetY + sizeY - (var22 + 1) * 16) / 256.0F;
                Tessellator var31 = Tessellator.instance;
                var31.startDrawingQuads();
                var31.setNormal(0.0F, 0.0F, -1.0F);
                var31.addVertexWithUV((double)var23, (double)var26, (double)(-var8), (double)var28, (double)var29);
                var31.addVertexWithUV((double)var24, (double)var26, (double)(-var8), (double)var27, (double)var29);
                var31.addVertexWithUV((double)var24, (double)var25, (double)(-var8), (double)var27, (double)var30);
                var31.addVertexWithUV((double)var23, (double)var25, (double)(-var8), (double)var28, (double)var30);
                var31.setNormal(0.0F, 0.0F, 1.0F);
                var31.addVertexWithUV((double)var23, (double)var25, (double)var8, (double)var9, (double)var11);
                var31.addVertexWithUV((double)var24, (double)var25, (double)var8, (double)var10, (double)var11);
                var31.addVertexWithUV((double)var24, (double)var26, (double)var8, (double)var10, (double)var12);
                var31.addVertexWithUV((double)var23, (double)var26, (double)var8, (double)var9, (double)var12);
                var31.setNormal(0.0F, 1.0F, 0.0F);
                var31.addVertexWithUV((double)var23, (double)var25, (double)(-var8), (double)var13, (double)var15);
                var31.addVertexWithUV((double)var24, (double)var25, (double)(-var8), (double)var14, (double)var15);
                var31.addVertexWithUV((double)var24, (double)var25, (double)var8, (double)var14, (double)var16);
                var31.addVertexWithUV((double)var23, (double)var25, (double)var8, (double)var13, (double)var16);
                var31.setNormal(0.0F, -1.0F, 0.0F);
                var31.addVertexWithUV((double)var23, (double)var26, (double)var8, (double)var13, (double)var15);
                var31.addVertexWithUV((double)var24, (double)var26, (double)var8, (double)var14, (double)var15);
                var31.addVertexWithUV((double)var24, (double)var26, (double)(-var8), (double)var14, (double)var16);
                var31.addVertexWithUV((double)var23, (double)var26, (double)(-var8), (double)var13, (double)var16);
                var31.setNormal(-1.0F, 0.0F, 0.0F);
                var31.addVertexWithUV((double)var23, (double)var25, (double)var8, (double)var18, (double)var19);
                var31.addVertexWithUV((double)var23, (double)var26, (double)var8, (double)var18, (double)var20);
                var31.addVertexWithUV((double)var23, (double)var26, (double)(-var8), (double)var17, (double)var20);
                var31.addVertexWithUV((double)var23, (double)var25, (double)(-var8), (double)var17, (double)var19);
                var31.setNormal(1.0F, 0.0F, 0.0F);
                var31.addVertexWithUV((double)var24, (double)var25, (double)(-var8), (double)var18, (double)var19);
                var31.addVertexWithUV((double)var24, (double)var26, (double)(-var8), (double)var18, (double)var20);
                var31.addVertexWithUV((double)var24, (double)var26, (double)var8, (double)var17, (double)var20);
                var31.addVertexWithUV((double)var24, (double)var25, (double)var8, (double)var17, (double)var19);
                var31.draw();
            }
        }
	}

	private void setLighting(EntityTableOfElements periodicTable, float par2, float par3) {
		int var4 = MathHelper.floor_double(periodicTable.posX);
        int var5 = MathHelper.floor_double(periodicTable.posY + (double)(par3 / 16.0F));
        int var6 = MathHelper.floor_double(periodicTable.posZ);

        if (periodicTable.hangingDirection == 2)
        {
            var4 = MathHelper.floor_double(periodicTable.posX + (double)(par2 / 16.0F));
        }

        if (periodicTable.hangingDirection == 1)
        {
            var6 = MathHelper.floor_double(periodicTable.posZ - (double)(par2 / 16.0F));
        }

        if (periodicTable.hangingDirection == 0)
        {
            var4 = MathHelper.floor_double(periodicTable.posX - (double)(par2 / 16.0F));
        }

        if (periodicTable.hangingDirection == 3)
        {
            var6 = MathHelper.floor_double(periodicTable.posZ + (double)(par2 / 16.0F));
        }

        int var7 = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(var4, var5, var6, 0);
        int var8 = var7 % 65536;
        int var9 = var7 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var8, (float)var9);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
	}

}
