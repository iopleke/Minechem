package minechem.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import minechem.Minechem;
import minechem.network.message.*;

public class MessageHandler implements IMessageHandler
{
    public static SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(Minechem.ID);

    public static void init()
    {
        INSTANCE.registerMessage(SynthesisUpdateMessage.class, SynthesisUpdateMessage.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(DecomposerUpdateMessage.class, DecomposerUpdateMessage.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(PolytoolUpdateMessage.class, PolytoolUpdateMessage.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(GhostBlockMessage.class, GhostBlockMessage.class, 3, Side.CLIENT);
        INSTANCE.registerMessage(ChemistJournalActiveItemMessage.class, ChemistJournalActiveItemMessage.class, 4, Side.SERVER);
        INSTANCE.registerMessage(FissionUpdateMessage.class, FissionUpdateMessage.class, 5, Side.CLIENT);
        INSTANCE.registerMessage(FusionUpdateMessage.class, FusionUpdateMessage.class, 6, Side.CLIENT);
    }

    @Override
    public IMessage onMessage(IMessage message, MessageContext ctx)
    {
        return null;
    }
}
