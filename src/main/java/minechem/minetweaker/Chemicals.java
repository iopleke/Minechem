package minechem.minetweaker;

import minechem.item.ChemicalRoomStateEnum;
import minechem.item.element.ElementClassificationEnum;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.MoleculeEnum;
import minechem.potion.PotionChemical;
import minechem.radiation.RadiationEnum;
import minechem.utils.InputHelper;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;

@ZenClass("mods.minechem.Chemicals")
public class Chemicals {
	
	@ZenMethod
	public static void addElement(int id, String name, String descriptiveName, String classification, String roomState, String radioactivity)
	{
		id--;
		if (id<0)
			throw new IllegalArgumentException(id+" is invalid");
		if (ElementEnum.getByID(id)!=null)
			throw new IllegalArgumentException(id+": "+ name +" is already registered as an element");
		ElementClassificationEnum eClass = InputHelper.getClassification(classification);
		ChemicalRoomStateEnum state = InputHelper.getRoomState(roomState);
		RadiationEnum radiation = InputHelper.getRadiation(radioactivity);
		MineTweakerAPI.apply(new AddElementAction(id,name, descriptiveName, eClass, state, radiation));
	}
	
	@ZenMethod
	public static void addMolecule(String name, int id, String roomState, IIngredient[] chemicals)
	{
		ChemicalRoomStateEnum state = InputHelper.getRoomState(roomState);
		if (MoleculeEnum.getByName(name)!=null||MoleculeEnum.getById(id)!=null)
			throw new IllegalArgumentException(name +" is already registered as a molecule");
		ArrayList<PotionChemical> chem = InputHelper.getChemicals(chemicals);
		PotionChemical[] chemical = (PotionChemical[]) chem.toArray(new PotionChemical[chem.size()]);
		MineTweakerAPI.apply(new AddMoleculeAction(name, id, state, chemical));
	}
	
		// ######################
		// ### Action classes ###
		// ######################
		
		private static class AddMoleculeAction implements IUndoableAction {
			private final MoleculeEnum molecule;
			
			public AddMoleculeAction(String name, int id, ChemicalRoomStateEnum state, PotionChemical... chemical) {
				molecule = new MoleculeEnum(name, id, state, chemical);
			}

			@Override
			public void apply() {
				MoleculeEnum.registerMTMolecule(molecule);
			}

			@Override
			public boolean canUndo() {
				return true;
			}

			@Override
			public void undo() {
				MoleculeEnum.unregisterMolecule(molecule);
			}

			@Override
			public String describe() {
				return "Adding Molecule: " + molecule.name();
			}

			@Override
			public String describeUndo() {
				return "Removing Molecule: " + molecule.name();
			}

			@Override
			public Object getOverrideKey() {
				return null;
			}
		}
		
		private static class AddElementAction implements IUndoableAction {
			private final ElementEnum element;
			
			public AddElementAction(int id, String name, String descriptiveName, ElementClassificationEnum classification, ChemicalRoomStateEnum roomState, RadiationEnum radioactivity) {
				element = new ElementEnum(id, name,descriptiveName, classification,roomState,radioactivity);
			}

			@Override
			public void apply() {
				ElementEnum.registerElement(element);
			}

			@Override
			public boolean canUndo() {
				return true;
			}

			@Override
			public void undo() {
				ElementEnum.unregisterElement(element);
			}

			@Override
			public String describe() {
				return "Adding Element: " + element.name();
			}

			@Override
			public String describeUndo() {
				return "Removing Element: " + element.name();
			}

			@Override
			public Object getOverrideKey() {
				return null;
			}
		}
}
