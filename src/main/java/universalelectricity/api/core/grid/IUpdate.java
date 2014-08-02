package universalelectricity.api.core.grid;

/**
 * Applies to objects that can receive update tick calls.
 *
 * @Calclavia
 */
public interface IUpdate
{
	/**
	 * Updates the network. Called by the {UpdateTicker}.
	 *
	 * @param deltaTime - The time taken in seconds between the last update.
	 */
	public void update(double deltaTime);

	/**
	 * Can this updater update?
	 *
	 * @return True to allow update(deltaTime) to be called.
	 */
	public boolean canUpdate();

	/**
	 * Should this updater continue receiving updates?
	 *
	 * @return True to leave the updater in the ticker. False to remove the updater from the ticker.
	 */
	public boolean continueUpdate();
}
