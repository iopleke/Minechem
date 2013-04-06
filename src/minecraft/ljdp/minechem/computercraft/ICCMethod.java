package ljdp.minechem.computercraft;

import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;

public interface ICCMethod {

    public String getMethodName();

    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception;

}
