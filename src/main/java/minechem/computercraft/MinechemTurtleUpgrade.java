package minechem.computercraft;

import minechem.MinechemBlocksGeneration;
import minechem.MinechemItemsRegistration;
import minechem.tileentity.multiblock.fusion.FusionBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.turtle.TurtleCommandResult;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.api.turtle.TurtleUpgradeType;
import dan200.computercraft.api.turtle.TurtleVerb;

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

	@Override
	public TurtleUpgradeType getType() {
		return TurtleUpgradeType.Peripheral;
	}

	@Override
	public ItemStack getCraftingItem() {
		return new ItemStack(MinechemItemsRegistration.journal);
	}

	@Override
	public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
		return new MinechemComputerPeripheral(turtle);
	}

	@Override
	public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
		return null;
	}

	@Override
	public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
		return MinechemBlocksGeneration.fusion.getIcon(1, 1);//MinechemItemsRegistration.atomicManipulator.getIcon();
	}

	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {
	}

}