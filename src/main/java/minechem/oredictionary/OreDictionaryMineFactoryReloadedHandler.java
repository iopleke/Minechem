package minechem.oredictionary;

import minechem.item.element.Element;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.potion.PotionChemical;
import minechem.tileentity.decomposer.DecomposerRecipe;

public class OreDictionaryMineFactoryReloadedHandler implements
		OreDictionaryHandler {

	@Override
	public boolean canHandle(String oreName) {
		if(oreName.equals("itemRawRubber") || oreName.equals("itemRubber") || oreName.equals("fertilizerOrganic")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void handle(String oreName) {
		if(oreName.equals("itemRawRubber")) {
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[] {
					new Molecule(MoleculeEnum.cellulose, 3), new Molecule(MoleculeEnum.polyisobutylene, 1)
			});
		} else if(oreName.equals("itemRubber")) {
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[] {
					new Molecule(MoleculeEnum.cellulose, 1), new Molecule(MoleculeEnum.polyisobutylene, 3)
			});
		} else if(oreName.equals("fertilizerOrganic")) {
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[] {
					new Element(ElementEnum.C, 1), new Element(ElementEnum.O, 1), new Element(ElementEnum.N, 2),
					new Element(ElementEnum.H, 2)
			});
		} else if(oreName.equals("rawPlastic")) {
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[] {
				new Molecule(MoleculeEnum.vinylChloride, 1)	
			});
		} else if(oreName.equals("sheetPlastic")) {
			DecomposerRecipe.createAndAddRecipeSafely(oreName, new PotionChemical[] {
					new Molecule(MoleculeEnum.vinylChloride, 2)	
			});
		} else {
			// This shouldn't happen
		}
	}

}
