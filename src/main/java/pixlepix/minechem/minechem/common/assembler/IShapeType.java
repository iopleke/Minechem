package pixlepix.minechem.minechem.common.assembler;

import java.util.ArrayList;

import pixlepix.minechem.minechem.common.CoordTuple;

public interface IShapeType {
	public ArrayList<CoordTuple> getCoords(CoordTuple origin, int size);
}
