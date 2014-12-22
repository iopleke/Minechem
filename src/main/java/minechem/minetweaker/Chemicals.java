package minechem.minetweaker;

import java.util.ArrayList;
import java.util.IllegalFormatConversionException;
import java.util.IllegalFormatException;
import java.util.List;

import minechem.item.ChemicalRoomStateEnum;
import minechem.item.element.ElementClassificationEnum;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.potion.PharmacologyEffect;
import minechem.potion.PharmacologyEffectRegistry;
import minechem.potion.PotionChemical;
import minechem.radiation.RadiationEnum;
import minechem.utils.InputHelper;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minechem.Chemicals")
public class Chemicals
{

	@ZenMethod
	public static void addElement(int id, String name, String descriptiveName, String classification, String roomState, String radioactivity)
	{
		if (id < 0)
		{
			throw new IllegalArgumentException(id + " is invalid");
		}
		if (ElementEnum.getByID(id) != null)
		{
			throw new IllegalArgumentException(id + ": " + name + " is already registered as an element");
		}
		ElementClassificationEnum eClass = InputHelper.getClassification(classification);
		ChemicalRoomStateEnum state = InputHelper.getRoomState(roomState);
		RadiationEnum radiation = InputHelper.getRadiation(radioactivity);
		MineTweakerAPI.apply(new AddElementAction(id, name, descriptiveName, eClass, state, radiation));
	}

	@ZenMethod
	public static void addMolecule(String name, int id, String roomState, IIngredient[] chemicals)
	{
		ChemicalRoomStateEnum state = InputHelper.getRoomState(roomState);
		if (MoleculeEnum.getByName(name) != null || MoleculeEnum.getById(id) != null)
		{
			throw new IllegalArgumentException(name + " is already registered as a molecule");
		}
		ArrayList<PotionChemical> chem = InputHelper.getChemicals(chemicals);
		PotionChemical[] chemical = chem.toArray(new PotionChemical[chem.size()]);
		MineTweakerAPI.apply(new AddMoleculeAction(name, id, state, chemical));
	}

	@ZenMethod
	public static void addCureEffect(IIngredient ingredient)
	{
		PharmacologyEffect effect = new PharmacologyEffect.Cure();
		addEffect(ingredient, effect);
	}

	@ZenMethod
	public static void addCureEffect(IIngredient ingredient, String string)
	{
		PharmacologyEffect effect = new PharmacologyEffect.Cure(string);
		addEffect(ingredient, effect);
	}

	@ZenMethod
	public static void addDamageEffect(IIngredient ingredient, double damage)
	{
		PharmacologyEffect effect = new PharmacologyEffect.Damage((float)damage);
		addEffect(ingredient,effect);
	}

	@ZenMethod
	public static void addFoodEffect(IIngredient ingredient, int level, double saturation)
	{
		PharmacologyEffect effect = new PharmacologyEffect.Food(level,(float)saturation);
		addEffect(ingredient,effect);
	}

	@ZenMethod
	public static void addBurnEffect(IIngredient ingredient, int time)
	{
		PharmacologyEffect effect = new PharmacologyEffect.Burn(time);
		addEffect(ingredient,effect);
	}

	@ZenMethod
	public static void addPotionEffect(IIngredient ingredient, String potion, int time)
	{
		addPotionEffect(ingredient, potion, time, 0);
	}

	@ZenMethod
	public static void addPotionEffect(IIngredient ingredient, String potion, int time, int level)
	{
		PharmacologyEffect effect = new PharmacologyEffect.Potion(potion, level, time);
		addEffect(ingredient,effect);
	}

	private static void addEffect(IIngredient ingredient, PharmacologyEffect effect)
	{
		PotionChemical chemical = InputHelper.getChemical(ingredient);
		if (!(chemical instanceof Molecule)) throw new IllegalArgumentException("Ingredient is not a molecule");
		MineTweakerAPI.apply(new AddMoleculeEffectAction(((Molecule)chemical).molecule, effect));
	}

	@ZenMethod
	public static void removeMoleculeEffects(IIngredient ingredient)
	{
		PotionChemical chemical = InputHelper.getChemical(ingredient);
		if (!(chemical instanceof Molecule)) throw new IllegalArgumentException("Ingredient is not a molecule");
		MineTweakerAPI.apply(new RemoveEffectsAction(((Molecule) chemical).molecule));
	}

	// ######################
	// ### Action classes ###
	// ######################
	private static class AddMoleculeAction implements IUndoableAction
	{
		private final MoleculeEnum molecule;

		public AddMoleculeAction(String name, int id, ChemicalRoomStateEnum state, PotionChemical... chemical)
		{
			molecule = new MoleculeEnum(name, id, state, chemical);
		}

		@Override
		public void apply()
		{
			MoleculeEnum.registerMTMolecule(molecule);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			MoleculeEnum.unregisterMolecule(molecule);
		}

		@Override
		public String describe()
		{
			return "Adding Molecule: " + molecule.name();
		}

		@Override
		public String describeUndo()
		{
			return "Removing Molecule: " + molecule.name();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	private static class AddElementAction implements IUndoableAction
	{
		private final ElementEnum element;

		public AddElementAction(int id, String name, String descriptiveName, ElementClassificationEnum classification, ChemicalRoomStateEnum roomState, RadiationEnum radioactivity)
		{
			element = new ElementEnum(id, name, descriptiveName, classification, roomState, radioactivity);
		}

		@Override
		public void apply()
		{
			ElementEnum.registerElement(element);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			ElementEnum.unregisterElement(element);
		}

		@Override
		public String describe()
		{
			return "Adding Element: " + element.name();
		}

		@Override
		public String describeUndo()
		{
			return "Removing Element: " + element.name();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	private static class AddMoleculeEffectAction implements IUndoableAction
	{
		private PharmacologyEffect effect;
		private MoleculeEnum molecule;

		public AddMoleculeEffectAction(MoleculeEnum molecule, PharmacologyEffect effect)
		{
			this.molecule = molecule;
			this.effect = effect;
		}

		@Override
		public void apply()
		{
			PharmacologyEffectRegistry.addEffect(molecule, effect);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			PharmacologyEffectRegistry.removeEffect(molecule, effect);
		}

		@Override
		public String describe()
		{
			return "Adding " + effect.toString() + " to molecule " + molecule.name();
		}

		@Override
		public String describeUndo()
		{
			return "Removing " + effect.toString() + " to molecule " + molecule.name();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}

	private static class RemoveEffectsAction implements IUndoableAction
	{
		private MoleculeEnum molecule;
		private List<PharmacologyEffect> list;

		public RemoveEffectsAction(MoleculeEnum molecule)
		{
			this.molecule = molecule;
		}

		@Override
		public void apply()
		{
			list = PharmacologyEffectRegistry.removeEffects(molecule);
		}

		@Override
		public boolean canUndo()
		{
			return true;
		}

		@Override
		public void undo()
		{
			if (list != null && !list.isEmpty())PharmacologyEffectRegistry.addEffects(molecule, list);
		}

		@Override
		public String describe()
		{
			return "Removing effects from " + molecule.name();
		}

		@Override
		public String describeUndo()
		{
			return "Restoring effects for " + molecule.name();
		}

		@Override
		public Object getOverrideKey()
		{
			return null;
		}
	}
}
