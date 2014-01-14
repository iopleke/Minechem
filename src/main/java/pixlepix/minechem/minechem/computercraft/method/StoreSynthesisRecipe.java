package pixlepix.minechem.minechem.computercraft.method;

import net.minecraft.item.ItemStack;
import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;
import pixlepix.minechem.minechem.api.recipe.SynthesisRecipe;
import pixlepix.minechem.minechem.common.recipe.SynthesisRecipeHandler;
import pixlepix.minechem.minechem.computercraft.ChemistryTurtleUpgradePeripherial;
import pixlepix.minechem.minechem.computercraft.ICCMethod;
import pixlepix.minechem.minechem.computercraft.IMinechemTurtlePeripheral;

public class StoreSynthesisRecipe implements ICCMethod {

    @Override
    public String getMethodName() {
        return "storeSynthesisRecipe";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception {
        int selectedSlot = turtle.getSelectedSlot();
        ItemStack selectedStack = turtle.getSlotContents(selectedSlot);
        SynthesisRecipe recipe = null;
        IMinechemTurtlePeripheral peripheral = ChemistryTurtleUpgradePeripherial.getMinechemPeripheral(turtle);
        if (selectedStack != null) {
            recipe = SynthesisRecipeHandler.instance.getRecipeFromOutput(selectedStack);
            peripheral.setSynthesisRecipe(recipe);
        }
        return new Object[] { recipe != null };
    }

}
