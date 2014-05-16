package minechem.common.polytool;

import minechem.api.core.EnumElement;
import minechem.common.items.ItemElement;
import net.minecraft.item.ItemStack;

public class GuiElementHelper
{
    int dist;
    double radians;
    EnumElement element;

    public GuiElementHelper(int notch, double radians, EnumElement element)
    {
        this.dist = notch * 20;
        this.radians = radians;
        this.element = element;
    }

    public void draw(GuiPolytool gui, long ticks)
    {
        // Calculate displacement
        radians += .01;
        double rad = radians;
        // if(ticks>120){
        gui.drawItemStack((ItemStack) ItemElement.createStackOf(element, 1), (int) (80 + Math.sin(rad) * dist), (int) (42 + Math.cos(rad) * dist), "");
        /* }else{ radians+=.04; int originX=88; int originY=50; int targetX=(int) (originX+(Math.sin(radians)*dist)); int targetY=(int) (originY+(Math.cos(radians)*dist));
         * 
         * int progressX=(int) (originX+((ticks/120)*(targetX-originX))); int progressY=(int) (originY+((ticks/120)*(targetY-originY))); gui.drawItemStack((ItemStack) ItemElement.createStackOf(element,1), progressX, progressX, ""); } */
    }

}
