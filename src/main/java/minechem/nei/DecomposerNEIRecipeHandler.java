package minechem.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import minechem.Minechem;
import minechem.gui.GuiDraw;
import minechem.potion.PotionChemical;
import minechem.tileentity.decomposer.DecomposerGui;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.decomposer.DecomposerRecipeChance;
import minechem.tileentity.decomposer.DecomposerRecipeHandler;
import minechem.tileentity.decomposer.DecomposerRecipeSelect;
import minechem.tileentity.decomposer.DecomposerRecipeSuper;
import minechem.utils.Compare;
import minechem.utils.MinechemHelper;
import minechem.utils.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class DecomposerNEIRecipeHandler extends TemplateRecipeHandler
{

	private static final String MINECHEM_DECOMPOSER_RECIPES_ID = "minechem.decomposer";

	private ResourceLocation texture = new ResourceLocation(Minechem.ID, Reference.DECOMPOSER_GUI_NEI);

	// GUI slot offsets, in GUI-relative pixel values.
	private static final int INPUT_X_OFS = 75;
	private static final int INPUT_Y_OFS = 5;
	private static final int OUTPUT_X_OFS = 10;
	private static final int OUTPUT_X_SCALE = 18;
	private static final int OUTPUT_Y_OFS = 51;
	private static final int INPUT_ARROW_Y_OFS = 20;

	@Override
	public String getRecipeName()
	{
		return MinechemHelper.getLocalString("gui.title.decomposer");
	}

	@Override
	public String getGuiTexture()
	{
		return texture.toString();
	}

	@Override
	public int recipiesPerPage()
	{
		return 1;
	}

	@Override
	public void drawBackground(int recipe)
	{
		GL11.glColor4f(1, 1, 1, 1);
		changeTexture(getGuiTexture());
		drawTexturedModalRect(0, 0, 5, 11, 166, 131);
	}

	@Override
	public void loadTransferRects()
	{
		transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(INPUT_X_OFS, INPUT_ARROW_Y_OFS, 16, 30), MINECHEM_DECOMPOSER_RECIPES_ID, new Object[0]));
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results)
	{
		if (outputId.equals(MINECHEM_DECOMPOSER_RECIPES_ID))
		{
			// Add all decomposer recipes to local arecipes array.
			for (DecomposerRecipe dr : DecomposerRecipe.recipes.values())
			{
				registerDecomposerRecipe(dr);
			}
		} else
		{
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result)
	{
		// If the result isn't a Minechem chemical, no decomposer recipe
		// can yield it.
		if (!Compare.isStackAChemical(result))
		{
			return;
		}
		// Add all decomposer recipes that can yield the result.
		PotionChemical resultChemical = MinechemHelper.itemStackToChemical(result);
		if (resultChemical == null)
		{
			return;
		}
		for (DecomposerRecipe dr : DecomposerRecipe.recipes.values())
		{
			if (dr.outputContains(resultChemical))
			{
				registerDecomposerRecipe(dr);
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient)
	{
		// Add all decomposer recipes that take the ingredient as an input.
		DecomposerRecipe dr = DecomposerRecipeHandler.instance.getRecipe(ingredient);
		if (dr != null)
		{
			registerDecomposerRecipe(dr);
		}
	}

	@Override
	public Class getGuiClass()
	{
		return DecomposerGui.class;
	}

	/**
	 * Registers a decomposer recipe with NEI. Anything that adds a new decomposer recipe after startup should call this to have the recipe reflected in NEI.
	 *
	 * @param dr Decomposer recipe to add.
	 */
	public void registerDecomposerRecipe(DecomposerRecipe dr)
	{
		if (dr != null)
		{
			if (dr.hasOutput())
			{
				BaseCachedDecomposerRecipe cdr = buildCachedRecipe(dr);

				arecipes.add(cdr);
			}
		}

	}

	@Override
	public void drawExtras(int recipeIdx)
	{
		BaseCachedDecomposerRecipe cdr = (BaseCachedDecomposerRecipe) arecipes.get(recipeIdx);
		// Render the chance next to the down arrow in the GUI, as a percent.
		float chance = cdr.getChance();
		if (chance < 1.0f)
		{
			String format = chance > 0.1F ? "%2.0f%%" : "%3.3f%%";
			String chanceStr = String.format(format, chance * 100.0);
			int xPos = INPUT_X_OFS - GuiDraw.getStringWidth(chanceStr) - 5;
			GuiDraw.drawString(chanceStr, xPos, INPUT_ARROW_Y_OFS + 10, 8, false);
		}

		// Potentially update the outputs that will be displayed on the next
		// tick, for variable multi-output recipes. This is done here and not
		// in onUpdate() to avoid cycling outputs of recipes that are not
		// visible.
		cdr.cycleOutput(cycleticks);
	}

	private BaseCachedDecomposerRecipe buildCachedRecipe(DecomposerRecipe dr)
	{
		// As Java uses the static type for method dispatch, we have to check
		// the runtime type information. It must be checked from most to least
		// specific type, as well.
		if (dr instanceof DecomposerRecipeSelect)
		{
			return new CachedDecomposerRecipeSelect((DecomposerRecipeSelect) dr);
		} else if (dr instanceof DecomposerRecipeChance)
		{
			return new CachedDecomposerRecipeChance((DecomposerRecipeChance) dr);
		} else if (dr instanceof DecomposerRecipeSuper)
		{
			return new CachedDecomposerRecipeSuper((DecomposerRecipeSuper) dr);
		} else
		{
			return new CachedDecomposerRecipe(dr);
		}
	}

	public abstract class BaseCachedDecomposerRecipe extends TemplateRecipeHandler.CachedRecipe
	{
		// The recipe's input item.
		protected PositionedStack input;
		// The first item to be rendered from the multi-output decomposer recipe.
		protected PositionedStack output1;
		// Other items to be rendered from the multi-output decomposer recipe.
		protected List<PositionedStack> otherOutputs = new ArrayList<PositionedStack>();

		protected BaseCachedDecomposerRecipe(ItemStack input)
		{
			this.input = new PositionedStack(input, INPUT_X_OFS, INPUT_Y_OFS);
		}

		@Override
		public PositionedStack getIngredient()
		{
			return input;
		}

		@Override
		public PositionedStack getResult()
		{
			return output1;
		}

		@Override
		public List<PositionedStack> getOtherStacks()
		{
			return otherOutputs;
		}

		/**
		 * Returns the chance that this recipe yields any output. Chance is in [0, 1], with 1 (always return output) being the default.
		 */
		public float getChance()
		{
			return 1.0f;
		}

		/**
		 * Cycles the output that this recipe will display, based on the given tick number. Does nothing if this recipe has no variable output.
		 */
		public void cycleOutput(long tick)
		{
		}

		protected void setOutputs(List<ItemStack> outputs)
		{
            if (outputs != null && !outputs.isEmpty())
            {
                outputs = MinechemHelper.pushTogetherStacks(outputs);
                output1 = new PositionedStack(outputs.get(0), OUTPUT_X_OFS, OUTPUT_Y_OFS);
                if (outputs.size() > 1)
                {
                    int itemsPerLine = calcItemsPerLine(outputs);
                    double scale = 8.5 / itemsPerLine;
                    int output_x_scale = (int) Math.ceil(OUTPUT_X_SCALE * scale);
                    for (int idx = 1; idx < outputs.size(); idx++)
                    {
                        ItemStack o = outputs.get(idx);
                        int x = OUTPUT_X_OFS + output_x_scale * (idx % itemsPerLine);
                        int y = OUTPUT_Y_OFS + ((idx / itemsPerLine) * OUTPUT_X_SCALE);
                        otherOutputs.add(new PositionedStack(o, x, y));
                    }
                }
            }
		}

		private int calcItemsPerLine(List<ItemStack> list)
		{
			int highestStackSize = 0;
			for (ItemStack stack : list)
			{
				if (highestStackSize < stack.stackSize)
				{
					highestStackSize = stack.stackSize;
				}
			}
			int length = (int) (Math.log10(highestStackSize) + 1);
			return 9 - length;
		}
	}

	public class CachedDecomposerRecipe extends BaseCachedDecomposerRecipe
	{
		public CachedDecomposerRecipe(DecomposerRecipe dr)
		{
			super(dr.getInput());
			ArrayList<ItemStack> outputs = MinechemHelper.convertChemicalsIntoItemStacks(dr.getOutput());
            setOutputs(outputs);
		}
	}

	public class CachedDecomposerRecipeChance extends BaseCachedDecomposerRecipe
	{
		// The fractional chance [0, 1] that this recipe will yield any output.
		private float chance;

		public CachedDecomposerRecipeChance(DecomposerRecipeChance dr)
		{
			super(dr.getInput());
			this.chance = dr.getChance();
			ArrayList<ItemStack> outputs = MinechemHelper.convertChemicalsIntoItemStacks(dr.getOutput());
            setOutputs(outputs);
		}

		@Override
		public float getChance()
		{
			return chance;
		}
	}

	public class CachedDecomposerRecipeSelect extends CachedDecomposerRecipeChance
	{
		// Number of possible output sets.
		private int numOutputSets;
		// Which set of output to be shown.
		private int outputSetToShow;
		// Which tick to cycle at. This is initialized lazily, so
		// the first output set is shown for the normal duration.
		private long cycleAtTick;

		private DecomposerRecipeSelect decomposerRecipeSelect;

		public CachedDecomposerRecipeSelect(DecomposerRecipeSelect dr)
		{
			// This picks only the first potential output set to be displayed.
			super(dr);
			numOutputSets = dr.getAllPossibleRecipes().size();
			outputSetToShow = -1;
			decomposerRecipeSelect = dr;
			cycleAtTick = 0;
		}

		@Override
		public void cycleOutput(long tick)
		{
			if (outputSetToShow == -1)
			{
				cycleAtTick = tick + 20;
				outputSetToShow = 0;
				return;
			}
			if (tick >= cycleAtTick)
			{
				cycleAtTick = tick + 20;
				outputSetToShow++;
				if (outputSetToShow >= numOutputSets)
				{
					outputSetToShow = 0;
				}
				ArrayList<DecomposerRecipe> possibleRecipes = decomposerRecipeSelect.getAllPossibleRecipes();
				ArrayList<ItemStack> outputsToShow = MinechemHelper.convertChemicalsIntoItemStacks(possibleRecipes.get(outputSetToShow).getOutput());
                setOutputs(outputsToShow);
			}
		}
	}

    public class CachedDecomposerRecipeSuper extends CachedDecomposerRecipe
    {
        // Which tick to cycle at. This is initialized lazily, so
        // the first output set is shown for the normal duration.
        private long cycleAtTick;

        // The fractional chance [0, 1] that this recipe will yield any output.
        private float chance;

        private DecomposerRecipeSuper decomposerRecipeSuper;

        public CachedDecomposerRecipeSuper(DecomposerRecipeSuper dr)
        {
            super(dr);
            cycleAtTick = -1;
            decomposerRecipeSuper = dr;
            chance = dr.getChance();
        }

        @Override
        public void cycleOutput(long tick)
        {
            if(cycleAtTick == -1) cycleAtTick = tick + 20;
            if (tick >= cycleAtTick)
            {
                cycleAtTick = tick + 20;
                ArrayList<ItemStack> outputsToShow = MinechemHelper.convertChemicalsIntoItemStacks(decomposerRecipeSuper.getOutput());
                setOutputs(outputsToShow);
            }
        }

        @Override
        public float getChance()
        {
            return chance;
        }

    }

}
