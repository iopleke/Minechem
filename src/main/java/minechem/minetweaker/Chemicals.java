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
	public static void addMoleculeEffect(IIngredient ingredient, String type, Object... args)
	{
		PotionChemical chemical = InputHelper.getChemical(ingredient);
		if (!(chemical instanceof Molecule)) throw new IllegalArgumentException("Ingredient is not a molecule");
		PharmacologyEffect effect;
		if (type.equalsIgnoreCase("cure"))
		{
			if (args.length == 0) effect = new PharmacologyEffect.Cure();
			else if (args.length == 1) effect = new PharmacologyEffect.Cure((String)args[0]);
			else throw new IllegalArgumentException("For cure use the potion name as last param");
		} else if (type.equalsIgnoreCase("potion"))
		{
			if (args.length == 2) effect = new PharmacologyEffect.Potion((String)args[0], (Integer)args[1]);
			else if (args.length == 3) effect = new PharmacologyEffect.Potion((String)args[0], (Integer)args[1], (Integer)args[2]);
			else throw new IllegalArgumentException("For potion use the potion name, duration, (optional level)");
		} else if (type.equalsIgnoreCase("damage"))
		{
			if (args.length == 1) effect = new PharmacologyEffect.Damage((Float)args[0]);
			else throw new IllegalArgumentException("For damage use a damage value (float) as last param");
		} else if (type.equalsIgnoreCase("burn"))
		{
			if (args.length == 1) effect = new PharmacologyEffect.Burn((Integer)args[0]);
			else throw new IllegalArgumentException("For burn use a time in sec (int) as last param");
		} else if (type.equalsIgnoreCase("food"))
		{
			if (args.length == 2) effect = new PharmacologyEffect.Food((Integer)args[0], (Float)args[2]);
			else throw new IllegalArgumentException("For food use the foodLevel (int) and the saturationLevel (float)");
		} else
		{
			throw new IllegalArgumentException(type + " is not a valid type");
		}
		MineTweakerAPI.apply(new AddMoleculeEffectAction(((Molecule) chemical).molecule, effect));
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
