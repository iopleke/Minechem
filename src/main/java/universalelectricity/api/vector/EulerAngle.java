package universalelectricity.api.vector;

import net.minecraftforge.common.ForgeDirection;

/** The euler angles describing a 3D rotation. The rotation always in degrees.
 * 
 * Note: The rotational system Minecraft uses is non-standard. The angles and vector calculations
 * have been calibrated to match. DEFINITIONS:
 * 
 * Yaw: 0 Degrees - Looking at NORTH 90 - Looking at WEST 180 - Looking at SOUTH 270 - Looking at
 * EAST
 * 
 * Pitch: 0 Degrees - Looking straight forward towards the horizon. 90 Degrees - Looking straight up
 * to the sky. -90 Degrees - Looking straight down to the void.
 * 
 * Make sure all models are use the Techne Model loader, they will naturally follow this rule.
 * 
 * @author Calclavia */
public class EulerAngle implements IRotation, IVector3
{
    /** Angles in degrees. */
    public double yaw, pitch, roll;

    public EulerAngle()
    {
        this(0, 0, 0);
    }

    public EulerAngle(ForgeDirection dir)
    {
        switch (dir)
        {
            case DOWN:
                pitch = -90;
                break;
            case UP:
                pitch = 90;
                break;
            case NORTH:
                yaw = 0;
                break;
            case SOUTH:
                yaw = 180;
                break;
            case WEST:
                yaw = 90;
                break;
            case EAST:
                // or 270 degrees
                yaw = -90;
                break;
            default:
                break;
        }
    }

    public EulerAngle(double yaw, double pitch, double roll)
    {
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    public EulerAngle(double[] angles)
    {
        this(angles[0], angles[1], angles[2]);
    }

    public EulerAngle(EulerAngle angle)
    {
        this(angle.yaw(), angle.pitch(), angle.roll());
    }

    public EulerAngle(double yaw, double pitch)
    {
        this(yaw, pitch, 0);
    }

    @Override
    public double yaw()
    {
        return yaw;
    }

    @Override
    public double pitch()
    {
        return pitch;
    }

    @Override
    public double roll()
    {
        return roll;
    }

    public double yawRadians()
    {
        return Math.toRadians(yaw());
    }

    public double pitchRadians()
    {
        return Math.toRadians(pitch());
    }

    public double rollRadians()
    {
        return Math.toRadians(roll());
    }

    @Override
    public double x()
    {
        return -Math.sin(yawRadians()) * Math.cos(pitchRadians());
    }

    @Override
    public double y()
    {
        return Math.sin(pitchRadians());
    }

    @Override
    public double z()
    {
        return -Math.cos(yawRadians()) * Math.cos(pitchRadians());
    }

    public double[] toArray()
    {
        return new double[] { yaw(), pitch(), roll() };
    }

    public double[] toRadianArray()
    {
        return new double[] { yawRadians(), pitchRadians(), rollRadians() };
    }

    public EulerAngle set(int i, double value)
    {
        switch (i)
        {
            case 0:
                yaw = value;
                break;
            case 1:
                pitch = value;
                break;
            case 2:
                roll = value;
                break;
        }

        return this;
    }

    /** gets the difference in degrees between the two angles */
    public EulerAngle difference(EulerAngle other)
    {
        return new EulerAngle(yaw() - other.yaw(), pitch() - other.pitch(), roll() - other.roll());
    }

    public EulerAngle absoluteDifference(EulerAngle other)
    {
        return new EulerAngle(getAngleDifference(yaw(), other.yaw()), getAngleDifference(pitch(), other.pitch()), getAngleDifference(roll(), other.roll()));
    }

    public boolean isWithin(EulerAngle other, double margin)
    {
        for (int i = 0; i < 3; i++)
            if (absoluteDifference(other).toArray()[i] > margin)
                return false;

        return true;
    }

    public static double getAngleDifference(double angleA, double angleB)
    {
        return Math.abs(angleA - angleB);
    }

    @Override
    public EulerAngle clone()
    {
        return new EulerAngle(this.yaw(), this.pitch(), this.roll());
    }

    public static double clampAngleTo360(double var)
    {
        return clampAngle(var, -360, 360);
    }

    public static double clampAngleTo180(double var)
    {
        return clampAngle(var, -180, 180);
    }

    public static double clampAngle(double var, double min, double max)
    {
        while (var < min)
            var += 360;

        while (var > max)
            var -= 360;

        return var;
    }

    @Override
    public int hashCode()
    {
        long x = Double.doubleToLongBits(yaw());
        long y = Double.doubleToLongBits(pitch());
        long z = Double.doubleToLongBits(roll());
        int hash = (int) (x ^ (x >>> 32));
        hash = 31 * hash + (int) (y ^ (y >>> 32));
        hash = 31 * hash + (int) (z ^ (z >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof EulerAngle)
        {
            EulerAngle angle = (EulerAngle) o;
            return yaw() == angle.yaw() && pitch() == angle.pitch() && roll() == angle.roll();
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "Angle [" + this.yaw() + "," + this.pitch() + "," + this.roll() + "]";
    }
}
