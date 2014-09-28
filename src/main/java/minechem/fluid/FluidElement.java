package minechem.fluid;

import minechem.MinechemItemsRegistration;
import minechem.item.element.ElementEnum;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.awt.Color;

public class FluidElement extends Fluid implements IMinechemFluid
{

    public ElementEnum element;

    public FluidElement(ElementEnum element)
    {
        super(element.name());
        this.element = element;
        setDensity(10); // How tick the fluid is, affects movement inside the liquid.
        setViscosity(1000); // How fast the fluid flows.
        FluidRegistry.registerFluid(this);
    }

    @Override
    public ItemStack getOutputStack()
    {
        return new ItemStack(MinechemItemsRegistration.element, 0, element.ordinal());
    }

    @Override
    public int getColor()
    {
        float red = 0.0F;
        float blue = 0.0F;
        float green = 0.0F;
        switch (element.classification())
        {
        case actinide:
            red = 1;
            break;
        case alkaliMetal:
            green = 1;
            break;
        case alkalineEarthMetal:
            blue = 1;
            break;
        case halogen:
            red = 1;
            green = 1;
            break;
        case inertGas:
            green = 1;
            blue = 1;
            break;
        case lanthanide:
            red = 1;
            blue = 1;
            break;
        case nonmetal:
            red = 1;
            green = 0.5F;
            break;
        case otherMetal:
            red = 0.5F;
            green = 1;
            break;
        case semimetallic:
            green = 1;
            blue = 0.5F;
            break;
        case transitionMetal:
            green = 0.5F;
            blue = 1;
            break;
        default:
            break;
        }

        return new Color(red, green ,blue).getRGB();
    }

}
