package pixlepix.minechem.common.inventory;

import java.util.ArrayList;
import pixlepix.minechem.api.util.Util;
import net.minecraft.item.ItemStack;

public class Transactor {

   BoundedInventory inventory;
   int maxStackSize;


   public Transactor(BoundedInventory var1) {
      this.maxStackSize = 64;
      this.inventory = var1;
   }

   public Transactor(BoundedInventory var1, int var2) {
      this(var1);
      this.maxStackSize = var2;
   }

   public int add(ItemStack var1, boolean var2) {
      int var3 = 0;
      int var4 = var1.stackSize;

      for(int var5 = 0; var4 > 0 && var5 < this.inventory.getSizeInventory(); ++var5) {
         int var6 = this.putStackInSlot(var1, var4, var5, var2);
         var3 += var6;
         var4 -= var6;
      }

      return var3;
   }

   public ItemStack[] remove(int var1, boolean var2) {
      int var3 = var1;
      ArrayList var4 = new ArrayList();

      for(int var5 = 0; var3 > 0 && var5 < this.inventory.getSizeInventory(); ++var5) {
         ItemStack var6 = null;
         if(var2) {
            var6 = this.inventory.decrStackSize(var5, var3);
         } else {
        	if (this.inventory.getStackInSlot(var5) != null){
        		var6 = this.inventory.getStackInSlot(var5).copy();
        		var6.stackSize = Math.min(var1, var6.stackSize);
        	}
         }

         if(var6 != null) {
            var3 -= var6.stackSize;
            var4.add(var6);
         }
      }

      return (ItemStack[])var4.toArray(new ItemStack[var4.size()]);
   }

   public ItemStack removeItem(boolean var1) {
      for(int var2 = 0; var2 < this.inventory.getSizeInventory(); ++var2) {
         ItemStack var3 = this.inventory.getStackInSlot(var2);
         if(var3 != null) {
            if(var1) {
               return this.inventory.decrStackSize(var2, 1);
            }

            ItemStack var4 = var3.copy();
            var4.stackSize = 1;
            return var4;
         }
      }

      return null;
   }

   public int putStackInSlot(ItemStack var1, int var2, int var3, boolean var4) {
      ItemStack var5 = this.inventory.getStackInSlot(var3);
      if(var5 == null) {
         ItemStack var6 = var1.copy();
         var6.stackSize = Math.min(var2, this.getMaxStackSize(var1));
         if(var4) {
            this.inventory.setInventorySlotContents(var3, var6);
         }

         return var6.stackSize;
      } else {
         return Util.stacksAreSameKind(var1, var5)?this.appendStackToSlot(var1, var2, var3, var4):0;
      }
   }

   public int appendStackToSlot(ItemStack var1, int var2, int var3, boolean var4) {
      ItemStack var5 = this.inventory.getStackInSlot(var3);
      if(var5.stackSize + var2 > this.getMaxStackSize(var5)) {
         int var6 = this.getMaxStackSize(var5) - var5.stackSize;
         if(var4) {
            var5.stackSize += var6;
         }

         return var6;
      } else {
         if(var4) {
            var5.stackSize += var2;
         }

         return var2;
      }
   }

   public int getMaxStackSize(ItemStack var1) {
      return Math.min(var1.getMaxStackSize(), this.maxStackSize);
   }
}
