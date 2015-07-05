package minechem.radiation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import minechem.MinechemItemsRegistration;
import minechem.fluid.FluidHelper;
import minechem.fluid.MinechemFluidBlock;
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
import net.minecraft.item.Item;
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
        Set<PotionChemical> decayedChemicals = computDecayMolecule((MoleculeEnum) ((MinechemBucketItem) itemStack.getItem()).chemical);
        for (PotionChemical chemical : decayedChemicals)
        {
            chemical.amount *= 8 * itemStack.stackSize;
        }

        List<ItemStack> items = toItemStacks(decayedChemicals);

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
        Set<PotionChemical> decayedChemicals = computDecayMolecule(MoleculeItem.getMolecule(itemStack));
        for (PotionChemical chemical : decayedChemicals)
        {
            chemical.amount *= itemStack.stackSize;
        }
        List<ItemStack> items = toItemStacks(decayedChemicals);

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

    private List<ItemStack> toItemStacks(Set<PotionChemical> decayedChemicals)
    {
        List<ItemStack> itemStacks = new ArrayList<ItemStack>();
        List<PotionChemical> chemicals = new ArrayList<PotionChemical>(decayedChemicals);

        while (!chemicals.isEmpty())
        {
            int index = chemicals.size() - 1;
            PotionChemical chemical = chemicals.remove(index);

            Item thisType;
            int thisDamage;
            if (chemical instanceof Element)
            {
                thisType = MinechemItemsRegistration.element;
                thisDamage = ((Element) chemical).element.atomicNumber();
            } else if (chemical instanceof Molecule)
            {
                thisType = MinechemItemsRegistration.molecule;
                thisDamage = ((Molecule) chemical).molecule.id();
            } else
            {
                continue;
            }

            for (int l = 0; l < itemStacks.size(); l++)
            {
                if (chemical.amount <= 0)
                {
                    break;
                }

                ItemStack stack = itemStacks.get(l);
                if ((stack.getItem() == thisType) && (stack.getItemDamage() == thisDamage))
                {
                    int freeSpace = 64 - stack.stackSize;
                    int append = freeSpace > chemical.amount ? chemical.amount : freeSpace;
                    chemical.amount -= append;
                    stack.stackSize += append;
                }
            }

            if (chemical.amount > 0)
            {
                itemStacks.add(new ItemStack(thisType, chemical.amount, thisDamage));
            }
        }

        return itemStacks;
    }

    private Set<PotionChemical> computDecayMolecule(MoleculeEnum molecule)
    {
        List<PotionChemical> chemicals = molecule.components();
        Set<PotionChemical> outChemicals = new HashSet<PotionChemical>();

        if (molecule.radioactivity() == RadiationEnum.stable)
        {
            outChemicals.add(new Molecule(molecule));
            return outChemicals;
        }

        for (PotionChemical chemical1 : chemicals)
        {
            PotionChemical chemical = chemical1.copy();

            if (chemical instanceof Element)
            {
                Element element = (Element) chemical;
                if (element.element.radioactivity() != RadiationEnum.stable)
                {
                    element.element = ElementEnum.getByID(element.element.atomicNumber() - 1);
                }
            } else if (chemical instanceof Molecule)
            {
                Molecule molecule2 = (Molecule) chemical;
                if (molecule2.molecule.radioactivity() != RadiationEnum.stable)
                {
                    outChemicals.addAll(computDecayMolecule(molecule2.molecule));
                    chemical = null;
                }
            }

            if (chemical != null)
            {
                outChemicals.add(chemical);
            }
        }

        return outChemicals;
    }
}
