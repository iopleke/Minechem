package minechem.radiation;

import java.util.ArrayList;
import java.util.List;
import minechem.MinechemItemsRegistration;
import minechem.api.INoDecay;
import minechem.api.IRadiationShield;
import minechem.item.MinechemChemicalType;
import minechem.item.bucket.MinechemBucketHandler;
import minechem.item.bucket.MinechemBucketItem;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeItem;
import minechem.utils.MinechemUtil;
import minechem.utils.TimeHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dan200.computercraft.api.turtle.ITurtleAccess;

public class RadiationHandler
{

    private static RadiationHandler instance = new RadiationHandler();

    public static RadiationHandler getInstance()
    {
        return instance == null ? new RadiationHandler() : instance;
    }

    private RadiationMoleculeHandler moleculeHandler = new RadiationMoleculeHandler();

    public RadiationHandler()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void update(EntityPlayer player)
    {
        Container openContainer = player.openContainer;
        if (openContainer != null)
        {
            if (openContainer instanceof INoDecay)
            {
                updateContainerNoDecay(player, openContainer, player.inventory);
            } else
            {
                updateContainer(player, openContainer, player.inventory);
            }
        } else
        {
            updateContainer(player, player.inventoryContainer, player.inventory);
        }
    }

    private void updateContainerNoDecay(EntityPlayer player, Container openContainer, IInventory inventory)
    {
        INoDecay container = (INoDecay) openContainer;
        List<ItemStack> itemstacks = container.getStorageInventory();
        if (itemstacks != null)
        {
            for (ItemStack itemstack : itemstacks)
            {
                if ((itemstack != null) && ((itemstack.getItem() == MinechemItemsRegistration.molecule) || (itemstack.getItem() == MinechemItemsRegistration.element) || (itemstack.getItem() instanceof MinechemBucketItem)) && (RadiationInfo.getRadioactivity(itemstack) != RadiationEnum.stable))
                {
                    RadiationInfo radiationInfo = RadiationInfo.getRadiationInfo(itemstack, player.worldObj);
                    radiationInfo.decayStarted += player.worldObj.getTotalWorldTime() - radiationInfo.lastDecayUpdate;
                    radiationInfo.lastDecayUpdate = player.worldObj.getTotalWorldTime();
                    RadiationInfo.setRadiationInfo(radiationInfo, itemstack);
                }
            }
        }
        List<ItemStack> playerStacks = container.getPlayerInventory();
        if (playerStacks != null)
        {
            updateRadiationOnItems(player.worldObj, player, openContainer, inventory, playerStacks);
        }
    }

    @Optional.Method(modid = "ComputerCraft")
    public void updateRadiationOnItems(World world, ITurtleAccess turtle, IInventory inventory, List<ItemStack> itemstacks)
    {
        updateRadiationOnItems(world, inventory, itemstacks, null, null, turtle.getPosition().posX, turtle.getPosition().posY, turtle.getPosition().posZ);

    }

    private void updateContainer(EntityPlayer player, Container container, IInventory inventory)
    {
        List<ItemStack> itemstacks = container.getInventory();
        updateRadiationOnItems(player.worldObj, player, container, inventory, itemstacks);
    }

    private void updateRadiationOnItems(World world, EntityPlayer player, Container container, IInventory inventory, List<ItemStack> itemstacks)
    {
        updateRadiationOnItems(world, inventory, itemstacks, player, container, player.posX, player.posY, player.posZ);
    }

    private void updateRadiationOnItems(World world, IInventory inventory, List<ItemStack> itemstacks, EntityPlayer player, Container container, double posX, double posY, double posZ)
    {
        for (int i = 0; i < itemstacks.size(); i++)
        {
            ItemStack itemstack = itemstacks.get(i);
            if (itemstack != null)
            {
                RadiationEnum radiation = null;
                Item item = itemstack.getItem();
                if (item == MinechemItemsRegistration.element)
                {
                    radiation = RadiationInfo.getRadioactivity(itemstack);
                } else if (item == MinechemItemsRegistration.molecule)
                {
                    radiation = MoleculeItem.getMolecule(itemstack).radioactivity();
                } else if (item instanceof MinechemBucketItem)
                {
                    radiation = ((MinechemBucketItem) item).chemical.radioactivity();
                }

                if ((radiation != null) && (radiation != RadiationEnum.stable))
                {
                    long time = world.getTotalWorldTime() - RadiationInfo.getRadiationInfo(itemstack, world).decayStarted;
                    ItemStack before = itemstack.copy();
                    DecayOutput output = updateRadiation(world, itemstack, inventory, posX, posY, posZ);
                    if (output != null)
                    {
                        ItemStack[] after = output.outputItems;
                        int damage = output.damage;

                        IInventory decayInventory = (container == null) ? inventory : container.getSlot(i).inventory;
                        RadiationDecayEvent event = new RadiationDecayEvent(decayInventory, damage, time, before, after, player);
                        MinecraftForge.EVENT_BUS.post(event);
                        if ((container != null) && (player != null))
                        {
                            applyRadiationDamage(player, container, damage);
                        }
                    }
                }
            }
        }
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
            if ((armour != null) && (armour.getItem() instanceof IRadiationShield))
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

    @SubscribeEvent
    public void onDecayEvent(RadiationDecayEvent e)
    {
        if (e.getPlayer() != null)
        {
            String nameBeforeDecay = getLongName(e.getBefore());
            String nameAfterDecay = e.getAfterAsString();
            String time = TimeHelper.getTimeFromTicks(e.getTime());
            String message = String.format("Radiation Warning: Element %s decayed into %s after %s.", nameBeforeDecay, nameAfterDecay, time);
            e.getPlayer().addChatMessage(new ChatComponentText(message));
        }
    }

    /**
     * For tubes, returns the localized name of the chemical in the tube.
     * For chemical buckets, returns:
     * <blockquote>
     * the localized name of chemical bucket + "(" + the localized name of the chemical in the bucket + ")"
     * </blockquote>
     *
     * @param stack
     * @return
     * @deprecated
     */
    @Deprecated
    public static String getLongName(ItemStack stack)
    {
        Item item = stack.getItem();
        if (item == MinechemItemsRegistration.element)
        {
            return ElementItem.getLongName(stack);
        } else if (item == MinechemItemsRegistration.molecule)
        {
            return MinechemUtil.getLocalString(MoleculeItem.getMolecule(stack).getUnlocalizedName(), true);
        } else if (item instanceof MinechemBucketItem)
        {
            return String.format("%s(%s)", MinechemUtil.getLocalString("item.minechemBucket.name"), MinechemUtil.getLocalString(((MinechemBucketItem) item).chemical.getUnlocalizedName(), true));
        }
        return "null";
    }

    private DecayOutput updateRadiation(World world, ItemStack element, IInventory inventory, double x, double y, double z)
    {
        RadiationInfo radiationInfo = RadiationInfo.getRadiationInfo(element, world);
        int dimensionID = world.provider.dimensionId;
        if ((dimensionID != radiationInfo.dimensionID) && radiationInfo.isRadioactive())
        {
            radiationInfo.dimensionID = dimensionID;
            RadiationInfo.setRadiationInfo(radiationInfo, element);
            return null;
        } else
        {
            long currentTime = world.getTotalWorldTime();
            return decayChemical(element, radiationInfo, currentTime, world, inventory, x, y, z);
        }
    }

    private DecayOutput decayChemical(ItemStack element, RadiationInfo radiationInfo, long currentTime, World world, IInventory inventory, double x, double y, double z)
    {
        if (element.stackSize == 0)
        {
            // won't decay
            radiationInfo.decayStarted += currentTime - radiationInfo.lastDecayUpdate;
            radiationInfo.lastDecayUpdate = currentTime;
            RadiationInfo.setRadiationInfo(radiationInfo, element);
            return null;
        }

        radiationInfo.lastDecayUpdate = currentTime;
        long timeToLeft = radiationInfo.getLeftTime(currentTime);
        if (timeToLeft <= 0)
        {
            int damage = radiationInfo.radioactivity.getDamage() * element.stackSize;
            ItemStack[] output;

            Item item = element.getItem();
            if (item == MinechemItemsRegistration.element)
            {
                element.setItemDamage(element.getItemDamage() - 1);
                output = new ItemStack[] { element };
            } else if (item == MinechemItemsRegistration.molecule)
            {
                output = moleculeHandler.handleRadiationMoleculeTube(world, element, inventory, x, y, z);
            } else if (item instanceof MinechemBucketItem)
            {
                // more damage because of more chemical
                damage *= 8;
                MinechemChemicalType type = ((MinechemBucketItem) item).chemical;
                if (type instanceof ElementEnum)
                {
                    element.func_150996_a(MinechemBucketHandler.getInstance().buckets.get(ElementEnum.getByID(((ElementEnum) type).atomicNumber() - 1)));
                    output = new ItemStack[] { element };
                } else
                {
                    output = moleculeHandler.handleRadiationMoleculeBucket(world, element, inventory, x, y, z);
                }
            } else
            {
                throw new RuntimeException("unexpected item: " + item);
            }

            for (ItemStack chemicalItem : output)
            {
                RadiationInfo.setRadiationInfo(RadiationInfo.initiateRadioactivity(chemicalItem, world), chemicalItem);
            }

            return new DecayOutput(output, damage);
        } else
        {
            RadiationInfo.setRadiationInfo(radiationInfo, element);
            return null;
        }
    }

}
