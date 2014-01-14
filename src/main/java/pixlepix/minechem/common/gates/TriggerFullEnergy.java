package pixlepix.minechem.common.gates;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.gates.ITriggerParameter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TriggerFullEnergy extends MinechemTrigger {
    public TriggerFullEnergy(int id) {
        super(id, "Full Energy");
    }

    @Override
    public boolean isTriggerActive(ForgeDirection fd, TileEntity tile, ITriggerParameter parameter) {
        if (!(tile instanceof IMinechemTriggerProvider))
            return false;
        return ((IMinechemTriggerProvider) tile).hasFullEnergy();
    }
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon() {
    
		return provider.getIcon(0);
        
    }

	@Override
	public boolean requiresParameter() {
		// TODO Auto-generated method stub
		return false;
	}

	


	
}
