/** 
* @author Carla Augusto
* @version 29/03/2017
**/
package src;

import java.util.ArrayList;

public class StartApp {
    public static void main(String args[]){          
        new MainForm(new  ArrayList<Product>());//This invokes the Mainform constructor with no object instatiated and sends an empty arraylist as a paramenter       
    }
} 