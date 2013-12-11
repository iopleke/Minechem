package ljdp.minechem.common.assembler;

import java.util.ArrayList;

public interface IShapeType {
	public ArrayList<CoordTuple> getCoords(CoordTuple origin, int size);
}
