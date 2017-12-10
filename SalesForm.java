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
    
public class  SalesForm extends Form implements ActionListener{    
    private JPanel secondTopLeftChildPanel= new JPanel();
    private JPanel secondTopRightChildPanel= new JPanel();
    private JPanel bottomRightChildPanel = new JPanel();
    private JPanel listItemsPanel =  new JPanel(new BorderLayout());
    private JButton btnRemoveProd, btnCheckout; 
    private JLabel lbQuantity  = new JLabel("Quantity of units");    
    private JLabel lbTotal  = new JLabel("Total:");  
    private ArrayList<String> listQuantItems=new ArrayList<>();
    private ArrayList<String> cartItems=new ArrayList<>();    
    private String[] nItems = new String[] {"1", "2","3", "4","5", "6","7", "8","9"};
    private JComboBox<String> comboBoxItems = new JComboBox<>(nItems);
    private DefaultListModel listItemsModel = new DefaultListModel();
    private JList listView;
    private double subTotal=0;
    
    public SalesForm(ArrayList<Product> listPrd, DefaultListModel list_ItemsModel, ArrayList<String> list_QuantItems, ArrayList<String> cart_Items, double finalPrice){
        super(listPrd);        
        if(list_ItemsModel!=null){
            listItemsModel = list_ItemsModel;   
            listQuantItems = list_QuantItems;
            cartItems =   cart_Items;
            subTotal = finalPrice;
            lbTotal.setText("Total: £"+Double.toString(subTotal));
        }
        //Setting Elements
        listView = new JList(listItemsModel); 
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listView.setSelectedIndex(-1);
        listView.setVisibleRowCount(5);
        listView.setFont(new Font("Verdana", Font.BOLD, 16));  
        lbTitle.setText("Sales");  
        lbTitle.setFont(new Font("Arial", Font.BOLD, 30));
        secondTopPanel.setLayout(new BorderLayout());
        lbTotal.setFont(new Font("Verdana", Font.BOLD, 26));        
        secondTopLeftChildPanel.setBackground(Color.black);      
        lbQuantity.setFont(new Font("Verdana", Font.BOLD, 16));
        lbQuantity.setForeground(Color.white);  
        secondTopRightChildPanel.setOpaque(false);
        bottomRightChildPanel.setLayout(new BoxLayout(bottomRightChildPanel,BoxLayout.Y_AXIS));
        bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.X_AXIS));
        listItemsPanel.setBackground(Color.white);
        comboBoxItems.setFont(new Font("Verdana", Font.BOLD, 16));        
        
        //Adding Elements
        firstTopPanel.add(lbTitle);               
        btnBack = super.standardBtnImage(new ImageIcon(imgBack));
        btnBack.addActionListener(this); 
        secondTopLeftChildPanel.add(btnBack);  
        secondTopLeftChildPanel.add(lbQuantity);  
        secondTopLeftChildPanel.add(comboBoxItems);         
        btnCheckout = super.standardBtnImage(new ImageIcon(getClass().getResource("cart.png")));
        btnCheckout.addActionListener(this);
        btnRemoveProd = super.standardBtnImage(new ImageIcon(getClass().getResource("remove.png")));
        btnRemoveProd.addActionListener(this);         
        secondTopRightChildPanel.add(btnCheckout);
        secondTopRightChildPanel.add(btnRemoveProd);  
        secondTopPanel.add(secondTopLeftChildPanel,  BorderLayout.WEST); 
        secondTopPanel.add(secondTopRightChildPanel,  BorderLayout.EAST);         
        addProdsToPanel();
        scrollablePanel.add(lbMiddlePanel);
        jScrollPane = new JScrollPane(scrollablePanel);
        jScrollPane.setPreferredSize(new Dimension(1500,900));         
        listItemsPanel.add(new JScrollPane(listView));        
        bottomRightChildPanel.add(listItemsPanel);
        bottomRightChildPanel.add(lbTotal);
        bottomPanel.add(jScrollPane); 
        bottomPanel.add(bottomRightChildPanel);        
        mainPanel.add(firstTopPanel);
        mainPanel.add(secondTopPanel);  
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
        else if(e.getSource()==btnCheckout){
            if(listItemsModel.size()==0)
                displayAlertDialog("The order is empty! Please, add some products.", "Empty order found",1);
            else{
                new PaymentForm(listProduct,listItemsModel,listQuantItems,cartItems,subTotal);//This invokes the PaymentForm constructor with no object instatiated
                setVisible(false);
            }
        }  
        else if(e.getSource()==btnRemoveProd){
            int selectedIndex = listView.getSelectedIndex();
            if(selectedIndex!=-1){
                //Before removing the selected item from the order, this if will first deduct the price of the selected item from the subTotal variable, then it will remove its quantity from the array listQuantItems 
                int strPos=-1;
                int indexAux=0;//This will store the index of the item on array listQuantItems
                for(String currentItem: cartItems){//I'm using this for because the index on the listItemsModel variable might not match with index on the array cartItems
                   strPos=currentItem.indexOf(listItemsModel.getElementAt(selectedIndex).toString());
                   if(strPos!=-1){//if both content match
                        strPos=currentItem.indexOf("#");//This will locate the price of the selected item
                        subTotal-=Double.parseDouble(currentItem.substring(strPos+1));//It deductes the price of the selected item from the subTotal variable
                        lbTotal.setText("Total: £"+Double.toString(subTotal));
                        listItemsModel.remove(selectedIndex);
                        break;
                    }
                   indexAux++;
                }                                  
                listQuantItems.remove(indexAux);
                cartItems.remove(indexAux);                       
            }
        }
        else{//This means that the user clicked on one product from the left panel
            int strPos=btnName.indexOf("PRD-");
            if(strPos>=0){
                int indexPrd= Integer.parseInt(btnName.substring(4));
                int indexFounded=-1;
                double newPrice;
                int quantity=0;
                if(listItemsModel.size()>0)indexFounded=findIfProductInTheOrder(listProduct.get(indexPrd).getName());//This will check if the product clicked is already in the order 
                if(indexFounded==-1){//This means the product is not in the order, so it will be added only if the quantity on the dropbox does not exceed the stock level of that product
                    quantity=Integer.parseInt(comboBoxItems.getSelectedItem().toString());
                    if(quantity>listProduct.get(indexPrd).getStockLevel()){
                        displayAlertDialog("It is not possible to add that amount of quantity! The current stock level is "+listProduct.get(indexPrd).getStockLevel(),"Stock limit reached",1);
                        return;
                    }
                    newPrice=listProduct.get(indexPrd).getPrice()*quantity;
                    listItemsModel.addElement(comboBoxItems.getSelectedItem()+"x "+listProduct.get(indexPrd).getName()+"   £"+newPrice);//This will be visible to the user
                    listQuantItems.add(btnName+"/"+comboBoxItems.getSelectedItem());//Stores the productID and the quantity
                    cartItems.add(comboBoxItems.getSelectedItem()+"x "+listProduct.get(indexPrd).getName()+"   £"+newPrice+">"+btnName+"/"+comboBoxItems.getSelectedItem()+"#"+newPrice);//This array will be used to create a link between array listItemsModel and array listQuantItems (since both indexes will not match)
                    subTotal+=newPrice;
                    lbTotal.setText("Total: £"+Double.toString(subTotal));
                }
                else {//This means the product is already in the order, so its quantity has to be updates and consequently the subtotal price
                    int indexAux=0;//This will store the index of the item on array listQuantItems
                    for(String currentItem: listQuantItems){//Because the item is already in the order, it will locale the quantity already on the order to increase it (only if the quantity on the dropbox does not exceed the stock level of that product)
                        if(currentItem.contains(btnName)){
                            strPos=currentItem.indexOf("/");
                            quantity=Integer.parseInt(currentItem.substring(strPos+1));                       
                            break;
                        }
                        indexAux++;
                    }                    
                    quantity+=Integer.parseInt(comboBoxItems.getSelectedItem().toString());
                    if(quantity>listProduct.get(indexPrd).getStockLevel())
                        displayAlertDialog("It is not possible to add that amount of quantity! The current stock level is "+listProduct.get(indexPrd).getStockLevel(),"Stock limit reached",1);
                    else{
                        int str= listQuantItems.get(indexAux).indexOf("/");                        
                        int prevQuantity=Integer.parseInt(listQuantItems.get(indexAux).substring(str+1));//Stores the previous quantity
                        listQuantItems.remove(indexAux);
                        subTotal-=listProduct.get(indexPrd).getPrice()*prevQuantity;//Reduces from the subtotal
                        listQuantItems.add(btnName+"/"+quantity);//Store the same product ID (on the left panel) and the new quantity
                        newPrice=listProduct.get(indexPrd).getPrice()*quantity;
                        listItemsModel.setElementAt(quantity+"x "+listProduct.get(indexPrd).getName()+"   £"+newPrice, indexFounded);//updates the item information on the array listItemsModel
                        cartItems.remove(indexAux);
                        cartItems.add(quantity+"x "+listProduct.get(indexPrd).getName()+"   £"+newPrice+">"+btnName+"/"+quantity+"#"+newPrice);
                        subTotal+=newPrice;
                        lbTotal.setText("Total: £"+Double.toString(subTotal));
                    }                    
                }
            }
        }
    }
    //This will check if the product clicked is already in the order 
    private int findIfProductInTheOrder(String elementToFind){    
        for (int i = 0; i<listItemsModel.size(); i++){
            int strPos=listItemsModel.getElementAt(i).toString().indexOf(elementToFind);
            if(strPos!=-1)
                return i;
        }
        return -1;
    }
    //This will add new buttons to the panel using the information of each product on the array   
    private void addProdsToPanel(){
        int index=0;
        for(Product currentPrd: listProduct){
            if(currentPrd.getStockLevel()>0){                
                JButton btnPrd = super.standardPRDBtn(index, "<html>"+currentPrd.getName()+"<br/><br/>£"+currentPrd.getPrice()+"</html>");
                btnPrd.addActionListener(this);
                lbMiddlePanel.add(btnPrd);   
                
            }
            index++;
        }
    } 
}