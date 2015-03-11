package minechem.apparatus.tier1.electrolysis;

import minechem.Compendium;
import minechem.apparatus.prefab.block.BasicBlockContainer;
import minechem.chemical.ChemicalBase;
import minechem.item.chemical.ChemicalItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ElectrolysisBlock extends BasicBlockContainer
{
    public ElectrolysisBlock()
    {
        super(Compendium.Naming.electrolysis, Material.glass, Block.soundTypeGlass);

        setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.85F, 0.8F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new ElectrolysisTileEntity();
    }

    /**
     * Open the GUI on block activation
     *
     * @param world  the game world object
     * @param x      the x coordinate of the block being activated
     * @param y      the y coordinate of the block being activated
     * @param z      the z coordinate of the block being activated
     * @param player the entityplayer object
     * @param side   which side was hit
     * @param hitX   on the side that was hit, the x coordinate
     * @param hitY   on the side that was hit, the y coordinate
     * @param hitZ   on the side that was hit, the z coordinate
     * @return boolean does the block get activated
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        // @TODO: add "player.capabilities.isCreativeMode" checks before removing/adding items to inventory
        TileEntity activatedTileEntity = world.getTileEntity(x, y, z);
        if (activatedTileEntity instanceof ElectrolysisTileEntity)
        {
            ElectrolysisTileEntity electrolysis = (ElectrolysisTileEntity) activatedTileEntity;
            acquireResearch(player, world);
            if (player.getCurrentEquippedItem() != null)
            {
                ItemStack clickedItemStack = player.getCurrentEquippedItem();
                if (clickedItemStack.getItem() instanceof ChemicalItem)
                {
                    ChemicalBase chemicalBase = ChemicalItem.getChemicalBase(clickedItemStack);
                    if (chemicalBase != null)
                    {
                        byte slot = electrolysis.addItem(clickedItemStack);
                        if (slot == 0 || slot == 1)
                        {
                            electrolysis.fillWithChemicalBase(chemicalBase, slot);
                            player.inventory.decrStackSize(player.inventory.currentItem, 1);
                        }

                    }
                }
            } else
            {
                ChemicalItem chemItem = null;
                if (electrolysis.getRightTube() != null)
                {
                    chemItem = electrolysis.removeItem(1);
                } else if (electrolysis.getLeftTube() != null)
                {
                    chemItem = electrolysis.removeItem(0);
                }

                if (chemItem != null)
                {
                    if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() != null)
                    {
                        if (player.getCurrentEquippedItem().getItem() instanceof ChemicalItem)
                        {
                            // @TODO: attempt to merge held items
                        }
                    } else
                    {
                        player.inventory.setInventorySlotContents(player.inventory.getFirstEmptyStack(), new ItemStack(chemItem));
                    }
                }
            }
        }
        return false;
    }
}
