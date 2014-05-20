package minechem.tileentity.decomposer;

import java.util.ArrayList;

import minechem.potion.Chemical;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class DecomposerFluidRecipe extends DecomposerRecipe
{

    public FluidStack inputFluid;

    public DecomposerFluidRecipe(FluidStack fluid, Chemical... chemicals)
    {
        super(chemicals);
        this.inputFluid = fluid;
    }

    public DecomposerFluidRecipe(String fluid, int amount, Chemical[] chemicals)
    {

        this(new FluidStack(FluidRegistry.getFluid(fluid), amount), chemicals);
    }

    public static void createAndAddFluidRecipeSafely(String fluid, int amount, Chemical... chemicals)
    {
        if (FluidRegistry.isFluidRegistered(fluid))
        {
            DecomposerRecipe.add(new DecomposerFluidRecipe(fluid, amount, chemicals));
        }
    }

    @Override
    public ItemStack getInput()
    {
        return new ItemStack(this.inputFluid.getFluid() != null ? this.inputFluid.getFluid().getBlockID() : null, 1, 0);
    }

    @Override
    public ArrayList<Chemical> getOutput()
    {
        return this.output;
    }

    @Override
    public ArrayList<Chemical> getOutputRaw()
    {
        return this.output;
    }

}
