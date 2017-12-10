/** 
* @author Carla Augusto
* @version 29/03/2017
**/
package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class  ManagementForm extends Form implements ActionListener{
    private JButton btnNewProd;
    
    ManagementForm(ArrayList<Product> listPrd, boolean alreadyVisible){     
        super(listPrd);
        //Setting Elements
        lbTitle.setText("Product Management");              
        lbTitle.setFont(new Font("Arial", Font.BOLD, 30)); 
        jScrollPane.setPreferredSize(new Dimension(100,700));
        
        //Adding Elements
        btnBack = super.standardBtnImage(new ImageIcon(imgBack));
        btnBack.addActionListener(this);
        btnNewProd = super.standardBtnImage(new ImageIcon(getClass().getResource("add.png")));
        btnNewProd.addActionListener(this);
        firstTopPanel.add(lbTitle);   
        secondTopPanel.add(btnBack);
        secondTopPanel.add(btnNewProd);
        addProdsToPanel();
        scrollablePanel.add(lbMiddlePanel);
        jScrollPane = new JScrollPane(scrollablePanel);        
        mainPanel.add(firstTopPanel);
        mainPanel.add(secondTopPanel);
        bottomPanel.add(jScrollPane);   
        mainPanel.add(bottomPanel);        
        add(mainPanel);      
        setVisible(true);   
    }     
     
    public void actionPerformed(ActionEvent e){ 
        String btnName=((JButton)e.getSource()).getName(); 
        if(e.getSource()==btnBack){            
            new MainForm(listProduct);//This invokes the Mainform constructor with no object instatiated and sends the array products as a paramenter
            setVisible(false);            
        }
        else if(e.getSource()==btnNewProd)
            new AddEditProductForm(listProduct,this,null, true,-1, false); //This invokes the AddEditProductForm constructor with no object instatiated (The -1 parameter means that the user will add a new product) 
        else{
            int strPos=btnName.indexOf("PRD-");
            if(strPos>=0){
                int indexPrd= Integer.parseInt(btnName.substring(4));
                new AddEditProductForm(listProduct,this,null, true,indexPrd,false);//This invokes the AddEditProductForm constructor with no object instatiated (The indexPrd parameter means that the user will edit the selected product)
            }
        }
    }
    //This will add the last created product to the panel 
    public void addNewProduct(ArrayList<Product> listPrd){
        listProduct=listPrd;
        JButton btn = standardPRDBtn((listProduct.size()-1),"<html>"+listProduct.get(listProduct.size()-1).getName()+"<br/><br />Stock Level: "+listProduct.get(listProduct.size()-1).getStockLevel()+"<br/><br/>Price £"+listProduct.get(listProduct.size()-1).getPrice()+"</html>");//HTML is used to add multiple lines in the button
        btn.addActionListener(this);
        lbMiddlePanel.add(btn);
        lbMiddlePanel.validate();//This will refresh the panel
        lbMiddlePanel.repaint();//This will refresh the panel
    }
    
    public void refreshPanel(){
        lbMiddlePanel.removeAll();//This will remove all elements from the panel
        addProdsToPanel();//This will add new elements to the panel        
        revalidate();//This will refresh the panel
        repaint();//This will refresh the panel
    }
    //This will add new buttons to the panel using the information of each product on the array
    private void addProdsToPanel(){
        int index=0;
        for(Product currentPrd: listProduct){
            JButton btnPrd = super.standardPRDBtn(index, "<html>"+currentPrd.getName()+"<br/><br/>Stock Level: "+currentPrd.getStockLevel()+"<br/><br/>Price £"+currentPrd.getPrice()+"</html>");//HTML is used to add multiple lines in the buttons
            btnPrd.addActionListener(this);
            lbMiddlePanel.add(btnPrd);
            index++;
        }
    }
}