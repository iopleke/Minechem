package minechem.helper;

public class MathHelper
{
    public static int clamp(int val, int min, int max)
    {
        if (val < min)
        {
            return min;
        }
        if (val > max)
        {
            return max;
        }
        return val;
    }

    public static float translateValue(float value, float leftMin, float leftMax, float rightMin, float rightMax)
    {
        float leftRange = leftMax - leftMin;
        float rightRange = rightMax - rightMin;
        float valueScaled = (value - leftMin) / leftRange;
        return rightMin + (valueScaled * rightRange);
    }
}
