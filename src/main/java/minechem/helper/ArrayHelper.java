package minechem.helper;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class ArrayHelper
{
    @SuppressWarnings("SuspiciousMethodCalls")
    public static <T> T[] removeNulls(T[] array, Class<T> type)
    {
        List<T> list =  Arrays.asList(array);
        list.removeAll(Collections.singleton(null));
        return list.toArray((T[]) Array.newInstance(type, list.size()));
    }
}
