/** 
* @author Carla Augusto
* @version 29/03/2017
**/
package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;

public class AddEditProductForm extends JFrame implements ActionListener { 
    private JPanel mainPanel = new JPanel();    
    private JLabel formName = new JLabel("Add New Product");     
    private JLabel productName = new JLabel("Product Name: ");    
    private JLabel stockLevel = new JLabel("Stock Level: ");
    private JLabel price = new JLabel("Price: ");
    private JTextField txtName= new JTextField(30);
    private JTextField txtStockLevel= new JTextField();
    private JTextField txtPrice= new JTextField();
    private JButton addEditBtn = new JButton();
    private JButton removeBtn = new JButton();
    private ArrayList<Product> listProduct;
    private boolean managementFormAlreadyVisible, proceedToSalesForm;
    private ManagementForm managementWindow;
    private MainForm mainWindow;
    private int productID;
    
    AddEditProductForm(ArrayList<Product> listPrd, ManagementForm mngWindow,MainForm mainWin, boolean mngFormAlreadyVisible, int prdID, boolean proceedToSalesWind){
        listProduct=listPrd;
        managementFormAlreadyVisible=mngFormAlreadyVisible;
        managementWindow=mngWindow;
        mainWindow=mainWin;
        productID=prdID;
        proceedToSalesForm=proceedToSalesWind;
        
        setTitle("Sales App - U1523913");
        setSize(800,300);
        setResizable(false);
        setLocationRelativeTo(null);//this will center the JFrame
        setLayout(new GridLayout(1,1));
        
        //Setting Elements
        formName.setFont(new Font("", Font.BOLD, 22));
        productName.setFont(new Font("Arial", Font.PLAIN, 20));        
        txtName.setFont(new Font("Arial", Font.PLAIN, 18));  
        txtName.setTransferHandler(null);//This will disable all copy/paste actions on this field
        stockLevel.setFont(new Font("Arial", Font.PLAIN, 20)); 
        txtStockLevel.setFont(new Font("Arial", Font.PLAIN, 18)); 
        txtStockLevel.setColumns(10);
        txtStockLevel.setTransferHandler(null);//This will disable all copy/paste actions on this field
        price.setFont(new Font("Arial", Font.PLAIN, 20));        
        txtPrice.setFont(new Font("Arial", Font.PLAIN, 18));         
        txtPrice.setColumns(10);
        txtPrice.setTransferHandler(null);//This will disable all copy/paste actions on this field        
        addEditBtn=standardBtn();
        addEditBtn.setPreferredSize(new Dimension(80, 60));
        addEditBtn.setText("ADD");
        removeBtn=standardBtn();
        removeBtn.setPreferredSize(new Dimension(100, 60));
        removeBtn.setText("REMOVE");
        mainPanel.setLayout(new GridBagLayout());      
        
        if(productID!=-1){ //This will fill the elements using the selected product information in order to be editable      
            formName.setText("Edit Product");
            txtName.setText(listProduct.get(productID).getName());   
            txtName.setEnabled(false);//This will prevent the product name being editable
            txtStockLevel.setText(Integer.toString(listProduct.get(productID).getStockLevel()));       
            txtPrice.setText(Double.toString(listProduct.get(productID).getPrice()));  
            addEditBtn.setText("SAVE");
        }
        
        
        txtName.addKeyListener(
            new KeyListener(){
                @Override
                public void keyPressed(KeyEvent e){}
                @Override
                public void keyTyped(KeyEvent e) {
                    if((txtName.getText().length()==0 && e.getKeyChar()==' ') || (e.getKeyChar()=='#') || (e.getKeyChar()=='>'))
                        e.consume();//This will prevent adding spaces (when the JTextfield is empty) or any character equals to '#' and '>' symbols (They are being used in the SalesForm and PaymentForm classes)
                }
                @Override
                public void keyReleased(KeyEvent e) {}
            }
        );       
        txtStockLevel.addKeyListener(
            new KeyListener(){
                 @Override
                public void keyPressed(KeyEvent e){}
                @Override
                public void keyTyped(KeyEvent e) {
                    if(!Character.isDigit(e.getKeyChar()) && e.getKeyCode()!=KeyEvent.VK_BACK_SPACE)
                        e.consume();//This will prevent characters different from a digit and back space                    
                }
                @Override
                public void keyReleased(KeyEvent e) {}
            }
        );        
        txtPrice.addKeyListener(
            new KeyListener(){
                 @Override
                public void keyPressed(KeyEvent e){}
                @Override
                public void keyTyped(KeyEvent e) {
                    if(!Character.isDigit(e.getKeyChar()) && e.getKeyCode()!=KeyEvent.VK_BACK_SPACE && e.getKeyChar()!='.')
                        e.consume();//This will prevent characters different from a digit, back space and full stop
                    else if(e.getKeyChar()=='.' && txtPrice.getText().contains("."))
                        e.consume();//This will prevent to add a second full stop
                }
                @Override
                public void keyReleased(KeyEvent e) {}
            }
        );
        addEditBtn.addActionListener(this);     
        removeBtn.addActionListener(this);
        
        
        GridBagConstraints  gc = new GridBagConstraints ();
        gc.anchor =  GridBagConstraints.CENTER;
        gc.gridx = 1;   //Second Columm
        gc.gridy = 0;   //First Row
        gc.weighty = 0.5;
        gc.insets = new Insets(0,0,10,0);
        mainPanel.add(formName, gc);        
               
        gc.insets = new Insets(0,30,0,0);
        gc.anchor =  GridBagConstraints.LINE_END;       
        
        gc.gridx = 0;   //First Columm
        gc.gridy = 1;   //Second Row 
        mainPanel.add(productName, gc);
        
        gc.gridx = 0;   //First Columm
        gc.gridy = 2; //Third Row  
        mainPanel.add(stockLevel, gc);
       
        gc.gridx = 0; //First Columm
        gc.gridy = 3; //Fourth Row  
        mainPanel.add(price, gc);
        
        gc.insets = new Insets(0,0,0,0);
        gc.anchor =  GridBagConstraints.LINE_START;
        gc.gridx = 1; //Second Columm
        gc.gridy = 1; //Second Row        
        mainPanel.add(txtName, gc);
        
        gc.gridx = 1; //Second Columm
        gc.gridy = 2; //Third Row  
        mainPanel.add(txtStockLevel, gc);
        
        gc.gridx = 1; //Second Columm
        gc.gridy = 3;  //Fourth Row 
        mainPanel.add(txtPrice, gc);
                
        gc.anchor =  GridBagConstraints.LINE_END;
        gc.gridx = 1; //Second Columm
        gc.gridy = 4; //Fith Row  
        gc.insets = new Insets(10,0,40,0);
        mainPanel.add(addEditBtn, gc);
        if(productID!=-1){ 
            gc.anchor =  GridBagConstraints.LINE_END;
            gc.gridx = 2; //Second Columm
            gc.gridy = 4; //Fith Row  
            gc.insets = new Insets(10,5,40,0);
            mainPanel.add(removeBtn, gc);
        }
        
        add(mainPanel);
        setVisible(true);
    }
    //This will update the price and the stock level of the selected product
    private void editProduct(){
        listProduct.get(productID).setPrice(Double.parseDouble(txtPrice.getText()));
        listProduct.get(productID).reStock(Integer.parseInt(txtStockLevel.getText()));
    }
    //This will return a button
    private JButton standardBtn(){
        JButton btn = new JButton();
        btn.setBackground(Color.black);
        btn.setForeground(Color.lightGray);
        btn.setFont(new Font("Verdana", Font.BOLD, 18));
        btn.setOpaque(true);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
        return btn;
    }
    
    public void actionPerformed(ActionEvent e){  
        if(e.getSource()==addEditBtn){
            boolean validValues = validateFields();  
            if(!validValues)//If all fields are not filled or there is duplication, this variable will prevent that product being added
                return;
            if(productID==-1)//This will add a new product 
                listProduct.add(new Product((txtName.getText().trim()), Integer.parseInt(txtStockLevel.getText()), Double.parseDouble(txtPrice.getText())));           
            if(proceedToSalesForm){//if the user was trying to get to the Sales screen from the main screen 
                if(isThereAtLeastOnePrdStockAvailable()){//This will check if there is at one product which stock level is higher than zero
                    mainWindow.setVisible(false);
                    new SalesForm(listProduct, null,null,null,0);//This invokes the SalesForm constructor with no object instatiated
                }
            }
            else if(!managementFormAlreadyVisible){//This means that the user was trying to get to Product Management screen from the main screen
                mainWindow.setVisible(false);
                new ManagementForm(listProduct, managementFormAlreadyVisible);//This invokes the ManagementForm constructor with no object instatiated
            }
            else {//This means the user is already on Product Management screen
                if(productID!=-1){//This means the user is editing a product
                    editProduct();//This will update the price and the stock level of the selected product
                    managementWindow.refreshPanel();
                }
                else//This means the user is adding a new product
                    managementWindow.addNewProduct(listProduct);
            }
            setVisible(false);
        }
        else
            displayConfDialog();        
    }
    
    //This will display a confirmation Dialog   
    private void displayConfDialog(){
        JFrame frame = new JFrame();
        UIManager.put("OptionPane.messageFont", new Font("System", Font.PLAIN, 18));//This will set the message Font
        UIManager.put("OptionPane.buttonFont", new Font("System", Font.PLAIN, 18));//This will set the button Font
        int n = JOptionPane.showConfirmDialog(
            frame, "This product will be removed from your list. Are you sure?",
            "Remove confirmation",
            JOptionPane.YES_NO_OPTION);
        if(n == JOptionPane.YES_OPTION){
            listProduct.remove(productID);//Product will be removed from the array
            managementWindow.refreshPanel();
            setVisible(false);
        } 
    }
    //This will display an error message Dialog
    private void displayErrorMessageDialog(String message, String title){
        JFrame frame = new JFrame();
        UIManager.put("OptionPane.messageFont", new Font("System", Font.PLAIN, 18));//This will set the message Font
        UIManager.put("OptionPane.buttonFont", new Font("System", Font.PLAIN, 18));//This will set the button Font
        JOptionPane.showMessageDialog(
            frame, message,title,JOptionPane.ERROR_MESSAGE);
    }
    
    private boolean validateFields(){
        if((txtStockLevel.getText().length()>0) && (txtPrice.getText().length()>0) && (txtName.getText().length()>0)){
            if(addEditBtn.getText().equals("ADD") && alreadyExist()){//If the user is adding a new product, this will check if there is already a product with the same name
                displayErrorMessageDialog("There is already a product with the same name! Please type a different one.", "Duplication found");
                return false;
            }
            return true;            
        }
        else{
            displayErrorMessageDialog("Please fill all fields!","Invalid entries found");
            return false;
        }
    }
    //This will check if there is already a product with the same name
    private boolean alreadyExist(){
        for (Product currentPRD : listProduct) {
            if(currentPRD.getName().equals(txtName.getText().trim())) 
                return true;            
        }        
        return false;
    }
    //This will check if there is at one product which stock level is higher than zero
    private boolean isThereAtLeastOnePrdStockAvailable(){
        for(Product currentPRD: listProduct){             
            if(currentPRD.getStockLevel()>0)
                return true;
        }
        return false;
    }
}