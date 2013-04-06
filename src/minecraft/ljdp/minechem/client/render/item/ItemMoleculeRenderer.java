package ljdp.minechem.client.render.item;

import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.common.items.ItemMolecule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class ItemMoleculeRenderer implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        if (type == ItemRenderType.INVENTORY)
            return true;
        if (type == ItemRenderType.EQUIPPED)
            return true;
        if (type == ItemRenderType.ENTITY)
            return true;
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        if (helper == ItemRendererHelper.ENTITY_BOBBING)
            return true;
        if (helper == ItemRendererHelper.ENTITY_ROTATION)
            return true;
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
        ItemMolecule item = (ItemMolecule) itemstack.getItem();
        Icon testtube = itemstack.getIconIndex();
        if (type == ItemRenderType.INVENTORY) {
            renderItemInInventory(type, itemstack, item.filledMolecule, item.render_pass1, item.render_pass2);
        } else if (type == ItemRenderType.EQUIPPED) {
            renderItemInEquipped(type, itemstack, testtube, item.render_pass1, item.render_pass2);
        } else {
            EntityItem entityItem = (EntityItem) data[1];
            if (entityItem.worldObj == null) {
                float angle = (Minecraft.getSystemTime() % 8000L) / 8000.0F * 360.0F;
                GL11.glPushMatrix();
                GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(-0.2F, -0.5F, 0.0F);
                renderItemAsEntity(type, itemstack, testtube, item.render_pass1, item.render_pass2);
                GL11.glPopMatrix();
            } else {
                renderItemAsEntity(type, itemstack, testtube, item.render_pass1, item.render_pass2);
            }
        }
    }

    private void renderItemInInventory(ItemRenderType type, ItemStack itemstack, Icon testtube, Icon pass1, Icon pass2) {
        EnumMolecule molecule = ItemMolecule.getMolecule(itemstack);
        GL11.glColor3f(molecule.red, molecule.green, molecule.blue);
        drawTexturedRectUV(type, 0, 0, 0, 16, 16, pass1);
        GL11.glColor3f(molecule.red2, molecule.green2, molecule.blue2);
        drawTexturedRectUV(type, 0, 0, 0, 16, 16, pass2);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        drawTexturedRectUV(type, 0, 0, 0, 16, 16, testtube);
    }

    private void renderItemInEquipped(ItemRenderType type, ItemStack itemstack, Icon testtube, Icon pass1, Icon pass2) {
        EnumMolecule molecule = ItemMolecule.getMolecule(itemstack);
        /*
         * float scale = 0.75F; GL11.glPushMatrix(); GL11.glScalef(scale, scale, scale); GL11.glTranslatef(1.2F, 1.1F, -0.25F); GL11.glRotatef(180.0F, 0.0F,
         * 0.0F, 1.0F); GL11.glColor3f(molecule.red, molecule.green, molecule.blue); drawTexturedRectUV(type, 0, 0, 0, 16, 16, pass1);
         * GL11.glColor3f(molecule.red2, molecule.green2, molecule.blue2); drawTexturedRectUV(type, 0, 0, 0, 16, 16, pass2); GL11.glColor3f(1.0F, 1.0F, 1.0F);
         * GL11.glTranslatef(0.0F, 0.0F, -0.001F); for (float i = 0.0F; i < .1F; i += .01F) { drawTexturedRectUV(type, 0, 0, i, 16, 16, testtube); }
         * GL11.glPopMatrix();
         */
        Tessellator tessellator = Tessellator.instance;
        GL11.glColor3f(molecule.red, molecule.green, molecule.blue);
        ItemRenderer.renderItemIn2D(tessellator, pass1.getMaxU(), pass1.getMinV(), pass1.getMinU(), pass1.getMaxV(), pass1.getSheetWidth(),
                pass1.getSheetHeight(), 0.0625F);
        GL11.glColor3f(molecule.red2, molecule.green2, molecule.blue2);
        ItemRenderer.renderItemIn2D(tessellator, pass2.getMaxU(), pass2.getMinV(), pass2.getMinU(), pass2.getMaxV(), pass2.getSheetWidth(),
                pass2.getSheetHeight(), 0.0625F);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        ItemRenderer.renderItemIn2D(tessellator, testtube.getMaxU(), testtube.getMinV(), testtube.getMinU(), testtube.getMaxV(),
                testtube.getSheetWidth(), testtube.getSheetHeight(), 0.0625F);
    }

    private void renderItemAsEntity(ItemRenderType type, ItemStack itemstack, Icon testtube, Icon pass1, Icon pass2) {
        EnumMolecule molecule = ItemMolecule.getMolecule(itemstack);
        GL11.glPushMatrix();
        GL11.glColor3f(molecule.red, molecule.green, molecule.blue);
        drawTextureIn3D(pass1);
        GL11.glColor3f(molecule.red2, molecule.green2, molecule.blue2);
        drawTextureIn3D(pass2);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        drawTextureIn3D(testtube);
        GL11.glPopMatrix();
    }

    private void drawTextureIn3D(Icon texture) {
        Tessellator tesselator = Tessellator.instance;
        float scale = 0.7F;
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        ItemRenderer.renderItemIn2D(tesselator, texture.getMaxU(), texture.getMinV(), texture.getMinU(), texture.getMaxV(),
                texture.getSheetWidth(), texture.getSheetHeight(), .0625F);
        GL11.glPopMatrix();
    }

    private void drawTexturedRectUV(ItemRenderType type, float x, float y, float z, float w, float h, Icon icon) {
        Tessellator tesselator = Tessellator.instance;
        tesselator.startDrawingQuads();
        tesselator.addVertexWithUV(x, y + h, z, icon.getMinU(), icon.getMaxV());
        tesselator.addVertexWithUV(x + w, y + h, z, icon.getMaxU(), icon.getMaxV());
        tesselator.addVertexWithUV(x + w, y, z, icon.getMaxU(), icon.getMinV());
        tesselator.addVertexWithUV(x, y, z, icon.getMinU(), icon.getMinV());
        tesselator.draw();
    }

}
