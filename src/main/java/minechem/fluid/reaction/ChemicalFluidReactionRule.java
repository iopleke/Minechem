package minechem.fluid.reaction;

public class ChemicalFluidReactionRule {
	
	public final Enum chemicalA;
	public final Enum chemicalB;
	
	public ChemicalFluidReactionRule(Enum a,Enum b) {
		chemicalA=a;
		chemicalB=b;
	}
	
	@Override
	public int hashCode(){
		return chemicalA.hashCode()^chemicalB.hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj==this){
			return true;
		}
		
		if (obj instanceof ChemicalFluidReactionRule){
			ChemicalFluidReactionRule another=(ChemicalFluidReactionRule) obj;
			return (chemicalA==another.chemicalA&&chemicalB==another.chemicalB)||(chemicalB==another.chemicalA&&chemicalA==another.chemicalB);
		}
		
		return false;
	}
}
