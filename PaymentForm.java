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

public class PaymentForm extends Form implements ActionListener{
    private JPanel listItemsPanel =  new JPanel(new BorderLayout());
    private JPanel panelMiddleButtons =  new JPanel();
    private JPanel panelRectangle =  new JPanel();
    private JPanel panelRectangleChild =  new JPanel();
    private JPanel panelFirstRow =  new JPanel(new BorderLayout());
    private JPanel panelSecondRow =  new JPanel(new BorderLayout());
    private JPanel panelFirstRowFirstColumn =  new JPanel();
    private JPanel panelFirstRowSecondColumn =  new JPanel();
    private JPanel panelSecondRowFirstColumn =  new JPanel();
    private JPanel panelSecondRowSecondColumn =  new JPanel(); 
    private JPanel panelMiddleButtonsLastRow = new JPanel();
    private JLabel lbCashEntered;
    private JButton btnEnter, btnBackSpace, btnZero;
    private DefaultListModel listItemsModel = new DefaultListModel();
    private JList listView;
    private ArrayList<String> listQuantItems=new ArrayList<>();
    private ArrayList<String> cartItems=new ArrayList<>();
    private double subTotal;
    private double cashEntered=0;
    
    PaymentForm(ArrayList<Product> listPrd, DefaultListModel list_ItemsModel, ArrayList<String> list_QuantItems, ArrayList<String> cart_Items, double finalPrice){
        super(listPrd);
        subTotal = finalPrice;               
        listQuantItems = list_QuantItems;
        cartItems = cart_Items;   
        listItemsModel = list_ItemsModel; 
        lbCashEntered=standardLabel("£0.00");
        listView = new JList(listItemsModel);  
        
        //Setting Elements
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listView.setSelectedIndex(-1);
        listView.setVisibleRowCount(5);
        listView.setFont(new Font("Verdana", Font.BOLD, 16));   
        listItemsPanel.setBackground(Color.white);   
        lbTitle.setText("Checkout");  
        lbTitle.setFont(new Font("Arial", Font.BOLD, 30));  
        lbMiddlePanel.setLayout(new BoxLayout(lbMiddlePanel,BoxLayout.Y_AXIS));
        lbMiddlePanel.setPreferredSize(new Dimension(1500,1300));   
        bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.X_AXIS));
        panelRectangle.setOpaque(false);
        panelMiddleButtons.setLayout(new BoxLayout(panelMiddleButtons,BoxLayout.Y_AXIS));
        panelMiddleButtons.setPreferredSize(new Dimension(1500,1100));
        panelMiddleButtons.setOpaque(false);
        panelMiddleButtonsLastRow.setOpaque(false);
        panelMiddleButtonsLastRow.setPreferredSize(new Dimension(800,800));
        panelRectangleChild.setPreferredSize(new Dimension(300, 150));
        panelRectangleChild.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
        panelRectangleChild.setBackground(Color.white);
        panelRectangleChild.setLayout(new BoxLayout(panelRectangleChild,BoxLayout.Y_AXIS));
        panelFirstRow.setOpaque(false);
        panelSecondRow.setOpaque(false); 
        panelFirstRowFirstColumn.setOpaque(false);
        panelFirstRowSecondColumn.setOpaque(false);
        panelSecondRowFirstColumn.setOpaque(false);
        panelSecondRowSecondColumn.setOpaque(false);
        
        //Adding Elements
        listItemsPanel.add(new JScrollPane(listView)); 
        firstTopPanel.add(lbTitle);   
        btnBack = super.standardBtnImage(new ImageIcon(imgBack));
        btnBack.addActionListener(this);     
        secondTopPanel.add(btnBack);                    
        panelFirstRowFirstColumn.add(standardLabel("Total:"));
        panelFirstRowSecondColumn.add(standardLabel("£"+Double.toString(subTotal)));
        panelSecondRowFirstColumn.add(standardLabel("Cash entered:"));        
        panelSecondRowSecondColumn.add(lbCashEntered);
        panelFirstRow.add(panelFirstRowFirstColumn,BorderLayout.WEST);
        panelFirstRow.add(panelFirstRowSecondColumn,BorderLayout.EAST);        
        panelSecondRow.add(panelSecondRowFirstColumn,BorderLayout.WEST);
        panelSecondRow.add(panelSecondRowSecondColumn,BorderLayout.EAST);
        panelRectangleChild.add(panelFirstRow);
        panelRectangleChild.add(panelSecondRow);
        panelRectangle.add(panelRectangleChild);       
        String[] arrayLv= {"1","2","3","4","5","6","7","8","9"};
        panelMiddleButtons.add(setBtnElements(arrayLv,0,3));
        panelMiddleButtons.add(setBtnElements(arrayLv,3,6));
        panelMiddleButtons.add(setBtnElements(arrayLv,6,9));
        panelMiddleButtonsLastRow.add(Box.createHorizontalStrut(450));
        btnZero=super.standardBtn("0",26);
        btnZero.addActionListener(this);
        panelMiddleButtonsLastRow.add(btnZero);
        btnBackSpace=super.standardBtn("",26);
        btnBackSpace.setIcon(new ImageIcon(getClass().getResource("backspace.png")));
        btnBackSpace.addActionListener(this);
        panelMiddleButtonsLastRow.add(btnBackSpace);
        btnEnter=super.standardBtn("Enter",20);
        btnEnter.addActionListener(this);
        panelMiddleButtonsLastRow.add(btnEnter);
        panelMiddleButtons.add(panelMiddleButtonsLastRow);
        panelMiddleButtons.add(Box.createVerticalStrut(600));           
        lbMiddlePanel.add(panelRectangle);
        lbMiddlePanel.add(panelMiddleButtons);
        bottomPanel.add(lbMiddlePanel);  
        bottomPanel.add(listItemsPanel);  
        mainPanel.add(firstTopPanel);
        mainPanel.add(secondTopPanel);
        mainPanel.add(bottomPanel);        
        add(mainPanel);              
        setVisible(true);
    }    
    
    public void actionPerformed(ActionEvent e){
        String btnName=((JButton)e.getSource()).getName();//Because buttons btnBackSpace, btnEnter,  btnBack and btnZero don't have Names, the variable btnName will be null
        int strPos=-1;
        try {strPos=btnName.indexOf("-");}//Because the variable btnName might be null, this indexOf might generate  NullPointerException
        catch(NullPointerException ex){}

        if(e.getSource()==btnBack){
            new SalesForm(listProduct, listItemsModel,listQuantItems,cartItems, subTotal);//This invokes the SalesForm constructor with no object instatiated  
            setVisible(false);
        }
        else if(e.getSource()==btnBackSpace){
            cashEntered=0;
            lbCashEntered.setText("£"+cashEntered);
        }
        else if(e.getSource()==btnEnter){
            double result=0;
            result=cashEntered-subTotal;
            if(result>=0){//This if will process the sell and then will go back to the sales screen only if there is at one product which stock level is higher than zero. Otherwhise, it will redirect the user to the main screen
                sell();//This will get the price and the quantity of the each item on the order and will process the sell  
                displayAlertDialog("The change is £"+result,"Change",-1);                

                if(!isThereAtLeastOnePrdStockAvailable())//This will check if there is at one product which stock level is higher than zero
                    new MainForm(listProduct);//This invokes the Mainform constructor with no object instatiated
                else{
                    subTotal=0;
                    listItemsModel = new DefaultListModel();
                    listQuantItems = new ArrayList<>();
                    cartItems = new ArrayList<>();
                    new SalesForm(listProduct, listItemsModel,listQuantItems,cartItems, subTotal);//This invokes the SalesForm constructor with no object instatiated  
                }                     
                setVisible(false);
            }
            else
                displayAlertDialog("The cash entered is not enough! ","Invalid value",0);            
        }
        else if((strPos!=-1) || (e.getSource()==btnZero)){            
            String digits = Double.toString(cashEntered);
            double prevCashEntered=cashEntered;
            if(e.getSource()==btnZero){
                cashEntered*=10;
                digits = Double.toString(cashEntered);
                if(digits.indexOf("E")!=-1){//If after adding more zero, the amount gets an E, then the user will be unable of adding more zeros
                    cashEntered=prevCashEntered;
                    return;
                }
                cashEntered=Math.round(cashEntered * 100.0) / 100.0;                
            }
            else{//This means the user has clicked on a number   
                int numberClicked = Integer.parseInt(btnName.substring(strPos+1));
                int strAuxPos=digits.indexOf(".");                
                int lastDecimal = Integer.parseInt(digits.substring(strAuxPos+1));
                boolean lastDecimalNotZero=(lastDecimal==0)?false:true;              
                if((cashEntered!=0) &&(lastDecimalNotZero)){
                    cashEntered*=10;  
                    digits = Double.toString(cashEntered);
                    if(digits.indexOf("E")!=-1){//If after adding more zero, the amount gets an E, then the user will be unable of adding more zeros
                        cashEntered=prevCashEntered;
                        return;
                    }                    
                }
                cashEntered+=numberClicked*0.01;     
                cashEntered=Math.round(cashEntered * 100.0) / 100.0;
            }                
            lbCashEntered.setText("£"+cashEntered);
        }
    }    
    //This will return a jpanel with buttons included
    private JPanel setBtnElements(String arrayValues[], int startIndex, int endIndex){
        JPanel p = new JPanel(); 
        p.setPreferredSize(new Dimension(800,500));
        p.setOpaque(false);
        for (int i = startIndex; i<endIndex; i++){
            JButton btn = super.standardBtn(arrayValues[i], 26);
            btn.setName("Btn-"+arrayValues[i]);
            btn.addActionListener(this);
            p.add(btn);
        }
        return p;
    }
    
    private JLabel standardLabel(String txt){
        JLabel lb = new JLabel(txt);
        lb.setFont(new Font("Verdana", Font.BOLD, 16));
        return lb;
    } 
    
    private void sell(){//This will get the price and the quantity of the each item on the order and will process the sell        
        for(String currentItem: cartItems){
            int strPos=currentItem.indexOf(">");
            int str_Pos=currentItem.indexOf("#");
            String prdId=currentItem.substring(strPos+1, str_Pos);
            strPos=prdId.indexOf("/");
            int indexPrd= Integer.parseInt(prdId.substring(4,strPos));
            int quantity=Integer.parseInt(prdId.substring(strPos+1)); 
            listProduct.get(indexPrd).sell(quantity);
        }
    }
}