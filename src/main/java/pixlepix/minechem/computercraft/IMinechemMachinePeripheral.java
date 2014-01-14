package pixlepix.minechem.computercraft;

import net.minecraft.item.ItemStack;

public interface IMinechemMachinePeripheral {

    /**
     * Turtle is got ItemStack from Machine's TestTube Inventory
     * 
     * @return ItemStack to send to Turtle
     */
    public ItemStack takeEmptyTestTube();

    /**
     * Turtle is put ItemStack to Machine's TestTube Inventory
     * 
     * @param output
     *            ItemStack send from Turtle
     * @return Amount of items used from the passed stack.
     */
    public int putEmptyTestTube(ItemStack testTube);

    /**
     * Turtle is got ItemStack from Machine's Output Inventory
     * 
     * @return ItemStack to send to Turtle
     */
    public ItemStack takeOutput();

    /**
     * Turtle is put ItemStack to Machine's Output Inventory
     * 
     * @param output
     *            ItemStack send from Turtle
     * @return Amount of items used from the passed stack.
     */
    public int putOutput(ItemStack output);

    /**
     * Turtle is got ItemStack from Machine's Input Inventory
     * 
     * @return ItemStack to send to Turtle
     */
    public ItemStack takeInput();

    /**
     * Turtle is put ItemStack to Machine's Input Inventory
     * 
     * @param output
     *            ItemStack send from Turtle
     * @return Amount of items used from the passed stack.
     */
    public int putInput(ItemStack input);

    /**
     * Turtle is got ItemStack from Machine's FusionStar Inventory
     * 
     * @return ItemStack to send to Turtle
     */
    public ItemStack takeFusionStar();

    /**
     * Turtle is put ItemStack to Machine's FusionStar Inventory
     * 
     * @param output
     *            ItemStack send from Turtle
     * @return Amount of items used from the passed stack.
     */
    public int putFusionStar(ItemStack fusionStar);

    /**
     * Turtle is got ItemStack from Machine's Journal Inventory
     * 
     * @return ItemStack to send to Turtle
     */
    public ItemStack takeJournal();

    /**
     * Turtle is put ItemStack to Machine's Journal Inventory
     * 
     * @param output
     *            ItemStack send from Turtle
     * @return Amount of items used from the passed stack.
     */
    public int putJournal(ItemStack journal);

    public String getMachineState();

}