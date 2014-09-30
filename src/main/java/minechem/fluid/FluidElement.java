package minechem.fluid;

import minechem.MinechemItemsRegistration;
import minechem.item.element.ElementClassificationEnum;
import minechem.item.element.ElementEnum;
import minechem.utils.MinechemHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;

public class FluidElement extends Fluid implements IMinechemFluid
{

    public ElementEnum element;

    public FluidElement(ElementEnum element)
    {
        super(element.name());
        this.element = element;
        boolean isGas = this.element.roomState() == ElementClassificationEnum.gas;
        setViscosity(1000); // How fast the fluid flows.
        setGaseous(isGas);
        setDensity(isGas ? -10 : 10);
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

    @Override
    public String getLocalizedName(FluidStack stack)
    {
        return MinechemHelper.getLocalString("element.property.liquid") + " " + element.descriptiveName();
    }
}
