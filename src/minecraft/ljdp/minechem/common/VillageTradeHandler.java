package ljdp.minechem.common;

import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class VillageTradeHandler implements IVillageTradeHandler {

    private static int farmer = 0;
    private static int librarian = 1;
    private static int priest = 2;
    private static int blacksmith = 3;
    private static int butcher = 4;

    @Override
    public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
        int profession = villager.getProfession();

        if (profession == librarian) {
            recipeList.add(new MerchantRecipe(new ItemStack(Item.emerald), new ItemStack(Item.diamond), new ItemStack(MinechemItems.blueprint, 1, 0)));
            if (random.nextFloat() < .5F) {
                recipeList.add(new MerchantRecipe(new ItemStack(Item.emerald), new ItemStack(Item.diamond), new ItemStack(MinechemItems.blueprint, 1, 3)));
            }
            /*
             * recipeList.add(new MerchantRecipe(new ItemStack(Item.emerald), new ItemStack(Item.diamond), new ItemStack(MinechemItems.blueprint, 1, 1)) );
             * recipeList.add(new MerchantRecipe(new ItemStack(Item.emerald), new ItemStack(Item.diamond), new ItemStack(MinechemItems.blueprint, 1, 2)) );
             */
        } else if (profession == blacksmith) {
            recipeList.add(new MerchantRecipe(new ItemStack(Item.emerald, 3), new ItemStack(MinechemBlocks.decomposer)));
            recipeList.add(new MerchantRecipe(new ItemStack(Item.emerald, 3), new ItemStack(MinechemBlocks.microscope)));
            recipeList.add(new MerchantRecipe(new ItemStack(Item.emerald, 10), new ItemStack(MinechemBlocks.synthesis)));
        }
    }

}
