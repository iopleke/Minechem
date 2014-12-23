package minechem.tileentity.blueprintprojector;

import minechem.MinechemBlocksGeneration;
import minechem.MinechemItemsRegistration;
import minechem.item.blueprint.BlueprintBlock;
import minechem.item.blueprint.BlueprintBlock.Type;
import minechem.item.blueprint.MinechemBlueprint;
import minechem.sound.LoopingSound;
import minechem.tileentity.multiblock.MultiBlockStatusEnum;
import minechem.tileentity.multiblock.MultiBlockTileEntity;
import minechem.tileentity.multiblock.fusion.FusionTileEntity;
import minechem.tileentity.multiblock.ghostblock.GhostBlockTileEntity;
import minechem.tileentity.prefab.MinechemTileEntity;
import minechem.tileentity.prefab.TileEntityProxy;
import minechem.utils.LocalPosition;
import minechem.utils.LocalPosition.Pos3;
import minechem.utils.MinechemUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashMap;

public class BlueprintProjectorTileEntity extends MinechemTileEntity
{
	private static int air;
	MinechemBlueprint blueprint;

	boolean isComplete = false;
	Integer[][][] structure;
	LoopingSound projectorSound;

	public BlueprintProjectorTileEntity()
	{
		this.projectorSound = new LoopingSound("minechem:projector", 20);
		this.projectorSound.setVolume(.2F);
		this.inventory = new ItemStack[getSizeInventory()];
	}

	@Override
	public void updateEntity()
	{
		if (blueprint != null && !isComplete)
		{
			projectBlueprint();
			this.projectorSound.play(worldObj, xCoord, yCoord, zCoord);
		}
	}

	private void projectBlueprint()
	{
		int facing = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		ForgeDirection direction = MinechemUtil.getDirectionFromFacing(facing);
		LocalPosition position = new LocalPosition(xCoord, yCoord, zCoord, direction);
		position.moveForwards(blueprint.zSize + 1);
		position.moveLeft(Math.floor(blueprint.xSize / 2));
		boolean shouldProjectGhostBlocks = true;
		int totalIncorrectCount = blueprint.getTotalSize();

		for (int x = 0; x < blueprint.xSize; x++)
		{
			int verticalIncorrectCount = blueprint.getVerticalSliceSize();
			for (int y = 0; y < blueprint.ySize; y++)
			{
				for (int z = 0; z < blueprint.zSize; z++)
				{
					if (shouldProjectGhostBlocks)
					{
						MultiBlockStatusEnum multiBlockStatusEnum;
						if (isManagerBlock(x, y, z))
						{
							multiBlockStatusEnum = MultiBlockStatusEnum.CORRECT;
						} else
						{
							multiBlockStatusEnum = projectGhostBlock(x, y, z, position);
						}
						if (multiBlockStatusEnum == MultiBlockStatusEnum.CORRECT)
						{
							verticalIncorrectCount--;
							totalIncorrectCount--;
						}
					} else
					{
						destroyGhostBlock(x, y, z, position);
					}
				}
			}
			if (verticalIncorrectCount != 0)
			{
				shouldProjectGhostBlocks = false;
			}
		}

		if (totalIncorrectCount == 0 && (!isComplete || !(worldObj.getTileEntity(blueprint.getManagerPosX(), blueprint.getManagerPosY(), blueprint.getManagerPosZ()) instanceof MultiBlockTileEntity)))
		{
			isComplete = true;
			buildStructure(position);
		}
	}

	private void buildStructure(LocalPosition position)
	{
		Integer[][][] resultStructure = blueprint.getResultStructure();
		HashMap<Integer, BlueprintBlock> blockLookup = blueprint.getBlockLookup();

		TileEntity managerTileEntity = buildManagerBlock(position);

		for (int x = 0; x < blueprint.xSize; x++)
		{
			for (int y = 0; y < blueprint.ySize; y++)
			{
				for (int z = 0; z < blueprint.zSize; z++)
				{
					if (isManagerBlock(x, y, z))
					{
						continue;
					}
					int structureId = resultStructure[y][x][z];
					setBlock(x, y, z, position, structureId, blockLookup, managerTileEntity);
				}
			}
		}
	}

	private boolean isManagerBlock(int x, int y, int z)
	{
		return x == blueprint.getManagerPosX() && y == blueprint.getManagerPosY() && z == blueprint.getManagerPosZ();
	}

	private TileEntity buildManagerBlock(LocalPosition position)
	{
		BlueprintBlock managerBlock = blueprint.getManagerBlock();
		if (managerBlock != null)
		{
			Pos3 worldPos = position.getLocalPos(blueprint.getManagerPosX(), blueprint.getManagerPosY(), blueprint.getManagerPosZ());
			worldObj.setBlock(worldPos.x, worldPos.y, worldPos.z, managerBlock.block, managerBlock.metadata, 3);
			if (this.blueprint == MinechemBlueprint.fusion && worldObj.getTileEntity(worldPos.x, worldPos.y, worldPos.z) == null)
			{
				FusionTileEntity fusion = new FusionTileEntity();
				fusion.setWorldObj(this.worldObj);
				fusion.xCoord = worldPos.x;
				fusion.yCoord = worldPos.y;
				fusion.zCoord = worldPos.z;
				fusion.blockType = MinechemBlocksGeneration.fusion;
				worldObj.addTileEntity(fusion);
			}
			return worldObj.getTileEntity(worldPos.x, worldPos.y, worldPos.z);
		} else
		{
			return null;
		}
	}

	private void setBlock(int x, int y, int z, LocalPosition position, int structureId, HashMap<Integer, BlueprintBlock> blockLookup, TileEntity managerTileEntity)
	{
		Pos3 worldPos = position.getLocalPos(x, y, z);
		if (structureId == MinechemBlueprint.wildcard)
		{
			return;
		}
		if (structureId == air)
		{
			worldObj.setBlockToAir(worldPos.x, worldPos.y, worldPos.z);
		} else
		{
			BlueprintBlock blueprintBlock = blockLookup.get(structureId);
			if (blueprintBlock.type == Type.MANAGER)
			{
				return;
			}
			worldObj.setBlock(worldPos.x, worldPos.y, worldPos.z, blueprintBlock.block, blueprintBlock.metadata, 3);
			if (blueprintBlock.type == Type.PROXY)
			{
				TileEntity te = worldObj.getTileEntity(worldPos.x, worldPos.y, worldPos.z);
				if (te instanceof TileEntityProxy)
				{
					TileEntityProxy proxy = (TileEntityProxy) te;
				}
			}

		}
	}

	private MultiBlockStatusEnum projectGhostBlock(int x, int y, int z, LocalPosition position)
	{
		Pos3 worldPos = position.getLocalPos(x, y, z);
		Integer structureID = structure[y][x][z];
		Block block = worldObj.getBlock(worldPos.x, worldPos.y, worldPos.z);
		int blockMetadata = worldObj.getBlockMetadata(worldPos.x, worldPos.y, worldPos.z);
		if (structureID == MinechemBlueprint.wildcard)
		{
			return MultiBlockStatusEnum.CORRECT;
		} else if (structureID == air)
		{
			if (block.isAir(worldObj,x,y,z))
			{
				return MultiBlockStatusEnum.CORRECT;
			} else
			{
				return MultiBlockStatusEnum.INCORRECT;
			}
		} else
		{
			HashMap<Integer, BlueprintBlock> lut = blueprint.getBlockLookup();
			BlueprintBlock blueprintBlock = lut.get(structureID);
			if (block.isAir(worldObj,x,y,z))
			{
				createGhostBlock(worldPos.x, worldPos.y, worldPos.z, structureID);
				return MultiBlockStatusEnum.INCORRECT;
			} else if (block == blueprintBlock.block && (blockMetadata == blueprintBlock.metadata || blueprintBlock.metadata == -1))
			{
				return MultiBlockStatusEnum.CORRECT;
			} else
			{
				return MultiBlockStatusEnum.INCORRECT;
			}
		}
	}

	private void createGhostBlock(int x, int y, int z, int blockID)
	{
		worldObj.setBlock(x, y, z, MinechemBlocksGeneration.ghostBlock, 0, 3);
		TileEntity tileEntity = worldObj.getTileEntity(x, y, z);
		if (tileEntity instanceof GhostBlockTileEntity)
		{
			GhostBlockTileEntity ghostBlock = (GhostBlockTileEntity) tileEntity;
			ghostBlock.setBlueprintAndID(blueprint, blockID);
		}
	}

	public void destroyProjection()
	{
		if (this.blueprint == null)
		{
			return;
		}
		int facing = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		ForgeDirection direction = MinechemUtil.getDirectionFromFacing(facing);
		LocalPosition position = new LocalPosition(xCoord, yCoord, zCoord, direction);
		position.moveForwards(blueprint.zSize + 1);
		position.moveLeft(Math.floor(blueprint.xSize / 2));
		for (int x = 0; x < blueprint.xSize; x++)
		{
			for (int y = 0; y < blueprint.ySize; y++)
			{
				for (int z = 0; z < blueprint.zSize; z++)
				{
					destroyGhostBlock(x, y, z, position);
				}
			}
		}
	}

	private void destroyGhostBlock(int x, int y, int z, LocalPosition position)
	{
		Pos3 worldPos = position.getLocalPos(x, y, z);
		Block block = worldObj.getBlock(worldPos.x, worldPos.y, worldPos.z);
		if (block == MinechemBlocksGeneration.ghostBlock)
		{
			worldObj.setBlockToAir(worldPos.x, worldPos.y, worldPos.z);
		}
	}

	public int getFacing()
	{
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}

	public void setBlueprint(MinechemBlueprint blueprint)
	{
		if (blueprint != null)
		{
			this.blueprint = blueprint;
			this.structure = blueprint.getStructure();
		} else
		{
			destroyProjection();
			this.blueprint = null;
			this.structure = null;
			this.isComplete = false;
		}
	}

	public MinechemBlueprint takeBlueprint()
	{
		MinechemBlueprint blueprint = this.blueprint;
		setBlueprint(null);
		return blueprint;
	}

	public boolean hasBlueprint()
	{
		return this.blueprint != null;
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		setBlueprint(null);
		return super.decrStackSize(slot, amount);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack)
	{
		super.setInventorySlotContents(slot, itemstack);
		if (itemstack != null)
		{
			MinechemBlueprint blueprint = MinechemItemsRegistration.blueprint.getBlueprint(itemstack);
			setBlueprint(blueprint);
		}
	}

	@Override
	public String getInventoryName()
	{
		return "container.blueprintProjector";
	}

	// Does inventory has custom name.
	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);
		ItemStack blueprintStack = inventory[0];
		if (blueprintStack != null)
		{
			NBTTagCompound blueprintNBT = new NBTTagCompound();
			blueprintStack.writeToNBT(blueprintNBT);
			nbtTagCompound.setTag("blueprint", blueprintNBT);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);
		this.inventory = new ItemStack[getSizeInventory()];
		this.isComplete = false;
		NBTTagCompound blueprintNBT = (NBTTagCompound) nbtTagCompound.getTag("blueprint");
		if (blueprintNBT != null)
		{
			ItemStack blueprintStack = ItemStack.loadItemStackFromNBT(blueprintNBT);
			MinechemBlueprint blueprint = MinechemItemsRegistration.blueprint.getBlueprint(blueprintStack);
			setBlueprint(blueprint);
			this.inventory[0] = blueprintStack;
		}
	}

	public MinechemBlueprint getBlueprint()
	{
		return this.blueprint;
	}

	// Is item valid.
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return itemstack.getItem() == MinechemItemsRegistration.blueprint;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void openInventory()
	{

	}

	@Override
	public void closeInventory()
	{

	}
}
