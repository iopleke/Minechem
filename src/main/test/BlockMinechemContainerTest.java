package pixlepix.minechem.minechem.common.blocks;

import java.util.ArrayList;

import pixlepix.minechem.minechem.common.items.ItemChemistJournal;
import pixlepix.minechem.minechem.common.utils.EntityItemMatcher;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.logging.ILogAgent;
import net.minecraft.profiler.Profiler;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveHandler;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.FMLInjectionData;

/**
 * Unit tests for BlockMinechemContainer.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameData.class, GameRegistry.class, StatList.class,
        FMLInjectionData.class })
public class BlockMinechemContainerTest {
    
    public static class TestableBlockMinechemContainer 
        extends BlockMinechemContainer {
        
        private ArrayList<ItemStack> stacksDroppedOnBlockBreak;
        
        public TestableBlockMinechemContainer() {
            super(DUMMY_BLOCKID, DUMMY_MATERIAL);
            stacksDroppedOnBlockBreak = new ArrayList<ItemStack>();
        }

        @Override
        public TileEntity createNewTileEntity(World world) {
            // TODO Auto-generated method stub
            return null;
        }
        
        public void resetTestingState() {
            stacksDroppedOnBlockBreak = new ArrayList<ItemStack>();
        }
        
        public void setStacksToDropForTest(ArrayList<ItemStack> stacksToDrop) {
            stacksDroppedOnBlockBreak = stacksToDrop;
        }

        @Override
        public void addStacksDroppedOnBlockBreak(TileEntity tileEntity,
                ArrayList<ItemStack> itemStacks) {
            if (itemStacks != null) {
                itemStacks.addAll(stacksDroppedOnBlockBreak);
            }
        }
    }

    private static final int DUMMY_BLOCKID = 4000;
    private static final int DUMMY_JOURNALID = 16000;
    private static final Material DUMMY_MATERIAL = Material.iron;
    private static final int DUMMY_X = 0;
    private static final int DUMMY_Y = 1;
    private static final int DUMMY_Z = 2;
    
    // A block type must be instantiated only once (as it registers its ID).
    // The instance is reused across tests, and its associated test data
    // must be reset per test.
    private static TestableBlockMinechemContainer bmc = null;
    
    // A mock World used in tests.
    private World mockWorld;
    
    @Before
    public void setUp() {
        // Set up static method stubs to avoid having to instantiate all of
        // Minecraft+Forge
        PowerMock.mockStatic(GameData.class);
        PowerMock.mockStaticNice(GameRegistry.class);
        PowerMock.mockStatic(StatList.class);
        PowerMock.mockStatic(FMLInjectionData.class);
        
        // Expect any number of GameData.newItemAdded calls.
        GameData.newItemAdded(EasyMock.anyObject(Item.class));
        PowerMock.expectLastCall().anyTimes();
        
        WorldProvider wp = new WorldProvider() {

            @Override
            public String getDimensionName() {
                return "DummyDimension";
            }
        };
        WorldSettings ws = new WorldSettings(0L, EnumGameType.SURVIVAL, 
                false, false, WorldType.DEFAULT);
            
        mockWorld = EasyMock.createMockBuilder(World.class)
                .withConstructor(ISaveHandler.class, String.class, 
                        WorldProvider.class, WorldSettings.class, 
                        Profiler.class, ILogAgent.class)
                .withArgs((ISaveHandler) null, "", wp, ws,
                        (Profiler) null, (ILogAgent) null)
                        .addMockedMethod("getBlockTileEntity")
                        .addMockedMethod("removeBlockTileEntity")
                        .addMockedMethod("spawnEntityInWorld")
                        .createMock();
        // Reset testing state of the testable block instance, creating it
        // if necessary. It must be created only once, as it registers its Id
        // in the global block array. Creation is deferred to ensure that
        // necessary static mocking has been completed.
        if (bmc == null) {
            bmc = new TestableBlockMinechemContainer();
        }
        bmc.resetTestingState();
    }

    /**
     * Tests when no items are dropped from the block.
     */
    @Test
    public void testBreakBlockNoDrops() {
        TileEntity dummyTileEntity = new TileEntity();
        // The TileEntity should be fetched once, but no items should be added
        // to the world as none are dropped by the block.
        EasyMock.expect(mockWorld.getBlockTileEntity(DUMMY_X, DUMMY_Y, DUMMY_Z))
            .andReturn(dummyTileEntity);
        mockWorld.removeBlockTileEntity(DUMMY_X, DUMMY_Y, DUMMY_Z);
        EasyMock.expectLastCall();
        
        // Drop no items.
        ArrayList<ItemStack> itemsToDrop = new ArrayList<ItemStack>();
        bmc.setStacksToDropForTest(itemsToDrop);
        
        PowerMock.replay(GameData.class, GameRegistry.class, StatList.class,
                FMLInjectionData.class);
        EasyMock.replay(mockWorld);

        bmc.breakBlock(mockWorld, DUMMY_X, DUMMY_Y, DUMMY_Z, DUMMY_BLOCKID, 0);
        EasyMock.verify(mockWorld);
    }
    
    /**
     * Tests that NBT data is preserved in any dropped blocks.
     */
    @Test
    public void testBreakBlockNBTDrops() {
        TileEntity dummyTileEntity = new TileEntity();
        // The TileEntity should be fetched once, and a journal with matching
        // NBT data should be dropped.
        EasyMock.expect(mockWorld.getBlockTileEntity(DUMMY_X, DUMMY_Y, DUMMY_Z))
            .andReturn(dummyTileEntity);
        mockWorld.removeBlockTileEntity(DUMMY_X, DUMMY_Y, DUMMY_Z);
        EasyMock.expectLastCall();

        ArrayList<ItemStack> itemsToDrop = new ArrayList<ItemStack>();
        ItemChemistJournal journal = new ItemChemistJournal(DUMMY_JOURNALID);
        ItemStack itemstack = new ItemStack(Item.flint);
        ItemStack journalItemStack = new ItemStack(journal);
        journal.addItemStackToJournal(itemstack, journalItemStack, mockWorld);
        itemsToDrop.add(journalItemStack);
        
        // Drop one item with NBT data, and expect it to be dropped.
        bmc.setStacksToDropForTest(itemsToDrop);
        EasyMock.expect(mockWorld.spawnEntityInWorld(
                EntityItemMatcher.eqEntityItem(journalItemStack))).andReturn(true);

        PowerMock.replay(GameData.class, GameRegistry.class, StatList.class,
                FMLInjectionData.class);
        EasyMock.replay(mockWorld);

        bmc.breakBlock(mockWorld, DUMMY_X, DUMMY_Y, DUMMY_Z, DUMMY_BLOCKID, 0);
        EasyMock.verify(mockWorld);
    }
    
    /**
     * Tests when items are dropped from the block.
     */
    @Test
    public void testBreakBlockDrops() {
        TileEntity dummyTileEntity = new TileEntity();
        // The TileEntity should be fetched once, and several items should drop.
        EasyMock.expect(mockWorld.getBlockTileEntity(DUMMY_X, DUMMY_Y, DUMMY_Z))
            .andReturn(dummyTileEntity);
        mockWorld.removeBlockTileEntity(DUMMY_X, DUMMY_Y, DUMMY_Z);
        EasyMock.expectLastCall();

        ArrayList<ItemStack> itemsToDrop = new ArrayList<ItemStack>();
        
        // Note that the size randomization logic in BlockMinechemContainer
        // may make these tests fail, if the stack sizes are large enough
        // to be split by the randomization.
        itemsToDrop.add(new ItemStack(Item.flint));
        itemsToDrop.add(new ItemStack(Item.coal, 4));
        itemsToDrop.add(new ItemStack(Block.sapling, 10, 2));
        
        bmc.setStacksToDropForTest(itemsToDrop);
        EasyMock.expect(mockWorld.spawnEntityInWorld(
                EntityItemMatcher.eqEntityItem(itemsToDrop.get(0)))).andReturn(true);
        EasyMock.expect(mockWorld.spawnEntityInWorld(
                EntityItemMatcher.eqEntityItem(itemsToDrop.get(1)))).andReturn(true);
        EasyMock.expect(mockWorld.spawnEntityInWorld(
                EntityItemMatcher.eqEntityItem(itemsToDrop.get(2)))).andReturn(true);
        
        PowerMock.replay(GameData.class, GameRegistry.class, StatList.class,
                FMLInjectionData.class);
        EasyMock.replay(mockWorld);

        bmc.breakBlock(mockWorld, DUMMY_X, DUMMY_Y, DUMMY_Z, DUMMY_BLOCKID, 0);
        EasyMock.verify(mockWorld);
    }
}
