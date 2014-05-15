package pixlepix.minechem.common.entity;

import net.minecraft.world.World;
import pixlepix.minechem.api.BaseParticle;

public class SeedParticle extends BaseParticle {

    public SeedParticle(World par1World) {
        super(par1World);
    }

    @Override
    public float getStartingPotential() {
        return 200;
    }

    @Override
    public String getName() {
        return "Seed";
    }

    @Override
    public void onCollideWithParticle(BaseParticle particle) {
        if (!(particle instanceof SeedParticle)) {
            particle.potential = (float) Math.min(particle.potential * 1.1, particle.getStartingPotential() * 2);
            this.setDead();
        }
    }

}
