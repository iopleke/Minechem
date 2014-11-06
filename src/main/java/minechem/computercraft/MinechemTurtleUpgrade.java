package minechem.computercraft;

import java.util.ArrayList;
import java.util.List;

import minechem.MinechemBlocksGeneration;
import minechem.MinechemItemsRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
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
	@Optional.Method(modid = "ComputerCraft")
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
		return new ItemStack(MinechemItemsRegistration.atomicManipulator);
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
		return MinechemBlocksGeneration.fusion.getIcon(1, 1);
	}

    @Optional.Method(modid = "ComputerCraft")
	@Override
	public void update(ITurtleAccess turtle, TurtleSide side) {
	}
    
    @Optional.Method(modid = "ComputerCraft")
    public void addTurtlesToCreative(List subItems) {
		for (int i = 0; i <= 3; i++) {
			ItemStack turtle = GameRegistry.findItemStack("ComputerCraft", "CC-TurtleExpanded", 1);
			ItemStack advancedTurtle = GameRegistry.findItemStack("ComputerCraft", "CC-TurtleAdvanced", 1);
			if (turtle != null)
			{
				NBTTagCompound tag = turtle.getTagCompound();
				if (tag == null)
				{
					tag = new NBTTagCompound();
					turtle.writeToNBT(tag);
				}
				tag.setShort("leftUpgrade", (short) getUpgradeID());
				tag.setShort("rightUpgrade", (short) i);
				turtle.setTagCompound(tag);
				subItems.add(turtle);
			}
			if (advancedTurtle != null)
			{
				NBTTagCompound tag = advancedTurtle.getTagCompound();
				if (tag == null)
				{
					tag = new NBTTagCompound();
					advancedTurtle.writeToNBT(tag);
				}
				tag.setShort("leftUpgrade", (short) getUpgradeID());
				tag.setShort("rightUpgrade", (short) i);
				advancedTurtle.setTagCompound(tag);
				subItems.add(advancedTurtle);
			}
		}
	}
}