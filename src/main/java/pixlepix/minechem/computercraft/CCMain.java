package pixlepix.minechem.computercraft;

import pixlepix.minechem.common.MinechemItems;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.turtle.api.TurtleAPI;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

public class CCMain implements ICCMain {

    private static CCMain instance;

    public static CCMain getInstance() {
        if (instance == null)
            new CCMain();
        return instance;
    }

    public CCMain() {
        instance = this;
    }

    public void loadConfig(Configuration config) {
        CCItems.loadConfig(config);
        config.save();
    }

    public void init() {
        CCItems.registerItems();
        TurtleAPI.registerUpgrade(new ChemistryUpgrade());
        GameRegistry.addRecipe(new ItemStack(CCItems.chemistryUpgrade), "RRR", "RAR", "RRR", 'R', new ItemStack(Item.redstone), 'A', new ItemStack(
                MinechemItems.atomicManipulator));
    }

}
