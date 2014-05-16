package minechem.common.assembler;

import java.util.ArrayList;

import minechem.common.utils.CoordTuple;

public interface IShapeType {
    public ArrayList<CoordTuple> getCoords(CoordTuple origin, int size);
}
