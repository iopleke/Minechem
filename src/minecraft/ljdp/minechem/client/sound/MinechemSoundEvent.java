package ljdp.minechem.client.sound;

import ljdp.minechem.common.ModMinechem;
import ljdp.minechem.common.utils.ConstantValue;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class MinechemSoundEvent {

    @ForgeSubscribe
    public void onSound(SoundLoadEvent event) {
        event.manager.soundPoolSounds.addSound("ljdp/minechem/projector.ogg", ModMinechem.class.getResource(ConstantValue.PROJECTOR_SOUND));
        event.manager.soundPoolSounds.addSound("ljdp/minechem/photobioreactor.ogg", ModMinechem.class.getResource(ConstantValue.FERMENTER_SOUND));
	}

}
