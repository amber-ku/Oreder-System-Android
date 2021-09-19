package backend;

/**
 * The Donut class defines the common data and operations for all Donut type.
 * 
 * @author Chang Li, Hsinghui Ku
 */
public class Donut extends MenuItem{
	private String flavor;
	public static final double PRICE = 1.39;


	/**
	 * Creates a Donut object with the given type, flavor and amount.
	 *
	 * @param flavor a String indicating the flavor
	 * @param amount indicating the quantity of coffee
	 */
	public Donut(String flavor, int amount) {
		super("Donut",amount);
		this.flavor = flavor;
	}

	
	/**
	 * Compute the price of the Donut.
	 */
	public void itemPrice() {
		setPrice(PRICE * super.getAmount());
	}
	
	/**
	 * Converts this Donut object to a String
	 * 
	 * @return a string representation of this Donut
	 */
	public String toString() {
		return String.format("%s [%d]\n",flavor,getAmount());
	}
	
	/**
	 * Compare this Donut object to the given Object for equality
	 * 
	 * @return true if two Donuts' type and flavor are the same
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Donut) {
			Donut donut = (Donut)obj;
			return donut.flavor.equals(flavor);
		}
		return false;
	}
}
