package minechem.oredictionary;

public interface OreDictionaryHandler
{
	boolean canHandle(String oreName);

	void handle(String oreName);
}
