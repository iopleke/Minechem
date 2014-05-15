package pixlepix.minechem.common.entity;

import net.minecraft.world.World;
import pixlepix.minechem.api.BaseParticle;

public class CoalParticle extends BaseParticle {

    public CoalParticle(World par1World) {
        super(par1World);
    }

    @Override
    public float getStartingPotential() {
        // TODO Auto-generated method stub
        return 4000;
    }

    @Override
    public void onEntityUpdate() {

        super.onEntityUpdate();
    }

    @Override
    public String getName() {
        return "Coal";
    }

    @Override
    public void onCollideWithParticle(BaseParticle particle) {
    }

}
