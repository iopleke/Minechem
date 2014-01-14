package pixlepix.particlephysics.common.render;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.entity.LeafParticle;
import pixlepix.particlephysics.common.helper.ParticleRegistry;
import pixlepix.particlephysics.common.helper.Vector3;

public class RenderParticle extends Render
{
    /** Render instance */
    public static RenderParticle INSTANCE = new RenderParticle();

    @Override
    public void doRender(Entity entity, double i, double j, double k, float f, float f1)
    {
        doRenderBlock((BaseParticle) entity, i, j, k);
    }

    /** Renders a block as an Entity */
    public void doRenderBlock(BaseParticle entity, double i, double j, double k)
    {
        if (entity.isDead)
        {
            return;
        }

        shadowSize = 0;
        World world = entity.worldObj;
        BlockRenderInfo util = entity.getRenderIcon();
        Random rand=new Random();
        if(entity.effect==2&&rand.nextInt(2)==0){
        	util=new BlockRenderInfo(ParticleRegistry.icons.get(LeafParticle.class));
        }
        this.bindTexture(TextureMap.locationBlocksTexture);

        for (int iBase = 0; iBase < entity.iSize; ++iBase)
        {
            for (int jBase = 0; jBase < entity.jSize; ++jBase)
            {
                for (int kBase = 0; kBase < entity.kSize; ++kBase)
                {

                    util.min = new Vector3();

                    double remainX = entity.iSize - iBase;
                    double remainY = entity.jSize - jBase;
                    double remainZ = entity.kSize - kBase;

                    util.max = new Vector3((remainX > 1.0 ? 1.0 : remainX), (remainY > 1.0 ? 1.0 : remainY), (remainZ > 1.0 ? 1.0 : remainZ));

                    GL11.glPushMatrix();
                    GL11.glTranslatef((float) i, (float) j, (float) k);
                    GL11.glRotatef(entity.rotationX, 1, 0, 0);
                    GL11.glRotatef(entity.rotationY, 0, 1, 0);
                    GL11.glRotatef(entity.rotationZ, 0, 0, 1);
                    GL11.glTranslatef(iBase, jBase, kBase);

                    GL11.glScaled(0.25, 0.25, 0.25);

                    int lightX, lightY, lightZ;

                    lightX = (int) (Math.floor(entity.posX) + iBase);
                    lightY = (int) (Math.floor(entity.posY) + jBase);
                    lightZ = (int) (Math.floor(entity.posZ) + kBase);

                    GL11.glDisable(2896 /* GL_LIGHTING */);
                    renderBlock(util, world, new Vector3(lightX, lightY, lightZ));
                    GL11.glEnable(2896 /* GL_LIGHTING */);
                    
                    GL11.glPopMatrix();

                }
            }
        }
    }

    /** Renders a block at given location
     * 
     * @param blockInterface - class used to store info for the render process
     * @param world - world rendering in
     * @param x - position on x axis
     * @param y - position on y axis
     * @param z - position on z axis */
    public void renderBlock(BlockRenderInfo blockInterface, IBlockAccess world, Vector3 vec)
    {
        renderBlocks.renderMaxX = blockInterface.max.x;
        renderBlocks.renderMinX = blockInterface.min.x;
        renderBlocks.renderMaxY = blockInterface.max.y;
        renderBlocks.renderMinY = blockInterface.min.y;
        renderBlocks.renderMaxZ = blockInterface.max.z;
        renderBlocks.renderMinZ = blockInterface.min.z;
        renderBlocks.enableAO = false;

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        Block block = blockInterface.baseBlock;
        //TODO do a check for "should render side" to solve issue with transparent blocks. Mainly rendering water blocks next to each other
        if (block.shouldSideBeRendered(world, vec.intX(), vec.intY(), vec.intZ(), 0))
        {
            renderBlocks.renderFaceYNeg(block, 0, 0, 0, blockInterface.getBlockIconFromSideAndMetadata(0, blockInterface.meta));
        }
        if (block.shouldSideBeRendered(world, vec.intX(), vec.intY(), vec.intZ(), 1))
        {
            renderBlocks.renderFaceYPos(block, 0, 0, 0, blockInterface.getBlockIconFromSideAndMetadata(1, blockInterface.meta));
        }
        if (block.shouldSideBeRendered(world, vec.intX(), vec.intY(), vec.intZ(), 2))
        {
            renderBlocks.renderFaceZNeg(block, 0, 0, 0, blockInterface.getBlockIconFromSideAndMetadata(2, blockInterface.meta));
        }
        if (block.shouldSideBeRendered(world, vec.intX(), vec.intY(), vec.intZ(), 3))
        {
            renderBlocks.renderFaceZPos(block, 0, 0, 0, blockInterface.getBlockIconFromSideAndMetadata(3, blockInterface.meta));
        }
        if (block.shouldSideBeRendered(world, vec.intX(), vec.intY(), vec.intZ(), 4))
        {
            renderBlocks.renderFaceXNeg(block, 0, 0, 0, blockInterface.getBlockIconFromSideAndMetadata(4, blockInterface.meta));
        }
        if (block.shouldSideBeRendered(world, vec.intX(), vec.intY(), vec.intZ(), 5))
        {
            renderBlocks.renderFaceXPos(block, 0, 0, 0, blockInterface.getBlockIconFromSideAndMetadata(5, blockInterface.meta));
        }
        tessellator.draw();

    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation("particlephysics","textures/entity/coal.png");
    }

}