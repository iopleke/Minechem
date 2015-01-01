package minechem.utils;

import java.util.ArrayList;
import minechem.item.ChemicalRoomStateEnum;
import minechem.item.element.ElementClassificationEnum;
import minechem.potion.PotionChemical;
import minechem.radiation.RadiationEnum;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IngredientStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class InputHelper
{
    public static ItemStack toStack(IItemStack iStack)
    {
        if (iStack == null)
        {
            return null;
        } else
        {
            Object internal = iStack.getInternal();
            if (internal == null || !(internal instanceof ItemStack))
            {
                MineTweakerAPI.getLogger().logError("Not a valid item stack: " + iStack);
            }

            return (ItemStack) internal;
        }
    }

    public static ItemStack[] toStacks(IItemStack[] iStack)
    {
        if (iStack == null)
        {
            return null;
        } else
        {
            ItemStack[] output = new ItemStack[iStack.length];
            for (int i = 0; i < iStack.length; i++)
            {
                output[i] = toStack(iStack[i]);
            }

            return output;
        }
    }

    public static FluidStack toFluid(ILiquidStack iStack)
    {
        if (iStack == null)
        {
            return null;
        } else
        {
            return FluidRegistry.getFluidStack(iStack.getName(), iStack.getAmount());
        }
    }

    public static FluidStack[] toFluids(ILiquidStack[] iStack)
    {
        FluidStack[] stack = new FluidStack[iStack.length];
        for (int i = 0; i < stack.length; i++)
        {
            stack[i] = toFluid(iStack[i]);
        }
        return stack;
    }

    public static ArrayList<ItemStack> getInputs(IIngredient input)
    {
        ArrayList<ItemStack> toAdd = new ArrayList<ItemStack>();
        if (input instanceof IOreDictEntry)
        {
            for (ItemStack inStack : OreDictionary.getOres(((IOreDictEntry) input).getName()))
            {
                ItemStack result = inStack.copy();
                result.stackSize = input.getAmount();
                toAdd.add(result);
            }

        } else if (input instanceof IItemStack)
        {
            toAdd.add(InputHelper.toStack((IItemStack) input));
        }
        return toAdd;
    }

    public static ItemStack getItem(IIngredient input)
    {
        if (input == null)
        {
            return null;
        }
        if (input instanceof IOreDictEntry)
        {
            ItemStack result = OreDictionary.getOres(((IOreDictEntry) input).getName()).get(0).copy();
            result.stackSize = input.getAmount();
            if (MinechemUtil.itemStackToChemical(result) != null)
            {
                return null;
            }
            return result;
        } else if (input instanceof IngredientStack)
        {
            ItemStack result = toStack(input.getItems().get(0));
            result.stackSize = input.getAmount();
            if (MinechemUtil.itemStackToChemical(result) != null)
            {
                return null;
            }
            return result;
        } else if (input instanceof IItemStack)
        {
            if (MinechemUtil.itemStackToChemical(toStack((IItemStack) input)) != null)
            {
                return null;
            }
            return toStack((IItemStack) input);
        }
        return null;
    }

    public static ItemStack getInput(IIngredient input)
    {
        if (input == null)
        {
            return null;
        }
        if (input instanceof IOreDictEntry)
        {
            ItemStack result = OreDictionary.getOres(((IOreDictEntry) input).getName()).get(0).copy();
            result.stackSize = input.getAmount();
            return result;
        } else if (input instanceof IngredientStack)
        {
            ItemStack result = toStack(input.getItems().get(0));
            result.stackSize = input.getAmount();
            return result;
        } else if (input instanceof IItemStack)
        {
            return toStack((IItemStack) input);
        }
        return null;
    }

    public static DecomposerRecipe[] getDecompArray(ArrayList<DecomposerRecipe> arrayList)
    {
        DecomposerRecipe[] result = new DecomposerRecipe[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++)
        {
            result[i] = arrayList.get(i);
        }
        return result;
    }

    public static ItemStack[] getItemArray(ArrayList<ItemStack> arrayList)
    {
        ItemStack[] result = new ItemStack[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++)
        {
            result[i] = arrayList.get(i);
        }
        return result;
    }

    public static PotionChemical[] getArray(ArrayList<PotionChemical> arrayList)
    {
        PotionChemical[] result = new PotionChemical[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++)
        {
            result[i] = arrayList.get(i);
        }
        return result;
    }

    public static PotionChemical getChemical(IIngredient ingredient)
    {
        if (ingredient instanceof IngredientStack)
        {
            for (IIngredient in : ingredient.getItems())
            {
                if (in instanceof IItemStack)
                {
                    ItemStack result = toStack((IItemStack) in);
                    result.stackSize = Math.max(1, ingredient.getAmount());
                    PotionChemical chemical = MinechemUtil.itemStackToChemical(result);
                    if (chemical != null)
                    {
                        return chemical;
                    }
                }
            }
            return null;
        } else if (ingredient instanceof IOreDictEntry)
        {

            ArrayList<ItemStack> results = (ArrayList<ItemStack>) OreDictionary.getOres(((IOreDictEntry) ingredient).getName()).clone();
            for (ItemStack res : results)
            {
                ItemStack result = res.copy();
                result.stackSize = ingredient.getAmount();
                PotionChemical chemical = MinechemUtil.itemStackToChemical(result);
                if (chemical != null)
                {
                    return chemical;
                }
            }

        } else if (ingredient instanceof IItemStack)
        {
            return MinechemUtil.itemStackToChemical(toStack((IItemStack) ingredient));
        }
        return null;
    }

    public static ArrayList<PotionChemical> getChemicals(IIngredient... array)
    {
        ArrayList<PotionChemical> output = new ArrayList<PotionChemical>();
        for (IIngredient outputStack : array)
        {
            PotionChemical out = getChemical(outputStack);
            if (out != null)
            {
                output.add(out);
            }
        }
        return output;
    }

    public static ChemicalRoomStateEnum getRoomState(String input)
    {
        for (ChemicalRoomStateEnum val : ChemicalRoomStateEnum.values())
        {
            if (val.stateName().equalsIgnoreCase(input))
            {
                return val;
            }
        }
        throw new IllegalArgumentException(input + " is not a valid Room State");
    }

    public static ElementClassificationEnum getClassification(String input)
    {
        for (ElementClassificationEnum val : ElementClassificationEnum.values())
        {
            if (val.className().equalsIgnoreCase(input))
            {
                return val;
            }
        }
        throw new IllegalArgumentException(input + " is not a valid element Classification");
    }

    public static RadiationEnum getRadiation(String input)
    {
        for (RadiationEnum val : RadiationEnum.values())
        {
            if (val.name().equalsIgnoreCase(input.replaceAll(" ", "")))
            {
                return val;
            }
        }
        throw new IllegalArgumentException(input + " is not a valid radioactivity Classification");
    }

}
