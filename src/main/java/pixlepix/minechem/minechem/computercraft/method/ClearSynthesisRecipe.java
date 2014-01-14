package pixlepix.minechem.minechem.computercraft.method;

import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;
import pixlepix.minechem.minechem.computercraft.ChemistryTurtleUpgradePeripherial;
import pixlepix.minechem.minechem.computercraft.ICCMethod;
import pixlepix.minechem.minechem.computercraft.IMinechemTurtlePeripheral;

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
