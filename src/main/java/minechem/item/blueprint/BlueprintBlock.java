package minechem.item.blueprint;

import net.minecraft.block.Block;

public class BlueprintBlock
{
	public enum Type
	{
		NORMAL, MANAGER, PROXY
	}

	public int metadata;
	public Block block;
	public Type type;

	public BlueprintBlock(Block block, int metadata, Type type)
	{
		this.block = block;
		this.metadata = metadata;
		this.type = type;
	}
}
