/** 
* @author Carla Augusto
* @version 29/03/2017
**/
package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;

public class Form extends JFrame{       
    protected JPanel mainPanel = new JPanel();
    protected JPanel bottomPanel= new JPanel(new BorderLayout());
    protected JPanel firstTopPanel= new JPanel(new FlowLayout());
    protected JPanel secondTopPanel= new JPanel(new FlowLayout(FlowLayout.LEFT));
    protected JPanel scrollablePanel = new JPanel(new BorderLayout());
    protected JScrollPane jScrollPane=new JScrollPane();
    protected JLabel lbTitle = new JLabel(); 
    protected JLabel lbMiddlePanel = new JLabel(new ImageIcon(getClass().getResource("background.jpg")));
    protected java.net.URL imgBack = getClass().getResource("arrow.png");
    protected JButton btnBack;
    protected ArrayList<Product> listProduct;    
    
    Form(ArrayList<Product> listPrd){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//When the close button is pressed the application will stop running
        setMinimumSize(new Dimension(700,500));
        setTitle("Sales App - U1523913");        
        setExtendedState(JFrame.MAXIMIZED_BOTH);//This will maximise the JFrame
        setLocationRelativeTo(null);//This will center the JFrame 
        listProduct=listPrd;
        
        //Setting Elements
        lbTitle.setForeground(Color.white); 
        lbMiddlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        firstTopPanel.setBackground(Color.black);  
        bottomPanel.setOpaque(false); 
        secondTopPanel.setBackground(Color.black);
        secondTopPanel.setOpaque(true); 
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        scrollablePanel.setOpaque(false);
        jScrollPane.setOpaque(false);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); //This will show the horizontal scrollbar only when is needed
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); //This will show the vertical scrollbar only when is needed       
    }
    //This function will return a button with the same text and fontsize given by the parameters
    protected JButton standardBtn(String txt, int fontSize){
        JButton btn = new JButton(txt);
        btn.setBackground(Color.black);
        btn.setForeground(Color.lightGray);
        btn.setFont(new Font("Verdana", Font.BOLD, fontSize));
        btn.setOpaque(true);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
        btn.setPreferredSize(new Dimension(220,100));
        btn.setFocusPainted(false);
        return btn;
    }
    //This function will return an image button
    protected JButton standardBtnImage(ImageIcon img){
        JButton btn = new JButton(img);
        btn.setPreferredSize(new Dimension(48,48));
        btn.setContentAreaFilled(false); 
        return btn;
    }           
    //This function will return a button with the same name and text given by the paramenters.    
    protected JButton standardPRDBtn(int btnIndex, String btnText){
        JButton btn = new JButton(btnText);
        btn.setBackground(Color.lightGray);
        btn.setForeground(Color.black);
        btn.setFont(new Font("Verdana", Font.BOLD, 16));
        btn.setOpaque(true);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        btn.setPreferredSize(new Dimension(200, 200));
        btn.setName("PRD-"+btnIndex);
        return btn;
    }
    //This will display a Message Dialog
    protected void displayAlertDialog(String message, String title,int iconType){
        JFrame frame = new JFrame();
        UIManager.put("OptionPane.messageFont", new Font("System", Font.PLAIN, 18));//This will set the message Font
        UIManager.put("OptionPane.buttonFont", new Font("System", Font.PLAIN, 18));//This will set the button Font
        JOptionPane.showMessageDialog(
            frame,message,title,iconType);
    }
    //This will check if there is at one product which stock level is higher than zero
    protected boolean isThereAtLeastOnePrdStockAvailable(){
        for(Product currentPRD: listProduct){             
            if(currentPRD.getStockLevel()>0)
                return true;
        }
        return false;
    }
}