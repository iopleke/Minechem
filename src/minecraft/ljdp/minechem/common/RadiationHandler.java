package ljdp.minechem.common;

import java.util.ArrayList;
import java.util.List;

import ljdp.minechem.api.core.IRadiationShield;
import ljdp.minechem.common.containers.ContainerChemicalStorage;
import ljdp.minechem.common.items.ItemElement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class RadiationHandler {

    private static RadiationHandler instance = new RadiationHandler();

    public static RadiationHandler getInstance() {
        if (instance == null)
            instance = new RadiationHandler();
        return instance;
    }

    public class DecayEvent {
        public ItemStack before;
        public ItemStack after;
        public int damage;
    }

    private RadiationHandler() {

    }

    public void update(EntityPlayer player) {
        Container openContainer = player.openContainer;
        if (openContainer != null && openContainer instanceof ContainerChemicalStorage)
            updateChemicalStorageContainer(player, openContainer);
        else if (openContainer != null)
            updateContainer(player, openContainer);
        else
            updateContainer(player, player.inventoryContainer);
    }

    public List<DecayEvent> updateRadiationOnItems(World world, List<ItemStack> itemstacks) {
        return updateRadiationOnItems(world, null, null, itemstacks);
    }

    public int getTicksUntilDecay(ItemStack itemstack, World world) {
        RadiationInfo info = ItemElement.getRadiationInfo(itemstack, world);
        return (int) (info.radiationLife);
    }

    private void updateChemicalStorageContainer(EntityPlayer player, Container openContainer) {
        ContainerChemicalStorage chemicalStorage = (ContainerChemicalStorage) openContainer;
        List<ItemStack> itemstacks = chemicalStorage.getStorageInventory();
        for (ItemStack itemstack : itemstacks) {
            if (itemstack != null && itemstack.itemID == MinechemItems.element.itemID) {
                RadiationInfo radiationInfo = ItemElement.getRadiationInfo(itemstack, player.worldObj);
                radiationInfo.lastRadiationUpdate = player.worldObj.getTotalWorldTime();
                ItemElement.setRadiationInfo(radiationInfo, itemstack);
            }
        }
        List<ItemStack> playerStacks = chemicalStorage.getPlayerInventory();
        updateRadiationOnItems(player.worldObj, player, openContainer, playerStacks);
    }

    private void updateContainer(EntityPlayer player, Container container) {
        List<ItemStack> itemstacks = container.getInventory();
        updateRadiationOnItems(player.worldObj, player, container, itemstacks);
    }

    private List<DecayEvent> updateRadiationOnItems(World world, EntityPlayer player, Container container, List<ItemStack> itemstacks) {
        List<DecayEvent> events = new ArrayList<DecayEvent>();
        for (ItemStack itemstack : itemstacks) {
            if (itemstack != null && itemstack.itemID == MinechemItems.element.itemID) {
                DecayEvent decayEvent = new DecayEvent();
                decayEvent.before = itemstack.copy();
                decayEvent.damage = updateRadiation(world, itemstack);
                decayEvent.after = itemstack.copy();
                if (decayEvent.damage > 0)
                    events.add(decayEvent);
                if (decayEvent.damage > 0 && container != null) {
                    applyRadiationDamage(player, container, decayEvent.damage);
                    printRadiationDamageToChat(player, decayEvent);
                }
            }
        }
        return events;
    }

    private void applyRadiationDamage(EntityPlayer player, Container container, int damage) {
        List<Float> reductions = new ArrayList<Float>();
        if (container instanceof IRadiationShield) {
            float reduction = ((IRadiationShield) container).getRadiationReductionFactor(damage, null, player);
            reductions.add(reduction);
        }
        for (ItemStack armour : player.inventory.armorInventory) {
            if (armour != null && armour.getItem() instanceof IRadiationShield) {
                float reduction = ((IRadiationShield) armour.getItem()).getRadiationReductionFactor(damage, armour, player);
                reductions.add(reduction);
            }
        }
        float totalReductionFactor = 1;
        for (float reduction : reductions)
            totalReductionFactor -= reduction;
        if (totalReductionFactor < 0)
            totalReductionFactor = 0;
        damage = Math.round((float) damage * totalReductionFactor);
        player.attackEntityFrom(DamageSource.generic, damage);
    }

    private void printRadiationDamageToChat(EntityPlayer player, DecayEvent decayEvent) {
        String nameBeforeDecay = ItemElement.getLongName(decayEvent.before);
        String nameAfterDecay = ItemElement.getLongName(decayEvent.after);
        String message = String.format("Radiation Warning: Element %s decayed into %s.", nameBeforeDecay, nameAfterDecay);
        player.addChatMessage(message);
    }

    private int updateRadiation(World world, ItemStack element) {
        RadiationInfo radiationInfo = ItemElement.getRadiationInfo(element, world);
        int dimensionID = world.getWorldInfo().getDimension();
        if (dimensionID != radiationInfo.dimensionID && radiationInfo.isRadioactive()) {
            radiationInfo.dimensionID = dimensionID;
            ItemElement.setRadiationInfo(radiationInfo, element);
            return 0;
        } else {
            long currentTime = world.getTotalWorldTime();
            return decayElement(element, radiationInfo, currentTime, world);
        }
    }

    private int decayElement(ItemStack element, RadiationInfo radiationInfo, long currentTime, World world) {
        long timeDifference = currentTime - radiationInfo.lastRadiationUpdate;
        int maxDamage = 0;
        while (timeDifference > 0 && radiationInfo.isRadioactive()) {
            long lifeToRemove = Math.min(timeDifference, radiationInfo.radiationLife);
            timeDifference -= lifeToRemove;
            radiationInfo.radiationLife -= lifeToRemove;
            radiationInfo.lastRadiationUpdate = currentTime;
            if (radiationInfo.radiationLife <= 0) {
                int defaultDamage = radiationInfo.radioactivity.getDamage();
                maxDamage = Math.max(maxDamage, defaultDamage);
                radiationInfo = ItemElement.decay(element, world);
            }
        }
        ItemElement.setRadiationInfo(radiationInfo, element);
        return maxDamage;
    }

}
