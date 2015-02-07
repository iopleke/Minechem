package minechem.apparatus.prefab.tileEntity.storageTypes;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Implementation of a basic single fluid type tank
 */
public class BasicFluidTank implements IFluidHandler
{
    public FluidTank tank;

    /**
     * Creates a new fluid tank with a specific capacity
     *
     * @param capacity max millibuckets for the tank
     */
    public BasicFluidTank(int capacity)
    {
        tank = new FluidTank(capacity);
    }

    /**
     * Can the tank be drained
     *
     * @param from  which direction is being checked
     * @param fluid the fluid object
     * @return boolean based on the fluid
     */
    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        if (fluid != null && fluid.getID() == tank.getFluid().fluidID)
        {
            return tank.getFluidAmount() > 0;
        }
        return false;
    }

    /**
     * Can the tank be filled
     *
     * @param from  which direction is being checked
     * @param fluid the fluid object
     * @return boolean based on the fluid
     */
    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        if (tank != null)
        {
            if (tank.getFluid().fluidID == fluid.getID())
            {
                return tank.getFluidAmount() < tank.getCapacity();
            } else if (tank.getFluidAmount() == 0)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Attempt to drain a resource from the tank
     *
     * @param from     which direction is being checked
     * @param resource the fluid stack being drained
     * @param doDrain  should it actually be drained
     * @return FluidStack object
     */
    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource != null && resource.isFluidEqual(tank.getFluid()))
        {
            return tank.drain(resource.amount, doDrain);
        }
        return null;
    }

    /**
     * Attemt to drain any fluid from the tank
     *
     * @param from    which direction is being checked
     * @param amount  how much to drain
     * @param doDrain should it actually be drained
     * @return FluidStack object
     */
    @Override
    public FluidStack drain(ForgeDirection from, int amount, boolean doDrain)
    {
        if (amount <= tank.getFluidAmount())
        {
            return tank.drain(amount, doDrain);
        }
        return tank.drain(tank.getFluidAmount(), doDrain);
    }

    /**
     * Fill the tank with a resource from a specific side
     *
     * @param from     which side is the tank being filled from
     * @param resource the FluidStack to fill with
     * @param doFill   should it be filled
     * @return how much was actually filled
     */
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        if (tank != null & resource != null)
        {
            if (tank.getFluidAmount() > 0 && resource.isFluidEqual(tank.getFluid()) || tank.getFluidAmount() == 0)
            {
                return tank.fill(resource, doFill);

            }
        }
        return 0;
    }

    /**
     * Get info for the specific tank
     *
     * @param from which side is requesting the info
     * @return FluidTankInfo array
     */
    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        FluidTankInfo[] tankInfo = new FluidTankInfo[1];
        tankInfo[0] = tank.getInfo();
        return tankInfo;
    }

}
