package minechem.tileentity.decomposer;

import minechem.potion.PotionChemical;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class DecomposerFluidRecipe extends DecomposerRecipe
{

    public FluidStack inputFluid;

    public DecomposerFluidRecipe(FluidStack fluid, PotionChemical... chemicals)
    {
        super(chemicals);
        this.inputFluid = fluid;
    }

    public DecomposerFluidRecipe(String fluid, int amount, PotionChemical[] chemicals)
    {

        this(new FluidStack(FluidRegistry.getFluid(fluid), amount), chemicals);
    }

    public static void createAndAddFluidRecipeSafely(String fluid, int amount, PotionChemical... chemicals)
    {
        if (FluidRegistry.isFluidRegistered(fluid))
        {
            DecomposerRecipe.add(new DecomposerFluidRecipe(fluid, amount, chemicals));
        }
    }

    @Override
    public ItemStack getInput()
    {
        return new ItemStack(this.inputFluid.getFluid() != null ? this.inputFluid.getFluid().getBlock() : null, 1, 0);
    }

//    @Override
//    public ArrayList<PotionChemical> getOutput()
//    {
//        //return this.output;
//    	
//    }

//    @Override
//    public ArrayList<PotionChemical> getOutputRaw()
//    {
//        return this.output;
//    }

}
