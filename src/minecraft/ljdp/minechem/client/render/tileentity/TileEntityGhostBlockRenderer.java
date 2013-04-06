package ljdp.minechem.client.render.tileentity;

import java.util.HashMap;
import java.util.Random;

import ljdp.minechem.common.blueprint.BlueprintBlock;
import ljdp.minechem.common.blueprint.MinechemBlueprint;
import ljdp.minechem.common.tileentity.TileEntityGhostBlock;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGhostBlockRenderer extends TileEntitySpecialRenderer {

    private final RenderBlocks renderBlocks = new RenderBlocks();
    private final Random random = new Random();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float scale) {
        BlueprintBlock blueprintBlock = getBlueprintBlock(tileEntity);
        if (blueprintBlock == null)
            return;
        float alpha = random.nextFloat();

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        // GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_COLOR);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        renderBlocks.blockAccess = tileEntity.worldObj;
        renderBlocks.renderBlockAsItem(blueprintBlock.block, blueprintBlock.metadata, alpha);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
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
