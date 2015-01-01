package minechem.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolGui;
import minechem.item.polytool.PolytoolHelper;
import minechem.item.polytool.PolytoolUpgradeType;
import net.minecraft.client.Minecraft;

public class PolytoolUpdateMessage implements IMessage, IMessageHandler<PolytoolUpdateMessage, IMessage>
{
    private int element;
    private float power;

    public PolytoolUpdateMessage()
    {

    }

    public PolytoolUpdateMessage(PolytoolUpgradeType type)
    {
        this.element = type.getElement().atomicNumber();
        this.power = type.power;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.element = buf.readInt();
        this.power = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.element);
        buf.writeFloat(this.power);
    }

    @Override
    public IMessage onMessage(PolytoolUpdateMessage message, MessageContext ctx)
    {
        if (Minecraft.getMinecraft().currentScreen instanceof PolytoolGui)
        {
            ((PolytoolGui) Minecraft.getMinecraft().currentScreen).addUpgrade(PolytoolHelper.getTypeFromElement(ElementEnum.getByID(message.element), message.power));
        }
        return null;
    }
}
