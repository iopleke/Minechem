package minechem.network.packet;

import minechem.item.element.ElementEnum;
import minechem.item.polytool.PolytoolGui;
import minechem.item.polytool.PolytoolHelper;
import minechem.item.polytool.PolytoolUpgradeType;
import minechem.network.PacketDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class PolytoolTypePacket extends PacketDispatcher.AbstractPacket<PolytoolTypePacket>{

	public PolytoolUpgradeType data;

	public PolytoolTypePacket(){}
	public PolytoolTypePacket(PolytoolUpgradeType type){
		this.data = type;
	}

	@Override
	public void sendPacket(PacketBuffer out) throws IOException {
		out.writeByte((byte) 42);
		out.writeByte(data.getElement().ordinal());
		out.writeByte((int) data.power);
	}

	@Override
	public void receivePacket(PacketBuffer in) throws IOException {
		in.readByte();
		data = PolytoolHelper.getTypeFromElement(ElementEnum.values()[in.readByte()], in.readByte());
	}

	@Override
	public void processPacket(EntityPlayer player) {
		if (Minecraft.getMinecraft().currentScreen instanceof PolytoolGui)
		{
			((PolytoolGui) Minecraft.getMinecraft().currentScreen).addUpgrade(data);
		}
	}
}
