package universalelectricity.api.vector;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class VectorWorld extends Vector3 implements IVectorWorld
{
    public World world;

    public VectorWorld(World world, double x, double y, double z)
    {
        super(x, y, z);
        this.world = world;
    }
    
    public VectorWorld(IVectorWorld vectorWorld)
    {
        this(vectorWorld.world(), vectorWorld.x(), vectorWorld.y(), vectorWorld.z());
    }

    public VectorWorld(NBTTagCompound nbt)
    {
        super(nbt);
        this.world = DimensionManager.getWorld(nbt.getInteger("d"));
    }

    public VectorWorld(Entity entity)
    {
        super(entity);
        this.world = entity.worldObj;
    }

    public VectorWorld(TileEntity tile)
    {
        super(tile);
        this.world = tile.worldObj;
    }

    public VectorWorld(World world, IVector3 v)
    {
        this.x = v.x();
        this.y = v.y();
        this.z = v.z();
        this.world = world;
    }
    
    @Override
    public World world()
    {
        return this.world;
    }

    @Override
    public VectorWorld translate(double x, double y, double z)
    {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public VectorWorld clone()
    {
        return new VectorWorld(world, x, y, z);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("d", this.world.provider.dimensionId);
        return nbt;
    }

    public int getBlockID()
    {
        return super.getBlockID(this.world);
    }

    public int getBlockMetadata()
    {
        return super.getBlockMetadata(this.world);
    }

    public TileEntity getTileEntity()
    {
        // Ensure that the world object actually exists.
        if (this.world != null)
        {
            return super.getTileEntity(this.world);
        }
        
        return null;
    }

    public boolean setBlock(int id, int metadata, int notify)
    {
        return super.setBlock(this.world, id, metadata, notify);
    }

    public boolean setBlock(int id, int metadata)
    {
        return this.setBlock(id, metadata, 3);
    }

    public boolean setBlock(int id)
    {
        return this.setBlock(id, 0);
    }

    public List<Entity> getEntitiesWithin(Class<? extends Entity> par1Class)
    {
        return super.getEntitiesWithin(this.world, par1Class);
    }

    public static VectorWorld fromCenter(Entity e)
    {
        return new VectorWorld(e.worldObj, e.posX, e.posY - e.yOffset + e.height / 2, e.posZ);
    }

    public static VectorWorld fromCenter(TileEntity e)
    {
        return new VectorWorld(e.worldObj, e.xCoord + 0.5, e.yCoord + 0.5, e.zCoord + 0.5);
    }

    public MovingObjectPosition rayTraceEntities(VectorWorld target)
    {
        return super.rayTraceEntities(target.world(), target);
    }
    
    public MovingObjectPosition rayTraceEntities(Entity target)
    {
        return super.rayTraceEntities(world, target);
    }

    public MovingObjectPosition rayTraceEntities(Vector3 target)
    {
        return super.rayTraceEntities(world, target);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof VectorWorld)
        {
            return super.equals(o) && this.world == ((VectorWorld) o).world;
        }
        return super.equals(o);
    }

    @Override
    public String toString()
    {
        return "VectorWorld [" + this.x + "," + this.y + "," + this.z + "," + this.world + "]";
    }    
}
