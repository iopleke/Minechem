package universalelectricity.api;

import java.util.LinkedList;
import java.util.List;

/**
 * An easy way to display information on electricity for the client.
 *
 * @author Calclavia
 */
public class UnitDisplay
{
	public Unit unit;
	public double value;
	public boolean useSymbol = false;
	public int decimalPlaces = 2;
	public boolean isSimple = false;

	public UnitDisplay(Unit unit, double value)
	{
		this.unit = unit;
		this.value = value;
	}

	/**
	 * Rounds a number to a specific number place places
	 *
	 * @param The number
	 * @return The rounded number
	 */
	public static double roundDecimals(double d, int decimalPlaces)
	{
		int j = (int) (d * Math.pow(10, decimalPlaces));
		return j / Math.pow(10, decimalPlaces);
	}

	public static double roundDecimals(double d)
	{
		return roundDecimals(d, 2);
	}

	public UnitDisplay multiply(double value)
	{
		this.value *= value;
		return this;
	}

	public UnitDisplay simple()
	{
		isSimple = true;
		return this;
	}

	public UnitDisplay symbol()
	{
		return symbol(true);
	}

	public UnitDisplay symbol(boolean useSymbol)
	{
		this.useSymbol = useSymbol;
		return this;
	}

	public UnitDisplay decimal(int decimalPlaces)
	{
		this.decimalPlaces = decimalPlaces;
		return this;
	}

	@Override
	public String toString()
	{
		String unitName = unit.name;
		String prefix = "";

		if (isSimple)
		{
			if (value > 1)
			{
				if (decimalPlaces < 1)
				{
					return (int) value + " " + unit.getPlural();
				}

				return roundDecimals(value, decimalPlaces) + " " + unit.getPlural();
			}

			if (decimalPlaces < 1)
			{
				return (int) value + " " + unit.name;
			}

			return roundDecimals(value, decimalPlaces) + " " + unit.name;
		}

		if (value < 0)
		{
			value = Math.abs(value);
			prefix = "-";
		}

		if (useSymbol)
		{
			unitName = unit.symbol;
		}
		else if (value > 1)
		{
			unitName = unit.getPlural();
		}

		if (value == 0)
		{
			return value + " " + unitName;
		}
		else
		{
			for (int i = 0; i < UnitPrefix.unitPrefixes.size(); i++)
			{
				UnitPrefix lowerMeasure = UnitPrefix.unitPrefixes.get(i);

				if (lowerMeasure.isBellow(value) && i == 0)
				{
					return prefix + roundDecimals(lowerMeasure.process(value), decimalPlaces) + " " + lowerMeasure.getName(useSymbol) + unitName;
				}
				if (i + 1 >= UnitPrefix.unitPrefixes.size())
				{
					return prefix + roundDecimals(lowerMeasure.process(value), decimalPlaces) + " " + lowerMeasure.getName(useSymbol) + unitName;
				}

				UnitPrefix upperMeasure = UnitPrefix.unitPrefixes.get(i + 1);

				if ((lowerMeasure.isAbove(value) && upperMeasure.isBellow(value)) || lowerMeasure.value == value)
				{
					return prefix + roundDecimals(lowerMeasure.process(value), decimalPlaces) + " " + lowerMeasure.getName(useSymbol) + unitName;
				}
			}
		}

		return prefix + roundDecimals(value, decimalPlaces) + " " + unitName;
	}

	/**
	 * Universal Electricity's units are in KILOJOULES, KILOWATTS and KILOVOLTS. Try to make your
	 * energy ratio as close to real life as possible.
	 */
	public static class Unit
	{
		public static final Unit AMPERE = new Unit("Amp", "I");
		public static final Unit AMP_HOUR = new Unit("Amp Hour", "Ah");
		public static final Unit VOLTAGE = new Unit("Volt", "V");
		public static final Unit WATT = new Unit("Watt", "W");
		public static final Unit WATT_HOUR = new Unit("Watt Hour", "Wh");
		public static final Unit RESISTANCE = new Unit("Ohm", "R");
		public static final Unit CONDUCTANCE = new Unit("Siemen", "S");
		public static final Unit JOULES = new Unit("Joule", "J");
		public static final Unit LITER = new Unit("Liter", "L");
		public static final Unit NEWTON_METER = new Unit("Newton Meter", "Nm");
		public static final Unit REDFLUX = new Unit("Redstone-Flux", "Rf");
		public static final Unit MINECRAFT_JOULES = new Unit("Minecraft-Joules", "Mj");
		public static final Unit ELECTRICAL_UNITS = new Unit("Electrical-Units", "Eu");

		public final String name;
		public final String symbol;

		private Unit(String name, String symbol)
		{
			this.name = name;
			this.symbol = symbol;
		}

		public String getPlural()
		{
			return this.name + "s";
		}
	}

	/**
	 * Metric system of measurement.
	 */
	public static class UnitPrefix
	{
		public static final List<UnitPrefix> unitPrefixes = new LinkedList();

		public static final UnitPrefix MICRO = new UnitPrefix("Micro", "u", 0.000001);
		public static final UnitPrefix MILLI = new UnitPrefix("Milli", "m", 0.001);
		public static final UnitPrefix BASE = new UnitPrefix("", "", 1);
		public static final UnitPrefix KILO = new UnitPrefix("Kilo", "k", 1000);
		public static final UnitPrefix MEGA = new UnitPrefix("Mega", "M", 1000000);
		public static final UnitPrefix GIGA = new UnitPrefix("Giga", "G", 1000000000);
		public static final UnitPrefix TERA = new UnitPrefix("Tera", "T", 1000000000000d);
		public static final UnitPrefix PETA = new UnitPrefix("Peta", "P", 1000000000000000d);
		public static final UnitPrefix EXA = new UnitPrefix("Exa", "E", 1000000000000000000d);
		public static final UnitPrefix ZETTA = new UnitPrefix("Zetta", "Z", 1000000000000000000000d);
		public static final UnitPrefix YOTTA = new UnitPrefix("Yotta", "Y", 1000000000000000000000000d);
		/**
		 * long name for the unit
		 */
		public final String name;
		/**
		 * short unit version of the unit
		 */
		public final String symbol;
		/**
		 * Point by which a number is consider to be of this unit
		 */
		public final double value;

		private UnitPrefix(String name, String symbol, double value)
		{
			this.name = name;
			this.symbol = symbol;
			this.value = value;
			unitPrefixes.add(this);
		}

		public String getName(boolean getShort)
		{
			if (getShort)
			{
				return symbol;
			}
			else
			{
				return name;
			}
		}

		/**
		 * Divides the value by the unit value start
		 */
		public double process(double value)
		{
			return value / this.value;
		}

		/**
		 * Checks if a value is above the unit value start
		 */
		public boolean isAbove(double value)
		{
			return value > this.value;
		}

		/**
		 * Checks if a value is lower than the unit value start
		 */
		public boolean isBellow(double value)
		{
			return value < this.value;
		}
	}
}
