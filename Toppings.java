package backend;

import java.util.ArrayList;

/**
 *  All Toppings can be used
 */
public enum Toppings {
	Cream,Syrup,Milk,Caramel,WhippedCream;
	public static final int numOfTopping = 5;

	/**
	 *  get the list of all toppings
	 * @return ArrayList<Toppings> an ArrayList of Topping object
	 */
	public static ArrayList<Toppings> getToppingsList(){
		ArrayList<Toppings> list = new ArrayList<>();
		list.add(Cream);
		list.add(WhippedCream);
		list.add(Syrup);
		list.add(Milk);
		list.add(Caramel);
		return  list;
	}

}
