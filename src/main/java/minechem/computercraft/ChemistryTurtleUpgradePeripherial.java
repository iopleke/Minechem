package minechem.computercraft;

import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IHostedPeripheral;
import dan200.turtle.api.ITurtleAccess;
import dan200.turtle.api.TurtleSide;
import minechem.api.recipe.SynthesisRecipe;
import minechem.api.util.Constants;
import minechem.common.RadiationHandler;
import minechem.common.RadiationHandler.DecayEvent;
import minechem.common.utils.SafeTimeTracker;
import minechem.computercraft.method.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class ChemistryTurtleUpgradePeripherial implements IHostedPeripheral, IMinechemTurtlePeripheral {

    private static String[] methodNames;
    private static ICCMethod[] methods = {new GetChemicalName(), new GetFormula(), new GetChemicals(), new GetAtomicMass(), new GetRadioactivity(),
		    new GetTicksUntilDecay(), new StoreSynthesisRecipe(), new PlaceSynthesisRecipe(), new ClearSynthesisRecipe(),
		    new TakeOuput(), new PutInput(), new TakeFusionStar(), new PutFusionStar(), new TakeJournal(), new PutJournal(),
		    new GetMachineState()};

    public ITurtleAccess turtle;
    public IComputerAccess computer;
    public SafeTimeTracker updateTracker = new SafeTimeTracker();
    private SynthesisRecipe synthesisRecipe;

    public static IMinechemTurtlePeripheral getMinechemPeripheral(ITurtleAccess turtle) {
        IHostedPeripheral leftPeripheral = turtle.getPeripheral(TurtleSide.Left);
        IHostedPeripheral rightPeripheral = turtle.getPeripheral(TurtleSide.Right);
        if (leftPeripheral != null && leftPeripheral instanceof IMinechemTurtlePeripheral)
            return (IMinechemTurtlePeripheral) leftPeripheral;
        else if (rightPeripheral != null && rightPeripheral instanceof IMinechemTurtlePeripheral)
            return (IMinechemTurtlePeripheral) rightPeripheral;
        else
            return null;
    }

    public ChemistryTurtleUpgradePeripherial(ITurtleAccess turtle) {
        this.turtle = turtle;
    }

    @Override
    public String getType() {
        return "chemistryTurtle";
    }

    @Override
    public String[] getMethodNames() {
        if (methodNames == null) {
            methodNames = new String[methods.length];
            int pos = 0;
            for (ICCMethod method : methods)
                methodNames[pos++] = method.getMethodName();
        }
        return methodNames;
    }

    @Override
    public Object[] callMethod(IComputerAccess computer, int method, Object[] arguments) throws Exception {
        return methods[method].call(computer, turtle, arguments);
    }

    @Override
    public boolean canAttachToSide(int side) {
        return true;
    }

    @Override
    public void attach(IComputerAccess computer) {
        this.computer = computer;
    }

    @Override
    public void detach(IComputerAccess computer) {
        this.computer = null;
    }

    @Override
    public void update() {
        if (updateTracker.markTimeIfDelay(turtle.getWorld(), Constants.TICKS_PER_SECOND)) {
            List<ItemStack> inventory = getTurtleInventory();
            List<DecayEvent> decayEvents = RadiationHandler.getInstance().updateRadiationOnItems(turtle.getWorld(), inventory);
            if (this.computer != null)
                postDecayEvents(decayEvents);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
    }

    private void postDecayEvents(List<DecayEvent> decayEvents) {
        for (DecayEvent event : decayEvents) {
            Object[] data = {event.before.getDisplayName().replaceAll(Constants.TEXT_MODIFIER_REGEX, ""),
                    event.after.getDisplayName().replaceAll(Constants.TEXT_MODIFIER_REGEX, ""), event.damage};
            this.computer.queueEvent("onDecay", data);
        }
    }

    public List<ItemStack> getTurtleInventory() {
        List<ItemStack> inventory = new ArrayList<ItemStack>();
        for (int slot = 0; slot < turtle.getInventorySize(); slot++) {
            ItemStack stack = turtle.getSlotContents(slot);
            if (stack != null)
                inventory.add(stack);
        }
        return inventory;
    }

    @Override
    public SynthesisRecipe getSynthesisRecipe() {
        return this.synthesisRecipe;
    }

    @Override
    public void setSynthesisRecipe(SynthesisRecipe synthesisRecipe) {
        this.synthesisRecipe = synthesisRecipe;
    }

    @Override
    public ICCMethod[] getMethods() {
        return methods;
    }

}
