package ZappApp;

import java.util.ArrayList;

public class Combination implements Comparable{
	
	private ArrayList<Product> products;
	private Double goal;
	private double distance;
	private double actual;
	
	
	public Combination(ArrayList<Product> p, Double t)
	{
		products = p;
		goal = t;
		for(Product f : p){actual += f.getPrice();}
		distance = Math.abs(goal - actual);
			
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((products == null) ? 0 : products.hashCode());
		result = prime * result + ((goal == null) ? 0 : goal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Combination other = (Combination) obj;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (goal == null) {
			if (other.goal != null)
				return false;
		} else if (!goal.equals(other.goal))
			return false;
		return true;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	
	@Override
	public int compareTo(Object o) {
		Combination other = (Combination) o;
		if(this.equals(other)) return 0;
		else if(this.distance < other.getDistance()) return -1;
		else return 1;
	}
	
	public double getDistance()
	{
		return distance;
	}
	
	public String toString()
	{
		String s = "Total cost: $" + actual + "\n";
		for(int i=0;i<products.size();i++)
		{
			s += " " + products.get(i).toString() + "\n";
		}
		s+= "\n";
		return s;
	}


}
