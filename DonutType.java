package backend;

import java.io.Serializable;

/**
 *  Types of Donuts can be used
 */
public enum DonutType implements Serializable {
	Yeast(1.39),Cake(1.59),Hole(0.33);
	
	private double price;
	public static int numOfFlavors = 12;
	/**
	 *  Construct a type with price
	 *  @param price indicating the price of this type
	 */
	DonutType(double price){
		this.price = price;
	}
	
	/**
	 *  Get the price of this type
	 *  @return indicating the price of this type
	 */
	public double getPrice() {
		return price;
	}
}
