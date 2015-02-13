package minechem.helper;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ArrayHelper
{
    /**
     * Clear all null values from an array
     * @param array the array
     * @param type the {@link java.lang.Class} of the array
     * @param <T> the type of the array as state above
     * @return a null less array
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public static <T> T[] removeNulls(T[] array, Class<T> type)
    {
        List<T> list =  new LinkedList<T>();
        for (T value : array)
            if (value != null) list.add(value);
        return list.toArray((T[]) Array.newInstance(type, list.size()));
    }
}
