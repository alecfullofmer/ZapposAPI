package ZappApp;

public class Product {
	
	private double price;
	private String name;
	
	//@param p = to the products price;
	//@param n = products name
	public Product(double p, String n)
	{
		price = p;
		name = n;
	}
	public double getPrice()
	{
		return price;
	}
	public String getName()
	{
		return name;
	}
	
	//to check for duplicate prices in our usable Array of products
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Product other = (Product) obj;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		return true;
	}
	
	public String toString()
	{
		return name + ": " + price;
	}

}
