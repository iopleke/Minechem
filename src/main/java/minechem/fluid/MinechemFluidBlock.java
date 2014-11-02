package minechem.fluid;

import minechem.Settings;
import minechem.fluid.reaction.ChemicalFluidReactionHandler;
import minechem.radiation.RadiationEnum;
import minechem.radiation.RadiationFluidTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class MinechemFluidBlock extends BlockFluidClassic implements ITileEntityProvider
{
	private final boolean isRadioactivity;
	private final String unlocalizedName;
	public static int RENDER_ID;

	public MinechemFluidBlock(MinechemFluid fluid, Material material)
	{
		super(fluid, material);
		setQuantaPerBlock(fluid.getQuanta());
		unlocalizedName = fluid.getUnlocalizedName();

		if (fluid instanceof FluidElement)
		{
			isRadioactivity = ((FluidElement) fluid).element.radioactivity() != RadiationEnum.stable;
		} else if (fluid instanceof FluidChemical)
		{
			isRadioactivity = ((FluidChemical) fluid).molecule.radioactivity() != RadiationEnum.stable;
		} else
		{
			isRadioactivity = false;
		}

		isBlockContainer = true;
	}

	@Override
	public String getUnlocalizedName()
	{
		return unlocalizedName;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
	{
		super.onNeighborBlockChange(world, x, y, z, neighborBlock);

		if (Settings.reactionFluidMeetFluid)
		{
			checkToExplode(world, x + 1, y, z, x, y, z);
			checkToExplode(world, x, y + 1, z, x, y, z);
			checkToExplode(world, x, y, z + 1, x, y, z);
			checkToExplode(world, x - 1, y, z, x, y, z);
			checkToExplode(world, x, y - 1, z, x, y, z);
			checkToExplode(world, x, y, z - 1, x, y, z);
		}
	}

	@Override
	public int getRenderType()
	{
		return RENDER_ID;
	}

	private boolean checkToExplode(World world, int dx, int dy, int dz, int sx, int sy, int sz)
	{
		return ChemicalFluidReactionHandler.checkToReact(this, world.getBlock(dx, dy, dz), world, dx, dy, dz, sx, sy, sz);
	}

	@Override
	public boolean hasTileEntity(int metadata)
	{
		return isRadioactivity && metadata == 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return hasTileEntity(metadata) ? new RadiationFluidTileEntity() : null;
	}

	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventID, int eventParameter)
	{
		super.onBlockEventReceived(world, x, y, z, eventID, eventParameter);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		return tileentity != null ? tileentity.receiveClientEvent(eventID, eventParameter) : false;
	}
}
