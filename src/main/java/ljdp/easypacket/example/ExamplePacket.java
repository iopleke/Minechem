package ljdp.easypacket.example;

import cpw.mods.fml.common.network.Player;
import ljdp.easypacket.EasyPacket;
import ljdp.easypacket.EasyPacketData;

public class ExamplePacket extends EasyPacket {

    @EasyPacketData
    public String name;

    @EasyPacketData
    public int age;

    @EasyPacketData
    public boolean isMale;

    @EasyPacketData
    public long someLong;

    public ExamplePacket(String name, int age, boolean isMale) {
        this.name = name;
        this.age = age;
        this.isMale = isMale;
        this.someLong = 1827366;
    }

    public ExamplePacket() {
    }

    @Override
    public boolean isChunkDataPacket() {
        return false;
    }

    @Override
    public void onReceive(Player player) {
    }
}
