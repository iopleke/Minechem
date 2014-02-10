package pixlepix.minechem.common.blueprint;

import net.minecraft.block.Block;
import pixlepix.minechem.common.blueprint.BlueprintBlock.Type;

import java.util.HashMap;

public class BlueprintDirewolf extends MinechemBlueprint {

    Integer[][][] structure = {
            {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1},},
            {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 1, 1, 1, 0, 1, 1, 1, 1},},
            {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 1, 1, 1, 0, 1, 1, 1, 1},},
            {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1},},
            {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1},},
            {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 2, 2, 1, 2, 2, 1, 1}, {1, 1, 2, 2, 1, 2, 2, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 2, 2, 1, 2, 2, 1, 1}, {1, 1, 2, 2, 1, 2, 2, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1},},};

    public BlueprintDirewolf() {
        super(9, 6, 9);
        this.name = "Direwolf's 9x9";
    }

    @Override
    public HashMap<Integer, BlueprintBlock> getBlockLookup() {
        HashMap<Integer, BlueprintBlock> lookup = new HashMap<Integer, BlueprintBlock>();
        lookup.put(1, new BlueprintBlock(Block.stone, 0, Type.NORMAL));
        lookup.put(2, new BlueprintBlock(Block.glass, 0, Type.NORMAL));
        return lookup;
    }

    @Override
    public Integer[][][] getStructure() {
        return structure;
    }

    @Override
    public Integer[][][] getResultStructure() {
        return structure;
    }

    @Override
    public int getManagerPosX() {
        return -1;
    }

    @Override
    public int getManagerPosY() {
        return -1;
    }

    @Override
    public int getManagerPosZ() {
        return -1;
    }

    @Override
    public BlueprintBlock getManagerBlock() {
        return null;
    }

}
