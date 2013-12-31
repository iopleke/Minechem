package ljdp.minechem.common.assembler;

import java.util.ArrayList;

import ljdp.minechem.common.CoordTuple;

public interface IShapeType {
	public ArrayList<CoordTuple> getCoords(CoordTuple origin, int size);
}
