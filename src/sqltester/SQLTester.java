/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqltester;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.swing.*;

import sqltest.SQLTest;

/**
 *
 * @author simon
 */
public class SQLTester {
    
    public class DataObjectPanel{
        
        public JPanel mainPanel;
        public JPanel pkPanel;
        public JPanel dataPanel;
        
        public JLabel pkLabel;
        public JLabel dataLabel;
        
        DataObjectPanel(SQLTest.DataObject dobject){
            pkLabel.setText(Integer.toString(dobject.pk));
            dataLabel.setText("<html>" + dobject.data);
            
            mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(1, 2));
            mainPanel.setBackground(Color.red);
            
            pkPanel = new JPanel();
            pkPanel.setLayout(new GridLayout(1, 1));
            pkPanel.setBackground(Color.green);
            
            dataPanel = new JPanel();
            dataPanel.setLayout(new GridLayout(1, 1));
            dataPanel.setBackground(Color.blue);
            
            mainPanel.add(pkPanel);
            mainPanel.add(dataPanel);
            
            pkPanel.add(pkLabel);
            dataPanel.add(dataLabel);
        }
        
        public void Initiate(SQLTest.DataObject dobject){
            pkLabel.setText(Integer.toString(dobject.pk));
            dataLabel.setText("<html>" + dobject.data);
            
            mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(1, 2));
            mainPanel.setBackground(Color.red);
            
            pkPanel = new JPanel();
            pkPanel.setLayout(new GridLayout(1, 1));
            pkPanel.setBackground(Color.green);
            
            dataPanel = new JPanel();
            dataPanel.setLayout(new GridLayout(1, 1));
            dataPanel.setBackground(Color.blue);
            
            mainPanel.add(pkPanel);
            mainPanel.add(dataPanel);
            
            pkPanel.add(pkLabel);
            dataPanel.add(dataLabel);
        }
        
        public JPanel getMainPanel(){
            return mainPanel;
        }
    }
    
    private static JFrame mainFrame;
    private static JLabel headerLabel;
    private static JLabel statusLabel;
    private static JLabel notSideLabel;
    private static JPanel controlPanel;
    private static JPanel controlPanelSubA;
    private static JPanel controlPanelSubB;
    private static JPanel sidePanel;
    private static JLabel sideLabel;
    
    /**
     * @param args the command line arguments
     */
    
    static SQLTest sql = new SQLTest();
    
    ArrayList<DataObjectPanel> dataObjectPanels = new ArrayList<DataObjectPanel>();
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        SQLTester sqltester = new SQLTester();
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
            System.out.println("Failed to set look and feel: " + e);
        }
        
        sqltester.prepareGUI();
        sqltester.showEventDemo();
        
        sql.connect("localhost", "3306", "javabase", "root", "");
        sqltester.printCommands();
        sql.closeConnection();
    }
     
    public void printCommands(){
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Boolean done = false;
        while(!done){
            System.out.println("Enter a command: ");
            String s = reader.next(); // Scans the next token of the input as an int.
            System.out.println("You entered: '" + s + "'");
            switch(s){
                case "exit":
                    System.out.println("Exiting...");
                    done = true;
                    break;
                case "print":
                    System.out.println("Enter table: ");
                    String tableToPrint = reader.next();
                    statusLabel.setText("<html>" + sql.printFromDatabase(tableToPrint));
                    break;
                case "insert":
                    System.out.println("Enter table: ");
                    String tableToInsert = reader.next();
                    System.out.println("Enter a: ");
                    String a = reader.next(); // Scans the next token of the input as an int.
                    System.out.println("Enter b: ");
                    String b = reader.next(); // Scans the next token of the input as an int.
                    System.out.println("Enter c: ");
                    String c = reader.next(); // Scans the next token of the input as an int.
                    sql.insertIntoDatabase(tableToInsert, a, b, c);
                    break;
                case "delete":
                    System.out.println("Enter table: ");
                    String tableToDelete = reader.next();
                    System.out.println("Enter column: ");
                    String column = reader.next();
                    System.out.println("Enter condition: ");
                    String conditionDelete = reader.next(); // Scans the next token of the input as an int.

                    sql.deleteFromDatabase(tableToDelete, column, conditionDelete);
                    break;
                case "update":
                    
                    System.out.println("Enter table: ");
                    String tableToUpdate = reader.next();
                    
                    System.out.println("Enter columns in order, enter done when finished: ");
                    ArrayList columnVector = new ArrayList();
                    Boolean finished = false;
                    while(!finished){
                        System.out.println("Enter column: ");
                        String tmp = reader.next();
                        if(tmp.equals("done")){
                            finished = true;
                        }
                        else{
                            columnVector.add(tmp);
                        }
                    }
                    
                    System.out.println("Enter new data in order, enter done when finished: ");
                    ArrayList newDataVector = new ArrayList();
                    finished = false;
                    while(!finished){
                        System.out.println("Enter new data: ");
                        String tmp = reader.next();
                        if(tmp.equals("done")){
                            finished = true;
                        }
                        else{
                            newDataVector.add(tmp);
                        }
                    }
                    
                    System.out.println("Enter condition column: ");
                    String conditionUpdateColumn = reader.next();
                    System.out.println("Enter condition data: ");
                    String conditionUpdateData = reader.next();
                    
                    sql.updateDatabase(tableToUpdate, columnVector, newDataVector, conditionUpdateColumn, conditionUpdateData);
                    break;
                case "help":
                    System.out.println("Available commands: exit, print, insert, delete, update, help");
                    break;
                default:
                    System.out.println("Invalid command, enter help for available commands");
                    break;
            }
        }

        //once finished
        reader.close();
    }
    
    
    private void prepareGUI(){
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(1920,1080);
        mainFrame.setLayout(new GridLayout(1, 2));

        //headerLabel = new JLabel("",JLabel.CENTER );
        statusLabel = new JLabel("",JLabel.CENTER);
        //notSideLabel = new JLabel("Not side label",JLabel.CENTER);         
        statusLabel.setSize(750,100);

        mainFrame.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent windowEvent){
              System.exit(0);
           }        
        });    
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 1));
        
        controlPanelSubA = new JPanel();
        controlPanelSubA.setLayout(new GridLayout(3, 1));
        controlPanelSubA.setBackground(Color.green);
        
        controlPanelSubB = new JPanel();
        controlPanelSubB.setLayout(new FlowLayout());
        controlPanelSubB.setBackground(Color.blue);
        
        //sideLabel = new JLabel("Side label", JLabel.CENTER);
        sidePanel = new JPanel();
        sidePanel.setLayout(new FlowLayout());
        sidePanel.setBackground(Color.red);

        //mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        sidePanel.add(statusLabel);
        controlPanel.add(controlPanelSubA);
        controlPanel.add(controlPanelSubB);
        mainFrame.add(sidePanel);
        //sidePanel.add(sideLabel);
        mainFrame.setVisible(true); 
    }
    private void showEventDemo(){
      //headerLabel.setText("Control in action: Button"); 

      JButton okButton = new JButton("OK");
      JButton submitButton = new JButton("Submit");
      JButton cancelButton = new JButton("Cancel");

      okButton.setActionCommand("OK");
      submitButton.setActionCommand("Submit");
      cancelButton.setActionCommand("Cancel");

      okButton.addActionListener(new ButtonClickListener()); 
      submitButton.addActionListener(new ButtonClickListener()); 
      cancelButton.addActionListener(new ButtonClickListener()); 

      controlPanelSubB.add(okButton);
      controlPanelSubB.add(submitButton);
      controlPanelSubB.add(cancelButton);       

      mainFrame.setVisible(true);  
      
      //create a menu bar
      final JMenuBar menuBar = new JMenuBar();

      //create menus
      JMenu fileMenu = new JMenu("File");
      JMenu editMenu = new JMenu("Edit"); 
      final JMenu aboutMenu = new JMenu("About");
      final JMenu linkMenu = new JMenu("Links");
     
      //create menu items
      JMenuItem newMenuItem = new JMenuItem("New");
      newMenuItem.setMnemonic(KeyEvent.VK_N);
      newMenuItem.setActionCommand("New");

      JMenuItem openMenuItem = new JMenuItem("Open");
      openMenuItem.setActionCommand("Open");

      JMenuItem saveMenuItem = new JMenuItem("Save");
      saveMenuItem.setActionCommand("Save");

      JMenuItem exitMenuItem = new JMenuItem("Exit");
      exitMenuItem.setActionCommand("Exit");

      JMenuItem cutMenuItem = new JMenuItem("Cut");
      cutMenuItem.setActionCommand("Cut");

      JMenuItem copyMenuItem = new JMenuItem("Copy");
      copyMenuItem.setActionCommand("Copy");

      JMenuItem pasteMenuItem = new JMenuItem("Paste");
      pasteMenuItem.setActionCommand("Paste");

      MenuItemListener menuItemListener = new MenuItemListener();

      newMenuItem.addActionListener(menuItemListener);
      openMenuItem.addActionListener(menuItemListener);
      saveMenuItem.addActionListener(menuItemListener);
      exitMenuItem.addActionListener(menuItemListener);
      cutMenuItem.addActionListener(menuItemListener);
      copyMenuItem.addActionListener(menuItemListener);
      pasteMenuItem.addActionListener(menuItemListener);

      final JCheckBoxMenuItem showWindowMenu = new JCheckBoxMenuItem(
         "Show About", true);
      showWindowMenu.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent e) {
            
            if(showWindowMenu.getState()){
               menuBar.add(aboutMenu);
            } else {
               menuBar.remove(aboutMenu);
            }
         }
      });
      final JRadioButtonMenuItem showLinksMenu = new JRadioButtonMenuItem(
         "Show Links", true);
      
      showLinksMenu.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent e) {
            
            if(menuBar.getMenu(3)!= null){
               menuBar.remove(linkMenu);
               mainFrame.repaint();
            } else {                   
               menuBar.add(linkMenu);
               mainFrame.repaint();
            }
         }
      });
      //add menu items to menus
      fileMenu.add(newMenuItem);
      fileMenu.add(openMenuItem);
      fileMenu.add(saveMenuItem);
      fileMenu.addSeparator();
      fileMenu.add(showWindowMenu);
      fileMenu.addSeparator();
      fileMenu.add(showLinksMenu);       
      fileMenu.addSeparator();
      fileMenu.add(exitMenuItem);        
      
      editMenu.add(cutMenuItem);
      editMenu.add(copyMenuItem);
      editMenu.add(pasteMenuItem);

      //add menu to menubar
      menuBar.add(fileMenu);
      menuBar.add(editMenu);
      menuBar.add(aboutMenu);       
      menuBar.add(linkMenu);

      //add menubar to the frame
      mainFrame.setJMenuBar(menuBar);
      mainFrame.setVisible(true); 
   }
   private class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();  
         
         if( command.equals( "OK" ))  {
            statusLabel.setText("Ok Button clicked.");
         } else if( command.equals( "Submit" ) )  {
            //statusLabel.setText("Submit Button clicked."); 
            ArrayList<SQLTest.DataObject> arr = sql.printFromDatabase("things");
            //String labelString = "";
            for(int i = 0; i < arr.size(); i++){
                SQLTest.DataObject tmp = arr.get(i);
                //labelString += tmp.toString();
                dataObjectPanels.add(new DataObjectPanel(tmp));
                //tmpDobjectPanel.Initiate(tmp);
                sidePanel.add(dataObjectPanels.get(dataObjectPanels.size() - 1).getMainPanel());
                
                System.out.println(dataObjectPanels.get(dataObjectPanels.size() - 1).dataLabel);
            }
         } else {
            statusLabel.setText("Cancel Button clicked.");
         }  	
      }		
   }
   class MenuItemListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {            
         statusLabel.setText(e.getActionCommand() + " JMenuItem clicked.");
      }    
   }
}
