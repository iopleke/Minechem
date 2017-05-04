package minechem.apparatus.prefab.fluid;

import minechem.interfaces.IrwNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Wrapper for a tank in a TE 
 * Internally uses {@link net.minecraftforge.fluids.FluidTank}
 */
public class TankHandler implements IFluidHandler, IrwNBT
{
    protected FluidTank tank;
    boolean drain, fill;

    /**
     * Get a tank for 1000mB
     */
    public TankHandler()
    {
        this(FluidContainerRegistry.BUCKET_VOLUME);
    }

    /**
     * Get a tank with given capacity 
     * @param capacity capacity in mB
     */
    public TankHandler(int capacity)
    {
        this(capacity, true, true);
    }

    /**
     * Get a tank for 1000mB
     * @param drain can be drained
     * @param fill can be filled
     */
    public TankHandler(boolean drain, boolean fill)
    {
        this(FluidContainerRegistry.BUCKET_VOLUME, drain, fill);
    }

    /**
     * Get a tank with given capacity
     * @param capacity capacity in mB
     * @param drain can be drained
     * @param fill can be filled
     */
    public TankHandler(int capacity, boolean drain, boolean fill)
    {
        this.drain = drain;
        this.fill = fill;
        tank = new FluidTank(capacity);
    }

    /**
     * Get a  tank that can only be drained
     * @return
     */
    public static TankHandler getSourceTank()
    {
        return new TankHandler(true, false);
    }

    /**
     * Get a  tank that can only be drained with given capacity
     * @param capacity
     * @return
     */
    public static TankHandler getSourceTank(int capacity)
    {
        return new TankHandler(capacity, true, false);
    }

    /**
     * Get a  tank that can only be filled
     * @return
     */
    public static TankHandler getDrainTank()
    {
        return new TankHandler(true, false);
    }

    /**
     * Get a  tank that can only be filled with given capacity
     * @param capacity
     * @return
     */
    public static TankHandler getDrainTank(int capacity)
    {
        return new TankHandler(capacity, true, false);
    }

    /* IrwNBT */
    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        this.tank.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        this.tank.writeToNBT(tag);
    }

    /* IFluidHandler */
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        return this.tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource == null || !resource.isFluidEqual(this.tank.getFluid()))
        {
            return null;
        }
        return this.tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return this.tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return this.fill;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return this.drain;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[] { tank.getInfo() };
    }
    
    public IFluidTank getInternalTank()
    {
        return this.tank;
    }
}
