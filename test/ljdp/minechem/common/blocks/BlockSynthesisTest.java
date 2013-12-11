package ljdp.minechem.common.blocks;

import static org.junit.Assert.fail;
import ljdp.minechem.api.core.EnumElement;
import ljdp.minechem.common.items.ItemChemistJournal;
import ljdp.minechem.common.items.ItemElement;
import ljdp.minechem.common.items.ItemTestTube;
import ljdp.minechem.common.tileentity.TileEntitySynthesis;
import ljdp.minechem.utils.test.EntityItemMatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.logging.ILogAgent;
import net.minecraft.profiler.Profiler;
import net.minecraft.stats.StatList;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveHandler;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ICrashCallable;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.Side;

/**
 * Unit tests for BlockSynthesis (chemical synthesis machine).
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameData.class, GameRegistry.class, StatList.class,
        FMLInjectionData.class, FMLCommonHandler.class, Loader.class })
public class BlockSynthesisTest {

    private static final int DUMMY_BLOCKID = 4000;
    private static final int DUMMY_JOURNALID = 16000;
    private static final int DUMMY_TESTTUBEID = 16001;
    private static final int DUMMY_ELEMENTID = 16002;
    private static final int DUMMY_X = 0;
    private static final int DUMMY_Y = 1;
    private static final int DUMMY_Z = 2;

    private World mockWorld;
    private Loader mockLoader;
    
    private static BlockSynthesis synthesisBlock;
    private static ItemChemistJournal chemistJournalItem;
    private static ItemTestTube testTubeItem;
    private static ItemElement elementItem;
    private static boolean itemsRegistered = false;
    private TileEntitySynthesis synthesisTileEntity;
    
    // Block and item Ids can only be registered once, as they populate
    // a global array. Registration must happen as late as feasible, to ensure
    // that all mock objects are properly configured.
    private static void registerItems() {
        if (itemsRegistered) {
            return;
        }
        synthesisBlock = new BlockSynthesis(DUMMY_BLOCKID);
        chemistJournalItem = new ItemChemistJournal(DUMMY_JOURNALID);
        testTubeItem = new ItemTestTube(DUMMY_TESTTUBEID);
        elementItem = new ItemElement(DUMMY_ELEMENTID);
        itemsRegistered = true;
    }
    
    @Before
    public void setUp() {
        // Set up static method stubs to avoid having to instantiate all of
        // Minecraft+Forge. Mock setup is particularly icky due to the heavy
        // use of static singletons throughout both Minecraft and Forge;
        // a mix of static mocks, partial mocks, and fakes is needed.
        mockLoader = EasyMock.createMockBuilder(Loader.class)
            .addMockedMethod("getCallableCrashInformation").createMock();
        PowerMock.mockStaticPartial(Loader.class, "instance");
        
        PowerMock.mockStatic(Loader.class);
        PowerMock.mockStatic(GameData.class);
        PowerMock.mockStaticNice(GameRegistry.class);
        PowerMock.mockStatic(StatList.class);
        PowerMock.mockStatic(FMLInjectionData.class);
        
        EasyMock.expect(Loader.instance()).andReturn(mockLoader);
        ICrashCallable mockCrashCallable = EasyMock.createMock(ICrashCallable.class);
        EasyMock.expect(mockLoader.getCallableCrashInformation()).andReturn(mockCrashCallable);
        
        EasyMock.replay(mockLoader);
        PowerMock.replay(Loader.class);
        FMLCommonHandler mockFMLCommonHandler = EasyMock.createMockBuilder(
                FMLCommonHandler.class).addMockedMethod("getSide")
                .addMockedMethod("getCurrentLanguage").createMock();
        EasyMock.expect(mockFMLCommonHandler.getSide()).andReturn(Side.SERVER);
        EasyMock.expect(mockFMLCommonHandler.getCurrentLanguage())
            .andReturn("en_US").anyTimes();
        PowerMock.mockStaticPartial(FMLCommonHandler.class, "instance");
        EasyMock.expect(FMLCommonHandler.instance())
            .andReturn(mockFMLCommonHandler).anyTimes();
        EasyMock.replay(mockFMLCommonHandler);
        PowerMock.replay(FMLCommonHandler.class);
        
        // Expect any number of GameData.newItemAdded calls.
        GameData.newItemAdded(EasyMock.anyObject(Item.class));
        PowerMock.expectLastCall().anyTimes();
        
        WorldProvider fakeWorldProvider = new WorldProvider() {

            @Override
            public String getDimensionName() {
                return "DummyDimension";
            }
        };
        WorldSettings fakeWorldSettings = new WorldSettings(
                0L, EnumGameType.SURVIVAL, false, false, WorldType.DEFAULT);
            
        mockWorld = EasyMock.createMockBuilder(World.class)
                .withConstructor(ISaveHandler.class, String.class,
                        WorldProvider.class, WorldSettings.class,
                        Profiler.class, ILogAgent.class)
                .withArgs((ISaveHandler) null, "", fakeWorldProvider, 
                        fakeWorldSettings, (Profiler) null, (ILogAgent) null)
                        .addMockedMethod("getBlockTileEntity")
                        .addMockedMethod("removeBlockTileEntity")
                        .addMockedMethod("spawnEntityInWorld")
                        .createMock();

        registerItems();
        synthesisTileEntity = new TileEntitySynthesis();
    }

    @Test
    public void testBreakBlockDrops() {

        EasyMock.expect(mockWorld.getBlockTileEntity(DUMMY_X, DUMMY_Y, DUMMY_Z))
            .andReturn(synthesisTileEntity);
        mockWorld.removeBlockTileEntity(DUMMY_X, DUMMY_Y, DUMMY_Z);
        EasyMock.expectLastCall();

        // Add some items to every inventory slot.
        ItemStack itemstack = new ItemStack(Item.flint);
        ItemStack journalItemStack = new ItemStack(chemistJournalItem);
        chemistJournalItem.addItemStackToJournal(itemstack, journalItemStack,
                mockWorld);
        boolean[] slotUsed = new boolean[synthesisTileEntity.inventory.length];
        for (int idx: synthesisTileEntity.kJournal) {
            synthesisTileEntity.inventory[idx] = journalItemStack;
            slotUsed[idx] = true;
        }
        int nItems = 1;
        for (int idx: synthesisTileEntity.kBottles) {
            ItemStack bottles = new ItemStack(testTubeItem, nItems++);
            synthesisTileEntity.inventory[idx] = bottles;
            slotUsed[idx] = true;
        }
        nItems = 1;
        for (int idx: synthesisTileEntity.kOutput) {
            ItemStack output = new ItemStack(Item.flint, nItems);
            synthesisTileEntity.inventory[idx] = output;
            slotUsed[idx] = true;
        }
        nItems = 1;
        for (int idx: synthesisTileEntity.kRecipe) {
            ItemStack recipeInput = new ItemStack(elementItem, nItems,
                    EnumElement.C.ordinal());
            synthesisTileEntity.inventory[idx] = recipeInput;
            slotUsed[idx] = true;
        }
        nItems = 1;
        for (int idx: synthesisTileEntity.kStorage) {
            ItemStack storedItem = new ItemStack(elementItem, nItems,
                    EnumElement.H.ordinal());
            synthesisTileEntity.inventory[idx] = storedItem;
            slotUsed[idx] = true;
        }
        
        // All slots must be populated for the test to be valid.
        for (int idx = 0; idx < slotUsed.length; idx++) {
            if (!slotUsed[idx]) {
                fail("Inventory slot " + idx + " not populated by test");
            }
        }
        
        // Only "real" slots should have their contents spawned in the world.
        for (int idx: synthesisTileEntity.kRealSlots) {
            if (synthesisTileEntity.isGhostSlot(idx)) {
                fail("Inventory slot " + idx + " is a ghost slot but in real list");
            }
            EasyMock.expect(mockWorld.spawnEntityInWorld(
                    EntityItemMatcher.eqEntityItem(
                            synthesisTileEntity.inventory[idx]))).andReturn(true);
        }
        
        PowerMock.replay(GameData.class, GameRegistry.class, StatList.class,
                FMLInjectionData.class, FMLCommonHandler.class);
        EasyMock.replay(mockWorld);

        synthesisBlock.breakBlock(mockWorld, DUMMY_X, DUMMY_Y, DUMMY_Z, DUMMY_BLOCKID, 0);
        EasyMock.verify(mockWorld);
    }
}
