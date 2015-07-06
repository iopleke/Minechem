package minechem.fluid;

import java.util.Random;
import minechem.Settings;
import minechem.fluid.reaction.ChemicalFluidReactionHandler;
import minechem.item.ChemicalRoomStateEnum;
import minechem.item.MinechemChemicalType;
import minechem.radiation.RadiationEnum;
import minechem.radiation.RadiationFluidTileEntity;
import minechem.utils.MinechemUtil;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class MinechemFluidBlock extends BlockFluidClassic implements ITileEntityProvider
{
    private final boolean isRadioactivity;
    private final boolean solid;

    public MinechemFluidBlock(MinechemFluid fluid)
    {
        super(fluid, Material.water);
        setQuantaPerBlock(fluid.getQuanta());

        if (fluid instanceof FluidElement)
        {
            isRadioactivity = ((FluidElement) fluid).element.radioactivity() != RadiationEnum.stable;
        } else if (fluid instanceof FluidMolecule)
        {
            isRadioactivity = ((FluidMolecule) fluid).molecule.radioactivity() != RadiationEnum.stable;
        } else
        {
            isRadioactivity = false;
        }

        isBlockContainer = true;
        solid = fluid.getChemical().roomState() == ChemicalRoomStateEnum.solid;
    }

    @Override
    public String getUnlocalizedName()
    {
        String fluidUnlocalizedName = getFluid().getUnlocalizedName();
        return fluidUnlocalizedName.substring(0, fluidUnlocalizedName.length() - 5);// Splits off ".name"
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
    {
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
        checkStatus(world, x, y, z);
    }

    public void checkStatus(World world, int x, int y, int z)
    {
        if (world.isRemote)
        {
            return;
        }

        if (Settings.reactionFluidMeetFluid)
        {
            for (EnumFacing face : EnumFacing.values())
            {
                if (checkToReact(world, x + face.getFrontOffsetX(), y + face.getFrontOffsetY(), z + face.getFrontOffsetZ(), x, y, z))
                {
                    return;
                }
            }
        }

        checkToExplode(world, x, y, z);
    }

    private boolean checkToReact(World world, int dx, int dy, int dz, int sx, int sy, int sz)
    {
        return ChemicalFluidReactionHandler.checkToReact(this, world.getBlock(dx, dy, dz), world, dx, dy, dz, sx, sy, sz);
    }

    private void checkToExplode(World world, int x, int y, int z)
    {
        MinechemChemicalType type = MinechemUtil.getChemical(this);
        float level = ExplosiveFluidHandler.getInstance().getExplosiveFluid(type);
        if (Float.isNaN(level))
        {
            return;
        }

        boolean flag = false;
        for (EnumFacing face : EnumFacing.values())
        {
            if (ExplosiveFluidHandler.getInstance().existingFireSource(world.getBlock(x + face.getFrontOffsetX(), y + face.getFrontOffsetY(), z + face.getFrontOffsetZ())))
            {
                flag = true;
                break;
            }
        }
        if (!flag)
        {
            return;
        }

        world.func_147480_a(x, y, z, true);
        world.setBlockToAir(x, y, z);
        world.createExplosion(null, x, y, z, ExplosiveFluidHandler.getInstance().getExplosiveFluid(type), true);
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return isRadioactivity && (metadata == 0);
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

    @Override
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion)
    {
        if (world.isRemote)
        {
            return;
        }

        MinechemChemicalType type = MinechemUtil.getChemical(this);
        world.func_147480_a(x, y, z, true);
        world.setBlockToAir(x, y, z);
        world.createExplosion(null, x, y, z, ExplosiveFluidHandler.getInstance().getExplosiveFluid(type), true);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (!solid)
        {
            super.updateTick(world, x, y, z, rand);
        }
        checkStatus(world, x, y, z);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        checkStatus(world, x, y, z);
    }
}
