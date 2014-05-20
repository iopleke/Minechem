package universalelectricity.api.vector;

import net.minecraft.world.World;

/** Useful interface to define that an object has a 3D location, and a defined world.
 * 
 * @author DarkGuardsman */
public interface IVectorWorld extends IVector3
{
    World world();
}
