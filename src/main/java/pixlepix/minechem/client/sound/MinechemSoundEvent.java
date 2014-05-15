package pixlepix.minechem.client.sound;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import pixlepix.minechem.common.utils.ConstantValue;

public class MinechemSoundEvent
{

    @ForgeSubscribe
    public void onSound(SoundLoadEvent event)
    {
        event.manager.soundPoolSounds.addSound(ConstantValue.TEXTURE_MOD_ID + "assets/minechem/sound/minechem/projector.ogg");
    }

}
