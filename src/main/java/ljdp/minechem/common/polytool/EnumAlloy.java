package ljdp.minechem.common.polytool;

import ljdp.minechem.api.core.EnumElement;

public enum EnumAlloy{
	//Axe-Shovel-Pickaxe-Stone-Sword
	//All of these are multiplied by 2
	Aluminium(EnumElement.Al,4F,-2F,-2F,0F,0F),
	Scandium(EnumElement.Sc,4F,0F,0F,-2F,-2F),
	Vandium(EnumElement.V,4F,-1F,-1F,-1F,-1F),
	
	Manganese(EnumElement.Mn,-2F,4F,-2F,0F,0F),
	Cobalt(EnumElement.Co,0F,4F,0F,-2F,-2F),
	Cadmium(EnumElement.Cd,-1F,4F,-1F,-1F,-1F),
	
	Gallium(EnumElement.Ga,-2F,-2F,4F,0F,0F),
	Selenium(EnumElement.Se,0F,0F,4F,-2F,-2F),
	Strontium(EnumElement.Sr,-1F,-1F,4F,-1F,-1F),
	
	Yttrium(EnumElement.Y,-2F,0F,-2F,4F,0F),
	Niobium(EnumElement.No,0F,-2F,0F,4F,-2F),
	Molybdeum(EnumElement.Mo,-1F,-1F,-1F,4F,-1F),

	Ruthenium(EnumElement.Ru,-2F,0F,-2F,0F,4F),
	Rhodium(EnumElement.Rh,0F,-2F,0F,-2F,4F),
	Palladium(EnumElement.Pd,-1F,-1F,-1F,-1F,4F);
	
	public final float axe;
	public final float shovel;
	public final float pickaxe;
	public final float stone;
	public final float sword;
	public final EnumElement element;
	private EnumAlloy(EnumElement element, float axe, float shovel, float pickaxe, float stone, float sword){
		this.element=element;
		this.axe=2*axe;
		this.shovel=2*shovel;
		this.pickaxe=2*pickaxe;
		this.stone=2*stone;
		this.sword=2*sword;
	}
	
	
}
