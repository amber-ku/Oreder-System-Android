package backend;


import java.io.Serializable;

/**
 * The Coffee class defines the common data and operations for all MenuItem.
 * 
 * @author Chang Li, Hsinghui Ku
 */
public class MenuItem implements Serializable {
	private double price;
	private int amount;
	private String name;


	
	/**
	 * Creates a MenuItem object with the given name and amount.
	 * 
	 * @param name indicating the name of MenuItem
	 * @param amount indicating the quantity of coffee
	 */
	public MenuItem(String name, int amount) {
		this.amount = amount;
		this.name = name;

	}
	
	/**
	 * Get the amount of this object
	 * 
	 * @return an integer indicating the amount of the object
	 */
	public int getAmount() {
		return amount;
	}
	
	/**
	 * Get the name of this object
	 * 
	 * @return a String indicating the name of the object
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the price of this object
	 * 
	 * @param price double indicating the price of the object
	 */
	protected void setPrice(double price) {
		this.price = price;

	}
	
	/**
	 * Get the price of this object
	 * 
	 * @return an double indicating the price of the object
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Set the amount of this object
	 * 
	 * @param amount indicating the amount to be set 
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	
	/**
	 * Compute the price of the MenuItem, this method will be defined in the subclass.
	 */
	public void itemPrice() {
	}

	/**
	 * Compare this MenuItem object to the given Object for equality
	 * 
	 * @return true if the price, amount, and name are the same.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MenuItem) {
			MenuItem item = (MenuItem)obj;
			return item.price == this.price && item.name == this.name 
					&& item.amount == this.amount;
		}
		return false;
	}
}
