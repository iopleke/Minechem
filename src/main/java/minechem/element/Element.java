package minechem.element;

/**
 * Data object for elements
 */
public class Element
{
	public final int atomicNumber;
	public final String fullName;
	public final String shortName;
	public final String form;
	public final int mohs;
	public final int neutrons;

	private String state;
	private int valenceElectronCount;
	private String valenceSubshellIdentifier;

	/**
	 * Basic data storage object for elements
	 *
	 * @param atomicNumber the element's atomic number and proton count
	 * @param fullName     the full name, eg "Gold"
	 * @param shortName    the abbreviation, eg "Au"
	 * @param form         solid, liquid, gas, or plasma
	 * @param mohs         the mohs hardness, normally 0-10
	 * @param neutrons     the number of neutrons in the element's nucleus
	 */
	public Element(int atomicNumber, String fullName, String shortName, String form, int mohs, int neutrons)
	{
		this.atomicNumber = atomicNumber;
		this.fullName = fullName;
		this.shortName = shortName;
		this.form = form;
		this.mohs = mohs;
		this.neutrons = neutrons;
		this.calculateValenceShells();
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getState()
	{
		return this.state;
	}

	/**
	 * Set all valence shell data
	 *
	 * @param valenceElectronCount      default number of electrons in the
	 *                                  valence shell
	 * @param valenceSubshellIdentifier identifier for the valence shell
	 */
	public void setValenceShell(int valenceElectronCount, String valenceSubshellIdentifier)
	{
		this.valenceElectronCount = valenceElectronCount;
		this.valenceSubshellIdentifier = valenceSubshellIdentifier;
	}

	public int getValenceElectronCount()
	{
		return this.valenceElectronCount;
	}

	public String getValenceSubshellIdentifier()
	{
		return this.valenceSubshellIdentifier;
	}

	private void calculateValenceShells()
	{
		// do the calculations for the valence shell values here, @hilburn
		// then use the setValenceShell method to save the values
	}

}
