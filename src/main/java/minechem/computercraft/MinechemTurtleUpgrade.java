package minechem.computercraft;

import cpw.mods.fml.common.Optional;
import minechem.MinechemBlocksGeneration;
import minechem.MinechemItemsRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.turtle.TurtleCommandResult;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.api.turtle.TurtleUpgradeType;
import dan200.computercraft.api.turtle.TurtleVerb;

@Optional.InterfaceList({
        @Optional.Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = "ComputerCraft"),
        @Optional.Interface(iface = "dan200.computercraft.api.turtle.ITurtleAccess", modid = "ComputerCraft"),
        @Optional.Interface(iface = "dan200.computercraft.api.turtle.ITurtleUpgrade", modid = "ComputerCraft"),
        @Optional.Interface(iface = "dan200.computercraft.api.turtle.TurtleCommandResult", modid = "ComputerCraft"),
        @Optional.Interface(iface = "dan200.computercraft.api.turtle.TurtleSide", modid = "ComputerCraft"),
        @Optional.Interface(iface = "dan200.computercraft.api.turtle.TurtleUpgradeType", modid = "ComputerCraft"),
        @Optional.Interface(iface = "dan200.computercraft.api.turtle.TurtleVerb", modid = "ComputerCraft"),
})
public class MinechemTurtleUpgrade implements ITurtleUpgrade {
	private int upgradeID;

	public MinechemTurtleUpgrade(int upgradeID) {
		this.upgradeID = upgradeID;
	}

	@Override
	public int getUpgradeID() {
		return upgradeID;
	}

	@Override
	public String getUnlocalisedAdjective() {
		return "Chemical";
	}

    @Optional.Method(modid = "ComputerCraft")
	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Peripheral;
	}

	@Override
	public ItemStack getCraftingItem() {
		return new ItemStack(MinechemItemsRegistration.journal);
	}

    @Optional.Method(modid = "ComputerCraft")
	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		return new MinechemComputerPeripheral(turtle);
	}

    @Optional.Method(modid = "ComputerCraft")
	@Override
	public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
		return null;
	}

    @Optional.Method(modid = "ComputerCraft")
	@Override
	public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
		return MinechemBlocksGeneration.fusion.getIcon(1, 1);//MinechemItemsRegistration.atomicManipulator.getIcon();
	}

    @Optional.Method(modid = "ComputerCraft")
	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {
	}

}