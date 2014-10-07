package minechem.item;

import net.minecraft.util.StatCollector;

public enum ChemicalRoomStateEnum implements IDescriptiveName {
	liquid("Liquid",false,800),solid("Solid",false,2000),gas("Gaseous",true,200);
	
	private boolean isGas;
	private int viscosity;
	private String descriptiveName;
	
	ChemicalRoomStateEnum(String descriptiveName,boolean isGas,int viscosity){
		this.isGas=isGas;
		this.viscosity=viscosity;
		this.descriptiveName=descriptiveName;
	}
	
	public boolean isGas() {
		return isGas;
	}

	
	public int getViscosity() {
		return viscosity;
	}

	
    /* （非 Javadoc）
	 * @see minechem.item.IDescriptiveName#descriptiveName()
	 */
    @Override
	public String descriptiveName()
    {
        String localizedName = StatCollector.translateToLocal("element.classification." + descriptiveName);
        if (!localizedName.isEmpty() || localizedName !="element.classification." + descriptiveName) 
        {
            return localizedName;
        }
        return descriptiveName;
    }
}
