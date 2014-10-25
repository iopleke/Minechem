package minechem.fluid.reaction;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import minechem.MinechemItemsRegistration;
import minechem.Settings;
import minechem.fluid.FluidHelper;
import minechem.item.MinechemChemicalType;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import minechem.utils.CoordTuple;
import minechem.utils.MinechemUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.*;

public class ChemicalFluidReactionHandler
{
	public static final Map<ChemicalFluidReactionRule, ChemicalFluidReactionOutput> reactionRules = new HashMap<ChemicalFluidReactionRule, ChemicalFluidReactionOutput>();
	public static final int FLUIDS_GENERATE_SPACE = 3;

	@SubscribeEvent
	public void tick(TickEvent.WorldTickEvent event)
	{
		if (!Settings.reactionItemMeetFluid)
		{
			return;
		}

		World world = event.world;

		for (Object p : world.playerEntities)
		{
			EntityPlayer player = (EntityPlayer) p;
			double rangeToCheck = 32.0D;

			List<EntityItem> itemList = world.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(rangeToCheck, rangeToCheck, rangeToCheck));
			for (EntityItem entityItem : itemList)
			{
				ItemStack itemStack = entityItem.getEntityItem();
				Item item = itemStack.getItem();
				MinechemChemicalType chemicalA = null;
				if (item == MinechemItemsRegistration.element)
				{
					chemicalA = ElementItem.getElement(itemStack);
				} else if (item == MinechemItemsRegistration.molecule)
				{
					chemicalA = MoleculeItem.getMolecule(itemStack);
				}

				if (chemicalA != null && world.isMaterialInBB(entityItem.boundingBox, Material.water))
				{
					int x = MathHelper.floor_double(entityItem.posX);
					int y = MathHelper.floor_double(entityItem.posY);
					int z = MathHelper.floor_double(entityItem.posZ);
					Block block = world.getBlock(x, y, z);
					MinechemChemicalType chemicalB = MinechemUtil.getChemical(block);

					if (chemicalB != null)
					{
						ChemicalFluidReactionRule rule = new ChemicalFluidReactionRule(chemicalA, chemicalB);
						if (reactionRules.containsKey(rule))
						{
							explosionReaction(world, entityItem, x, y, z, rule, !(MinechemUtil.canDrain(world, block, x, y, z)));
							itemStack.stackSize--;
							if (itemStack.stackSize <= 0)
							{
								world.removeEntity(entityItem);
							} else
							{
								entityItem.setEntityItemStack(itemStack);
							}
						}
					}

				}
			}
		}
	}

	public static void initExplodableChemical()
	{
		// TODO Add more reaction rules -yushijinhun
		List<MinechemChemicalType> map;

		//H2O+Li==LiOH+H
		map = new ArrayList<MinechemChemicalType>();
		map.add(ElementEnum.H);
		map.add(MoleculeEnum.lithiumHydroxide);
		reactionRules.put(new ChemicalFluidReactionRule(MoleculeEnum.water, ElementEnum.Li), new ChemicalFluidReactionOutput(map, 0.1f));

		//H2O+Na==NaOH+H
		map = new ArrayList<MinechemChemicalType>();
		map.add(ElementEnum.H);
		map.add(MoleculeEnum.sodiumHydroxide);
		reactionRules.put(new ChemicalFluidReactionRule(MoleculeEnum.water, ElementEnum.Na), new ChemicalFluidReactionOutput(map, 0.15f));

		//H2O+K==KOH+H
		map = new ArrayList<MinechemChemicalType>();
		map.add(ElementEnum.H);
		map.add(MoleculeEnum.potassiumHydroxide);
		reactionRules.put(new ChemicalFluidReactionRule(MoleculeEnum.water, ElementEnum.K), new ChemicalFluidReactionOutput(map, 0.2f));

		//H2O+Li==RbOH+H
		map = new ArrayList<MinechemChemicalType>();
		map.add(ElementEnum.H);
		map.add(MoleculeEnum.rubidiumHydroxide);
		reactionRules.put(new ChemicalFluidReactionRule(MoleculeEnum.water, ElementEnum.Rb), new ChemicalFluidReactionOutput(map, 0.25f));

		//H2O+Cs==CsOH+H
		map = new ArrayList<MinechemChemicalType>();
		map.add(ElementEnum.H);
		map.add(MoleculeEnum.cesiumHydroxide);
		reactionRules.put(new ChemicalFluidReactionRule(MoleculeEnum.water, ElementEnum.Cs), new ChemicalFluidReactionOutput(map, 0.3f));

		//H2O+Fr==FrOH+H
		map = new ArrayList<MinechemChemicalType>();
		map.add(ElementEnum.H);
		map.add(MoleculeEnum.franciumHydroxide);
		reactionRules.put(new ChemicalFluidReactionRule(MoleculeEnum.water, ElementEnum.Fr), new ChemicalFluidReactionOutput(map, 0.4f));

		//H2SO4+Cu==CuSO4+2H
		map = new ArrayList<MinechemChemicalType>();
		map.add(ElementEnum.H);
		map.add(ElementEnum.H);
		map.add(MoleculeEnum.lightbluePigment);
		reactionRules.put(new ChemicalFluidReactionRule(MoleculeEnum.sulfuricAcid, ElementEnum.Cu), new ChemicalFluidReactionOutput(map, 0.1f));

		//H2SO4+S==2SO2+2H
		map = new ArrayList<MinechemChemicalType>();
		map.add(ElementEnum.H);
		map.add(ElementEnum.H);
		map.add(MoleculeEnum.sulfurDioxide);
		reactionRules.put(new ChemicalFluidReactionRule(MoleculeEnum.sulfuricAcid, ElementEnum.S), new ChemicalFluidReactionOutput(map, 0.1f));

		//H2SO4+H2S==S+SO2+2H2O
		map = new ArrayList<MinechemChemicalType>();
		map.add(ElementEnum.S);
		map.add(MoleculeEnum.sulfurDioxide);
		map.add(MoleculeEnum.water);
		map.add(MoleculeEnum.water);
		reactionRules.put(new ChemicalFluidReactionRule(MoleculeEnum.sulfuricAcid, MoleculeEnum.hydrogenSulfide), new ChemicalFluidReactionOutput(map, 0.1f));

		//HCl+NaOH==H2O+NaCl
		map = new ArrayList<MinechemChemicalType>();
		map.add(MoleculeEnum.salt);
		map.add(MoleculeEnum.water);
		reactionRules.put(new ChemicalFluidReactionRule(MoleculeEnum.hcl, MoleculeEnum.sodiumHydroxide), new ChemicalFluidReactionOutput(map, 0.1f));

		//H+Cl==HCl
		map = new ArrayList<MinechemChemicalType>();
		map.add(MoleculeEnum.hcl);
		reactionRules.put(new ChemicalFluidReactionRule(ElementEnum.H, ElementEnum.Cl), new ChemicalFluidReactionOutput(map, 0.1f));

		//NaCl+H2SO4==NaHSO4+HCl
		map = new ArrayList<MinechemChemicalType>();
		map.add(MoleculeEnum.sodiumBisulfate);
		map.add(MoleculeEnum.hcl);
		reactionRules.put(new ChemicalFluidReactionRule(MoleculeEnum.salt, MoleculeEnum.sulfuricAcid), new ChemicalFluidReactionOutput(map, 0.1f));

		//NaHSO4+NaCl==Na2SO4+2HCl
		map = new ArrayList<MinechemChemicalType>();
		map.add(MoleculeEnum.sodiumSulfate);
		map.add(MoleculeEnum.hcl);
		map.add(MoleculeEnum.hcl);
		reactionRules.put(new ChemicalFluidReactionRule(MoleculeEnum.salt, MoleculeEnum.sodiumBisulfate), new ChemicalFluidReactionOutput(map, 0.1f));

	}

	private static void explosionReaction(World world, Entity entity, int x, int y, int z, ChemicalFluidReactionRule rule, boolean popFlowingFluid)
	{
		ChemicalFluidReactionOutput output = reactionRules.get(rule);
		if (output == null)
		{
			return;
		}

		if (output.explosionLevel != Float.NaN)
		{
			world.createExplosion(null, x, y, z, output.explosionLevel, true);
		}

		int halfSpace = FLUIDS_GENERATE_SPACE / 2;
		List[] availableSpaces = new List[FLUIDS_GENERATE_SPACE];
		for (int i = 0; i < availableSpaces.length; i++)
		{
			availableSpaces[i] = findAvailableSpacesAtCrossSection(world, x, y - halfSpace + i, z, 1);
		}

		Iterator<MinechemChemicalType> it = output.outputs.iterator();
		while (it.hasNext())
		{
			MinechemChemicalType chemical = it.next();
			boolean hasFlowingStatus = chemical.roomState().getQuanta() > 2;

			CoordTuple coords = null;
			if (!(!hasFlowingStatus && popFlowingFluid))
			{
				boolean isGas = chemical.roomState().isGas();
				if (isGas)
				{
					for (int i = availableSpaces.length - 1; i > -1; i--)
					{
						if (!availableSpaces[i].isEmpty())
						{
							coords = (CoordTuple) availableSpaces[i].remove(availableSpaces[i].size() - 1);
							break;
						}
					}
				} else
				{
					for (int i = 0; i < availableSpaces.length; i++)
					{
						if (!availableSpaces[i].isEmpty())
						{
							coords = (CoordTuple) availableSpaces[i].remove(availableSpaces[i].size() - 1);
							break;
						}
					}
				}
			}

			if (coords == null)
			{
				if (!popFlowingFluid)
				{
					ItemStack itemStack = MinechemUtil.createItemStack(chemical, 1);
					MinechemUtil.throwItemStack(world, itemStack, x, y, z);
				}
			} else if (!(popFlowingFluid && !hasFlowingStatus))
			{
				int px = coords.getX();
				int py = coords.getY();
				int pz = coords.getZ();

				world.func_147480_a(px, py, pz, true);
				world.setBlockToAir(px, py, pz);

				Block fluidBlock = null;
				if (chemical instanceof ElementEnum)
				{
					fluidBlock = FluidHelper.elementsBlocks.get(FluidHelper.elements.get(chemical));
				} else if (chemical instanceof MoleculeEnum)
				{
					fluidBlock = FluidHelper.moleculeBlocks.get(FluidHelper.molecules.get(chemical));
				}

				if (fluidBlock != null)
				{
					world.setBlock(px, py, pz, fluidBlock, popFlowingFluid ? 1 : 0, 3);
				}
			}
		}
	}

	public static boolean checkToExplode(Block source, Block destination, World world, int destinationX, int destinationY, int destinationZ, int sourceX, int sourceY, int sourceZ)
	{
		MinechemChemicalType chemicalA = MinechemUtil.getChemical(source);
		MinechemChemicalType chemicalB = MinechemUtil.getChemical(destination);
		if (chemicalA != null && chemicalB != null)
		{
			ChemicalFluidReactionRule rule = new ChemicalFluidReactionRule(chemicalA, chemicalB);

			if (reactionRules.containsKey(rule))
			{
				boolean flag = !(MinechemUtil.canDrain(world, source, sourceX, sourceY, sourceZ) && MinechemUtil.canDrain(world, destination, destinationX, destinationY, destinationZ));
				world.setBlockToAir(sourceX, sourceY, sourceZ);
				world.setBlockToAir(destinationX, destinationY, destinationZ);
				explosionReaction(world, null, destinationX, destinationY, destinationZ, rule, flag);
				return true;
			}
		}

		return false;
	}

	public static List<CoordTuple> findAvailableSpacesAtCrossSection(World world, int centerX, int centerY, int centerZ, int size)
	{
		List<CoordTuple> spaces = new ArrayList<CoordTuple>();
		for (int xOffset = -size; xOffset <= size; xOffset++)
		{
			for (int zOffset = -size; zOffset <= size; zOffset++)
			{
				int x = centerX + xOffset;
				int z = centerZ + zOffset;

				if (world.isAirBlock(x, centerY, z) || !world.getBlock(x, centerY, z).getMaterial().isSolid())
				{
					spaces.add(new CoordTuple(x, centerY, z));
				}
			}
		}

		return spaces;
	}
}
