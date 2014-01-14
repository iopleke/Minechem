package pixlepix.minechem.computercraft.method;

import buildcraft.api.core.Position;
import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import pixlepix.minechem.api.recipe.SynthesisRecipe;
import pixlepix.minechem.common.tileentity.TileEntitySynthesis;
import pixlepix.minechem.computercraft.ChemistryTurtleUpgradePeripherial;
import pixlepix.minechem.computercraft.ICCMethod;
import pixlepix.minechem.computercraft.IMinechemTurtlePeripheral;

public class PlaceSynthesisRecipe implements ICCMethod {

    @Override
    public String getMethodName() {
        return "placeSynthesisRecipe";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception {
        boolean didPlace = false;
        TileEntitySynthesis synthesis = getSynthesisMachineInFront(turtle);
        if (synthesis != null) {
            IMinechemTurtlePeripheral periperal = ChemistryTurtleUpgradePeripherial.getMinechemPeripheral(turtle);
            SynthesisRecipe recipe = periperal.getSynthesisRecipe();
            synthesis.setRecipe(recipe);
            didPlace = true;
        }
        return new Object[]{didPlace};
    }

    public TileEntitySynthesis getSynthesisMachineInFront(ITurtleAccess turtle) {
        Vec3 vector = turtle.getPosition();
        ForgeDirection direction = ForgeDirection.getOrientation(turtle.getFacingDir());
        Position position = new Position(vector.xCoord, vector.yCoord, vector.zCoord, direction);
        position.moveForwards(1.0F);
        World world = turtle.getWorld();
        TileEntity tileEntity = world.getBlockTileEntity((int) position.x, (int) position.y, (int) position.z);
        if (tileEntity != null && tileEntity instanceof TileEntitySynthesis)
            return (TileEntitySynthesis) tileEntity;
        else
            return null;
    }

}
