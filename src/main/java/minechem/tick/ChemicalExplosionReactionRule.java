package minechem.tick;

public class ChemicalExplosionReactionRule {
	
	public final Enum chemicalA;
	public final Enum chemicalB;
	
	public ChemicalExplosionReactionRule(Enum a,Enum b) {
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
		
		if (obj instanceof ChemicalExplosionReactionRule){
			ChemicalExplosionReactionRule another=(ChemicalExplosionReactionRule) obj;
			return (chemicalA==another.chemicalA&&chemicalB==another.chemicalB)||(chemicalB==another.chemicalA&&chemicalA==another.chemicalB);
		}
		
		return false;
	}
}
