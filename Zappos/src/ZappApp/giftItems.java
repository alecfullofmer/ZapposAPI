package ZappApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class giftItems {
	
	  // for subtracting doubles
	public int numItems;
	public double totalCost;
	private double mostExpensive;
	private ArrayList<Product> usable;
	private ArrayList<Combination> totalCombos;
	
	
	
	//@param items is the number of items in a combination
	//@param total is the total amount the customer wants to spend
	public giftItems(int items, double total)
	{
		numItems = items;
		totalCost = total;
		totalCombos = new ArrayList<Combination>();
		
	}
	
	public void getProducts() throws IOException, org.json.simple.parser.ParseException, JSONException
	{
		System.out.println("Searching...");
		String goods = GetData.httpGet(GetData.root +"&term=&limit=100&sort={\"price\":\"asc\"}");
		JSONObject obj = GetData.parseReply(goods);
		JSONArray giftArray = GetData.getResults(obj);
		
		double cheapestItem = getPrice(giftArray.get(0));
		
		//check if we'll have at least one combo that will work
		
		if(cheapestItem*numItems > totalCost)
		{
			System.out.println("Sorry, we couldn't find "+numItems+" product(s) for "+totalCost);
		}
		else
		{
			//get all items in Zappos database that are <= the most expensive possible item
			mostExpensive = (totalCost - cheapestItem *(numItems - 1));
			int page = 2;
			double currMax = getPrice(giftArray.get(giftArray.size()-1));
			while(currMax < mostExpensive)
			{
				String next = GetData.httpGet(GetData.root +"&term=&limit=100&sort={\"price\":\"asc\"}&page=" + page);
				JSONObject object = GetData.parseReply(next);
				JSONArray productArray = GetData.getResults(object);
				currMax = getPrice(productArray.get(productArray.size()-1));
				giftArray.addAll(productArray);
				page++;
				
			}
			
			usable(giftArray);
		}
		
						
	}
	
	//find all the products that have a price <= mostExpensive
	public void usable(JSONArray use)
	{
		double currMax = getPrice(use.get(use.size()-1));
		while(currMax > mostExpensive)
		{
			use.remove(use.size()-1);
			currMax = getPrice(use.get(use.size()-1));
		}
		usable = new ArrayList<Product>();
		
		//create a Product object for all usable items and add to a list
		for(Object gift : use)
		{
			Product p = new Product(getPrice(gift), getName(gift));
			
			//if an item with that same price is already in the array, dont add it
			if(!usable.contains(p))
			usable.add(p);
		}
		
			getCombosRecursive(usable, new ArrayList<Product>(), totalCost);
			sortCombos();
			print();
			
	}
	
	
	
	//recursively look for combos
	//overall algorithm came from
	//http://stackoverflow.com/questions/4632322/finding-all-possible-combinations-of-numbers-to-reach-a-given-sum
	//@param usable is the products I have to choose from
	//@param tempList combination I have built so far
	//@param total amount customer is willing to spend. 
	
	public void getCombosRecursive(ArrayList<Product> usable, ArrayList<Product> tempList, double total ){
		if(tempList.size() > numItems)
			return;
		double currSum=0;
		for(Product p : tempList)
		{
			currSum += p.getPrice();
		}
		if(tempList.size() == numItems && totalCombos.size() < 10 && Math.abs(currSum - total)<= 1)
		{
			//if totalCombos is empty, add. else check to see if combo is already in there, if not, add
			if(totalCombos.size() == 0)
			{
				totalCombos.add(new Combination(tempList, totalCost));
			}
			else
			{
				Combination temp = new Combination(tempList, totalCost);
				if(!totalCombos.contains(temp))
				{
					totalCombos.add(temp);
				}
			}
		}
		//no need to go any further with this combo if already at total
		if(currSum >= total)
			return;
		
		for(int i=0; i<usable.size() && !(currSum <total && tempList.size() == numItems); i++)
		{
			ArrayList<Product> remaining = new ArrayList<Product>();
			Product temp = usable.get(i);
			for(int j=i+1; j<usable.size(); j++){remaining.add(usable.get(j));}
			ArrayList<Product> partial = new ArrayList<Product>(tempList);
			partial.add(temp);
			int r = remaining.size();
			getCombosRecursive(remaining, partial, total);
			
		}
	}
	

	public void sortCombos()
	{
		Collections.sort(totalCombos);
	}
	
	public void print()
	{
		if(totalCombos.size() > 0)
		{
			String s = "We found some good stuff for you." + "\n";
			for(Combination com : totalCombos)
			{
				s += com.toString();
			}
			System.out.println(s);
		}
		else
		{
			System.out.println("Sorry, your search didn't return any result");
		}
	}
	
	
	//get a products price
	private Double getPrice(Object item){
		return Double.parseDouble(((String) ((JSONObject) item).get("price")).substring(1));
	}
	//get a products name
	private String getName(Object item)
	{
		return (String)((JSONObject)item).get("productName");
	}

}
