/** 
* @author Carla Augusto
* @version 29/03/2017
**/
package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class MainForm extends Form implements ActionListener{   
    private JLabel lbLogo = new JLabel(new ImageIcon(getClass().getResource("settings.png")));    
    private JButton btnSell, btnProdManagement;
    private JPanel panelButtons = new JPanel();
    
    public MainForm(ArrayList<Product> listPrd){ 
        super(listPrd);                
        btnSell=super.standardBtn("Sell Products",16);
        btnSell.addActionListener(this);
        btnProdManagement=super.standardBtn("Product Management",16);  
        btnProdManagement.addActionListener(this);
        
        //Setting Elements
        panelButtons.setOpaque(false);
        panelButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbMiddlePanel.setLayout(new BoxLayout(lbMiddlePanel,BoxLayout.Y_AXIS));
        lbLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbTitle.setText("Sales App");
        lbTitle.setFont(new Font("Arial", Font.BOLD, 36));
        
        //Adding Elements
        panelButtons.add(btnSell);         
        panelButtons.add(btnProdManagement);                
        lbMiddlePanel.add(Box.createVerticalStrut(300));        
        lbMiddlePanel.add(lbLogo);
        lbMiddlePanel.add(Box.createVerticalStrut(100));
        lbMiddlePanel.add(panelButtons);     
        bottomPanel.add(lbMiddlePanel);    
        firstTopPanel.add(lbTitle);   
        mainPanel.add(firstTopPanel); 
        mainPanel.add(bottomPanel);          
        add(mainPanel);   
        setVisible(true);        
    }    
    //This will display a confirmation Dialog
    private void displayPopUp(Object btnPressed){
        JFrame frame = new JFrame();
        UIManager.put("OptionPane.messageFont", new Font("System", Font.PLAIN, 18));//This will set the message Font
        UIManager.put("OptionPane.buttonFont", new Font("System", Font.PLAIN, 18));//This will set the button Font
        int n = JOptionPane.showConfirmDialog(
            frame, "There is no products available. Would you like to add a new product?",
            "No products available",
            JOptionPane.YES_NO_OPTION);
        if(n == JOptionPane.YES_OPTION){//By pressing yes button, the form used to add a product will be displayed
            if(btnPressed==btnProdManagement)
               new AddEditProductForm(listProduct,null,this, false,-1,false);//This invokes the AddEditProductForm constructor with no object instatiated (The last false parameter means that when that window is closed, the user will be automatically redirected to the Product Management Window) 
            else if (btnPressed==btnSell)
               new AddEditProductForm(listProduct,null,this, false,-1,true);//This invokes the AddEditProductForm constructor with no object instatiated (The last true parameter means that when that window is closed, the user will be automatically redirected to the Sales Window)           
        } 
    }
    
    public void actionPerformed(ActionEvent e){  
        boolean valid=true;
        if(listProduct.isEmpty())
            displayPopUp(e.getSource());//This will ask the user to add new product
        else{
            if(e.getSource()==btnProdManagement)
                new ManagementForm(listProduct,false);//This invokes the ManagementForm constructor with no object instatiated  (The false parameter means that the user is not on Product Management window yet) 
            else if(e.getSource()==btnSell){
                if(isThereAtLeastOnePrdStockAvailable())//This will check if there is at one product which stock level is higher than zero
                    new SalesForm(listProduct,null,null,null,0);//This invokes the SalesForm constructor with no object instatiated  
                else {
                    displayAlertDialog("All products are out of stock!","No product in stock",0);
                    valid=false;//This will prevent the current window being closed
                }
            }
            if(valid)
                setVisible(false); 
        }
    }     
}