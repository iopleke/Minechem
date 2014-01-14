//1.5 Code
//Switching to UE to get this information
//Leaving this here 'Just in Case'

/*
  package pixlepix.minechem.particlephysics;


import net.minecraft.tileentity.TileEntity;
import buildcraft.api.core.SafeTimeTracker;
import buildcraft.api.power.IPowerReceptor;

public class MinechemPowerProvider extends PowerProvider {

    public static final int ENERGY_USAGE_UPDATE_RATE = 10;
    float lastEnergyStored = 0.0F;
    float energyUsageAccumulator = 0.0F;
    float lastEnergyUsage = 0.0F;
    float currentEnergyUsage = 0.0F;
    boolean didEnergyStoredChange;
    boolean didEnergyUsageChange;
    SafeTimeTracker energyUsageTracker = new SafeTimeTracker();

    public MinechemPowerProvider(int minEnergyReceived, int maxEnergyReceived, int activationEnergy, int maxStoredEnergy) {
        this.configure(0, minEnergyReceived, maxEnergyReceived, activationEnergy, maxStoredEnergy);
    }

    public boolean didEnergyStoredChange() {
        boolean didChange = didEnergyStoredChange;
        didEnergyStoredChange = false;
        return didChange;
    }

    public boolean didEnergyUsageChange() {
        boolean didChange = didEnergyUsageChange;
        didEnergyUsageChange = false;
        return didChange;
    }

    public void setEnergyStored(float amount) {
        this.energyStored = Math.min(amount, maxEnergyStored);
    }

    public void setCurrentEnergyUsage(float amount) {
        currentEnergyUsage = amount;
    }

    public float getCurrentEnergyUsage() {
        return currentEnergyUsage;
    }

    @Override
    public boolean update(IPowerReceptor receptor) {
        if (energyStored != lastEnergyStored)
            didEnergyStoredChange = true;
        lastEnergyStored = energyStored;

        TileEntity tile = (TileEntity) receptor;
        if (!tile.worldObj.isRemote && energyUsageTracker.markTimeIfDelay(tile.worldObj, ENERGY_USAGE_UPDATE_RATE)) {
            currentEnergyUsage = energyUsageAccumulator / ENERGY_USAGE_UPDATE_RATE;
            if (currentEnergyUsage != lastEnergyUsage)
                didEnergyUsageChange = true;
            lastEnergyUsage = currentEnergyUsage;
            energyUsageAccumulator = 0.0F;
        }
        return super.update(receptor);
    }

    @Override
    public float useEnergy(float min, float max, boolean doUse) {
        float energyUsage = super.useEnergy(min, max, doUse);
        energyUsageAccumulator += energyUsage;
        return energyUsage;
    }

}
*/
