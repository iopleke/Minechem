package minechem.helper;

import minechem.chemical.Element;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class PeriodicTableHelper
{
    // Please keep the format of the numbers
    public static int[][] layout = new int[][]
    {
        {   1,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   2},
        {   3,   4,   5,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   6,   7,   8,   9,  10},
        {  11,  12,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  13,  14,  15,  16,  17,  18},
        {  19,  20,  21,  22,  23,  24,  25,  26,  27,  28,  29,  30,  31,  32,  33,  34,  35,  36},
        {  37,  38,  39,  40,  41,  42,  43,  44,  45,  46,  47,  48,  49,  50,  51,  52,  53,  54},
        {  55,  56,   0,  72,  73,  74,  75,  76,  77,  78,  79,  80,  81,  82,  83,  84,  85,  86},
        {  87,  88,   0, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118},
        {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0},
        {   0,   0,  57,  58,  59,  60,  61,  62,  63,  64,  65,  66,  67,  68,  69,  70,  71,   0},
        {   0,   0,  89,  90,  91,  92,  93,  94,  95,  96,  97,  98,  99, 100, 101, 102, 103,   0}
    };

    /**
     * Used to cache searches because it is quite heavy to search
     */
    private static Map<Element, Integer[]> layoutCache = new TreeMap<Element, Integer[]>();

    /**
     * Get the position of an element in the periodic table
     * @param element the element to find the position of
     * @return an int[] with row and column
     */
    public static int[] getPosition(Element element)
    {
        Integer[] cached = layoutCache.get(element);
        if (cached != null) return new int[] { cached[0], cached[1] };
        
        for (int row = 0; row < layout.length; row++)
        {
            int column = -1;
            for (int i = 0; i < layout[row].length; i++)
            {
                if (layout[row][i] == element.atomicNumber)
                {
                    column = i;
                    break;
                }
            }
            if (column != -1)
            {
                Integer[] result =  new Integer[] { row, column };
                layoutCache.put(element, result);
                return new int[] { result[0], result[1] };
            }
        }
        return new int[] { -1, -1 };
    }
}
