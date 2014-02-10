package ljdp.easypacket.example;

import ljdp.easypacket.EasyPacketDispatcher;
import ljdp.easypacket.EasyPacketHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Example {

    public Example() {
        EasyPacketDispatcher dispatcher = new EasyPacketDispatcher("examplechannel");
        ExamplePacket packet1 = new ExamplePacket("Luke Perkin", 21, true);
        EasyPacketHandler examplePacketHandler = EasyPacketHandler.registerEasyPacket(ExamplePacket.class, dispatcher);

        byte[] data = examplePacketHandler.write(packet1);
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));

        ExamplePacket packet2 = new ExamplePacket();
        examplePacketHandler.read(packet2, in);

        assert packet1.age == packet2.age;
        assert packet1.name.equals(packet2.name);
        assert packet1.isMale == packet2.isMale;
        assert packet1.someLong == packet2.someLong;
    }
}
