package pixlepix.minechem.common.assembler;

import pixlepix.minechem.common.utils.CoordTuple;

import java.util.ArrayList;

public interface IShapeType {
    public ArrayList<CoordTuple> getCoords(CoordTuple origin, int size);
}
