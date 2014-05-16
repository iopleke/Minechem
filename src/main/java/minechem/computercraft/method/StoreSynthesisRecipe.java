package minechem.computercraft.method;

import minechem.api.recipe.SynthesisRecipe;
import minechem.common.recipe.SynthesisRecipeHandler;
import minechem.computercraft.ChemistryTurtleUpgradePeripherial;
import minechem.computercraft.ICCMethod;
import minechem.computercraft.IMinechemTurtlePeripheral;
import net.minecraft.item.ItemStack;
import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;

public class StoreSynthesisRecipe implements ICCMethod
{

    @Override
    public String getMethodName()
    {
        return "storeSynthesisRecipe";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception
    {
        int selectedSlot = turtle.getSelectedSlot();
        ItemStack selectedStack = turtle.getSlotContents(selectedSlot);
        SynthesisRecipe recipe = null;
        IMinechemTurtlePeripheral peripheral = ChemistryTurtleUpgradePeripherial.getMinechemPeripheral(turtle);
        if (selectedStack != null)
        {
            recipe = SynthesisRecipeHandler.instance.getRecipeFromOutput(selectedStack);
            peripheral.setSynthesisRecipe(recipe);
        }
        return new Object[]
        { recipe != null };
    }

}
