package ljdp.minechem.api.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import ljdp.minechem.api.core.Chemical;
import net.minecraft.item.ItemStack;

public class BluePrinterRecipe {

   public static ArrayList<BluePrinterRecipe> recipes = new ArrayList<BluePrinterRecipe>();
   private ItemStack output;
   private Object[] shapedRecipe;
   private ArrayList unshapedRecipe;
   private int energyCost;
   private boolean isShaped;


   public static BluePrinterRecipe add(BluePrinterRecipe var0) {
      recipes.add(var0);
      return var0;
   }

   public static void remove(ItemStack itemStack){
		ArrayList<BluePrinterRecipe> recipes = BluePrinterRecipe.search(itemStack);
		
		for (BluePrinterRecipe recipe : recipes){
			BluePrinterRecipe.recipes.remove(recipe);
		}
   }
   
   public static ArrayList<BluePrinterRecipe> search(ItemStack itemStack){
	  ArrayList<BluePrinterRecipe> results = new ArrayList<BluePrinterRecipe>(); 
	   
	  for (BluePrinterRecipe recipe : BluePrinterRecipe.recipes){
		  if (itemStack.isItemEqual(recipe.output)){
			  results.add(recipe);
		  }
	  }
	  
	  return results;
	   
   }   
   
   public BluePrinterRecipe(ItemStack var1, boolean var2, int var3, Object ... var4) {
      this.output = var1;
      this.isShaped = var2;
      this.energyCost = var3;
      this.shapedRecipe = var4;
      this.unshapedRecipe = new ArrayList();
      Object[] var5 = var4;
      int var6 = var4.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Object var8 = var5[var7];
         if(var8 != null) {
            this.unshapedRecipe.add(var8);
         }
      }

   }

   public BluePrinterRecipe(ItemStack var1, boolean var2, int var3, ArrayList var4) {
      this.output = var1;
      this.isShaped = var2;
      this.energyCost = var3;
      this.shapedRecipe = (Chemical[])var4.toArray(new Chemical[var4.size()]);
      this.unshapedRecipe = var4;
   }

   public ItemStack getOutput() {
      return this.output;
   }

   public boolean isShaped() {
      return this.isShaped;
   }

   public int energyCost() {
      return this.energyCost;
   }

   public Object[] getShapedRecipe() {
      return this.shapedRecipe;
   }

   public ArrayList getShapelessRecipe() {
      return this.unshapedRecipe;
   }

   public int getIngredientCount() {
      int var1 = 0;

      Chemical var3;
      for(Iterator var2 = this.unshapedRecipe.iterator(); var2.hasNext(); var1 += var3.amount) {
         var3 = (Chemical)var2.next();
      }

      return var1;
   }

}
