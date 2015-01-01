package minechem.item.polytool;

import java.util.ArrayList;
import minechem.item.element.ElementEnum;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class PolytoolInventoryRender implements IItemRenderer
{
    // Not used right now
    // Possible future feature
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
            case INVENTORY:
                return true;
            default:
                return false;
        }
    }

    private static RenderItem renderItem = new RenderItem();

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {

        return false;
    }

    public float getRed(ElementEnum element)
    {
        switch (element.classification())
        {
            case actinide:
            case halogen:
            case lanthanide:
            case nonmetal:
                return 1.0F;
            case otherMetal:
                return 0.5F;
            default:
                return 0;
        }
    }

    public float getGreen(ElementEnum element)
    {
        switch (element.classification())
        {
            case alkaliMetal:
            case halogen:
            case inertGas:
            case otherMetal:
            case semimetallic:
                return 1.0F;
            case transitionMetal:
            case nonmetal:
                return 0.5F;
            default:
                return 0F;
        }
    }

    public float getBlue(ElementEnum element)
    {
        switch (element.classification())
        {
            case alkalineEarthMetal:
            case inertGas:
            case lanthanide:
            case transitionMetal:
                return 1.0F;
            case semimetallic:
                return 0.5F;
            default:
                return 0F;
        }
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();
        ArrayList upgrades = PolytoolItem.getUpgrades(item);
        int count = 1;
        float red = 0F;
        float green = 0F;
        float blue = 0F;

        for (int i = 0; i < upgrades.size(); i++)
        {
            for (int j = 0; j < ((PolytoolUpgradeType) upgrades.get(i)).power; j++)
            {
                count++;
            }
        }
        for (int i = 0; i < upgrades.size(); i++)
        {
            for (int j = 0; j < ((PolytoolUpgradeType) upgrades.get(i)).power; j++)
            {
                ElementEnum element = ((PolytoolUpgradeType) upgrades.get(i)).getElement();
                red += (1 / count) * getRed(element);
                green += (1 / count) * getGreen(element);
                blue += (1 / count) * getBlue(element);
            }
        }
        GL11.glColor3f(red, green, blue);

        // Get icon index for the texture
        IIcon icon = item.getIconIndex();
        // Use vanilla code to render the icon in a 16x16 square of inventory slot
        renderItem.renderIcon(0, 0, icon, 16, 16);

        GL11.glPopMatrix();

    }

}
