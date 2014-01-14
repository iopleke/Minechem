package pixlepix.minechem.minechem.common.gates;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.gates.ITriggerParameter;

public class TriggerNoTestTubes extends MinechemTrigger {
    public TriggerNoTestTubes(int id) {
        super(id, "No Test Tubes");
    }

    @Override
    public boolean isTriggerActive(ForgeDirection fd, TileEntity tile, ITriggerParameter parameter) {
        if (!(tile instanceof IMinechemTriggerProvider))
            return false;
        return ((IMinechemTriggerProvider) tile).hasNoTestTubes();
    }

	@Override
	public boolean requiresParameter() {
		// TODO Auto-generated method stub
		return false;
	}
}
