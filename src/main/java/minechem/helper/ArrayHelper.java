package minechem.helper;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ArrayHelper
{
    @SuppressWarnings("SuspiciousMethodCalls")
    public static <T> T[] removeNulls(T[] array, Class<T> type)
    {
        List<T> list =  new LinkedList<T>();
        for (T value : array)
            if (value != null) list.add(value);
        return list.toArray((T[]) Array.newInstance(type, list.size()));
    }
}
