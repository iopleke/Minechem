package minechem.item.bucket;

import minechem.fluid.FluidElement;
import minechem.fluid.FluidMolecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.render.RenderingUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;


public class MinechemBucketRenderer implements IItemRenderer
{

    @Override
    public boolean handleRenderType(ItemStack itemstack, ItemRenderType type)
    {
        switch (type)
        {
            case ENTITY:
            case EQUIPPED:
            case EQUIPPED_FIRST_PERSON:
            case INVENTORY:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack itemstack, ItemRendererHelper helper)
    {
        switch (helper)
        {
            case ENTITY_BOBBING:
            case ENTITY_ROTATION:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data)
    {
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        MinechemBucketItem item = (MinechemBucketItem) itemstack.getItem();
        Fluid fluid = item.fluid;
        IIcon bucket = item.icons[0];
        IIcon pass = item.icons[1];

        switch (type)
        {
            case ENTITY:
                EntityItem entityItem = (EntityItem) data[1];
                if (entityItem.worldObj == null)
                {
                    float angle = (Minecraft.getSystemTime() % 8000L) / 8000.0F * 360.0F;
                    GL11.glPushMatrix();
                    GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(-0.2F, -0.5F, 0.0F);
                    renderItemAsEntity(bucket, pass, fluid);
                    GL11.glPopMatrix();
                } else
                {
                    renderItemAsEntity(bucket, pass, fluid);
                }
                break;
            case EQUIPPED_FIRST_PERSON:
            case EQUIPPED:
                Tessellator tessellator = Tessellator.instance;
                ItemRenderer.renderItemIn2D(tessellator, bucket.getMaxU(), bucket.getMinV(), bucket.getMinU(), bucket.getMaxV(), bucket.getIconWidth(), bucket.getIconHeight(), 0.0625F);
                colorFluid(fluid);
                ItemRenderer.renderItemIn2D(tessellator, pass.getMaxU(), pass.getMinV(), pass.getMinU(), pass.getMaxV(), pass.getIconWidth(), pass.getIconHeight(), 0.0625F);
                break;
            case INVENTORY:
                RenderingUtil.drawTexturedRectUV(type, 0, 0, 0, 16, 16, bucket);
                colorFluid(fluid);
                RenderingUtil.drawTexturedRectUV(type, 0, 0, 0, 16, 16, pass);
                break;
            default:
                break;

        }
    }

    private void colorFluid(Fluid fluid)
    {
        if (fluid instanceof FluidElement)
        {
            RenderingUtil.setColorForElement(((FluidElement) fluid).element);
        } else if (fluid instanceof FluidMolecule)
        {
            MoleculeEnum molecule = ((FluidMolecule) fluid).molecule;
            GL11.glColor3f(molecule.red, molecule.green, molecule.blue);
        }
    }

    private void renderItemAsEntity(IIcon bucket, IIcon pass, Fluid fluid)
    {
        GL11.glPushMatrix();
        RenderingUtil.drawTextureIn3D(bucket);
        colorFluid(fluid);
        RenderingUtil.drawTextureIn3D(pass);
        GL11.glPopMatrix();
    }
}
