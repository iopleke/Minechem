package minechem.item.chemical;

import java.util.LinkedList;
import java.util.List;
import minechem.chemical.ChemicalBase;
import minechem.chemical.Element;
import minechem.helper.MathHelper;
import minechem.proxy.client.font.Font;
import minechem.proxy.client.render.ILayer;
import minechem.proxy.client.render.IconLayer;
import minechem.proxy.client.render.TextLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ChemicalItemRenderer implements IItemRenderer
{
    private final Font font = new Font(8);

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        ChemicalBase chemicalBase = ChemicalItem.getChemicalBase(item);
        if (chemicalBase == null)
        {
            return false;
        }
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
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        ChemicalBase chemicalBase = ChemicalItem.getChemicalBase(item);
        if (chemicalBase == null)
        {
            return false;
        }
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
    public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);

        ChemicalItem item = (ChemicalItem) itemStack.getItem();
        ChemicalBase chemicalBase = ChemicalItem.getChemicalBase(itemStack);
        List<ILayer> layers = new LinkedList<ILayer>();
        float duration = 1500;
        float t = Minecraft.getSystemTime() % duration;
        int frame;
        switch (chemicalBase.form)
        {
            case solid:
                layers.add(new IconLayer(item.dust, true));
                break;
            case liquid:
                layers.add(new IconLayer(item.tube, false));
                frame = (int) MathHelper.translateValue(t, 0, duration, 0, item.liquid.length);
                layers.add(new IconLayer(item.liquid[frame], true));
                break;
            case gas:
                layers.add(new IconLayer(item.tube, false));
                frame = (int) MathHelper.translateValue(t, 0, duration, 0, item.gas.length);
                layers.add(new IconLayer(item.gas[frame], true));
                break;
            case plasma:
                layers.add(new IconLayer(item.tube, false));
                frame = (int) MathHelper.translateValue(t, 0, duration, 0, item.plasma.length);
                layers.add(new IconLayer(item.plasma[frame], true));
                break;
        }
        if (chemicalBase.isElement())
        {
            layers.add(new TextLayer(((Element) chemicalBase).shortName, false, font));
        } else
        {
            layers.add(new IconLayer(item.moleculeSymbol, false));
        }
        switch (type)
        {
            case INVENTORY:
                renderItemInInventory(chemicalBase, layers.toArray(new ILayer[layers.size()]));
                break;
            case EQUIPPED_FIRST_PERSON:
            case EQUIPPED:
                renderItemInEquipped(chemicalBase, layers.toArray(new ILayer[layers.size()]));
                break;
            case ENTITY:
                EntityItem entityItem = (EntityItem) data[1];
                if (entityItem.worldObj == null)
                {
                    float angle = (Minecraft.getSystemTime() % 8000L) / 8000.0F * 360.0F;
                    GL11.glPushMatrix();
                    GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(-0.2F, -0.5F, 0.0F);
                    renderItemAsEntity(chemicalBase, layers.toArray(new ILayer[layers.size()]));
                    GL11.glPopMatrix();
                } else
                {
                    renderItemAsEntity(chemicalBase, layers.toArray(new ILayer[layers.size()]));
                }
                break;
            default:
                break;
        }

    }

    private void renderItemInInventory(ChemicalBase chemicalBase, ILayer... layers)
    {
        for (ILayer layer : layers)
        {
            layer.render(5, chemicalBase.getColour());
        }
    }

    private void renderItemInEquipped(ChemicalBase chemicalBase, ILayer... layers)
    {
        for (ILayer layer : layers)
        {
            layer.render2D(chemicalBase.getColour());
        }
    }

    private void renderItemAsEntity(ChemicalBase chemicalBase, ILayer... layers)
    {
        GL11.glPushMatrix();
        for (ILayer layer : layers)
        {
            layer.render3D(chemicalBase.getColour());
        }
        GL11.glPopMatrix();
    }
}
