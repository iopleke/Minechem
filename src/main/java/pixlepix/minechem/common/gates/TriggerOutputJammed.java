package pixlepix.minechem.common.gates;

import buildcraft.api.gates.ITriggerParameter;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TriggerOutputJammed extends MinechemTrigger {
    public TriggerOutputJammed(int id) {
        super(id, "Output Jammed");
    }

    @Override
    public boolean isTriggerActive(ForgeDirection fd, TileEntity tile, ITriggerParameter parameter) {
        if (!(tile instanceof IMinechemTriggerProvider))
            return false;
        return ((IMinechemTriggerProvider) tile).isJammed();
    }

    @Override
    public boolean requiresParameter() {
        // TODO Auto-generated method stub
        return false;
    }

}
