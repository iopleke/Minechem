package minechem.client.sound;

import minechem.common.utils.Reference;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class MinechemSoundEvent
{

    @ForgeSubscribe
    public void onSound(SoundLoadEvent event)
    {
        event.manager.soundPoolSounds.addSound(Reference.TEXTURE_MOD_ID + "assets/minechem/sound/minechem/projector.ogg");
    }

}
