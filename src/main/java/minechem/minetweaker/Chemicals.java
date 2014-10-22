package minechem.minetweaker;

import java.util.ArrayList;

import minechem.item.ChemicalRoomStateEnum;
import minechem.item.molecule.MoleculeEnum;
import minechem.potion.PotionChemical;
import minechem.utils.InputHelper;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minechem.Chemicals")
public class Chemicals {
	public static void addElement()
	{
		
	}
	
	@ZenMethod
	public static void addMolecule(String name, int id, String roomState, IItemStack[] chemicals)
	{
		ChemicalRoomStateEnum state = InputHelper.getRoomState(roomState);
		if (MoleculeEnum.getByName(name)!=null||(MoleculeEnum.molecules.size()>id&&MoleculeEnum.molecules.get(id)!=null))
			throw new IllegalArgumentException(name +" is already registered as a molecule");
		ArrayList<PotionChemical> chem = InputHelper.getChemicals(chemicals);
		PotionChemical[] chemical = (PotionChemical[]) chem.toArray(new PotionChemical[chem.size()]);
		MineTweakerAPI.apply(new AddMoleculeAction(name, id, state, chemical));
	}
	
	public static void removeElement()
	{
		
	}
	
	public static void removeMolecule(int id)
	{
		if (id<=MoleculeEnum.baseMolecules);
	}
	
	public static void removeMolecule(String name)
	{
		MoleculeEnum molecule = MoleculeEnum.getByName(name);
		if (molecule.id()<=MoleculeEnum.baseMolecules);
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
				return false;
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
}
