package minechem.item.journal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minechem.handler.JournalHandler;
import minechem.item.prefab.BasicItem;
import minechem.registry.ResearchRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 *
 */
public class JournalItem extends BasicItem
{
    public JournalItem()
    {
        super("journal");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        Minecraft.getMinecraft().displayGuiScreen(new JournalGUI(player.getUniqueID()));
        return stack;
    }
}
