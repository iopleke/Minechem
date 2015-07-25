package minechem.api;

/**
 * The API of Minechem.
 * <p>
 * Use {@link MinechemAPI.Instance#get()} to get the instance.
 */
public interface MinechemAPI
{

    public static final class Instance
    {

        private static MinechemAPI INSTANCE;

        /**
         * Gets the instance of {@link MinechemAPI}.
         *
         * @return the instance of {@link MinechemAPI}, null if Minechem is not loaded
         */
        public static MinechemAPI get()
        {
            return INSTANCE;
        }

        private Instance()
        {
        }

    }

    // API methods

}
