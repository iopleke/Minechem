package minechem.item.journal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import minechem.helper.ArrayHelper;
import minechem.item.prefab.BasicItem;
import minechem.registry.ResearchRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;

/**
 *
 */
public class JournalItem extends BasicItem
{
    public JournalItem()
    {
        super("journal");
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (player.isSneaking())
        {
            ResearchRegistry.getInstance().addResearch(player, "Hydrogen");
            writeKnowledge(stack, player);
        } else
        {
            Minecraft.getMinecraft().displayGuiScreen(new JournalGUI(getKnowledgeKeys(stack), getAuthors(stack)));
        }
        return stack;
    }

    /**
     * Writes knowledge to the journal
     *
     * @param itemStack the journal stack
     * @param player    the player that writes the knowledge
     */
    public void writeKnowledge(ItemStack itemStack, EntityPlayer player)
    {
        NBTTagCompound tagCompound = itemStack.stackTagCompound;
        Set<String> playerKnowledge = ResearchRegistry.getInstance().getResearchFor(player);
        Set<String> bookKnowledge = new LinkedHashSet<String>();
        if (tagCompound == null)
        {
            tagCompound = new NBTTagCompound();
        } else if (tagCompound.hasKey("research"))
        {
            NBTTagList bookKnowledgeTag = tagCompound.getTagList("research", 8);
            for (int i = 0; i < bookKnowledgeTag.tagCount(); i++)
            {
                bookKnowledge.add(bookKnowledgeTag.getStringTagAt(i));
            }
        }
        bookKnowledge.addAll(playerKnowledge);
        NBTTagList bookKnowledgeTag = new NBTTagList();
        for (String knowledgeKey : bookKnowledge)
        {
            bookKnowledgeTag.appendTag(new NBTTagString(knowledgeKey));
        }
        tagCompound.setTag("research", bookKnowledgeTag);

        Set<String> authors = new LinkedHashSet<String>();
        if (tagCompound.hasKey("authors"))
        {
            NBTTagList authorsTag = tagCompound.getTagList("authors", 8);
            for (int i = 0; i < authorsTag.tagCount(); i++)
            {
                authors.add(authorsTag.getStringTagAt(i));
            }
        }
        authors.add(player.getDisplayName());
        NBTTagList authorsTag = new NBTTagList();
        for (String author : authors)
        {
            authorsTag.appendTag(new NBTTagString(author));
        }
        tagCompound.setTag("authors", authorsTag);
        itemStack.setTagCompound(tagCompound);
    }

    /**
     * Gets a list of authors
     *
     * @param itemStack the journal Stack
     * @return an array of authors can be either empty or null
     */
    public String[] getAuthors(ItemStack itemStack)
    {
        if (itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey("authors"))
        {
            NBTTagList authorsTag = itemStack.stackTagCompound.getTagList("authors", 8);
            String[] authors = new String[authorsTag.tagCount()];
            for (int i = 0; i < authorsTag.tagCount(); i++)
            {
                authors[i] = authorsTag.getStringTagAt(i);
            }
            return ArrayHelper.removeNulls(authors, String.class);
        }
        return null;
    }

    /**
     * Gets a list of knowledgeKeys
     *
     * @param itemStack the journal Stack
     * @return an array of knowledgeKeys can be either empty or null
     */
    public String[] getKnowledgeKeys(ItemStack itemStack)
    {
        if (itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey("research"))
        {
            NBTTagList authorsTag = itemStack.stackTagCompound.getTagList("research", 8);
            String[] knowledgeKeys = new String[authorsTag.tagCount()];
            for (int i = 0; i < authorsTag.tagCount(); i++)
            {
                knowledgeKeys[i] = authorsTag.getStringTagAt(i);
            }
            return ArrayHelper.removeNulls(knowledgeKeys, String.class);
        }
        return null;
    }

    /**
     * Writes the tooltip with author info
     *
     * @param itemStack the ItemStack
     * @param player    the player
     * @param lines     lines to print
     * @param bool      dunno ?
     */
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List lines, boolean bool)
    {
        super.addInformation(itemStack, player, lines, bool);
        String[] authors = getAuthors(itemStack);
        if (authors == null || authors.length < 1)
        {
            return;
        }
        lines.add("Written by:");
        for (String author : authors)
        {
            lines.add("- " + author);
        }
    }
}
