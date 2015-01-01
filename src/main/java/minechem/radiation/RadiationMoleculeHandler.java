package minechem.radiation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import minechem.MinechemItemsRegistration;
import minechem.fluid.FluidHelper;
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
import minechem.utils.SoftHashMap;
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

    private final Map<MoleculeEnum, PotionChemical[]> decayedMoleculesPre;
    private final Map<MoleculeEnum, PotionChemical[]> decayedMoleculesCache;

    private RadiationMoleculeHandler()
    {
        decayedMoleculesCache = new SoftHashMap<MoleculeEnum, PotionChemical[]>();
        decayedMoleculesPre = new SoftHashMap<MoleculeEnum, PotionChemical[]>();
        initDecayedMoleculesPre();
    }

    public RadiationInfo handleRadiationMoleculeBucket(World world, ItemStack itemStack, IInventory inventory, double x, double y, double z)
    {
        PotionChemical[] decayedChemicals = getDecayedMolecule((MoleculeEnum) ((MinechemBucketItem) itemStack.getItem()).chemical);
        for (int i = 0; i < decayedChemicals.length; i++)
        {
            decayedChemicals[i].amount *= 8 * itemStack.stackSize;
        }
        ItemStack[] items = toItemStacks(decayedChemicals);
        for (int i = 1; i < items.length; i++)
        {
            ItemStack stack = MinechemUtil.addItemToInventory(inventory, items[i]);
            if (stack != null)
            {
                MinechemUtil.throwItemStack(world, itemStack, x, y, z);
            }
        }

        Item item = items[0].getItem();
        if (item instanceof MoleculeItem)
        {
            itemStack.func_150996_a(MinechemBucketHandler.getInstance().buckets.get(FluidHelper.moleculeBlocks.get(FluidHelper.molecules.get(MoleculeItem.getMolecule(items[0])))));
        } else if (item instanceof ElementItem)
        {
            itemStack.func_150996_a(MinechemBucketHandler.getInstance().buckets.get(FluidHelper.elementsBlocks.get(FluidHelper.elements.get(ElementItem.getElement(items[0])))));
        }
        itemStack.stackSize = (items[0].stackSize / 8);
        itemStack.setTagCompound(items[0].stackTagCompound);

        return ElementItem.initiateRadioactivity(itemStack, world);
    }

    public RadiationInfo handleRadiationMolecule(World world, ItemStack itemStack, IInventory inventory, double x, double y, double z)
    {
        PotionChemical[] decayedChemicals = getDecayedMolecule(MoleculeItem.getMolecule(itemStack));
        for (int i = 0; i < decayedChemicals.length; i++)
        {
            decayedChemicals[i].amount *= itemStack.stackSize;
        }
        ItemStack[] items = toItemStacks(decayedChemicals);
        for (int i = 1; i < items.length; i++)
        {
            ItemStack stack = MinechemUtil.addItemToInventory(inventory, items[i]);
            if (stack != null)
            {
                MinechemUtil.throwItemStack(world, itemStack, x, y, z);
            }
        }

        itemStack.setItemDamage(items[0].getItemDamage());
        itemStack.func_150996_a(items[0].getItem());
        itemStack.stackSize = (items[0].stackSize);
        itemStack.setTagCompound(items[0].stackTagCompound);

        return ElementItem.initiateRadioactivity(itemStack, world);
    }

    private void initDecayedMoleculesPre()
    {

    }

    private PotionChemical[] getDecayedMolecule(MoleculeEnum molecule)
    {
        PotionChemical[] chemicals = decayedMoleculesPre.get(molecule);
        if (chemicals != null)
        {
            return copyOf(chemicals);
        }

        chemicals = decayedMoleculesCache.get(molecule);
        if (chemicals == null)
        {
            Set<PotionChemical> potionChemicalsSet = computDecayMolecule(molecule);
            chemicals = potionChemicalsSet.toArray(new PotionChemical[potionChemicalsSet.size()]);
            decayedMoleculesCache.put(molecule, chemicals);
        }
        return copyOf(chemicals);
    }

    private ItemStack[] toItemStacks(PotionChemical[] chemicalsArray)
    {
        List<ItemStack> itemStacks = new ArrayList<ItemStack>();
        List<PotionChemical> chemicals = new ArrayList<PotionChemical>(chemicalsArray.length);
        for (int i = 0; i < chemicalsArray.length; i++)
        {
            chemicals.add(chemicalsArray[i]);
        }

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
                if (stack.getItem() == thisType && stack.getItemDamage() == thisDamage)
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

        return itemStacks.toArray(new ItemStack[itemStacks.size()]);
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
                    element.element = ElementEnum.getByID(element.element.atomicNumber());
                }
            } else if (chemical instanceof Molecule)
            {
                Molecule molecule2 = (Molecule) chemical;
                if (molecule2.molecule.radioactivity() != RadiationEnum.stable)
                {
                    PotionChemical[] chemicals2 = getDecayedMolecule(molecule2.molecule);
                    Collections.addAll(outChemicals, chemicals2);
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

    private PotionChemical[] copyOf(PotionChemical[] a)
    {
        PotionChemical[] b = new PotionChemical[a.length];
        for (int i = 0; i < b.length; i++)
        {
            b[i] = a[i].clone();
        }
        return b;
    }
}
