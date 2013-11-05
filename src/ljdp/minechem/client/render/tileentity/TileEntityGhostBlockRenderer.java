package ljdp.minechem.client.render.tileentity;

import java.util.HashMap;
import java.util.Random;

import ljdp.minechem.common.blueprint.BlueprintBlock;
import ljdp.minechem.common.blueprint.MinechemBlueprint;
import ljdp.minechem.common.tileentity.TileEntityGhostBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityGhostBlockRenderer extends TileEntitySpecialRenderer {

    private final RenderBlocks renderBlocks = new RenderBlocks();
    private final Random random = new Random();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale) {
        BlueprintBlock blueprintBlock = getBlueprintBlock(tileEntity);
        if (blueprintBlock == null){
            return;
        }
        /*float alpha = random.nextFloat();

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        // GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_COLOR);
        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, 1-GL11.GL_DST_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        renderBlocks.blockAccess = tileEntity.worldObj;
        renderBlocks.renderBlockAsItem(blueprintBlock.block, blueprintBlock.metadata, alpha);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
        */

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        Block block = blueprintBlock.block;
        World world=tileEntity.worldObj;
        System.out.println(block);
        renderBlocks.renderMaxX = 1;
        renderBlocks.renderMinX = 0;
        renderBlocks.renderMaxY = 1;
        renderBlocks.renderMinY = 0;
        renderBlocks.renderMaxZ =1;
        renderBlocks.renderMinZ = 0;
            renderBlocks.renderFaceYNeg(block, 0,0,0, block.getIcon(0, blueprintBlock.metadata));
        
            renderBlocks.renderFaceYPos(block, 0,0,0, block.getIcon(1, blueprintBlock.metadata));
        
            renderBlocks.renderFaceZNeg(block, 0,0,0, block.getIcon(2, blueprintBlock.metadata));
       
            renderBlocks.renderFaceZPos(block, 0,0,0, block.getIcon(3, blueprintBlock.metadata));
            renderBlocks.renderFaceXNeg(block, 0,0,0, block.getIcon(4, blueprintBlock.metadata));
        
        
            renderBlocks.renderFaceXPos(block, 0,0,0, block.getIcon(5, blueprintBlock.metadata));
        
        tessellator.draw();

    }
    private BlueprintBlock getBlueprintBlock(TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityGhostBlock) {
            TileEntityGhostBlock ghostBlock = (TileEntityGhostBlock) tileEntity;
            MinechemBlueprint blueprint = ghostBlock.getBlueprint();
            if (blueprint != null) {
                int blockID = ghostBlock.getBlockID();
                HashMap<Integer, BlueprintBlock> blockLookup = blueprint.getBlockLookup();
                return blockLookup.get(blockID);
            }
        }
        return null;
    }

}
