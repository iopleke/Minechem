package minechem.helper;

import minechem.chemical.Element;
import minechem.handler.MessageHandler;
import minechem.handler.message.AchievementMessage;
import minechem.registry.AchievementRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;

public class AchievementHelper
{
    /**
     * Short hand for getting an {@link net.minecraft.stats.Achievement} from the {@link minechem.registry.AchievementRegistry} by name
     *  
     * @param name the achievement name
     * @return the {@link net.minecraft.stats.Achievement} or null if the name does not exist
     */
    public static Achievement getAchievement(String name)
    {
        return AchievementRegistry.getInstance().getAchievement(name);
    }

    /**
     * Short hand for getting an {@link net.minecraft.stats.Achievement} from the {@link minechem.registry.AchievementRegistry} by {@link minechem.chemical.Element}
     * 
     * @param element an {@link minechem.chemical.Element}
     * @return the {@link net.minecraft.stats.Achievement} or null if the {@link minechem.chemical.Element} does not exist
     */
    public static Achievement getAchievement(Element element)
    {
        return AchievementRegistry.getInstance().getAchievement(element);
    }

    /**
     * Give a {@link net.minecraft.entity.player.EntityPlayer} an {@link net.minecraft.stats.Achievement} for an {@link minechem.chemical.Element}
     * It will send an {@link minechem.handler.message.AchievementMessage} when the world is remote
     *  
     * @param player the {@link net.minecraft.entity.player.EntityPlayer} to grand the {@link net.minecraft.stats.Achievement}
     * @param element the {@link minechem.chemical.Element} to give the {@link net.minecraft.stats.Achievement} for
     * @param isRemote {@link net.minecraft.world.World#isRemote} is the to pass argument here
     */
    public static void giveAchievement(EntityPlayer player, Element element, boolean isRemote)
    {
        if (isRemote)
            MessageHandler.INSTANCE.sendToServer(new AchievementMessage(element));
        else
            giveAchievement(player, getAchievement(element));
    }

    /**
     * Give a {@link net.minecraft.entity.player.EntityPlayer} an {@link net.minecraft.stats.Achievement} wih given name
     * It will send an {@link minechem.handler.message.AchievementMessage} when the world is remote
     *
     * @param player the {@link net.minecraft.entity.player.EntityPlayer} to grand the {@link net.minecraft.stats.Achievement}
     * @param name the name of the {@link net.minecraft.stats.Achievement} to give
     * @param isRemote {@link net.minecraft.world.World#isRemote} is the to pass argument here
     */
    public static void giveAchievement(EntityPlayer player, String name, boolean isRemote)
    {
        if (isRemote)
            MessageHandler.INSTANCE.sendToServer(new AchievementMessage(name));
        else
            giveAchievement(player, getAchievement(name));
    }

    /**
     * Grants the {@link net.minecraft.entity.player.EntityPlayer}  an {@link net.minecraft.stats.Achievement}
     * @param player the {@link net.minecraft.entity.player.EntityPlayer} to grant the {@link net.minecraft.stats.Achievement}
     * @param achievement the {@link net.minecraft.stats.Achievement} to grant
     */
    public static void giveAchievement(EntityPlayer player, Achievement achievement)
    {
        player.triggerAchievement(achievement);
        LogHelper.info(player.getDisplayName() + "granted" + achievement.statId);
    }
}
