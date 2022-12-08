
package geneuser;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.util.logging.*;
import javax.swing.table.DefaultTableModel;

public class TestClass {
private static String fname;
private static int idname;
private static String lname;
private static String tempdata;
     
  // My basis for password Checking
    public static void main (String[]Args)
    {
        
        int q, i;
       
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connect.getConnection().prepareStatement("SELECT * FROM ancestor_tb");
            rs = ps.executeQuery();
            ResultSetMetaData stData = rs.getMetaData();       
            q = stData.getColumnCount();       
                    while(rs.next())
                    {
                        System.out.println(rs.getString("firstname"));
                    }                  
        } catch (SQLException ex) {
            
        } 
        
        
        
        
    }  
}
