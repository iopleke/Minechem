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

	private enum EnumOrePrefix {
		dust, block, ingot, ore, dustSmall, nugget, dustDirty, plate
	}

	private String[] supportedOres;

	private Map<EnumOre, ArrayList<EnumOrePrefix>> seenOres = new HashMap<EnumOre, ArrayList<EnumOrePrefix>>();

	private Map<EnumOre, ItemStack> registeredIngots = new HashMap<EnumOre, ItemStack>();

	public DefaultOreDictionaryHandler() {
		ArrayList<String> ores = new ArrayList<String>();
		for (EnumOre ore : EnumOre.values()) {
			ores.add(ore.name());
		}
		supportedOres = ores.toArray(new String[ores.size()]);
	}

	public String[] parseOreName(String oreName) {
		for (EnumOrePrefix prefix : EnumOrePrefix.values()) {
			if (oreName.startsWith(prefix.name())) {
				String remainder = oreName.substring(prefix.name().length())
						.toLowerCase();
				if (Arrays.asList(supportedOres).contains(remainder))
					return new String[] { prefix.name(), remainder };
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
		EnumOrePrefix prefix = EnumOrePrefix.valueOf(tokens[0]);
		EnumOre ore = EnumOre.valueOf(tokens[1]);

		switch (prefix) {
		case ore:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore, scaleFloor(
					ore.getComposition(), 3d)));
			SynthesisRecipe.add(new SynthesisRecipe(event.Ore, false, 1000, scaleFloor(ore.getComposition(),3d)));
			break;
		case ingot:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore, ore
					.getComposition()));
			if (!haveSeen(ore, EnumOrePrefix.dust)
					&& !haveSeen(ore, EnumOrePrefix.dustSmall)) {
				SynthesisRecipe.add(new SynthesisRecipe(event.Ore, false, 1000,
						ore.getComposition()));
				registeredIngots.put(ore, event.Ore);
			}
			SynthesisRecipe.add(new SynthesisRecipe(event.Ore, false, 1000, ore
					.getComposition()));
			break;
		case nugget:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore, scaleFloor(
					ore.getComposition(), 1d / 9d)));
			break;
		case dust:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore, ore
					.getComposition()));
			unregisterIngot(ore);
			SynthesisRecipe.add(new SynthesisRecipe(event.Ore, false, 1000, ore
					.getComposition()));
			break;
		case dustDirty:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore, ore
					.getComposition()));
			break;
		case plate:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore, ore
					.getComposition()));
			break;
		case dustSmall:
			DecomposerRecipe.add(new DecomposerRecipe(event.Ore, scaleFloor(
					ore.getComposition(), 0.25d)));
			unregisterIngot(ore);
			SynthesisRecipe.add(new SynthesisRecipe(event.Ore, false, 1000,
					scaleCeil(ore.getComposition(), 0.25d)));
			break;
		default:
			ModMinechem.logger.log(Level.WARNING,
					DefaultOreDictionaryHandler.class.getSimpleName()
							+ " : This cannot happen! (well... in theory)");
			break;
		}

		seen(ore, prefix);
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

	private boolean haveSeen(EnumOre ore, EnumOrePrefix prefix) {
		if (this.seenOres.containsKey(ore)
				&& this.seenOres.get(ore).contains(prefix))
			return true;
		return false;
	}

	private void seen(EnumOre ore, EnumOrePrefix prefix) {
		if (!this.seenOres.containsKey(ore))
			this.seenOres.put(ore, new ArrayList<EnumOrePrefix>());
		if (!this.seenOres.get(ore).contains(prefix))
			this.seenOres.get(ore).add(prefix);
	}
}
