package minechem.radiation;

import java.util.ArrayList;
import java.util.List;
import minechem.MinechemItemsRegistration;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeItem;
import minechem.tileentity.decomposer.DecomposerContainer;
import minechem.tileentity.leadedchest.LeadedChestContainer;
import minechem.tileentity.synthesis.SynthesisContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class RadiationHandler
{

    private static RadiationHandler instance = new RadiationHandler();

    public static RadiationHandler getInstance()
    {
        if (instance == null)
        {
            instance = new RadiationHandler();
        }
        return instance;
    }

    public class DecayEvent
    {

        public ItemStack before;
        public ItemStack after;
        public int damage;
    }

    private RadiationHandler()
    {

    }

    public void update(EntityPlayer player)
    {
        Container openContainer = player.openContainer;
        if (openContainer != null)
        {
            if (openContainer instanceof LeadedChestContainer)
            {
                updateContainerLeadedChest(player, openContainer);
            } else if (openContainer instanceof DecomposerContainer)
            {
                updateDecomposerContainer(player, openContainer);
            } else if (openContainer instanceof SynthesisContainer)
            {
                updateSynthesisContainer(player, openContainer);
            } else
            {
                updateContainer(player, openContainer);
            }
        } else
        {
            updateContainer(player, player.inventoryContainer);
        }
    }

    public List<DecayEvent> updateRadiationOnItems(World world, List<ItemStack> itemstacks)
    {
        return updateRadiationOnItems(world, null, null, itemstacks);
    }

    public int getTicksUntilDecay(ItemStack itemstack, World world)
    {
        RadiationInfo info = ElementItem.getRadiationInfo(itemstack, world);
        return (int) (info.radiationLife);
    }

    private void updateContainerLeadedChest(EntityPlayer player, Container openContainer)
    {
        LeadedChestContainer leadedChest = (LeadedChestContainer) openContainer;
        List<ItemStack> itemstacks = leadedChest.getStorageInventory();
        for (ItemStack itemstack : itemstacks)
        {
            if (itemstack != null && itemstack.getItem() == MinechemItemsRegistration.element && ElementItem.getRadioactivity(itemstack) != RadiationEnum.stable)
            {
                RadiationInfo radiationInfo = ElementItem.getRadiationInfo(itemstack, player.worldObj);
                radiationInfo.lastRadiationUpdate = player.worldObj.getTotalWorldTime();
                ElementItem.setRadiationInfo(radiationInfo, itemstack);
            }
        }
        List<ItemStack> playerStacks = leadedChest.getPlayerInventory();
        updateRadiationOnItems(player.worldObj, player, openContainer, playerStacks);
    }

    private void updateDecomposerContainer(EntityPlayer player, Container openContainer)
    {
        DecomposerContainer decomposer = (DecomposerContainer) openContainer;
        List<ItemStack> itemstacks = decomposer.getStorageInventory();
        for (ItemStack itemstack : itemstacks)
        {
            if (itemstack != null && itemstack.getItem() == MinechemItemsRegistration.element && ElementItem.getRadioactivity(itemstack) != RadiationEnum.stable)
            {
                RadiationInfo radiationInfo = ElementItem.getRadiationInfo(itemstack, player.worldObj);
                radiationInfo.lastRadiationUpdate = player.worldObj.getTotalWorldTime();
                ElementItem.setRadiationInfo(radiationInfo, itemstack);
            }
        }
        List<ItemStack> playerStacks = decomposer.getPlayerInventory();
        updateRadiationOnItems(player.worldObj, player, openContainer, playerStacks);
    }

    private void updateSynthesisContainer(EntityPlayer player, Container openContainer)
    {
        SynthesisContainer synthesizer = (SynthesisContainer) openContainer;
        List<ItemStack> itemstacks = synthesizer.getStorageInventory();
        for (ItemStack itemstack : itemstacks)
        {
            if (itemstack != null && itemstack.getItem() == MinechemItemsRegistration.element && ElementItem.getRadioactivity(itemstack) != RadiationEnum.stable)
            {
                RadiationInfo radiationInfo = ElementItem.getRadiationInfo(itemstack, player.worldObj);
                radiationInfo.lastRadiationUpdate = player.worldObj.getTotalWorldTime();
                ElementItem.setRadiationInfo(radiationInfo, itemstack);
            }
        }
        List<ItemStack> playerStacks = synthesizer.getPlayerInventory();
        updateRadiationOnItems(player.worldObj, player, openContainer, playerStacks);
    }

    private void updateContainer(EntityPlayer player, Container container)
    {
        List<ItemStack> itemstacks = container.getInventory();
        updateRadiationOnItems(player.worldObj, player, container, itemstacks);
    }

    private List<DecayEvent> updateRadiationOnItems(World world, EntityPlayer player, Container container, List<ItemStack> itemstacks)
    {
        List<DecayEvent> events = new ArrayList<DecayEvent>();
        for (ItemStack itemstack : itemstacks)
        {
        	if (itemstack != null){
        		RadiationEnum radiation=null;
        		Item item=itemstack.getItem();
        		if (item==MinechemItemsRegistration.element){
        			radiation=ElementItem.getRadioactivity(itemstack);
        		}else if (item==MinechemItemsRegistration.molecule){
        			radiation=MoleculeItem.getMolecule(itemstack).radioactivity();
        		}
        		
	            if (radiation!=null&&radiation!=RadiationEnum.stable)
	            {
	                DecayEvent decayEvent = new DecayEvent();
	                decayEvent.before = itemstack.copy();
	                decayEvent.damage = updateRadiation(world, itemstack);
	                decayEvent.after = itemstack.copy();
	                if (decayEvent.damage > 0)
	                {
	                    events.add(decayEvent);
	                }
	                if (decayEvent.damage > 0 && container != null)
	                {
	                    applyRadiationDamage(player, container, decayEvent.damage);
	                    printRadiationDamageToChat(player, decayEvent);
	                }
	            }
        	}
        }
        return events;
    }

    private void applyRadiationDamage(EntityPlayer player, Container container, int damage)
    {
        List<Float> reductions = new ArrayList<Float>();
        if (container instanceof IRadiationShield)
        {
            float reduction = ((IRadiationShield) container).getRadiationReductionFactor(damage, null, player);
            reductions.add(reduction);
        }
        for (ItemStack armour : player.inventory.armorInventory)
        {
            if (armour != null && armour.getItem() instanceof IRadiationShield)
            {
                float reduction = ((IRadiationShield) armour.getItem()).getRadiationReductionFactor(damage, armour, player);
                reductions.add(reduction);
            }
        }
        float totalReductionFactor = 1;
        for (float reduction : reductions)
        {
            totalReductionFactor -= reduction;
        }
        if (totalReductionFactor < 0)
        {
            totalReductionFactor = 0;
        }
        damage = Math.round(damage * totalReductionFactor);
        player.attackEntityFrom(DamageSource.generic, damage);
    }

    private void printRadiationDamageToChat(EntityPlayer player, DecayEvent decayEvent)
    {
        String nameBeforeDecay = getLongName(decayEvent.before);
        String nameAfterDecay = getLongName(decayEvent.after);
        String message = String.format("Radiation Warning: Element %s decayed into %s.", nameBeforeDecay, nameAfterDecay);
        player.addChatMessage(new ChatComponentText(message));
    }

    private String getLongName(ItemStack stack){
    	Item item=stack.getItem();
    	if (item==MinechemItemsRegistration.element){
    		return ElementItem.getLongName(stack);
    	}else if (item==MinechemItemsRegistration.molecule){
    		return MoleculeItem.getMolecule(stack).descriptiveName();
    	}
    	return "null";
    }
    
    private int updateRadiation(World world, ItemStack element)
    {
        RadiationInfo radiationInfo = ElementItem.getRadiationInfo(element, world);System.err.println(radiationInfo.radiationLife);
        int dimensionID = world.provider.dimensionId;
        if (dimensionID != radiationInfo.dimensionID && radiationInfo.isRadioactive())
        {
            radiationInfo.dimensionID = dimensionID;
            ElementItem.setRadiationInfo(radiationInfo, element);
            return 0;
        } else
        {
            long currentTime = world.getTotalWorldTime();
            return decayElement(element, radiationInfo, currentTime, world);
        }
    }

    private int decayElement(ItemStack element, RadiationInfo radiationInfo, long currentTime, World world)
    {
        long timeDifference = currentTime - radiationInfo.lastRadiationUpdate;
        int maxDamage = 0;
        while (timeDifference > 0 && radiationInfo.isRadioactive())
        {
            long lifeToRemove = Math.min(timeDifference, radiationInfo.radiationLife);
            timeDifference -= lifeToRemove;
            radiationInfo.radiationLife -= lifeToRemove;
            radiationInfo.lastRadiationUpdate = currentTime;
            if (radiationInfo.radiationLife <= 0)
            {
                int defaultDamage = radiationInfo.radioactivity.getDamage();
                maxDamage = Math.max(maxDamage, defaultDamage);
                radiationInfo = ElementItem.decay(element, world);
            }
        }
        ElementItem.setRadiationInfo(radiationInfo, element);
        return maxDamage;
    }

}
