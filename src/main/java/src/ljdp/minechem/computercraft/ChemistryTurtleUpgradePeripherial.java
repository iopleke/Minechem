package ljdp.minechem.computercraft;

import java.util.ArrayList;
import java.util.List;

import ljdp.minechem.api.recipe.SynthesisRecipe;
import ljdp.minechem.api.util.Constants;
import ljdp.minechem.common.RadiationHandler;
import ljdp.minechem.common.RadiationHandler.DecayEvent;
import ljdp.minechem.computercraft.method.ClearSynthesisRecipe;
import ljdp.minechem.computercraft.method.GetAtomicMass;
import ljdp.minechem.computercraft.method.GetChemicalName;
import ljdp.minechem.computercraft.method.GetChemicals;
import ljdp.minechem.computercraft.method.GetFormula;
import ljdp.minechem.computercraft.method.GetMachineState;
import ljdp.minechem.computercraft.method.GetRadioactivity;
import ljdp.minechem.computercraft.method.GetTicksUntilDecay;
import ljdp.minechem.computercraft.method.PlaceSynthesisRecipe;
import ljdp.minechem.computercraft.method.PutEmptyTestTube;
import ljdp.minechem.computercraft.method.PutFusionStar;
import ljdp.minechem.computercraft.method.PutInput;
import ljdp.minechem.computercraft.method.PutJournal;
import ljdp.minechem.computercraft.method.StoreSynthesisRecipe;
import ljdp.minechem.computercraft.method.TakeEmptyTestTube;
import ljdp.minechem.computercraft.method.TakeFusionStar;
import ljdp.minechem.computercraft.method.TakeJournal;
import ljdp.minechem.computercraft.method.TakeOuput;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import buildcraft.api.core.SafeTimeTracker;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IHostedPeripheral;
import dan200.turtle.api.ITurtleAccess;
import dan200.turtle.api.TurtleSide;

public class ChemistryTurtleUpgradePeripherial implements IHostedPeripheral, IMinechemTurtlePeripheral {

    private static String[] methodNames;
    private static ICCMethod[] methods = { new GetChemicalName(), new GetFormula(), new GetChemicals(), new GetAtomicMass(), new GetRadioactivity(),
            new GetTicksUntilDecay(), new StoreSynthesisRecipe(), new PlaceSynthesisRecipe(), new ClearSynthesisRecipe(), new PutEmptyTestTube(),
            new TakeEmptyTestTube(), new TakeOuput(), new PutInput(), new TakeFusionStar(), new PutFusionStar(), new TakeJournal(), new PutJournal(),
            new GetMachineState() };

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
    public void readFromNBT(NBTTagCompound nbttagcompound) {}

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {}

    private void postDecayEvents(List<DecayEvent> decayEvents) {
        for (DecayEvent event : decayEvents) {
            Object[] data = { event.before.getDisplayName().replaceAll(Constants.TEXT_MODIFIER_REGEX, ""),
                    event.after.getDisplayName().replaceAll(Constants.TEXT_MODIFIER_REGEX, ""), event.damage };
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
