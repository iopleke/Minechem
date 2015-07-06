package minechem.radiation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import minechem.fluid.FluidHelper;
import minechem.fluid.MinechemFluidBlock;
import minechem.item.MinechemChemicalType;
import minechem.item.bucket.MinechemBucketHandler;
import minechem.item.bucket.MinechemBucketItem;
import minechem.item.element.Element;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import minechem.potion.PotionChemical;
import minechem.utils.MinechemUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RadiationMoleculeHandler
{

    private static RadiationMoleculeHandler instance = null;

    public static RadiationMoleculeHandler getInstance()
    {
        if (instance == null)
        {
            instance = new RadiationMoleculeHandler();
        }

        return instance;
    }

    public RadiationInfo handleRadiationMoleculeBucket(World world, ItemStack itemStack, IInventory inventory, double x, double y, double z)
    {
        List<ItemStack> items = toItemStacks(computDecayMolecule(new Molecule((MoleculeEnum) ((MinechemBucketItem) itemStack.getItem()).chemical, itemStack.stackSize * 8)));

        /*
         * select one of the items
         * wrap it with bukkit
         * and replace the old item with this
         */
        ItemStack oneItem = items.isEmpty() ? null : items.remove(0);

        for (ItemStack item : items)
        {
            ItemStack stack = MinechemUtil.addItemToInventory(inventory, item);
            if (stack != null)
            {
                MinechemUtil.throwItemStack(world, itemStack, x, y, z);
            }
        }

        if (oneItem == null)
        {
            return null;
        }

        MinechemFluidBlock bukkitFilled;
        if (oneItem.getItem() instanceof MoleculeItem)
        {
            bukkitFilled = FluidHelper.moleculeBlocks.get(FluidHelper.molecules.get(MoleculeItem.getMolecule(oneItem)));
        } else if (oneItem.getItem() instanceof ElementItem)
        {
            bukkitFilled = FluidHelper.elementsBlocks.get(FluidHelper.elements.get(ElementItem.getElement(oneItem)));
        } else
        {
            throw new RuntimeException("unexpected item type: " + oneItem.getItem().getClass());
        }
        itemStack.func_150996_a(MinechemBucketHandler.getInstance().buckets.get(bukkitFilled));
        itemStack.stackSize = (oneItem.stackSize / 8);
        itemStack.setTagCompound(oneItem.stackTagCompound);

        return ElementItem.initiateRadioactivity(itemStack, world);
    }

    public RadiationInfo handleRadiationMolecule(World world, ItemStack itemStack, IInventory inventory, double x, double y, double z)
    {
        List<ItemStack> items = toItemStacks(computDecayMolecule(new Molecule(MoleculeItem.getMolecule(itemStack), itemStack.stackSize)));

        /*
         * select one of the items
         * replace the undecayed item with this
         * to let one stack stay at the old location
         */
        ItemStack oneItem = items.isEmpty() ? null : items.remove(0);

        for (ItemStack item : items)
        {
            ItemStack stack = MinechemUtil.addItemToInventory(inventory, item);
            if (stack != null)
            {
                MinechemUtil.throwItemStack(world, itemStack, x, y, z);
            }
        }

        if (oneItem == null)
        {
            return null;
        }

        itemStack.setItemDamage(oneItem.getItemDamage());
        itemStack.func_150996_a(oneItem.getItem());
        itemStack.stackSize = (oneItem.stackSize);
        itemStack.setTagCompound(oneItem.stackTagCompound);

        return ElementItem.initiateRadioactivity(itemStack, world);
    }

    private List<ItemStack> toItemStacks(List<PotionChemical> chemicals)
    {
        Map<MinechemChemicalType, AtomicInteger> amounts = new HashMap<MinechemChemicalType, AtomicInteger>();
        for (PotionChemical chemical : chemicals)
        {
            MinechemChemicalType type = MinechemUtil.getChemical(chemical);
            AtomicInteger counter = amounts.get(type);
            if (counter == null)
            {
                amounts.put(type, new AtomicInteger(1));
            } else
            {
                counter.set(counter.get() + 1); // faster than getAndIncrement()
            }
        }

        List<ItemStack> output = new ArrayList<ItemStack>();
        for (Entry<MinechemChemicalType, AtomicInteger> entry : amounts.entrySet())
        {
            MinechemChemicalType type = entry.getKey();
            int amount = entry.getValue().get();
            for (int i = amount / 64; i > 0; i--)
            {
                output.add(MinechemUtil.chemicalToItemStack(type, 64));
            }
            int remaining = amount % 64;
            if (remaining > 0)
            {
                output.add(MinechemUtil.chemicalToItemStack(type, remaining));
            }
        }
        return output;
    }

    private List<PotionChemical> computDecayMolecule(MoleculeEnum molecule)
    {
        List<PotionChemical> outputChemicals = new ArrayList<PotionChemical>();

        if (molecule.radioactivity() == RadiationEnum.stable)
        {
            outputChemicals.add(new Molecule(molecule));
            return outputChemicals;
        }

        for (PotionChemical chemical : molecule.components())
        {
            MinechemChemicalType type = MinechemUtil.getChemical(chemical);
            if (type.radioactivity() == RadiationEnum.stable)
            {
                outputChemicals.add(chemical.clone());
            } else
            {
                if (type instanceof ElementEnum)
                {
                    outputChemicals.add(new Element(ElementEnum.getByID(((Element) chemical).element.atomicNumber() - 1), chemical.amount));
                } else if (type instanceof MoleculeEnum)
                {
                    outputChemicals.addAll(computDecayMolecule(((Molecule) chemical)));
                }
            }
        }

        return outputChemicals;
    }

    private List<PotionChemical> computDecayMolecule(Molecule molecule)
    {
        List<PotionChemical> result = computDecayMolecule(molecule.molecule);
        for (PotionChemical chemical : result)
        {
            chemical.amount *= molecule.amount;
        }
        return result;
    }
}
