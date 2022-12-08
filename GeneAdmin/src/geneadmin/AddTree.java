/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package geneadmin;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.*;
import java.util.logging.*;
import javax.swing.table.DefaultTableModel;
//Encryption
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Kaiserin
 */
public class AddTree extends javax.swing.JFrame {
private static String mname;
private static String lname;
private static String fname;
private static String gender;
private static String death;
private static String age;
private static String birth;
private static int idtemp;
private static String toks;
private static String treetemp;
private static int tempid;


File f = null; 
String path = null;
private ImageIcon format = null;
String imgname = null;
int a = 0;
byte[] pimage = null; 
private static String temporaryUsername;


    /**
     * Creates new form AddTree
     */
    public AddTree() {
        initComponents();
    }

    public void getValues(String a1,String a2,String a3)
    {
    
        PreparedStatement st;
        ResultSet rs;
     
        String query = "SELECT * FROM `ancestor_tb` WHERE `firstname` = ? AND `lastname` = ? AND `middlename` = ?";
         try {
            st = connect.getConnection().prepareStatement(query);
            st.setString(1, a1);
            st.setString(2, a2);
            st.setString(3, a3);
            rs = st.executeQuery();
            if(rs.next())
            {
                JOptionPane.showMessageDialog(null, "Ancestor Found");
              fname = rs.getString("firstname");
              lname = rs.getString("lastname");
              mname = rs.getString("middlename");
              gender = rs.getString("gender");
              death = rs.getString("death");
              age = rs.getString("age");
              birth = rs.getString("birthdate");
              

            }else{
                //error message
                JOptionPane.showMessageDialog(null, "Error or Ancestor Not Found");
             //   this.dispose(); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    
    }
    public void updater()
    {
    
    
        PreparedStatement st;
        ResultSet rs;
        String firstname = jfname1.getText();
        String lastname = jlname1.getText();
        String middlename = jmname1.getText();
        String query = "SELECT * FROM `ancestor_tb` WHERE `firstname` = ? AND `lastname` = ? AND `middlename` = ?";
        try {
            st = connect.getConnection().prepareStatement(query);
            st.setString(1, firstname);
            st.setString(2, lastname);
            st.setString(3, middlename);
            rs = st.executeQuery();
            if(rs.next())
            {
                
              process();

            }else{
                //error message
                
                JOptionPane.showMessageDialog(null, "Error or Ancestor Not Found");
                this.dispose();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
   
    public void process()
    {
        String firstname = jfname1.getText();
        String lastname = jlname1.getText();
        String middlename = jmname1.getText();
        String user = juser.getText();
        temporaryUsername = user;
        String pass = String.valueOf(jpass.getPassword());  
        
        //User and Pass Encrypt
        String usersalt = PasswordEncrypt.getSalt(30);
        String passwordsalt = PasswordEncrypt.getSalt(30);
        
        String usersecure = PasswordEncrypt.generateSecurePassword(user, usersalt);
        String passwordsecure = PasswordEncrypt.generateSecurePassword(pass, passwordsalt);
                
        
        
        
        String tree = jtree.getText();
        
java.util.Date currentDate = GregorianCalendar.getInstance().getTime();
DateFormat df = DateFormat.getDateInstance();
String dateString = df.format(currentDate);
       
java.util.Date d = new java.util.Date();
SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
String timeString = sdf.format(d);

String value0 = timeString;
String values = dateString;
String ok = value0 +" / "+values;
   try {    
        PreparedStatement ps;
        ResultSet rs;   
String AddTreeQuery = "INSERT INTO `user_tb`(`firstname`, `lastname`, `middlename`, `usersalt`, `usersecure`, `passwordsalt`, `passwordsecure`, `tree`, `dateadded`) VALUES (?,?,?,?,?,?,?,?,?)";
         ps = connect.getConnection().prepareStatement(AddTreeQuery);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, middlename);
            ps.setString(4, usersalt);
            ps.setString(5, usersecure);
            ps.setString(6, passwordsalt);
            ps.setString(7, passwordsecure);
            ps.setString(8, tree);
            ps.setString(9, ok);
        
        if(ps.executeUpdate() != 0)
            {              
                treetemp = tree;
                feedbackconfirm();         
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error: Check Your Information");
            }
   }catch(SQLException ex)
   {
       
   }
    }
    
    public void feedbackconfirm()
    {
       PreparedStatement st;
       ResultSet rs; 
    String query = "SELECT * FROM `user_tb` WHERE `tree` = ?"; 
     try {
            st = connect.getConnection().prepareStatement(query);
           
            st.setString(1, treetemp);
            
            rs = st.executeQuery();
            if(rs.next())
            {
                
                idtemp = rs.getInt("id");       
                feedbackadd();
            }else{
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void feedbackadd(){
java.util.Date currentDate = GregorianCalendar.getInstance().getTime();
DateFormat df = DateFormat.getDateInstance();
String dateString = df.format(currentDate);
       
java.util.Date d = new java.util.Date();
SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
String timeString = sdf.format(d);

String value0 = timeString;
String values = dateString;
String ok = value0 +" / "+values;  
        
        
        try { 
    PreparedStatement ps;
    ResultSet rs; 
String AddFeedbackQuery = "INSERT INTO `feedback_tb`(`username`, `user_text`, `dateadded`) VALUES (?,?,?)";
   ps = connect.getConnection().prepareStatement(AddFeedbackQuery);
   ps.setString(1, temporaryUsername);
   ps.setString(2, " ");
   ps.setString(3, ok);
         if(ps.executeUpdate() != 0)
            {
                  treemaker();
                
            }
            else
            {
                
            }
       
       
   }catch (SQLException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    public void treemaker(){
        
        for(int i = 2; i <= 12; i++){
        try{
        PreparedStatement ps;
            ResultSet rs;
 String AddTreeQuery = "INSERT INTO `tree_tb`(`tree`, `anc_id`, `position`, `relationship`) VALUES (?,?,?,?)";
        ps = connect.getConnection().prepareStatement(AddTreeQuery);
        ps.setString(1, treetemp);
            ps.setInt(2, -1);
            ps.setInt(3, i);
            ps.setString(4, "");
            if(ps.executeUpdate() != 0)
            {
               
                
            }
            else
            {
                 JOptionPane.showMessageDialog(null, "Error: Check Your Information");
            }
            
           
        }catch(SQLException ex) {

        } 
    }
        
          
     // ******************************************************************************
    try {    
        PreparedStatement ps;
            ResultSet rs;
 String AddTreeQuery = "INSERT INTO `tree_tb`(`tree`, `anc_id`, `position`, `relationship`) VALUES (?,?,?,?)";
            ps = connect.getConnection().prepareStatement(AddTreeQuery);
            ps.setString(1, treetemp);
            ps.setInt(2, tempid);
            ps.setInt(3, 1);
            ps.setString(4, "You");

            
            
         if(ps.executeUpdate() != 0)
            {
                JOptionPane.showMessageDialog(null, "Family Tree Has Been Created");
                this.dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error: Check Your Information");
            }
        } catch (SQLException ex) {

        }
    // ******************************************************************************
    
    
  
    
    

    }
    
    public boolean verifyFields()
    {
        String firstn = jfname1.getText();
        String lastn = jlname1.getText();
        String middlen = jmname1.getText();
        String user = juser.getText();
        String pass = String.valueOf(jpass.getPassword());
        String tree = jtree.getText();
      
      
        if(firstn.trim().equals("") || lastn.trim().equals("") || middlen.trim().equals("") || user.trim().equals("")
            || pass.trim().equals("") || tree.trim().equals(""))    
              
        {
               JOptionPane.showMessageDialog(null, "One or More Fields Are Empty");
               return false;
        }
        else{
            return true;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonAddData = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jfname1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jlname1 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jmname1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        juser = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jpass = new javax.swing.JPasswordField();
        jLabel18 = new javax.swing.JLabel();
        jtree = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(0, 51, 0));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setText("Create Family Tree and User Account");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 153, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/geneadmin/icons8-back-30.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(48, 48, 48)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, 50));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(0, 51, 0));

        jButtonAddData.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButtonAddData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/geneadmin/icons8-add-32.png"))); // NOI18N
        jButtonAddData.setText("Add");
        jButtonAddData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddDataActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel23.setText("First Name");

        jfname1.setPreferredSize(new java.awt.Dimension(59, 26));
        jfname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jfname1ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setText("Last Name");

        jlname1.setPreferredSize(new java.awt.Dimension(59, 26));
        jlname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jlname1ActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel20.setText("Middle Name");

        jmname1.setPreferredSize(new java.awt.Dimension(59, 26));
        jmname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmname1ActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel22.setText("Username");

        juser.setPreferredSize(new java.awt.Dimension(59, 26));
        juser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                juserActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setText("Password");

        jLabel18.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel18.setText("Tree Name");

        jtree.setPreferredSize(new java.awt.Dimension(59, 26));
        jtree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtreeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jtree, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(183, 183, 183)
                        .addComponent(jButtonAddData)
                        .addGap(145, 145, 145))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel23)
                                    .addComponent(jfname1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel19)
                                    .addComponent(jlname1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel20)
                                    .addComponent(jmname1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(juser, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17)
                                    .addComponent(jpass, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(5, 5, 5)
                        .addComponent(jfname1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel19)
                        .addGap(5, 5, 5)
                        .addComponent(jlname1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(5, 5, 5)
                        .addComponent(juser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel17)
                        .addGap(5, 5, 5)
                        .addComponent(jpass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addComponent(jLabel20)
                .addGap(5, 5, 5)
                .addComponent(jmname1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jLabel18)
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAddData, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 510, 250));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 740, 10));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked

        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void juserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_juserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_juserActionPerformed

    private void jtreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtreeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtreeActionPerformed

    private void jfname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jfname1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jfname1ActionPerformed

    private void jlname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jlname1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jlname1ActionPerformed

    private void jmname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmname1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmname1ActionPerformed

    private void jButtonAddDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddDataActionPerformed

    if(verifyFields()){    
        
        PreparedStatement st;
        ResultSet rs;
       
        String firstname = jfname1.getText();
        String lastname = jlname1.getText();
        String middlename = jmname1.getText();
      
        
        
        String query = "SELECT * FROM `ancestor_tb` WHERE `firstname` = ? AND `lastname` = ? AND `middlename` = ?";
        try {
            st = connect.getConnection().prepareStatement(query);
            st.setString(1, firstname);
            st.setString(2, lastname);
            st.setString(3, middlename);
            rs = st.executeQuery();
            if(rs.next())
            {
                
              fname = rs.getString("firstname");
              lname = rs.getString("lastname");
              mname = rs.getString("middlename");
              gender = rs.getString("gender");
              death = rs.getString("death");
              age = rs.getString("age");
              birth = rs.getString("birthdate");
              tempid = rs.getInt("id");
                updater();
                
                
            }else{
                //error message
                JOptionPane.showMessageDialog(null, "Error or Ancestor Not Found");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
           
    }//GEN-LAST:event_jButtonAddDataActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddTree.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddTree.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddTree.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddTree.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddTree().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddData;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jfname1;
    private javax.swing.JTextField jlname1;
    private javax.swing.JTextField jmname1;
    private javax.swing.JPasswordField jpass;
    private javax.swing.JTextField jtree;
    private javax.swing.JTextField juser;
    // End of variables declaration//GEN-END:variables
}

class PasswordEncrypt{
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    
     public static String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }
    public static String generateSecurePassword(String password, String salt) {
        String returnValue = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
 
        returnValue = Base64.getEncoder().encodeToString(securePassword);
 
        return returnValue;
    }
    
    public static boolean verifyUserPassword(String providedPassword,
            String securedPassword, String salt)
    {
        boolean returnValue = false;
        
        // Generate New secure password with the same salt
        String newSecurePassword = generateSecurePassword(providedPassword, salt);
        
        // Check if two passwords are equal
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);
        
        return returnValue;
    }
}


