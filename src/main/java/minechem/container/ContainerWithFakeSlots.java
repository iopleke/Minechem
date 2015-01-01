package minechem.container;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import minechem.slot.SlotFake;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerWithFakeSlots extends Container
{

    private static int MOUSE_LEFT = 0;

    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {
        return false;
    }

    /**
     * returns a list if itemStacks, for each non-fake slot.
     */
    @Override
    public List getInventory()
    {
        ArrayList arraylist = new ArrayList();

        for (int i = 0; i < this.inventorySlots.size(); ++i)
        {
            Slot slot = ((Slot) this.inventorySlots.get(i));
            arraylist.add((slot instanceof SlotFake) ? null : slot.getStack());
        }

        return arraylist;
    }

    @Override
    public ItemStack slotClick(int slotNum, int mouseButton, int isShiftPressed, EntityPlayer entityPlayer)
    {
        Slot slot = null;
        if (slotNum >= 0 && slotNum < this.inventorySlots.size())
        {
            slot = getSlot(slotNum);
        }
        if (slot != null && slot instanceof SlotFake)
        {
            ItemStack stackOnMouse = entityPlayer.inventory.getItemStack();
            if (stackOnMouse != null && slot.isItemValid(stackOnMouse))
            {
                if (mouseButton == MOUSE_LEFT)
                {
                    addStackToSlot(stackOnMouse, slot, stackOnMouse.stackSize);
                } else
                {
                    addStackToSlot(stackOnMouse, slot, 1);
                }
            } else
            {
                slot.putStack(null);
            }
            return null;
        } else
        {
            return superSlotClick(slotNum, mouseButton, isShiftPressed, entityPlayer);
        }
    }

    public ItemStack superSlotClick(int slotNum, int mouseButton, int isShiftPressed, EntityPlayer entityPlayer)
    {
        ItemStack itemstack = null;
        InventoryPlayer inventoryplayer = entityPlayer.inventory;
        int i1;
        ItemStack itemstack3;

        if (isShiftPressed == 5)
        {
            int l = this.field_94536_g;
            this.field_94536_g = func_94532_c(mouseButton);

            if ((l != 1 || this.field_94536_g != 2) && l != this.field_94536_g)
            {
                this.func_94533_d();
            } else if (inventoryplayer.getItemStack() == null)
            {
                this.func_94533_d();
            } else if (this.field_94536_g == 0)
            {
                this.field_94535_f = func_94529_b(mouseButton);

                if (func_94528_d(this.field_94535_f))
                {
                    this.field_94536_g = 1;
                    this.field_94537_h.clear();
                } else
                {
                    this.func_94533_d();
                }
            } else if (this.field_94536_g == 1)
            {
                Slot slot = (Slot) this.inventorySlots.get(slotNum);

                if (slot != null && func_94527_a(slot, inventoryplayer.getItemStack(), true) && slot.isItemValid(inventoryplayer.getItemStack()) && inventoryplayer.getItemStack().stackSize > this.field_94537_h.size() && this.canDragIntoSlot(slot))
                {
                    this.field_94537_h.add(slot);
                }
            } else if (this.field_94536_g == 2)
            {
                if (!this.field_94537_h.isEmpty())
                {
                    itemstack3 = inventoryplayer.getItemStack().copy();
                    i1 = inventoryplayer.getItemStack().stackSize;
                    Iterator iterator = this.field_94537_h.iterator();

                    while (iterator.hasNext())
                    {
                        Slot slot1 = (Slot) iterator.next();

                        if (slot1 != null && func_94527_a(slot1, inventoryplayer.getItemStack(), true) && slot1.isItemValid(inventoryplayer.getItemStack()) && inventoryplayer.getItemStack().stackSize >= this.field_94537_h.size() && this.canDragIntoSlot(slot1))
                        {
                            ItemStack itemstack1 = itemstack3.copy();
                            int j1 = slot1.getHasStack() ? slot1.getStack().stackSize : 0;
                            func_94525_a(this.field_94537_h, this.field_94535_f, itemstack1, j1);

                            if (itemstack1.stackSize > itemstack1.getMaxStackSize())
                            {
                                itemstack1.stackSize = itemstack1.getMaxStackSize();
                            }

                            if (itemstack1.stackSize > slot1.getSlotStackLimit())
                            {
                                itemstack1.stackSize = slot1.getSlotStackLimit();
                            }

                            i1 -= itemstack1.stackSize - j1;
                            slot1.putStack(itemstack1);
                        }
                    }

                    itemstack3.stackSize = i1;

                    if (itemstack3.stackSize <= 0)
                    {
                        itemstack3 = null;
                    }

                    inventoryplayer.setItemStack(itemstack3);
                }

                this.func_94533_d();
            } else
            {
                this.func_94533_d();
            }
        } else if (this.field_94536_g != 0)
        {
            this.func_94533_d();
        } else
        {
            Slot slot2;
            int l1;
            ItemStack itemstack5;

            if ((isShiftPressed == 0 || isShiftPressed == 1) && (mouseButton == 0 || mouseButton == 1))
            {
                if (slotNum == -999)
                {
                    if (inventoryplayer.getItemStack() != null && slotNum == -999)
                    {
                        if (mouseButton == 0)
                        {
                            entityPlayer.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), true);
                            inventoryplayer.setItemStack(null);
                        }

                        if (mouseButton == 1)
                        {
                            entityPlayer.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack().splitStack(1), true);

                            if (inventoryplayer.getItemStack().stackSize == 0)
                            {
                                inventoryplayer.setItemStack(null);
                            }
                        }
                    }
                } else if (isShiftPressed == 1)
                {
                    if (slotNum < 0)
                    {
                        return null;
                    }

                    slot2 = (Slot) this.inventorySlots.get(slotNum);

                    if (slot2 != null && slot2.canTakeStack(entityPlayer))
                    {
                        itemstack3 = this.transferStackInSlot(entityPlayer, slotNum);

                        if (itemstack3 != null)
                        {
                            Item item = itemstack3.getItem();
                            itemstack = itemstack3.copy();

                            if (slot2.getStack() != null && slot2.getStack().getItem() == item)
                            {
                                this.retrySlotClick(slotNum, mouseButton, true, entityPlayer);
                            }
                        }
                    }
                } else
                {
                    if (slotNum < 0)
                    {
                        return null;
                    }

                    slot2 = (Slot) this.inventorySlots.get(slotNum);

                    if (slot2 != null)
                    {
                        itemstack3 = slot2.getStack();
                        ItemStack itemstack4 = inventoryplayer.getItemStack();

                        if (itemstack3 != null)
                        {
                            itemstack = itemstack3.copy();
                        }

                        if (itemstack3 == null)
                        {
                            if (itemstack4 != null && slot2.isItemValid(itemstack4))
                            {
                                l1 = mouseButton == 0 ? itemstack4.stackSize : 1;

                                if (l1 > slot2.getSlotStackLimit())
                                {
                                    l1 = slot2.getSlotStackLimit();
                                }

                                if (itemstack4.stackSize >= l1)
                                {
                                    slot2.putStack(itemstack4.splitStack(l1));
                                }

                                if (itemstack4.stackSize == 0)
                                {
                                    inventoryplayer.setItemStack(null);
                                }
                            }
                        } else if (slot2.canTakeStack(entityPlayer))
                        {
                            if (itemstack4 == null)
                            {
                                l1 = mouseButton == 0 ? itemstack3.stackSize : (itemstack3.stackSize + 1) / 2;
                                itemstack5 = slot2.decrStackSize(l1);
                                inventoryplayer.setItemStack(itemstack5);

                                if (itemstack3.stackSize == 0)
                                {
                                    slot2.putStack(null);
                                }

                                slot2.onPickupFromSlot(entityPlayer, inventoryplayer.getItemStack());
                            } else if (slot2.isItemValid(itemstack4))
                            {
                                if (itemstack3.getItem() == itemstack4.getItem() && itemstack3.getItemDamage() == itemstack4.getItemDamage() && ItemStack.areItemStackTagsEqual(itemstack3, itemstack4))
                                {
                                    l1 = mouseButton == 0 ? itemstack4.stackSize : 1;

                                    if (l1 > slot2.getSlotStackLimit() - itemstack3.stackSize)
                                    {
                                        l1 = slot2.getSlotStackLimit() - itemstack3.stackSize;
                                    }

                                    if (l1 > itemstack4.getMaxStackSize() - itemstack3.stackSize)
                                    {
                                        l1 = itemstack4.getMaxStackSize() - itemstack3.stackSize;
                                    }

                                    itemstack4.splitStack(l1);

                                    if (itemstack4.stackSize == 0)
                                    {
                                        inventoryplayer.setItemStack(null);
                                    }

                                    itemstack3.stackSize += l1;
                                } else if (itemstack4.stackSize <= slot2.getSlotStackLimit())
                                {
                                    slot2.putStack(itemstack4);
                                    inventoryplayer.setItemStack(itemstack3);
                                }
                            } else if (itemstack3.getItem() == itemstack4.getItem() && itemstack4.getMaxStackSize() > 1 && (!itemstack3.getHasSubtypes() || itemstack3.getItemDamage() == itemstack4.getItemDamage()) && ItemStack.areItemStackTagsEqual(itemstack3, itemstack4))
                            {
                                l1 = itemstack3.stackSize;

                                if (l1 > 0 && l1 + itemstack4.stackSize <= itemstack4.getMaxStackSize())
                                {
                                    itemstack4.stackSize += l1;
                                    itemstack3 = slot2.decrStackSize(l1);

                                    if (itemstack3.stackSize == 0)
                                    {
                                        slot2.putStack(null);
                                    }

                                    slot2.onPickupFromSlot(entityPlayer, inventoryplayer.getItemStack());
                                }
                            }
                        }

                        slot2.onSlotChanged();
                    }
                }
            } else if (isShiftPressed == 2 && mouseButton >= 0 && mouseButton < 9)
            {
                slot2 = (Slot) this.inventorySlots.get(slotNum);

                if (slot2.canTakeStack(entityPlayer))
                {
                    itemstack3 = inventoryplayer.getStackInSlot(mouseButton);
                    boolean flag = itemstack3 == null || slot2.inventory == inventoryplayer && slot2.isItemValid(itemstack3);
                    l1 = -1;

                    if (!flag)
                    {
                        l1 = inventoryplayer.getFirstEmptyStack();
                        flag |= l1 > -1;
                    }

                    if (slot2.getHasStack() && flag)
                    {
                        itemstack5 = slot2.getStack();
                        inventoryplayer.setInventorySlotContents(mouseButton, itemstack5.copy());

                        if ((slot2.inventory != inventoryplayer || !slot2.isItemValid(itemstack3)) && itemstack3 != null)
                        {
                            if (l1 > -1)
                            {
                                inventoryplayer.addItemStackToInventory(itemstack3);
                                slot2.decrStackSize(itemstack5.stackSize);
                                slot2.putStack(null);
                                slot2.onPickupFromSlot(entityPlayer, itemstack5);
                            }
                        } else
                        {
                            slot2.decrStackSize(itemstack5.stackSize);
                            slot2.putStack(itemstack3);
                            slot2.onPickupFromSlot(entityPlayer, itemstack5);
                        }
                    } else if (!slot2.getHasStack() && itemstack3 != null && slot2.isItemValid(itemstack3))
                    {
                        inventoryplayer.setInventorySlotContents(mouseButton, null);
                        slot2.putStack(itemstack3);
                    }
                }
            } else if (isShiftPressed == 3 && entityPlayer.capabilities.isCreativeMode && inventoryplayer.getItemStack() == null && slotNum >= 0)
            {
                slot2 = (Slot) this.inventorySlots.get(slotNum);

                if (slot2 != null && slot2.getHasStack())
                {
                    itemstack3 = slot2.getStack().copy();
                    itemstack3.stackSize = itemstack3.getMaxStackSize();
                    inventoryplayer.setItemStack(itemstack3);
                }
            } else if (isShiftPressed == 4 && inventoryplayer.getItemStack() == null && slotNum >= 0)
            {
                slot2 = (Slot) this.inventorySlots.get(slotNum);

                if (slot2 != null && slot2.getHasStack() && slot2.canTakeStack(entityPlayer))
                {
                    itemstack3 = slot2.decrStackSize(mouseButton == 0 ? 1 : slot2.getStack().stackSize);
                    slot2.onPickupFromSlot(entityPlayer, itemstack3);
                    entityPlayer.dropPlayerItemWithRandomChoice(itemstack3, true);
                }
            } else if (isShiftPressed == 6 && slotNum >= 0)
            {
                slot2 = (Slot) this.inventorySlots.get(slotNum);
                itemstack3 = inventoryplayer.getItemStack();

                if (itemstack3 != null && (slot2 == null || !slot2.getHasStack() || !slot2.canTakeStack(entityPlayer)))
                {
                    i1 = mouseButton == 0 ? 0 : this.inventorySlots.size() - 1;
                    l1 = mouseButton == 0 ? 1 : -1;

                    for (int i2 = 0; i2 < 2; ++i2)
                    {
                        for (int j2 = i1; j2 >= 0 && j2 < this.inventorySlots.size() && itemstack3.stackSize < itemstack3.getMaxStackSize(); j2 += l1)
                        {
                            Slot slot3 = (Slot) this.inventorySlots.get(j2);
                            if (slot3 instanceof SlotFake)
                            {
                                continue;
                            }
                            if (slot3.getHasStack() && func_94527_a(slot3, itemstack3, true) && slot3.canTakeStack(entityPlayer) && this.func_94530_a(itemstack3, slot3) && (i2 != 0 || slot3.getStack().stackSize != slot3.getStack().getMaxStackSize()))
                            {
                                int k1 = Math.min(itemstack3.getMaxStackSize() - itemstack3.stackSize, slot3.getStack().stackSize);
                                ItemStack itemstack2 = slot3.decrStackSize(k1);
                                itemstack3.stackSize += k1;

                                if (itemstack2.stackSize <= 0)
                                {
                                    slot3.putStack(null);
                                }

                                slot3.onPickupFromSlot(entityPlayer, itemstack2);
                            }
                        }
                    }
                }

                this.detectAndSendChanges();
            }
        }

        return itemstack;
    }

    private void addStackToSlot(ItemStack stackOnMouse, Slot slot, int amount)
    {
        ItemStack stackInSlot = slot.inventory.getStackInSlot(slot.slotNumber);
        if (stackInSlot != null)
        {
            stackInSlot.stackSize = Math.min(stackInSlot.stackSize + amount, slot.inventory.getInventoryStackLimit());
        } else
        {
            ItemStack copyStack = stackOnMouse.copy();
            copyStack.stackSize = amount;
            slot.putStack(copyStack);
        }
    }

}
