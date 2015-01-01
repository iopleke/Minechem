package minechem.minetweaker;

import java.util.ArrayList;
import minechem.utils.InputHelper;
import minechem.utils.MinechemFuelHandler;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minechem.Fuels")
public class Fuels
{

    @ZenMethod
    public static void addFuel(IIngredient input, int value)
    {
        ArrayList<ItemStack> toAdd = InputHelper.getInputs(input);
        for (ItemStack add : toAdd)
        {
            MineTweakerAPI.apply(new AddFuelAction(add, value));
        }
    }

    @ZenMethod
    public static void removeFuel(IIngredient input)
    {
        ArrayList<ItemStack> toRemove = InputHelper.getInputs(input);
        for (ItemStack remove : toRemove)
        {
            MineTweakerAPI.apply(new RemoveFuelAction(remove));
        }
    }

    // ######################
    // ### Action classes ###
    // ######################
    private static class AddFuelAction implements IUndoableAction
    {
        private final ItemStack stack;
        private final int value;
        private int oldValue;

        public AddFuelAction(ItemStack input, int burn)
        {
            stack = input;
            value = burn;
        }

        @Override
        public void apply()
        {
            oldValue = MinechemFuelHandler.addFuel(stack, value);
        }

        @Override
        public boolean canUndo()
        {
            return true;
        }

        @Override
        public void undo()
        {
            MinechemFuelHandler.removeFuel(stack);
            if (oldValue > 0)
            {
                MinechemFuelHandler.addFuel(stack, oldValue);
            }
        }

        @Override
        public String describe()
        {
            return "Adding fuel value for " + stack.getDisplayName();
        }

        @Override
        public String describeUndo()
        {
            return "Removing fuel value for " + stack.getDisplayName();
        }

        @Override
        public Object getOverrideKey()
        {
            return null;
        }
    }

    private static class RemoveFuelAction implements IUndoableAction
    {
        private final ItemStack stack;
        private int oldValue;

        public RemoveFuelAction(ItemStack input)
        {
            stack = input;
        }

        @Override
        public void apply()
        {
            oldValue = MinechemFuelHandler.removeFuel(stack);
        }

        @Override
        public boolean canUndo()
        {
            return true;
        }

        @Override
        public void undo()
        {
            if (oldValue > 0)
            {
                MinechemFuelHandler.addFuel(stack, oldValue);
            }
        }

        @Override
        public String describe()
        {
            return "Removing fuel value for " + stack.getDisplayName();
        }

        @Override
        public String describeUndo()
        {
            return "Restoring fuel value for " + stack.getDisplayName();
        }

        @Override
        public Object getOverrideKey()
        {
            return null;
        }
    }
}
