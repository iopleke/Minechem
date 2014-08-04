package minechem.sound;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import minechem.utils.Reference;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class MinechemSoundEvent
{

    @SubscribeEvent
    public void onSound(SoundLoadEvent event)
    {
        event.manager.soundPoolSounds.addSound(Reference.TEXTURE_MOD_ID + "assets/minechem/sound/minechem/projector.ogg");
    }

}
