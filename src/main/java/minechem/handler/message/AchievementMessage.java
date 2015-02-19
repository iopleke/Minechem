package minechem.handler.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import minechem.chemical.Element;
import minechem.helper.AchievementHelper;
import minechem.registry.ElementRegistry;

/**
 * Used for triggering achievements from Client only code
 */
public class AchievementMessage extends BaseTEMessage implements IMessageHandler<AchievementMessage, IMessage>
{
    private String achievement;
    private boolean isElement;
    
    public AchievementMessage()
    {
                
    }
    
    public AchievementMessage(String achievement)
    {
        this.achievement = achievement;
        this.isElement = false;
    }
    
    public AchievementMessage(Element element)
    {
        this.achievement = element.shortName;
        this.isElement = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.isElement = buf.readBoolean();
        int length = buf.readInt();
        this.achievement = new String(buf.readBytes(length).array());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(this.isElement);
        buf.writeInt(this.achievement.length());
        buf.writeBytes(this.achievement.getBytes());
    }

    @Override
    public IMessage onMessage(AchievementMessage message, MessageContext ctx)
    {
        if (message.isElement)
            AchievementHelper.giveAchievement(getServerPlayer(ctx), AchievementHelper.getAchievement(ElementRegistry.getInstance().getElement(message.achievement)));
        else
            AchievementHelper.giveAchievement(getServerPlayer(ctx), AchievementHelper.getAchievement(message.achievement));
        return null;
    }
}
