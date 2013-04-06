package ljdp.minechem.computercraft.method;

import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;
import ljdp.minechem.computercraft.ChemistryTurtleUpgradePeripherial;
import ljdp.minechem.computercraft.ICCMethod;
import ljdp.minechem.computercraft.IMinechemTurtlePeripheral;

public class ClearSynthesisRecipe implements ICCMethod {

    @Override
    public String getMethodName() {
        return "clearSynthesisRecipe";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception {
        IMinechemTurtlePeripheral peripheral = ChemistryTurtleUpgradePeripherial.getMinechemPeripheral(turtle);
        peripheral.setSynthesisRecipe(null);
        return null;
    }

}
