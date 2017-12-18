/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqltester;

import sqltest.SQLTest;

/**
 *
 * @author simon
 */
public class SQLTester {

    /**
     * @param args the command line arguments
     */
    
    static SQLTest sql = new SQLTest();
    
    public static void main(String[] args) {
        // TODO code application logic here
        sql.connect("localhost", "3306", "javabase", "root", "");
        sql.printCommands();
        sql.closeConnection();
    }
}
