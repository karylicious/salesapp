/** 
* @author Carla Augusto
* @version 29/03/2017
**/
package src;

public class Product {    
    private String name;
    private int stockLevel;
    private double price;     
    
    Product(String productName, int productStockLevel, double productPrice){
        name = productName;
        stockLevel = productStockLevel;
        price = productPrice;        
    }
       
    public String getName(){return name;}
    public int getStockLevel(){return stockLevel;}
    public double getPrice(){return price;}
    public void setPrice(double newPrice){price = newPrice;}//Receives and sets a new price for the product    
    
    public int reStock(int newStockLevel){//Receives the number of new items of to stock, updates and returns the new stock level
        stockLevel = newStockLevel;
        return stockLevel;
    }
    
    /*Receives the number of items sold, reduces the stock level accordingly (first checking to see if enough items are in stock) 
    and returns the cost of the order. */    
    public double sell(int totalItemSold){
        if(totalItemSold <= stockLevel){
            stockLevel -= totalItemSold;
            return price * totalItemSold;
        }
        else
            return 0;
    }
}