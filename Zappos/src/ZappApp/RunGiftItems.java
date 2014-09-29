package ZappApp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

public class RunGiftItems {
	
	
	
			
	public static void main(String args[]) throws IOException, ParseException, JSONException
	{
		boolean input = false;
		int numberOfItems = 0;
		double price = 0;
		
		//collect input and make sure valid.
		
		while(!input)
		{
			System.out.println("Welcome. Please enter the number of items you'd like to purchase. ");
		
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String num = br.readLine();
			numberOfItems = Integer.parseInt(num);
			 
			System.out.println("Please enter the total amount (USD) you would like to spend. ");
			
			BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
			String pr = read.readLine();
			price = Double.parseDouble(pr);
			if(numberOfItems != (int)numberOfItems || numberOfItems <= 0)
			{
				System.out.println("Whoops. Number of items needs to be a whole number greater than 0. Try again. ");
				
			}
			 if(price != (double)price || price <= 0)
			{
				System.out.println("Whoops. Total cost needs to be a number greater than zero. Try again. ");
				
			}
			else
				input = true;
			
		}
		
		//carry out seeking for combinations.
		giftItems gifts = new giftItems(numberOfItems, price);
		gifts.getProducts();

	}
}



