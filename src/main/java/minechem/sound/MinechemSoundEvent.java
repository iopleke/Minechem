package minechem.sound;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;

public class MinechemSoundEvent
{

    @SubscribeEvent
    public void onSound(SoundLoadEvent event)
    {
	    //TODO:Sound event
        //event.manager.soundPoolSounds.addSound(Reference.TEXTURE_MOD_ID + "assets/minechem/sound/minechem/projector.ogg");
    }

}
