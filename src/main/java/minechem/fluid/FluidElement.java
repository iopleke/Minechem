package minechem.fluid;

import minechem.MinechemItemGeneration;
import minechem.item.element.EnumElement;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class FluidElement extends Fluid implements IMinechemFluid
{

    public EnumElement element;

    public FluidElement(EnumElement element)
    {
        super(element.descriptiveName());
        this.element = element;
        setDensity(10); // How tick the fluid is, affects movement inside the liquid.
        setViscosity(1000); // How fast the fluid flows.
        FluidRegistry.registerFluid(this);

        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
            this.setFlowingIcon(Block.waterMoving.getIcon(0, 0));
            this.setStillIcon(Block.waterMoving.getIcon(0, 0));
        }
    }

    @Override
    public ItemStack getOutputStack()
    {
        return new ItemStack(MinechemItemGeneration.element, 0, element.ordinal());
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

        return (int) (0x100000 * red + 0x100 * green + blue);
    }

}
