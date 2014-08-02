package universalelectricity.api.core.grid;

import java.util.HashMap;

/**
 * A dynamic node loader for registering different nodes for different node interfaces.
 * <p/>
 * This is the essential class for loading different nodes.
 *
 * @author Calclavia
 */
public class NodeRegistry
{
	private static final HashMap<Class, Class> INTERFACE_NODE_MAP = new HashMap<Class, Class>();

	public static void register(Class nodeInterface, Class nodeClass)
	{
		INTERFACE_NODE_MAP.put(nodeInterface, nodeClass);
	}

	public static <N extends INode> N get(INodeProvider parent, Class<N> nodeInterface)
	{
		Class nodeClass = INTERFACE_NODE_MAP.get(nodeInterface);

		try
		{
			return (N) nodeClass.getConstructor(INodeProvider.class).newInstance(parent);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}