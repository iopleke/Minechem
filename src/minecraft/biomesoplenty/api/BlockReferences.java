package biomesoplenty.api;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import biomesoplenty.api.Blocks;
import com.google.common.base.Optional;

public class BlockReferences {

	public static enum EnumBOPBlocks
	{
		acaciaLog (BOPBlocks.logs1, 0),
		cherryLog (BOPBlocks.logs1, 1),
		darkLog (BOPBlocks.logs1, 2),
		firLog (BOPBlocks.logs1, 3),
		holyLog (BOPBlocks.logs2, 0),
		magicLog (BOPBlocks.logs2, 1),
		mangroveLog (BOPBlocks.logs2, 2),
		palmLog (BOPBlocks.logs2, 3),
		redwoodLog (BOPBlocks.logs3, 0),
		willowLog (BOPBlocks.logs3, 1),
		deadLog (BOPBlocks.logs3, 2),
		bigFlowerStem (BOPBlocks.logs3, 3),
		pineLog (BOPBlocks.logs4, 0),
		hellBarkLog (BOPBlocks.logs4, 1),
		jacarandaLog (BOPBlocks.logs4, 2),

		acaciaPlank (BOPBlocks.planks, 0),
		cherryPlank (BOPBlocks.planks, 1),
		darkPlank (BOPBlocks.planks, 2),
		firPlank (BOPBlocks.planks, 3),
		holyPlank (BOPBlocks.planks, 4),
		magicPlank (BOPBlocks.planks, 5),
		mangrovePlank (BOPBlocks.planks, 6),
		palmPlank (BOPBlocks.planks, 7),
		redwoodPlank (BOPBlocks.planks, 8),
		willowPlank (BOPBlocks.planks, 9),
		bambooThatching (BOPBlocks.planks, 10),
		pinePlank (BOPBlocks.planks, 11),
		hellBarkPlank (BOPBlocks.planks, 12),
		jacarandaPlank (BOPBlocks.planks, 13),

		acaciaLeaves (BOPBlocks.leavesColorized, 0),
		mangroveLeaves (BOPBlocks.leavesColorized, 1),
		palmLeaves (BOPBlocks.leavesColorized, 2),
		redwoodLeaves (BOPBlocks.leavesColorized, 3),
		willowLeaves (BOPBlocks.leavesColorized, 4),
		pineLeaves (BOPBlocks.leavesColorized, 5),

		yellowAutumnLeaves (BOPBlocks.leaves1, 0),
		bambooLeaves (BOPBlocks.leaves1, 1),
		magicLeaves (BOPBlocks.leaves1, 2),
		darkLeaves (BOPBlocks.leaves1, 3),
		deadLeaves (BOPBlocks.leaves1, 4),
		firLeaves (BOPBlocks.leaves1, 5),
		holyLeaves (BOPBlocks.leaves1, 6),
		orangeAutumnLeaves (BOPBlocks.leaves1, 7),
		originLeaves (BOPBlocks.leaves2, 0),
		pinkCherryLeaves (BOPBlocks.leaves2, 1),
		mapleLeaves (BOPBlocks.leaves2, 2),
		whiteCherryLeaves (BOPBlocks.leaves2, 3),
		hellBarkLeaves (BOPBlocks.leaves2, 4),
		jacarandaLeaves (BOPBlocks.leaves2, 5),

		appleLeaves (BOPBlocks.leavesFruit, 3),
		appleLeavesFruitless (BOPBlocks.leavesFruit, 0),

		bamboo (BOPBlocks.bamboo, 0),

		poisonIvyItem (BOPBlocks.foliage, 7),
		sproutItem (BOPBlocks.foliage, 5),
		bushItem (BOPBlocks.foliage, 4),
		highGrassItem (BOPBlocks.foliage, 3),
		mediumGrassItem (BOPBlocks.foliage, 2),
		shortGrassItem (BOPBlocks.foliage, 1),
		algae (BOPBlocks.foliage, 0),

		holySapling (BOPBlocks.saplings, 7),
		magicSapling (BOPBlocks.saplings, 3),
		darkSapling (BOPBlocks.saplings, 4),
		deadSapling (BOPBlocks.saplings, 5),
		acaciaSapling (BOPBlocks.colorizedSaplings, 0),
		firSapling (BOPBlocks.saplings, 6),
		mangroveSapling (BOPBlocks.colorizedSaplings, 1),
		palmSapling (BOPBlocks.colorizedSaplings, 2),
		redwoodSapling (BOPBlocks.colorizedSaplings, 3),
		willowSapling (BOPBlocks.colorizedSaplings, 4),
		pineSapling (BOPBlocks.colorizedSaplings, 5),
		mapleSapling (BOPBlocks.saplings, 11),
		orangeAutumnSapling (BOPBlocks.saplings, 8),
		pinkCherrySapling (BOPBlocks.saplings, 10),
		whiteCherrySapling (BOPBlocks.saplings, 12),
		hellBarkSapling (BOPBlocks.saplings, 13),
		jacarandaSapling (BOPBlocks.saplings, 14),
		appleSapling (BOPBlocks.saplings, 0),
		originSapling (BOPBlocks.saplings, 9),
		yellowAutumnSapling (BOPBlocks.saplings, 1),
		bambooSapling (BOPBlocks.saplings, 2),

		mud (BOPBlocks.mud, 0),
		driedDirt (BOPBlocks.driedDirt, 0),
		redRock (BOPBlocks.redRock, 0),
		ash (BOPBlocks.ash, 0),
		ashStone (BOPBlocks.ashStone, 0),
		hardIce (BOPBlocks.hardIce, 0),
		originGrass (BOPBlocks.originGrass, 0),
		hardSand (BOPBlocks.hardSand, 0),
		hardDirt (BOPBlocks.hardDirt, 0),
		holyGrass (BOPBlocks.holyGrass, 0),
		holyDirt (BOPBlocks.holyDirt, 0),
		holyStone (BOPBlocks.holyStone, 0),
		holyStoneCobble (BOPBlocks.holyStone, 1),
		crystal (BOPBlocks.crystal, 0),
		cragRock (BOPBlocks.cragRock, 0),
		quicksand (BOPBlocks.mud, 1),
		cloud (BOPBlocks.cloud, 0),
		smolderingGrass (BOPBlocks.holyGrass, 1),
		redRockCobble (BOPBlocks.redRock, 1),
		giantFlowerRed (BOPBlocks.petals, 0),
		giantFlowerYellow (BOPBlocks.petals, 1),
		
		amethystOre (BOPBlocks.amethystOre, 0),
		amethystBlock (BOPBlocks.amethystOre, 1),
		rubyOre (BOPBlocks.amethystOre, 2),
		rubyBlock (BOPBlocks.amethystOre, 3),
		peridotOre (BOPBlocks.amethystOre, 4),
		peridotBlock (BOPBlocks.amethystOre, 5),
		topazOre (BOPBlocks.amethystOre, 6),
		topazBlock (BOPBlocks.amethystOre, 7),
		tanzaniteOre (BOPBlocks.amethystOre, 8),
		tanzaniteBlock (BOPBlocks.amethystOre, 9),
		apatiteOre (BOPBlocks.amethystOre, 10),
		apatiteBlock (BOPBlocks.amethystOre, 11),
		sapphireOre (BOPBlocks.amethystOre, 12),
		sapphireBlock (BOPBlocks.amethystOre, 13),

		smallBoneSegment (BOPBlocks.bones, 0),
		mediumBoneSegment (BOPBlocks.bones, 1),
		largeBoneSegment (BOPBlocks.bones, 2),

		kelp (BOPBlocks.coral, 0),

		toadstool (BOPBlocks.mushrooms, 0),
		portobello (BOPBlocks.mushrooms, 1),
		bluemilk (BOPBlocks.mushrooms, 2),
		glowshroom (BOPBlocks.mushrooms, 3),

		deadGrass (BOPBlocks.plants, 0),
		desertGrass (BOPBlocks.plants, 1),
		desertSprouts (BOPBlocks.plants, 2),
		duneGrass (BOPBlocks.plants, 3),
		holyTallGrass (BOPBlocks.plants, 4),
		thorn (BOPBlocks.plants, 5),
		barley (BOPBlocks.plants, 6),
		cattail (BOPBlocks.plants, 7),
		reed (BOPBlocks.plants, 8),

		treeMoss (BOPBlocks.treeMoss, 0),
		moss (BOPBlocks.moss, 0),
		willow (BOPBlocks.willow, 0),
		ivy (BOPBlocks.ivy, 0),

		clover (BOPBlocks.flowers, 0),
		swampFlower (BOPBlocks.flowers, 1),
		deathbloom (BOPBlocks.flowers, 2),
		glowFlower (BOPBlocks.flowers, 3),
		hydrangea (BOPBlocks.flowers, 4),
		daisy (BOPBlocks.flowers, 5),
		tulip (BOPBlocks.flowers, 6),
		wildFlower (BOPBlocks.flowers, 7),
		violet (BOPBlocks.flowers, 8),
		anenome (BOPBlocks.flowers, 9),
		lilyflower (BOPBlocks.flowers, 10),
		tinyCactus (BOPBlocks.flowers, 11),
		aloe (BOPBlocks.flowers, 12),
		sunflower (BOPBlocks.flowers, 13),
		dandelion (BOPBlocks.flowers, 15),
		;

		public Optional<? extends Block> block;
		public int meta;

		private EnumBOPBlocks(Optional<? extends Block> block, int meta) {
			this.block = block;
			this.meta = meta;
		}

		public Optional<? extends Block> getBlock() {
			return block;
		}

		public int getMeta() {
			return meta;
		}
	}

	public static ItemStack getBlockItemStack(String string)
	{
		Optional<? extends Block> stackblock = EnumBOPBlocks.valueOf(string).block;
		int stackmeta = EnumBOPBlocks.valueOf(string).meta;

		if (stackmeta != 0)
			return new ItemStack(stackblock.get(), 1, stackmeta);
		else
			return new ItemStack(stackblock.get(), 1);
	}

	public static int getBlockID(String string)
	{
		Optional<? extends Block> stackblock = EnumBOPBlocks.valueOf(string).block;

		return stackblock.get().blockID;
	}

	public static int getBlockMeta(String string)
	{
		int stackmeta = EnumBOPBlocks.valueOf(string).meta;

		return stackmeta;
	}
}
