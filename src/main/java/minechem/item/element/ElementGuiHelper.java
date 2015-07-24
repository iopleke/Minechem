package minechem.item.element;

import minechem.item.polytool.PolytoolGui;
import minechem.utils.MinechemUtil;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ElementGuiHelper
{
    int dist;
    double radians;
    ElementEnum element;
    ItemStack stack;

    public ElementGuiHelper(int notch, double radians, ElementEnum element)
    {
        this.dist = notch * 20;
        this.radians = radians;
        this.element = element;
        stack = MinechemUtil.chemicalToItemStack(element, 1);
    }

    public void draw(PolytoolGui gui, long ticks)
    {
        GL11.glPushMatrix();
        // Calculate displacement
        radians += .01;
        double rad = radians;
        // if(ticks>120){
        gui.drawItemStack(stack, (int) (80 + (Math.sin(rad) * dist)), (int) (42 + (Math.cos(rad) * dist)), "");
        /*
         * }else{ radians+=.04; int originX=88; int originY=50; int targetX=(int) (originX+(Math.sin(radians)*dist)); int targetY=(int) (originY+(Math.cos(radians)*dist));
         * 
         * int progressX=(int) (originX+((ticks/120)*(targetX-originX))); int progressY=(int) (originY+((ticks/120)*(targetY-originY))); gui.drawItemStack((ItemStack) ItemElement.createStackOf(element,1), progressX, progressX, ""); }
         */
        GL11.glPopMatrix();
    }

}
