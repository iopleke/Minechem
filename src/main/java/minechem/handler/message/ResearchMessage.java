package minechem.handler.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import minechem.registry.ResearchRegistry;

public class ResearchMessage extends BaseMessage implements IMessageHandler<ResearchMessage, IMessage>
{
    private String key;

    public ResearchMessage()
    {

    }

    public ResearchMessage(String key)
    {
        this.key = key;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        int length = buf.readInt();
        this.key = new String(buf.readBytes(length).array());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.key.length());
        buf.writeBytes(this.key.getBytes());
    }

    @Override
    public IMessage onMessage(ResearchMessage message, MessageContext ctx)
    {
        ResearchRegistry.getInstance().addResearch(getServerPlayer(ctx), message.key);
        return null;
    }
}
