package minechem.handler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import minechem.reference.Compendium;

public class MessageHandler implements IMessageHandler
{
    public static SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(Compendium.Naming.id);

    /**
     * Initialize the MessageHandler
     */
    public static void init()
    {
    }

    /**
     * Called when a message is received of the appropriate type. You can optionally return a reply message, or null if no reply is needed.
     *
     * @param message The message
     * @return an optional return message
     */
    @Override
    public IMessage onMessage(IMessage message, MessageContext ctx)
    {
        return null;
    }
}
