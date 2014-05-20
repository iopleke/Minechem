package minechem.item.blueprint;

import java.util.HashMap;

import minechem.MinechemBlockGeneration;
import minechem.item.blueprint.BlueprintBlock.Type;

public class BlueprintFission extends MinechemBlueprint
{

    private static int w = wildcard;
    private static int A = 1;
    private static int C = 2;
    private static Integer[][][] structure =
    {
    {
    { C, C, C, C, C },
    { C, C, C, C, C },
    { C, C, C, C, C },
    { C, C, C, C, C },
    { C, C, C, C, C } }

    ,
    {
    { C, C, C, C, C },
    { C, w, w, w, C },
    { C, w, w, w, C },
    { C, w, w, w, C },
    { C, C, C, C, C } },
    {
    { C, C, C, C, C },
    { C, w, w, w, C },
    { C, w, w, w, C },
    { C, w, w, w, C },
    { C, C, C, C, C } },
    {
    { C, C, C, C, C },
    { C, w, w, w, C },
    { C, w, w, w, C },
    { C, w, w, w, C },
    { C, C, C, C, C } },
    {
    { C, C, C, C, C },
    { C, C, C, C, C },
    { C, C, C, C, C },
    { C, C, C, C, C },
    { C, C, C, C, C } }

    };

    public BlueprintFission()
    {
        super(5, 5, 5);
        this.name = "blueprintFisssion";
    }

    @Override
    public HashMap<Integer, BlueprintBlock> getBlockLookup()
    {
        HashMap<Integer, BlueprintBlock> lookup = new HashMap<Integer, BlueprintBlock>();
        lookup.put(A, new BlueprintBlock(MinechemBlockGeneration.fusion, 0, Type.PROXY));
        lookup.put(C, new BlueprintBlock(MinechemBlockGeneration.fusion, 1, Type.PROXY));
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
        return 2;
    }

    @Override
    public int getManagerPosY()
    {
        return 2;
    }

    @Override
    public int getManagerPosZ()
    {
        return 2;
    }

    @Override
    public BlueprintBlock getManagerBlock()
    {
        return new BlueprintBlock(MinechemBlockGeneration.fusion, 3, Type.MANAGER);
    }

}
