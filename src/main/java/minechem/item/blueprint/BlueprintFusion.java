package minechem.item.blueprint;

import java.util.HashMap;

<<<<<<< HEAD
<<<<<<< HEAD
import minechem.MinechemBlockGeneration;
=======
import minechem.MinechemBlocksGeneration;
>>>>>>> MaxwolfRewrite
=======
import minechem.MinechemBlocksGeneration;
>>>>>>> MaxwolfRewrite
import minechem.item.blueprint.BlueprintBlock.Type;

public class BlueprintFusion extends MinechemBlueprint
{

    private static int w = wildcard;
    private static int A = 1;
    private static int C = 2;
    private static Integer[][][] structure =
    {
    {
    { w, w, w, w, A, A, A, A, A, w, w, w, w },
    { w, w, A, A, A, A, A, A, A, A, A, w, w },
    { w, A, A, A, C, C, C, C, C, A, A, A, w },
    { w, A, A, C, C, C, C, C, C, C, A, A, w },
    { A, A, C, C, C, C, C, C, C, C, C, A, A },
    { A, A, C, C, C, C, C, C, C, C, C, A, A },
    { A, A, C, C, C, C, C, C, C, C, C, A, A },
    { A, A, C, C, C, C, C, C, C, C, C, A, A },
    { A, A, C, C, C, C, C, C, C, C, C, A, A },
    { w, A, A, C, C, C, C, C, C, C, A, A, w },
    { w, A, A, A, C, C, C, C, C, A, A, A, w },
    { w, w, A, A, A, A, A, A, A, A, A, w, w },
    { w, w, w, w, A, A, A, A, A, w, w, w, w } },
    {
    { w, w, w, w, A, A, A, A, A, w, w, w, w },
    { w, w, A, A, C, C, C, C, C, A, A, w, w },
    { w, A, C, C, w, w, w, w, w, C, C, A, w },
    { w, A, C, w, w, w, w, w, w, w, C, A, w },
    { A, C, w, w, w, w, w, w, w, w, w, C, A },
    { A, C, w, w, w, w, C, w, w, w, w, C, A },
    { A, C, w, w, w, C, 0, C, w, w, w, C, A },
    { A, C, w, w, w, w, C, w, w, w, w, C, A },
    { A, C, w, w, w, w, w, w, w, w, w, C, A },
    { w, A, C, w, w, w, w, w, w, w, C, A, w },
    { w, A, C, C, w, w, w, w, w, C, C, A, w },
    { w, w, A, A, C, C, C, C, C, A, A, w, w },
    { w, w, w, w, A, A, A, A, A, w, w, w, w }, },
    {
    { w, w, w, w, A, A, A, A, A, w, w, w, w },
    { w, w, A, A, C, C, C, C, C, A, A, w, w },
    { w, A, C, C, w, w, w, w, w, C, C, A, w },
    { w, A, C, w, w, w, w, w, w, w, C, A, w },
    { A, C, w, w, w, w, w, w, w, w, w, C, A },
    { A, C, w, w, w, w, C, w, w, w, w, C, A },
    { A, C, w, w, w, C, 0, C, w, w, w, C, A },
    { A, C, w, w, w, w, C, w, w, w, w, C, A },
    { A, C, w, w, w, w, w, w, w, w, w, C, A },
    { w, A, C, w, w, w, w, w, w, w, C, A, w },
    { w, A, C, C, w, w, w, w, w, C, C, A, w },
    { w, w, A, A, C, C, C, C, C, A, A, w, w },
    { w, w, w, w, A, A, A, A, A, w, w, w, w }, },
    {
    { w, w, w, w, A, A, A, A, A, w, w, w, w },
    { w, w, A, A, C, C, C, C, C, A, A, w, w },
    { w, A, C, C, w, w, w, w, w, C, C, A, w },
    { w, A, C, w, w, w, w, w, w, w, C, A, w },
    { A, C, w, w, w, w, w, w, w, w, w, C, A },
    { A, C, w, w, w, w, C, w, w, w, w, C, A },
    { A, C, w, w, w, C, 0, C, w, w, w, C, A },
    { A, C, w, w, w, w, C, w, w, w, w, C, A },
    { A, C, w, w, w, w, w, w, w, w, w, C, A },
    { w, A, C, w, w, w, w, w, w, w, C, A, w },
    { w, A, C, C, w, w, w, w, w, C, C, A, w },
    { w, w, A, A, C, C, C, C, C, A, A, w, w },
    { w, w, w, w, A, A, A, A, A, w, w, w, w }, },
    {
    { w, w, w, w, w, w, w, w, w, w, w, w, w },
    { w, w, w, w, w, w, w, w, w, w, w, w, w },
    { w, w, w, w, C, C, C, C, C, w, w, w, w },
    { w, w, w, C, C, C, C, C, C, C, w, w, w },
    { w, w, C, C, C, C, C, C, C, C, C, w, w },
    { w, w, C, C, C, C, A, C, C, C, C, w, w },
    { w, w, C, C, C, A, A, A, C, C, C, w, w },
    { w, w, C, C, C, C, A, C, C, C, C, w, w },
    { w, w, C, C, C, C, C, C, C, C, C, w, w },
    { w, w, w, C, C, C, C, C, C, C, w, w, w },
    { w, w, w, w, C, C, C, C, C, w, w, w, w },
    { w, w, w, w, w, w, w, w, w, w, w, w, w },
    { w, w, w, w, w, w, w, w, w, w, w, w, w } }, };

    public BlueprintFusion()
    {
        super(13, 5, 13);
        this.name = "blueprintFusion";
    }

    @Override
    public HashMap<Integer, BlueprintBlock> getBlockLookup()
    {
        HashMap<Integer, BlueprintBlock> lookup = new HashMap<Integer, BlueprintBlock>();
<<<<<<< HEAD
<<<<<<< HEAD
        lookup.put(A, new BlueprintBlock(MinechemBlockGeneration.fusion, 0, Type.PROXY));
        lookup.put(C, new BlueprintBlock(MinechemBlockGeneration.fusion, 1, Type.NORMAL));
=======
        lookup.put(A, new BlueprintBlock(MinechemBlocksGeneration.fusion, 0, Type.PROXY));
        lookup.put(C, new BlueprintBlock(MinechemBlocksGeneration.fusion, 1, Type.NORMAL));
>>>>>>> MaxwolfRewrite
=======
        lookup.put(A, new BlueprintBlock(MinechemBlocksGeneration.fusion, 0, Type.PROXY));
        lookup.put(C, new BlueprintBlock(MinechemBlocksGeneration.fusion, 1, Type.NORMAL));
>>>>>>> MaxwolfRewrite
        return lookup;
    }

    @Override
    public Integer[][][] getStructure()
    {
        return structure;
    }

    @Override
    public Integer[][][] getResultStructure()
    {
        return structure;
    }

    @Override
    public int getManagerPosX()
    {
        return 6;
    }

    @Override
    public int getManagerPosY()
    {
        return 1;
    }

    @Override
    public int getManagerPosZ()
    {
        return 6;
    }

    @Override
    public BlueprintBlock getManagerBlock()
    {
<<<<<<< HEAD
<<<<<<< HEAD
        return new BlueprintBlock(MinechemBlockGeneration.fusion, 2, Type.MANAGER);
=======
        return new BlueprintBlock(MinechemBlocksGeneration.fusion, 2, Type.MANAGER);
>>>>>>> MaxwolfRewrite
=======
        return new BlueprintBlock(MinechemBlocksGeneration.fusion, 2, Type.MANAGER);
>>>>>>> MaxwolfRewrite
    }

}
