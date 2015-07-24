package minechem.radiation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import minechem.fluid.FluidHelper;
import minechem.item.MinechemChemicalType;
import minechem.item.bucket.MinechemBucketHandler;
import minechem.item.bucket.MinechemBucketItem;
import minechem.item.element.Element;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import minechem.potion.PotionChemical;
import minechem.utils.MinechemUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RadiationMoleculeHandler
{

    /**
     *
     * @param world
     * @param itemStack
     * @param inventory
     * @param x
     * @param y
     * @param z
     * @return the output of the decay
     */
    public ItemStack[] handleRadiationMoleculeBucket(World world, ItemStack itemStack, IInventory inventory, double x, double y, double z)
    {
        List<ItemStack> output = toItemStacks(decayMolecule(new Molecule((MoleculeEnum) ((MinechemBucketItem) itemStack.getItem()).chemical, itemStack.stackSize * 8)), inventory.getInventoryStackLimit());

        /*
         * select one of the item stacks and wrap it with bucket
         * replace the undecayed item stack with this, to let one stack stay at the old location and return buckets
         */
        if (!output.isEmpty())
        {
            ItemStack oneItem = output.get(0);

            Item bukkitFilled = MinechemBucketHandler.getInstance().buckets.get(FluidHelper.getFluidBlock(MinechemUtil.getChemical(oneItem)));

            int filledBuckets = itemStack.stackSize;
            oneItem.stackSize -= filledBuckets * 8;

            if (oneItem.stackSize == 0)
            {
                output.remove(0);
            }

            ItemStack outputBucket = new ItemStack(bukkitFilled, oneItem.stackSize / 8);
            MinechemUtil.copyItemStack(outputBucket, itemStack);
            itemStack.stackSize = filledBuckets;
        }

        for (ItemStack item : output)
        {
            MinechemUtil.throwItemStack(world, MinechemUtil.addItemToInventory(inventory, item), x, y, z);
        }

        output.add(itemStack);
        return output.toArray(new ItemStack[output.size()]);
    }

    /**
     *
     * @param world
     * @param itemStack
     * @param inventory
     * @param x
     * @param y
     * @param z
     * @return the output of the decay
     */
    public ItemStack[] handleRadiationMoleculeTube(World world, ItemStack itemStack, IInventory inventory, double x, double y, double z)
    {
        List<ItemStack> output = toItemStacks(decayMolecule(new Molecule(MoleculeItem.getMolecule(itemStack), itemStack.stackSize)), inventory.getInventoryStackLimit());

        /*
         * select one of the item stacks and replace the undecayed item with it,
         * to let one stack stay at the old location
         */
        if (!output.isEmpty())
        {
            MinechemUtil.copyItemStack(output.remove(0), itemStack);
        }

        for (ItemStack item : output)
        {
            MinechemUtil.throwItemStack(world, MinechemUtil.addItemToInventory(inventory, item), x, y, z);
        }

        output.add(itemStack);
        return output.toArray(new ItemStack[output.size()]);
    }

    private List<ItemStack> toItemStacks(List<PotionChemical> chemicals, int maxStackSize)
    {
        Map<MinechemChemicalType, AtomicInteger> amounts = new HashMap<MinechemChemicalType, AtomicInteger>();
        for (PotionChemical chemical : chemicals)
        {
            MinechemChemicalType type = MinechemUtil.getChemical(chemical);
            AtomicInteger counter = amounts.get(type);
            if (counter == null)
            {
                amounts.put(type, new AtomicInteger(chemical.amount));
            } else
            {
                counter.set(counter.get() + chemical.amount);
            }
        }

        List<ItemStack> output = new ArrayList<ItemStack>();
        for (Entry<MinechemChemicalType, AtomicInteger> entry : amounts.entrySet())
        {
            MinechemChemicalType type = entry.getKey();
            int amount = entry.getValue().get();
            for (int i = amount / maxStackSize; i > 0; i--)
            {
                output.add(MinechemUtil.chemicalToItemStack(type, maxStackSize));
            }
            int remaining = amount % maxStackSize;
            if (remaining > 0)
            {
                output.add(MinechemUtil.chemicalToItemStack(type, remaining));
            }
        }
        return output;
    }

    private List<PotionChemical> decayMolecule(MoleculeEnum molecule)
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
                    outputChemicals.addAll(decayMolecule(((Molecule) chemical)));
                }
            }
        }

        return outputChemicals;
    }

    private List<PotionChemical> decayMolecule(Molecule molecule)
    {
        List<PotionChemical> result = decayMolecule(molecule.molecule);
        for (PotionChemical chemical : result)
        {
            chemical.amount *= molecule.amount;
        }
        return result;
    }
}
