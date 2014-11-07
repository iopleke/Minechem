package minechem.computercraft;

import java.util.List;

import minechem.MinechemBlocksGeneration;
import minechem.MinechemItemsRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.turtle.TurtleCommandResult;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.api.turtle.TurtleUpgradeType;
import dan200.computercraft.api.turtle.TurtleVerb;


@Optional.Interface(iface = "dan200.computercraft.api.turtle.ITurtleUpgrade", modid = "ComputerCraft")
public class MinechemTurtleUpgrade implements ITurtleUpgrade{
	private int upgradeID;
	public IIcon icon;
	
	public MinechemTurtleUpgrade(int upgradeID) {
		this.upgradeID = upgradeID;
	}

	@Optional.Method(modid = "ComputerCraft")
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

    @Optional.Method(modid = "ComputerCraft")
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
		return icon;
	}

    @Optional.Method(modid = "ComputerCraft")
    @Override
	public void update(ITurtleAccess turtle, TurtleSide side) {
	}


	@SubscribeEvent
	public void registerIcons(TextureStitchEvent e) {
		if (e.map.getTextureType() == 0) icon = e.map.registerIcon("minechem:chemicalTurtleUpgrade");
	}	
    
    
    @Optional.Method(modid = "ComputerCraft")
    public void addTurtlesToCreative(List subItems) {
		for (int i = 0; i < 3; i++) {
			ItemStack turtle = GameRegistry.findItemStack("ComputerCraft", "CC-TurtleExpanded", 1);
			ItemStack advancedTurtle = GameRegistry.findItemStack("ComputerCraft", "CC-TurtleAdvanced", 1);
			if (turtle != null && advancedTurtle!=null)
			{
				NBTTagCompound tag = turtle.getTagCompound();
				if (tag == null)
				{
					tag = new NBTTagCompound();
					turtle.writeToNBT(tag);
				}
				tag.setShort("leftUpgrade", (short) getUpgradeID());
				tag.setShort("rightUpgrade", (short) i);
				advancedTurtle.setTagCompound(tag);
				turtle.setTagCompound(tag);
				subItems.add(turtle);
				subItems.add(advancedTurtle);
			}
		}
	}
}