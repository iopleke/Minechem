package pixlepix.minechem.minechem.common.sound;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LoopingSound {

    private String sound;
    private int soundLength;
    private int timer;
    private Entity entity;
    private float volume;
    private float pitch;

    public LoopingSound(String sound, int soundLength) {
        this.sound = sound;
        this.soundLength = soundLength;
        this.volume = 1.0F;
        this.pitch = 1.0F;
        this.timer = soundLength;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void play(World world, double x, double y, double z) {
        if (timer == soundLength) {
            timer = 0;
            if (this.entity == null) {
                world.playSoundEffect(x, y, z, this.sound, this.volume, this.pitch);
            } else {
                world.playSoundAtEntity(this.entity, this.sound, this.volume, this.pitch);
            }
        }
        timer++;
    }

}
