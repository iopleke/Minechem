package minechem.utils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import minechem.tileentity.decomposer.DecomposerRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipe
{

    public static Map<MapKey, Recipe> recipes = new LinkedHashMap<MapKey, Recipe>();

    public static Map<ItemStack, ItemStack> smelting;
    public ItemStack output;
    public ItemStack[] inStacks;
    private int depth;

    private static final int MAXDEPTH = 20;

    @SuppressWarnings(
            {
                "unchecked", "rawtypes"
            })
    @Optional.Method(modid = "RotaryCraft")
    public static List getRotaryRecipes()
    {
        try
        {
            Class worktable = Class.forName("Reika.RotaryCraft.Auxiliary.WorktableRecipes");
            Method instance = worktable.getMethod("getInstance");
            Method list = worktable.getMethod("getRecipeListCopy");
            Class config = Class.forName("Reika.RotaryCraft.Registry.ConfigRegistry");
            Method state = config.getMethod("getState");
            boolean add = !(Boolean) state.invoke(Enum.valueOf(config, "TABLEMACHINES"));
            if (add)
            {
                return (List) list.invoke(instance.invoke(null));
            }
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        } catch (SecurityException e)
        {
            e.printStackTrace();
        } catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings(
            {
                "unchecked", "rawtypes"
            })
    @Optional.Method(modid = "Railcraft")
    public static List getRailcraftRecipes()
    {
        try
        {
            Class rollingMachine = Class.forName("mods.railcraft.common.util.crafting.RollingMachineCraftingManager");
            Method instance = rollingMachine.getMethod("getInstance");
            Method list = rollingMachine.getMethod("getRecipeList");
            return (List) list.invoke(instance.invoke(null));
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        } catch (SecurityException e)
        {
            e.printStackTrace();
        } catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings(
            {
                "unchecked", "rawtypes"
            })
    public static void init()
    {
        Map<MapKey, ArrayList<Recipe>> preCullRecipes = new Hashtable<MapKey, ArrayList<Recipe>>();
        recipes = new LinkedHashMap<MapKey, Recipe>();
        smelting = FurnaceRecipes.smelting().getSmeltingList();
        List craftingRecipes = CraftingManager.getInstance().getRecipeList();
        if (Loader.isModLoaded("RotaryCraft"))
        {
            List add = getRotaryRecipes();
            if (add != null)
            {
                craftingRecipes.addAll(add);
            }
        }
        if (Loader.isModLoaded("Railcaft"))
        {
            List add = getRailcraftRecipes();
            if (add != null)
            {
                craftingRecipes.addAll(add);
            }
        }
        for (Object recipe : craftingRecipes)
        {
            if (recipe instanceof IRecipe)
            {
                ItemStack input = ((IRecipe) recipe).getRecipeOutput();
                if (!invalidStack(input))
                {
                    LogHelper.debug("Adding recipe for " + input.toString());
                    ItemStack[] components = null;

                    if (recipe instanceof ShapelessOreRecipe && ((ShapelessOreRecipe) recipe).getInput().size() > 0)
                    {
                        ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
                        for (Object o : ((ShapelessOreRecipe) recipe).getInput())
                        {
                            if (o instanceof ItemStack)
                            {
                                inputs.add((ItemStack) o);
                            }
                        }
                        components = inputs.toArray(new ItemStack[inputs.size()]);
                    } else if (recipe instanceof ShapedOreRecipe)
                    {
                        ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
                        for (Object o : ((ShapedOreRecipe) recipe).getInput())
                        {

                            if (o instanceof ItemStack)
                            {
                                inputs.add((ItemStack) o);
                            } else if (o instanceof String)
                            {
                                inputs.add(OreDictionary.getOres((String) o).get(0));
                            } else if (o instanceof ArrayList && !((ArrayList) o).isEmpty())
                            {
                                inputs.add((ItemStack) ((ArrayList) o).get(0));
                            }
                        }
                        components = inputs.toArray(new ItemStack[inputs.size()]);

                    } else if (recipe instanceof ShapelessRecipes && ((ShapelessRecipes) recipe).recipeItems.toArray() instanceof ItemStack[])
                    {
                        components = (ItemStack[]) ((ShapelessRecipes) recipe).recipeItems.toArray();
                    } else if (recipe instanceof ShapedRecipes)
                    {
                        components = ((ShapedRecipes) recipe).recipeItems;
                    }

                    MapKey key = MapKey.getKey(input);
                    if (components != null && key != null)
                    {
                        boolean badRecipe = false;
                        for (ItemStack component : components)
                        {
                            if (component != null && (component.getItem() == null || component.isItemEqual(input) || component.stackSize < 1))
                            {
                                badRecipe = true;
                            }
                        }
                        if (!badRecipe)
                        {
                            Recipe addRecipe = new Recipe(input, components);
                            addPreCullRecipe(key, addRecipe, preCullRecipes);
                        }
                    }
                }
            }
        }
        for (ItemStack input : smelting.keySet())
        {
            ItemStack output = smelting.get(input);
            if (invalidStack(input) || invalidStack(output))
            {
                continue;
            }
            MapKey key = MapKey.getKey(output);
            if (key != null)
            {
                Recipe addRecipe = new Recipe(output, new ItemStack[]
                {
                    input
                });
                LogHelper.debug("Adding Smelting recipe for " + output.toString());
                addPreCullRecipe(key, addRecipe, preCullRecipes);
            }
        }

        for (Map.Entry<MapKey, ArrayList<Recipe>> entry : preCullRecipes.entrySet())
        {
            if (entry.getValue() != null && entry.getValue().size() > 0)
            {
                LogHelper.debug("Culling recipe for " + entry.getValue().get(0).output.toString());
            }
            int depth = cullRecipes(entry, preCullRecipes);
            if (entry.getValue().size() == 1)
            {
                Recipe addRecipe = entry.getValue().get(0);
                addRecipe.depth = depth;
                recipes.put(entry.getKey(), addRecipe);
            }
        }
    }

    private static boolean invalidStack(ItemStack stack)
    {
        return stack == null || stack.getItem() == null || stack.stackSize < 1 || stack.getItemDamage() < 0;
    }

    private static void addPreCullRecipe(MapKey key, Recipe addRecipe, Map<MapKey, ArrayList<Recipe>> preCullRecipes)
    {
        ArrayList<Recipe> recipeList = preCullRecipes.get(key);
        if (recipeList == null)
        {
            recipeList = new ArrayList<Recipe>();
        }
        recipeList.add(addRecipe);
        preCullRecipes.put(key, recipeList);
    }

    public Recipe(ItemStack outStack, ItemStack[] componentsParam)
    {
        output = outStack;
        ItemStack[] components = new ItemStack[componentsParam.length];
        int i = 0;
        for (ItemStack itemStack : componentsParam)
        {
            if (itemStack != null && itemStack.getItem() != null)
            {
                if (itemStack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
                {
                    components[i] = new ItemStack(itemStack.getItem(), itemStack.stackSize, 0);
                } else
                {
                    components[i] = new ItemStack(itemStack.getItem(), itemStack.stackSize, itemStack.getItemDamage());
                }
            } else
            {
                components[i] = null;
            }
            i++;
        }
        inStacks = components;
    }

    public int getOutStackSize()
    {
        return output.stackSize;
    }

    public static Recipe get(ItemStack output)
    {
        return get(new MapKey(output));
    }

    public static Recipe get(MapKey key)
    {
        if (recipes.containsKey(key))
        {
            return recipes.get(key);
        }
        return null;
    }

    public static Recipe get(String string)
    {
        return recipes.get(string);
    }

    private static int cullRecipes(Entry<MapKey, ArrayList<Recipe>> entry, Map<MapKey, ArrayList<Recipe>> preCullRecipes)
    {
        int returnVal = 0;
        ArrayList<Recipe> value = entry.getValue();
        if (DecomposerRecipe.get(entry.getKey()) != null)
        {
            value.clear();
            entry.setValue(value);
            return 0;
        }
        Map<Recipe, Integer> result = new Hashtable<Recipe, Integer>();
        for (Recipe check : value)
        {
            int depth = 0;
            if (check.inStacks != null && check.inStacks.length > 0)
            {
                for (ItemStack stack : check.inStacks)
                {
                    if (stack != null)
                    {
                        MapKey key = MapKey.getKey(stack);
                        depth = Math.max(depth, getSize(key, 0, preCullRecipes));
                        if (depth >= MAXDEPTH)
                        {
                            break;
                        }
                    }
                }
                result.put(check, depth);
            } else
            {
                result.put(check, MAXDEPTH);
            }
        }
        value.clear();
        Recipe minValue = null;
        for (Recipe key : result.keySet())
        {
            if (minValue == null && result.get(key) < MAXDEPTH)
            {
                minValue = key;
            } else if (minValue != null)
            {
                if (key.getOutStackSize() < minValue.getOutStackSize())
                {
                    minValue = key;
                } else if (key.getOutStackSize() == minValue.getOutStackSize() && result.get(key) < result.get(minValue))
                {
                    minValue = key;
                }
            }
        }
        if (minValue != null)
        {
            returnVal = result.get(minValue);
            value.add(minValue);
        }
        entry.setValue(value);
        return returnVal;
    }

    private static int getSize(MapKey key, int depth, Map<MapKey, ArrayList<Recipe>> preCullRecipes)
    {
        if (depth > MAXDEPTH)
        {
            return depth;
        }
        if (DecomposerRecipe.get(key) != null)
        {
            return 0;
        }
        if (!preCullRecipes.containsKey(key) || preCullRecipes.get(key).size() < 1)
        {
            return 1;
        }
        if (recipes.containsKey(key))
        {
            return recipes.get(key).depth;
        }
        LogHelper.debug("Depth: " + depth + ", stack: " + preCullRecipes.get(key).get(0).output.toString());
        int result = 0;
        for (Recipe recipe : preCullRecipes.get(key))
        {
            int thisDepth = 0;
            for (ItemStack stack : recipe.inStacks)
            {
                if (stack != null)
                {
                    MapKey nextKey = MapKey.getKey(stack);
                    int nextDepth = getSize(nextKey, depth + 1, preCullRecipes);
                    thisDepth = Math.max(thisDepth, nextDepth);
                    if (thisDepth > MAXDEPTH)
                    {
                        break;
                    }
                }
            }
            result = Math.max(thisDepth, result);
            if (result > MAXDEPTH)
            {
                break;
            }
        }
        return result + 1;
    }

}
