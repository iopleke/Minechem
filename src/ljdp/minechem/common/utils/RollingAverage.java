package ljdp.minechem.common.utils;

public class RollingAverage {

    private float[] samples;
    private int size;
    private int index = 0;
    private float total;

    public RollingAverage(int size) {
        samples = new float[size];
        this.size = size;
        for (int i = 0; i < size; i++)
            samples[i] = 0.0F;
    }

    public void add(float sample) {
        total -= samples[index];
        samples[index] = sample;
        total += sample;
        index++;
        if (index == size)
            index = 0;
    }

    public float getAverage() {
        return total / size;
    }
}
