package minechem.computercraft.method;

import minechem.computercraft.ChemistryTurtleUpgradePeripherial;
import minechem.computercraft.ICCMethod;
import minechem.computercraft.IMinechemTurtlePeripheral;
import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;

public class ClearSynthesisRecipe implements ICCMethod
{

    @Override
    public String getMethodName()
    {
        return "clearSynthesisRecipe";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception
    {
        IMinechemTurtlePeripheral peripheral = ChemistryTurtleUpgradePeripherial.getMinechemPeripheral(turtle);
        peripheral.setSynthesisRecipe(null);
        return null;
    }

}
