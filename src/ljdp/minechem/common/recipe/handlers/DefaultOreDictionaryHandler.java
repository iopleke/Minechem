package ljdp.minechem.common.recipe.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import ljdp.minechem.api.core.Chemical;
import ljdp.minechem.api.core.EnumOre;
import ljdp.minechem.api.recipe.DecomposerRecipe;
import ljdp.minechem.api.recipe.SynthesisRecipe;
import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.recipe.OreDictionaryHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class DefaultOreDictionaryHandler implements OreDictionaryHandler {

	private String[] supportedTypes = new String[] { "dust", "block", "ingot", "ore",
			"dustSmall", "nugget", "dustDirty" };

	private String[] supportedOres;

	private Map<EnumOre, ArrayList<String>> seenOres = new HashMap<EnumOre, ArrayList<String>>();

	private Map<EnumOre, ItemStack> registeredIngots = new HashMap<EnumOre, ItemStack>();

	public DefaultOreDictionaryHandler() {
		ArrayList<String> ores = new ArrayList<String>();
		for (EnumOre ore : EnumOre.values()) {
			ores.add(ore.name());
		}
		supportedOres = ores.toArray(new String[ores.size()]);
	}

	public String[] parseOreName(String oreName) {
		for (String prefix : supportedTypes) {
			if (oreName.startsWith(prefix)) {
				String remainder = oreName.substring(prefix.length())
						.toLowerCase();
				if (Arrays.asList(supportedOres).contains(remainder))
					return new String[] { prefix, remainder };
			}
		}

		return null;
	}

	@Override
	public boolean canHandle(OreRegisterEvent event) {
		if (this.parseOreName(event.Name) != null)
			return true;
		return false;
	}

	@Override
	public void handle(OreRegisterEvent event) {
		ModMinechem.logger.log(Level.INFO,
				DefaultOreDictionaryHandler.class.getSimpleName()
						+ " registered : " + event.Name);

		String[] tokens = this.parseOreName(event.Name);
		String type = tokens[0];
		EnumOre ore = EnumOre.valueOf(tokens[1]);

		if(type.equals("ore")){
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore, scaleFloor(
					ore.getComposition(), 1.5d)));
			
		}
		if(type.equals("ore")){
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore, scaleFloor(
					ore.getComposition(), 9d)));
			
		}
		if(type.equals("ingot")){
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore, ore
					.getComposition()));
			if (!haveSeen(ore, "dust") && !haveSeen(ore, "dustSmall")) {
				SynthesisRecipe.add(new SynthesisRecipe(event.Ore, false, 1000,
						ore.getComposition()));
				registeredIngots.put(ore, event.Ore);
			}
		}
		if(type.equals("nugget")){
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore, scaleFloor(
					ore.getComposition(), 1d / 9d)));
		}
		if(type.equals("dust")){
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore, ore
					.getComposition()));
			unregisterIngot(ore);
			SynthesisRecipe.add(new SynthesisRecipe(event.Ore, false, 1000, ore
					.getComposition()));
		}
		if(type.equals("dustDirty")){
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore, ore
					.getComposition()));
		}
		if(type.equals("dustSmall")){
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore, scaleFloor(
					ore.getComposition(), 0.25d)));
			unregisterIngot(ore);
			SynthesisRecipe.add(new SynthesisRecipe(event.Ore, false, 1000,
					scaleCeil(ore.getComposition(), 0.25d)));
		}
		//This used to be string switch statement code
		//Someone should clean this up
		//This was an emergency java 6 patch
		//	ModMinechem.logger.log(Level.WARNING,
		//			DefaultOreDictionaryHandler.class.getSimpleName()
		//					+ " : This cannot happen! (well... in theory)");
		
		seen(ore, type);
	}

	private void unregisterIngot(EnumOre ore) {
		if (registeredIngots.containsKey(ore)) {
			SynthesisRecipe.remove(registeredIngots.get(ore));
			registeredIngots.remove(ore);
		}
	}

	private Chemical[] scaleCeil(Chemical[] composition, double factor) {
		ArrayList<Chemical> newComposition = new ArrayList<Chemical>();

		for (Chemical chem : composition) {
			Chemical newChem = chem.copy();
			newChem.amount = (int) Math.ceil(chem.amount * factor);
			newComposition.add(newChem);
		}

		return newComposition.toArray(new Chemical[newComposition.size()]);
	}

	private Chemical[] scaleFloor(Chemical[] composition, double factor) {
		ArrayList<Chemical> newComposition = new ArrayList<Chemical>();

		for (Chemical chem : composition) {
			Chemical newChem = chem.copy();
			newChem.amount = (int) Math.floor(chem.amount * factor);
			newComposition.add(newChem);
		}

		return newComposition.toArray(new Chemical[newComposition.size()]);
	}

	private boolean haveSeen(EnumOre ore, String type) {
		if (this.seenOres.containsKey(ore)
				&& this.seenOres.get(ore).contains(type))
			return true;
		return false;
	}

	private void seen(EnumOre ore, String type) {
		if (!this.seenOres.containsKey(ore))
			this.seenOres.put(ore, new ArrayList<String>());
		if (!this.seenOres.get(ore).contains(type))
			this.seenOres.get(ore).add(type);
	}
}
