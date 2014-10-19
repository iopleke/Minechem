package minechem.item.blueprint;

import java.util.HashMap;
import minechem.MinechemBlocksGeneration;
import minechem.item.blueprint.BlueprintBlock.Type;

public class BlueprintFusion extends MinechemBlueprint
{

	private static int w = wildcard;
	private static int A = 1;
	private static int C = 2;
	private static Integer[][][] structure =
	{
		{
			{
				w, w, w, w, A, A, A, A, A, w, w, w, w
			},
			{
				w, w, A, A, A, A, A, A, A, A, A, w, w
			},
			{
				w, A, A, A, C, C, C, C, C, A, A, A, w
			},
			{
				w, A, A, C, C, C, C, C, C, C, A, A, w
			},
			{
				A, A, C, C, C, C, C, C, C, C, C, A, A
			},
			{
				A, A, C, C, C, C, C, C, C, C, C, A, A
			},
			{
				A, A, C, C, C, C, C, C, C, C, C, A, A
			},
			{
				A, A, C, C, C, C, C, C, C, C, C, A, A
			},
			{
				A, A, C, C, C, C, C, C, C, C, C, A, A
			},
			{
				w, A, A, C, C, C, C, C, C, C, A, A, w
			},
			{
				w, A, A, A, C, C, C, C, C, A, A, A, w
			},
			{
				w, w, A, A, A, A, A, A, A, A, A, w, w
			},
			{
				w, w, w, w, A, A, A, A, A, w, w, w, w
			}
		},
		{
			{
				w, w, w, w, A, A, A, A, A, w, w, w, w
			},
			{
				w, w, A, A, C, C, C, C, C, A, A, w, w
			},
			{
				w, A, C, C, w, w, w, w, w, C, C, A, w
			},
			{
				w, A, C, w, w, w, w, w, w, w, C, A, w
			},
			{
				A, C, w, w, w, w, w, w, w, w, w, C, A
			},
			{
				A, C, w, w, w, w, C, w, w, w, w, C, A
			},
			{
				A, C, w, w, w, C, 0, C, w, w, w, C, A
			},
			{
				A, C, w, w, w, w, C, w, w, w, w, C, A
			},
			{
				A, C, w, w, w, w, w, w, w, w, w, C, A
			},
			{
				w, A, C, w, w, w, w, w, w, w, C, A, w
			},
			{
				w, A, C, C, w, w, w, w, w, C, C, A, w
			},
			{
				w, w, A, A, C, C, C, C, C, A, A, w, w
			},
			{
				w, w, w, w, A, A, A, A, A, w, w, w, w
			},
		},
		{
			{
				w, w, w, w, A, A, A, A, A, w, w, w, w
			},
			{
				w, w, A, A, C, C, C, C, C, A, A, w, w
			},
			{
				w, A, C, C, w, w, w, w, w, C, C, A, w
			},
			{
				w, A, C, w, w, w, w, w, w, w, C, A, w
			},
			{
				A, C, w, w, w, w, w, w, w, w, w, C, A
			},
			{
				A, C, w, w, w, w, C, w, w, w, w, C, A
			},
			{
				A, C, w, w, w, C, 0, C, w, w, w, C, A
			},
			{
				A, C, w, w, w, w, C, w, w, w, w, C, A
			},
			{
				A, C, w, w, w, w, w, w, w, w, w, C, A
			},
			{
				w, A, C, w, w, w, w, w, w, w, C, A, w
			},
			{
				w, A, C, C, w, w, w, w, w, C, C, A, w
			},
			{
				w, w, A, A, C, C, C, C, C, A, A, w, w
			},
			{
				w, w, w, w, A, A, A, A, A, w, w, w, w
			},
		},
		{
			{
				w, w, w, w, A, A, A, A, A, w, w, w, w
			},
			{
				w, w, A, A, C, C, C, C, C, A, A, w, w
			},
			{
				w, A, C, C, w, w, w, w, w, C, C, A, w
			},
			{
				w, A, C, w, w, w, w, w, w, w, C, A, w
			},
			{
				A, C, w, w, w, w, w, w, w, w, w, C, A
			},
			{
				A, C, w, w, w, w, C, w, w, w, w, C, A
			},
			{
				A, C, w, w, w, C, 0, C, w, w, w, C, A
			},
			{
				A, C, w, w, w, w, C, w, w, w, w, C, A
			},
			{
				A, C, w, w, w, w, w, w, w, w, w, C, A
			},
			{
				w, A, C, w, w, w, w, w, w, w, C, A, w
			},
			{
				w, A, C, C, w, w, w, w, w, C, C, A, w
			},
			{
				w, w, A, A, C, C, C, C, C, A, A, w, w
			},
			{
				w, w, w, w, A, A, A, A, A, w, w, w, w
			},
		},
		{
			{
				w, w, w, w, w, w, w, w, w, w, w, w, w
			},
			{
				w, w, w, w, w, w, w, w, w, w, w, w, w
			},
			{
				w, w, w, w, C, C, C, C, C, w, w, w, w
			},
			{
				w, w, w, C, C, C, C, C, C, C, w, w, w
			},
			{
				w, w, C, C, C, C, C, C, C, C, C, w, w
			},
			{
				w, w, C, C, C, C, A, C, C, C, C, w, w
			},
			{
				w, w, C, C, C, A, A, A, C, C, C, w, w
			},
			{
				w, w, C, C, C, C, A, C, C, C, C, w, w
			},
			{
				w, w, C, C, C, C, C, C, C, C, C, w, w
			},
			{
				w, w, w, C, C, C, C, C, C, C, w, w, w
			},
			{
				w, w, w, w, C, C, C, C, C, w, w, w, w
			},
			{
				w, w, w, w, w, w, w, w, w, w, w, w, w
			},
			{
				w, w, w, w, w, w, w, w, w, w, w, w, w
			}
		},
	};

	public BlueprintFusion()
	{
		super(13, 5, 13);
		this.name = "blueprintFusion";
	}

	@Override
	public HashMap<Integer, BlueprintBlock> getBlockLookup()
	{
		HashMap<Integer, BlueprintBlock> lookup = new HashMap<Integer, BlueprintBlock>();
		lookup.put(A, new BlueprintBlock(MinechemBlocksGeneration.fusion, 0, Type.PROXY));
		lookup.put(C, new BlueprintBlock(MinechemBlocksGeneration.fusion, 1, Type.NORMAL));
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
		return new BlueprintBlock(MinechemBlocksGeneration.fusion, 2, Type.MANAGER);
	}

}
