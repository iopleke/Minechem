package minechem.item;

import minechem.radiation.RadiationEnum;

public abstract class MinechemChemicalType {
	
	private final ChemicalRoomStateEnum roomState;
	private final RadiationEnum radioactivity;
	
	public MinechemChemicalType(ChemicalRoomStateEnum roomState,RadiationEnum radioactivity){
		this.roomState=roomState;
		this.radioactivity=radioactivity;
	}
	
	public RadiationEnum radioactivity() {
		return radioactivity;
	}

	public ChemicalRoomStateEnum roomState(){
        return roomState;
    }
}
