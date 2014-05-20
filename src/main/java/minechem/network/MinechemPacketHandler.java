package minechem.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import minechem.ModMinechem;
import minechem.item.element.EnumElement;
import minechem.item.polytool.GuiPolytool;
import minechem.item.polytool.PolytoolHelper;
import minechem.item.polytool.PolytoolUpgradeType;
import minechem.network.MinechemPackets.ProtocolException;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MinechemPacketHandler implements IPacketHandler
{
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
        try
        {
            EntityPlayer entityPlayer = (EntityPlayer) player;
            ByteArrayDataInput in = ByteStreams.newDataInput(packet.data);
            int packetId = in.readUnsignedByte(); // Assuming your packetId is between 0 (inclusive) and 256 (exclusive). If you need more you need to change this
            MinechemPackets minechemPackets = MinechemPackets.constructPacket(packetId);
            minechemPackets.read(in);
            minechemPackets.execute(entityPlayer, entityPlayer.worldObj.isRemote ? Side.CLIENT : Side.SERVER);
        }
        catch (ProtocolException e)
        {
            if (player instanceof EntityPlayerMP)
            {
                ((EntityPlayerMP) player).playerNetServerHandler.kickPlayerFromServer("Protocol Exception!");
                ModMinechem.LOGGER.warning("Player " + ((EntityPlayer) player).username + " caused a Protocol Exception!");
            }
        }
        catch (ReflectiveOperationException e)
        {
            throw new RuntimeException("Unexpected Reflection exception during Packet construction!", e);
        }

        if (packet.data != null)
        {
            if (packet.data[0] == 42)
            {
                // PolytoolUpdatePacket
                this.receivePolytoolUpdate(manager, packet, player);
                return;
            }
            else
            {
                // dispatcher.onPacketData(manager, packet, player);
                DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
                int type = -1;
                try
                {
                    type = inputStream.readByte();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sendInterfacePacket(byte type, byte val)
    {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        try
        {
            dataStream.writeByte((byte) 1);

            dataStream.writeByte(type);
            dataStream.writeByte(val);

            PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket("Particle", byteStream.toByteArray()));
        }
        catch (IOException ex)
        {
            System.err.append("Failed to send button click packet");
        }
    }

    @SideOnly(Side.CLIENT)
    public void receivePolytoolUpdate(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiPolytool)
        {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.data);
            DataInputStream inputStream = new DataInputStream(byteArrayInputStream);
            try
            {
                inputStream.readByte();
                PolytoolUpgradeType upgrade = PolytoolHelper.getTypeFromElement(EnumElement.values()[inputStream.readByte()], inputStream.readByte());
                ((GuiPolytool) Minecraft.getMinecraft().currentScreen).addUpgrade(upgrade);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void sendPolytoolUpdatePacket(PolytoolUpgradeType data, EntityPlayer player)
    {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);
        try
        {
            dataStream.writeByte((byte) 42);
            dataStream.writeByte(data.getElement().ordinal());
            dataStream.writeByte((int) data.power);
        }
        catch (IOException e)
        {
            System.out.println("Exception sending polytool update packet");
            e.printStackTrace();
        }

        PacketDispatcher.sendPacketToPlayer(PacketDispatcher.getPacket(ModMinechem.CHANNEL_NAME, byteStream.toByteArray()), (Player) player);

    }
}