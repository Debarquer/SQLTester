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
    
    private static JFrame mainFrame;
    private static JLabel headerLabel;
    private static JLabel statusLabel;
    private static JPanel controlPanel;

    /**
     * @param args the command line arguments
     */
    
    static SQLTest sql = new SQLTest();
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
            System.out.println("Failed to set look and feel: " + e);
        }
        
        
        
        prepareGUI();
        showEventDemo();
        
        sql.connect("localhost", "3306", "javabase", "root", "");
        printCommands();
        sql.closeConnection();
    }
    
     
    public static void printCommands(){
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
                    statusLabel.setText("<html>" + SQLTest.printFromDatabase(tableToPrint));
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
                    SQLTest.insertIntoDatabase(tableToInsert, a, b, c);
                    break;
                case "delete":
                    System.out.println("Enter table: ");
                    String tableToDelete = reader.next();
                    System.out.println("Enter column: ");
                    String column = reader.next();
                    System.out.println("Enter condition: ");
                    String conditionDelete = reader.next(); // Scans the next token of the input as an int.

                    SQLTest.deleteFromDatabase(tableToDelete, column, conditionDelete);
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
                    
                    SQLTest.updateDatabase(tableToUpdate, columnVector, newDataVector, conditionUpdateColumn, conditionUpdateData);
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
    
    
    private static void prepareGUI(){
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(800,400);
        mainFrame.setLayout(new GridLayout(3, 1));

        headerLabel = new JLabel("",JLabel.CENTER );
        statusLabel = new JLabel("",JLabel.CENTER);        
        statusLabel.setSize(750,100);

        mainFrame.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent windowEvent){
              System.exit(0);
           }        
        });    
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true); 
    }
    private static void showEventDemo(){
      headerLabel.setText("Control in action: Button"); 

      JButton okButton = new JButton("OK");
      JButton submitButton = new JButton("Submit");
      JButton cancelButton = new JButton("Cancel");

      okButton.setActionCommand("OK");
      submitButton.setActionCommand("Submit");
      cancelButton.setActionCommand("Cancel");

      okButton.addActionListener(new ButtonClickListener()); 
      submitButton.addActionListener(new ButtonClickListener()); 
      cancelButton.addActionListener(new ButtonClickListener()); 

      controlPanel.add(okButton);
      controlPanel.add(submitButton);
      controlPanel.add(cancelButton);       

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
   private static class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();  
         
         if( command.equals( "OK" ))  {
            statusLabel.setText("Ok Button clicked.");
         } else if( command.equals( "Submit" ) )  {
            //statusLabel.setText("Submit Button clicked."); 
            statusLabel.setText("<html>" + SQLTest.printFromDatabase("things"));
         } else {
            statusLabel.setText("Cancel Button clicked.");
         }  	
      }		
   }
   static class MenuItemListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {            
         statusLabel.setText(e.getActionCommand() + " JMenuItem clicked.");
      }    
   }
}
