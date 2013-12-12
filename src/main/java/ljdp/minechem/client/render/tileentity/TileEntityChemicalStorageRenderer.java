package ljdp.minechem.client.render.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ljdp.minechem.common.tileentity.TileEntityChemicalStorage;
import ljdp.minechem.common.utils.ConstantValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class TileEntityChemicalStorageRenderer extends TileEntitySpecialRenderer {
    /** The normal small chest model. */
    private ModelChest chestModel = new ModelChest();

    public TileEntityChemicalStorageRenderer() {

    }

    /**
     * Renders the TileEntity for the chest at a position.
     */
    public void renderTileEntityChestAt(TileEntityChemicalStorage chemicalStorage, double par2, double par4, double par6, float par8) {
        int var9;

        if ((chemicalStorage.worldObj==null)) {
            var9 = 0;
        } else {
            Block var10 = chemicalStorage.getBlockType();
            var9 = chemicalStorage.getBlockMetadata();

            if (var10 != null && var9 == 0) {
                //((BlockChest) var10).unifyAdjacentChests(chemicalStorage.getWorldObj(), chemicalStorage.xCoord, chemicalStorage.yCoord, chemicalStorage.zCoord);
                var9 = chemicalStorage.getBlockMetadata();
            }

            //chemicalStorage.checkForAdjacentChests();
        }

        //if (chemicalStorage.adjacentChestZNeg == null && chemicalStorage.adjacentChestXNeg == null) {
            ModelChest var14 = this.chestModel;

            bindTexture(new ResourceLocation(ConstantValue.MOD_ID,ConstantValue.CHEMICAL_STORAGE_MODEL));

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) par2, (float) par4 + 1.0F, (float) par6 + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            short var11 = 0;

            if (var9 == 2) {
                var11 = 180;
            }

            if (var9 == 3) {
                var11 = 0;
            }

            if (var9 == 4) {
                var11 = 90;
            }

            if (var9 == 5) {
                var11 = -90;
            }

            if (var9 == 2 && chemicalStorage.adjacentChestXPos != null) {
                GL11.glTranslatef(1.0F, 0.0F, 0.0F);
            }

            if (var9 == 5 && chemicalStorage.adjacentChestZPosition != null) {
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);
            }

            GL11.glRotatef((float) var11, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            float var12 = chemicalStorage.prevLidAngle + (chemicalStorage.lidAngle - chemicalStorage.prevLidAngle) * par8;
            float var13;


            var12 = 1.0F - var12;
            var12 = 1.0F - var12 * var12 * var12;
            var14.chestLid.rotateAngleX = -(var12 * (float) Math.PI / 2.0F);
            var14.renderAll();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
       // }
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
        this.renderTileEntityChestAt((TileEntityChemicalStorage) par1TileEntity, par2, par4, par6, par8);
    }
}
