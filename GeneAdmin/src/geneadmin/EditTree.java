/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package geneadmin;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.*;
import java.util.logging.*;
import javax.swing.border.LineBorder;


/**
 *
 * @author Kaiserin
 */
public class EditTree extends javax.swing.JFrame {
File f = null; 
String path = null;
private ImageIcon format =null;
String imgname = null;
int a = 0; 
byte[] pimage = null; 
PreparedStatement ps;
ResultSet rs;
private static String treetemp;
private int selectedPosition;

    public EditTree() {
        initComponents();
        this.setLocationRelativeTo(null); 
        AutoSelect();
        // ************ Sets all to white
        YouLine1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        YouLine2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));   
        Sibling1Line1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));     
        Sibling2Line1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Sibling2Line2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Sibling3Line1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Sibling3Line2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Sibling4Line1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Sibling4Line2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Sibling5Line1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Sibling5Line2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MotherToChildren.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        MotherToGrandParents.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        FatherToGrandParents.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ParentRelationship.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        GrandParentsConnection21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        GrandParentsConnection22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        GrandParentsConnection.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Grandpa1Line1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Grandpa1Line2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Grandpa2Line1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Grandpa2Line2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Grandpa2Line2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Grandma1Line1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Grandma1Line2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Grandma2Line1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Grandma2Line2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
                
        // *********** Sets Occupied Values with correct line positions
        Position1(); // You
        Position2(); // 2-6 is Siblings
        Position3();
        Position4();
        Position5();
        Position6(); 
        Position7(); // 7 Mother
        Position8(); // 8 Dad
        Position9(); // 9 Grandpa01
        Position10(); // 10 Grandma01
        Position11(); // 11 Grandpa02
        Position12(); // 12 Grandma02
    }
public EditTree(String tre) {
     
    
       PreparedStatement st;
        ResultSet rs;
        String query = "SELECT * FROM `tree_tb` WHERE `tree` = ?";
       
        try {
            st = geneadmin.connect.getConnection().prepareStatement(query);    
            st.setString(1, tre);
            rs = st.executeQuery();              
            if(rs.next()){
                treetemp = tre;
                
            
            }else{
                //error message
                JOptionPane.showMessageDialog(null, "Login Error","Login Error", 2);         
            }    
        } catch (SQLException ex) {
            Logger.getLogger(geneadmin.LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

void AutoSelect(){
    int getid=0;
    String fname="";
    String lname="";
   
    String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 1);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
            selectedPosition = 1;
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    if(getid != -1){
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        byte[] imagedata = rs.getBytes("imgfile");
        format = new ImageIcon(imagedata);
        Image mm = format.getImage();
        Image img2 = mm.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        PictureLabel.setIcon(image);
        TheName.setText(fname+" "+lname);
        }else{
            
        }            
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }  
 
    }
}
       

public boolean Parent1Check()
{
    int getid = -2;
    boolean check = true;
    
    String Parent1CheckQuery = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?";
     try {
        ps = connect.getConnection().prepareStatement(Parent1CheckQuery);
        ps.setInt(1, 7);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
     
    if(getid == -1) { check = false; }
    getid = -2;
   return check;
}
public boolean Grandpa1Check()
{
    int getid = -2;
    boolean check = true;
    
    String Parent1CheckQuery = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?";
     try {
        ps = connect.getConnection().prepareStatement(Parent1CheckQuery);
        ps.setInt(1, 9);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
     
    if(getid == -1) { check = false; }
    getid = -2;
   return check;
}
public boolean Grandpa2Check()
{
    int getid = -2;
    boolean check = true;
    
    String Parent1CheckQuery = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?";
     try {
        ps = connect.getConnection().prepareStatement(Parent1CheckQuery);
        ps.setInt(1, 11);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
     
    if(getid == -1) { check = false; }
    getid = -2;
   return check;
}
public boolean Grandma1Check()
{
    int getid = -2;
    boolean check = true;
    
    String Parent1CheckQuery = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?";
     try {
        ps = connect.getConnection().prepareStatement(Parent1CheckQuery);
        ps.setInt(1, 10);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
     
    if(getid == -1) { check = false; }
    getid = -2;
   return check;
}
public boolean Grandma2Check()
{
    int getid = -2;
    boolean check = true;
    
    String Parent1CheckQuery = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?";
     try {
        ps = connect.getConnection().prepareStatement(Parent1CheckQuery);
        ps.setInt(1, 12);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
     
    if(getid == -1) { check = false; }
    getid = -2;
   return check;
}

public boolean Parent2Check()
{
    int getid = -2;
    boolean check = true;
    
    String Parent1CheckQuery = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?";
     try {
        ps = connect.getConnection().prepareStatement(Parent1CheckQuery);
        ps.setInt(1, 8);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
     
    if(getid == -1) { check = false; }
    getid = -2;
   return check;
}

/*public void loadimage(){
    
    
    try { 
        byte[] imagedata = rs.getBytes("imgfile");
        format = new ImageIcon(imagedata);
        Image mm = format.getImage();
        Image img2 = mm.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        labeldisplay.setIcon(image);
        
    } catch (SQLException ex) {
        Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    }   */


public void Position1(){
int getid=0;
String fname="";
String lname="";
String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 1);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    } 
    /********************************/
    if(getid != -1){
        MainLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        MainLabel.setText(fname+" "+lname);
        }          
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }   
    
        if(Grandpa1Check())
        {
            Grandpa1Line1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            Grandpa1Line2.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.black));
            GrandParentsConnection.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            GrandParentsConnection21.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.black));
            MotherToGrandParents.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            MotherToChildren.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            YouLine2.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
            YouLine1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
        }
        if(Grandpa2Check())
        {
            Grandpa2Line1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            Grandpa2Line2.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.black));
            GrandParentsConnection.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            GrandParentsConnection21.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.black));
            MotherToGrandParents.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            MotherToChildren.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            YouLine2.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
            YouLine1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
        }
        if(Grandma1Check())
        {
            Grandma1Line1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            Grandma1Line2.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.black));
            GrandParentsConnection.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            GrandParentsConnection21.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.black));
            MotherToGrandParents.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            MotherToChildren.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            YouLine2.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
            YouLine1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
           
        }
        if(Grandma2Check())
        {
            Grandma2Line1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            Grandma2Line2.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.black));
            GrandParentsConnection.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            GrandParentsConnection21.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.black));
            MotherToGrandParents.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            MotherToChildren.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            YouLine2.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
            YouLine1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
        }
        
        if(Parent1Check())
        {
            MotherToChildren.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
            YouLine2.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
            YouLine1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
        }
    
    }else
    {
        MainLabel.setText("You");
        MainLabel.setForeground(Color.white);
        MainLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    }
    
}
public void Position2(){
SiblingLabel1.setText("Sibling1");
int getid=0;
String fname="";
String lname="";
String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 2);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){ 
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    } 
    /********************************/
    if(getid != -1){
        System.out.println("Test if working");
        SiblingLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        SiblingLabel1.setText(fname+" "+lname);
        }          
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }   
    
    if(Parent1Check())
        {
            Sibling1Line1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
        }
    
    
    }else
    {
        SiblingLabel1.setText("Sibling1");
        SiblingLabel1.setForeground(Color.white);
        SiblingLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    }
}
public void Position3(){
SiblingLabel2.setText("Sibling2");
int getid=0;
String fname="";
String lname="";
String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 3);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    } 
    /********************************/
    if(getid != -1){
        SiblingLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        SiblingLabel2.setText(fname+" "+lname);
        }          
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }   
    
     if (Parent1Check())
     {
         Sibling2Line1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
         Sibling2Line2.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
     }
    
    }else
    {
        SiblingLabel2.setText("Sibling2");
        SiblingLabel2.setForeground(Color.white);
        SiblingLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    }
}
public void Position4(){
SiblingLabel3.setText("Sibling3");
int getid=0;
String fname="";
String lname="";
String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 4);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    } 
    /********************************/
    if(getid != -1){
        SiblingLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        SiblingLabel3.setText(fname+" "+lname);
        }          
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }   
    if (Parent1Check())
     {
         Sibling3Line1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
         Sibling3Line2.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
     }
    
    }else
    {
        SiblingLabel3.setText("Sibling3");
        SiblingLabel3.setForeground(Color.white);
        SiblingLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    }
}
public void Position5(){
SiblingLabel4.setText("Sibling4");
int getid=0;
String fname="";
String lname="";
String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 5);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    } 
    /********************************/
    if(getid != -1){
        SiblingLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        SiblingLabel4.setText(fname+" "+lname);
        }          
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }   
    if (Parent1Check())
     {
         Sibling4Line1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
         Sibling4Line2.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
     }
    
    }else
    {
        SiblingLabel4.setText("Sibling4");
        SiblingLabel4.setForeground(Color.white);
        SiblingLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    }
}
public void Position6(){
SiblingLabel5.setText("Sibling5");
int getid=0;
String fname="";
String lname="";
String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 6);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    } 
    /********************************/
    if(getid != -1){
        SiblingLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        SiblingLabel5.setText(fname+" "+lname);
        }          
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }   
    if (Parent1Check())
     {
         Sibling5Line1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
         Sibling5Line2.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.black));
     }
    
    }else
    {
        SiblingLabel5.setText("Sibling5");
        SiblingLabel5.setForeground(Color.white);
        SiblingLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    }
}
public void Position7(){
ParentsLabel1.setText("Mother");
int getid=0;
String fname="";
String lname="";
String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 7);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    } 
    /********************************/
    if(getid != -1){
        ParentsLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        ParentsLabel1.setText(fname+" "+lname);
        }          
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }  
    if(Parent2Check())
    {
        
        ParentRelationship.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.black));
    }
    }else
    {
        ParentsLabel1.setText("Mother");
        ParentsLabel1.setForeground(Color.white);
        ParentsLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    }
}
public void Position8(){
ParentsLabel2.setText("Father");
int getid=0;
String fname="";
String lname="";
String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 8);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    } 
    /********************************/
    if(getid != -1){
        ParentsLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        ParentsLabel2.setText(fname+" "+lname);
        }          
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }   
    if(Grandpa1Check() ||  Grandpa2Check() || Grandma1Check() || Grandma2Check())
    {
        GrandParentsConnection22.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.black));
        FatherToGrandParents.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
    }
    
    }else
    {
        ParentsLabel2.setText("Father");
        ParentsLabel2.setForeground(Color.white);
        ParentsLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    }
}
public void Position9(){
GrandpaLabel1.setText("Grandpa1");
int getid=0;
String fname="";
String lname="";
String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 9);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    } 
    /********************************/
    if(getid != -1){
        GrandpaLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        GrandpaLabel1.setText(fname+" "+lname);
        }          
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }   
    }else
    {
        GrandpaLabel1.setText("Grandpa1");
        GrandpaLabel1.setForeground(Color.white);
        GrandpaLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    }
}
public void Position10(){
GranmaLabel1.setText("Grandma1");
int getid=0;
String fname="";
String lname="";
String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 10);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    } 
    /********************************/
    if(getid != -1){
        GranmaLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        GranmaLabel1.setText(fname+" "+lname);
        }          
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }   
    }else
    {
        GranmaLabel1.setText("Grandma1");
        GranmaLabel1.setForeground(Color.white);
        GranmaLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    }
}
public void Position11(){
Grandpa2.setText("Grandpa2");
int getid=0;
String fname="";
String lname="";
String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 11);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    } 
    /********************************/
    if(getid != -1){
        Grandpa2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        Grandpa2.setText(fname+" "+lname);
        }          
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }   
    }else
    {
        Grandpa2.setText("Grandpa2");
        Grandpa2.setForeground(Color.white);
        Grandpa2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    }
}
public void Position12(){
Granma2.setText("Grandpa2");
int getid=0;
String fname="";
String lname="";
String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 12);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    } 
    /********************************/
    if(getid != -1){
        Granma2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        Granma2.setText(fname+" "+lname);
        }          
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }   
    }else
    {
        Granma2.setText("Grandma2");
        Granma2.setForeground(Color.white);
        Granma2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        firstnametext = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jButton4 = new javax.swing.JButton();
        lastnametext = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jrelation = new javax.swing.JTextField();
        savebutton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        TheName = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        MainLabel = new javax.swing.JLabel();
        SiblingLabel1 = new javax.swing.JLabel();
        SiblingLabel2 = new javax.swing.JLabel();
        SiblingLabel3 = new javax.swing.JLabel();
        SiblingLabel4 = new javax.swing.JLabel();
        SiblingLabel5 = new javax.swing.JLabel();
        ParentsLabel1 = new javax.swing.JLabel();
        ParentsLabel2 = new javax.swing.JLabel();
        GrandpaLabel1 = new javax.swing.JLabel();
        GranmaLabel1 = new javax.swing.JLabel();
        Granma2 = new javax.swing.JLabel();
        Grandpa2 = new javax.swing.JLabel();
        Sibling1Line1 = new javax.swing.JLabel();
        Sibling2Line1 = new javax.swing.JLabel();
        Sibling3Line1 = new javax.swing.JLabel();
        Sibling4Line1 = new javax.swing.JLabel();
        Sibling5Line1 = new javax.swing.JLabel();
        YouLine1 = new javax.swing.JLabel();
        FatherToGrandParents = new javax.swing.JLabel();
        MotherToChildren = new javax.swing.JLabel();
        Grandma2Line1 = new javax.swing.JLabel();
        ParentRelationship = new javax.swing.JLabel();
        YouLine2 = new javax.swing.JLabel();
        Sibling4Line2 = new javax.swing.JLabel();
        Sibling2Line2 = new javax.swing.JLabel();
        Sibling3Line2 = new javax.swing.JLabel();
        Sibling5Line2 = new javax.swing.JLabel();
        MotherToGrandParents = new javax.swing.JLabel();
        Grandpa1Line1 = new javax.swing.JLabel();
        Grandpa2Line2 = new javax.swing.JLabel();
        Grandpa2Line1 = new javax.swing.JLabel();
        GrandParentsConnection = new javax.swing.JLabel();
        GrandParentsConnection22 = new javax.swing.JLabel();
        Grandma1Line1 = new javax.swing.JLabel();
        Grandma1Line2 = new javax.swing.JLabel();
        Grandma2Line2 = new javax.swing.JLabel();
        GrandParentsConnection21 = new javax.swing.JLabel();
        Grandpa1Line2 = new javax.swing.JLabel();
        PictureLabel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jfamilytype = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1200, 550));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(153, 255, 153));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel2.setText("EDIT VISUAL TREE ANCESTOR ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 153, 255));
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1172, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1500, 50));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, -1, -1));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Last Name");

        firstnametext.setToolTipText("");
        firstnametext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstnametextActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("RELATIONSHIP TYPE");

        jButton4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/geneadmin/icons8-update-32.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        lastnametext.setToolTipText("");
        lastnametext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastnametextActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setText("Relationship");

        jrelation.setToolTipText("");
        jrelation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrelationActionPerformed(evt);
            }
        });

        savebutton.setText("Add");
        savebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebuttonActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Currently Selected:");

        TheName.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        TheName.setText("You");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainLabel.setText("You");
        MainLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        MainLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MainLabelMouseClicked(evt);
            }
        });
        jPanel4.add(MainLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, 125, -1));

        SiblingLabel1.setText("Sibling1");
        SiblingLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SiblingLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SiblingLabel1MouseClicked(evt);
            }
        });
        jPanel4.add(SiblingLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 430, 125, -1));

        SiblingLabel2.setText("Sibling2");
        SiblingLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SiblingLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SiblingLabel2MouseClicked(evt);
            }
        });
        jPanel4.add(SiblingLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 430, 125, -1));

        SiblingLabel3.setText("Sibling3");
        SiblingLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SiblingLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SiblingLabel3MouseClicked(evt);
            }
        });
        jPanel4.add(SiblingLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 430, 125, -1));

        SiblingLabel4.setText("Sibling4");
        SiblingLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SiblingLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SiblingLabel4MouseClicked(evt);
            }
        });
        jPanel4.add(SiblingLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 430, 125, -1));

        SiblingLabel5.setText("Sibling5");
        SiblingLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SiblingLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SiblingLabel5MouseClicked(evt);
            }
        });
        jPanel4.add(SiblingLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 430, 125, -1));

        ParentsLabel1.setText("Mother");
        ParentsLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ParentsLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ParentsLabel1MouseClicked(evt);
            }
        });
        jPanel4.add(ParentsLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 280, 125, -1));

        ParentsLabel2.setText("Father");
        ParentsLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ParentsLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ParentsLabel2MouseClicked(evt);
            }
        });
        jPanel4.add(ParentsLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 280, 125, -1));

        GrandpaLabel1.setText("Granpa1");
        GrandpaLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        GrandpaLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GrandpaLabel1MouseClicked(evt);
            }
        });
        jPanel4.add(GrandpaLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 125, -1));

        GranmaLabel1.setText("Grandma1");
        GranmaLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        GranmaLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                GranmaLabel1MousePressed(evt);
            }
        });
        jPanel4.add(GranmaLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 125, -1));

        Granma2.setText("Grandma2");
        Granma2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Granma2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Granma2MouseClicked(evt);
            }
        });
        jPanel4.add(Granma2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 100, 125, -1));

        Grandpa2.setText("Grandpa2");
        Grandpa2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Grandpa2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Grandpa2MouseClicked(evt);
            }
        });
        jPanel4.add(Grandpa2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, 125, -1));

        Sibling1Line1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Sibling1Line1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 380, 70, 50));

        Sibling2Line1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Sibling2Line1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 380, 70, 50));

        Sibling3Line1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Sibling3Line1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 380, 70, 50));

        Sibling4Line1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Sibling4Line1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 380, 70, 50));

        Sibling5Line1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Sibling5Line1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 380, 70, 50));

        YouLine1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.add(YouLine1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, 70, 50));

        FatherToGrandParents.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.add(FatherToGrandParents, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 210, 70, 70));

        MotherToChildren.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.add(MotherToChildren, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 300, 70, 80));

        Grandma2Line1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Grandma2Line1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 120, 70, 40));

        ParentRelationship.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0, 0, 0)));
        jPanel4.add(ParentRelationship, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 240, 60, 50));

        YouLine2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel4.add(YouLine2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, 230, 50));

        Sibling4Line2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Sibling4Line2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 330, 340, 50));

        Sibling2Line2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Sibling2Line2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 330, 60, 50));

        Sibling3Line2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Sibling3Line2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 330, 200, 50));

        Sibling5Line2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Sibling5Line2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 330, 480, 50));

        MotherToGrandParents.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.add(MotherToGrandParents, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 70, 70));

        Grandpa1Line1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Grandpa1Line1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 70, 40));

        Grandpa2Line2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Grandpa2Line2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 100, 40));

        Grandpa2Line1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Grandpa2Line1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 70, 40));

        GrandParentsConnection.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.add(GrandParentsConnection, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 160, 70, 50));

        GrandParentsConnection22.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        jPanel4.add(GrandParentsConnection22, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 210, 100, 40));

        Grandma1Line1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Grandma1Line1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, 70, 40));

        Grandma1Line2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Grandma1Line2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 110, 40));

        Grandma2Line2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Grandma2Line2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 280, 40));

        GrandParentsConnection21.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        jPanel4.add(GrandParentsConnection21, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, 110, 40));

        Grandpa1Line2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));
        jPanel4.add(Grandpa1Line2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, 240, 40));

        PictureLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setText("First Name");

        jButton1.setText("Delete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setText("Family Type");

        jButton2.setText("View Ancestor");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel25.setText("Add Family");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 989, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TheName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(33, 33, 33))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jfamilytype, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                                            .addComponent(jrelation, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lastnametext, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(firstnametext, javax.swing.GroupLayout.Alignment.LEADING))))
                                .addContainerGap(71, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(156, 156, 156)
                                .addComponent(savebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(115, 115, 115)
                                .addComponent(PictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 1500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TheName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(firstnametext, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lastnametext, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jrelation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jfamilytype, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(savebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(466, 466, 466)
                .addComponent(jLabel11)
                .addGap(737, 737, 737)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(191, 191, 191)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 1039, Short.MAX_VALUE)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 1039, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1480, 660));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        ManageTree ManageTree =new ManageTree();
        ManageTree.setVisible(true);
        ManageTree.pack();
        ManageTree.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void lastnametextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastnametextActionPerformed
       
    }//GEN-LAST:event_lastnametextActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        EditTree EditTree =new EditTree();
        EditTree.setVisible(true);
        EditTree.pack();
        EditTree.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void firstnametextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstnametextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstnametextActionPerformed

    private void jrelationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrelationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrelationActionPerformed

    private void savebuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebuttonActionPerformed
        int getid = -5;
        boolean canPick = true;
        String firstname = firstnametext.getText();
        String lastname = lastnametext.getText();
        String relationship = jrelation.getText();
            String familytype = jfamilytype.getText();
        int positionset = -5;
        boolean valid = true;
        if(familytype.equals("Grandma")|| familytype.equals("Grandpa")|| familytype.equals("Father")|| familytype.equals("Mother")|| familytype.equals("Sibling"))
        {
            if(familytype.equals("Sibling"))
        {
            if(SiblingLabel1.getText().equals("Sibling1") && canPick) { positionset = 2; canPick = false;}
            if(SiblingLabel2.getText().equals("Sibling2") && canPick) { positionset = 3; canPick = false;}
            if(SiblingLabel3.getText().equals("Sibling3") && canPick) { positionset = 4; canPick = false;}
            if(SiblingLabel4.getText().equals("Sibling4") && canPick) { positionset = 5; canPick = false;}
            if(SiblingLabel5.getText().equals("Sibling5") && canPick) { positionset = 6; canPick = false;}
            
            canPick = true;
            
        }
        if(familytype.equals("Mother"))
        {
            if(ParentsLabel1.getText().equals("Mother")) { positionset = 7;  }
            if(!ParentsLabel1.getText().equals("Mother")) { positionset = -5; JOptionPane.showMessageDialog(null, "Family Type Occupied");}
        }
        
        if(familytype.equals("Father"))
        {
            if(ParentsLabel2.getText().equals("Father")) { positionset = 8; }
            if(!ParentsLabel2.getText().equals("Father")) { positionset = -5; JOptionPane.showMessageDialog(null, "Family Type Occupied");}
        }
        
        if(familytype.equals("Grandpa"))
        {
            if(GrandpaLabel1.getText().equals("Grandpa1") && canPick) { positionset = 9; canPick = false;}
            if(Grandpa2.getText().equals("Grandpa2") && canPick) { positionset = 11; canPick = false;}
            
            canPick = true;
            
        }
        if(familytype.equals("Grandma"))
        {
            if(GranmaLabel1.getText().equals("Grandma1") && canPick) { positionset = 10; canPick = false;}
            if(Granma2.getText().equals("Grandma2") && canPick) { positionset = 12; canPick = false;}
            
            canPick = true;
            
        }
        }else  
        {
            JOptionPane.showMessageDialog(null, "Family Type Incorrect");
        }
  
         //Break 
        
        
        if(positionset != -5)
        {
            
            String query = "SELECT * FROM `ancestor_tb` WHERE `firstname` = ? AND `lastname` = ?";
        try {
            ps = connect.getConnection().prepareStatement(query);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            rs = ps.executeQuery();
            if(rs.next())
            {
                getid = rs.getInt("id");
                JOptionPane.showMessageDialog(null, "Ancestor found");
                valid = true;
                
            }else{      
                JOptionPane.showMessageDialog(null, "Error or Ancestor Not Found");
                valid = false;
                positionset = -5;
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (valid)
        {
            System.out.println("Position is: "+ positionset);
            System.out.println("Ancestor ID is: "+getid);
          
                    
           String queryfind = "UPDATE tree_tb SET anc_id = ? , relationship = ? WHERE position = ? AND tree = ?";
            try{
            ps = connect.getConnection().prepareStatement(queryfind);
            ps.setInt(1,getid);
            ps.setString(2, relationship);
            ps.setInt(3,positionset);
            ps.setString(4, treetemp);
            ps.executeUpdate();
            
            
            }catch (SQLException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
           
        
        //end
        }
             
        EditTree EditTree =new EditTree();
        EditTree.setVisible(true);
        EditTree.pack();
        EditTree.setLocationRelativeTo(null);
        this.dispose();
        
        
        
        
    }//GEN-LAST:event_savebuttonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        
        //Seeting the specific value to -1 = Delete
        if(selectedPosition != 1)
        {
            String queryfind = "UPDATE tree_tb SET anc_id = ? , relationship = ? WHERE position = ? AND tree = ?";
            try{
            ps = connect.getConnection().prepareStatement(queryfind);
            ps.setInt(1,-1);
            ps.setString(2, "");
            ps.setInt(3,selectedPosition);
            ps.setString(4, treetemp);
            ps.executeUpdate();
            
            EditTree EditTree =new EditTree();
            EditTree.setVisible(true);
            EditTree.pack();
            EditTree.setLocationRelativeTo(null);
            this.dispose();
            
            
            }catch (SQLException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void MainLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainLabelMouseClicked
       
        //if(MainLabel.getIcon() != null)
        //{
        //   
        //}
        
    int getid=0;
    String fname="";
    String lname="";
   
    String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 1);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
            selectedPosition = 1;
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    if(getid != -1){
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        byte[] imagedata = rs.getBytes("imgfile");
        format = new ImageIcon(imagedata);
        Image mm = format.getImage();
        Image img2 = mm.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        PictureLabel.setIcon(image);
        TheName.setText(fname+" "+lname);
        }else{
            
        }            
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }  
 
    }
        
        
        
    }//GEN-LAST:event_MainLabelMouseClicked

    private void SiblingLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SiblingLabel1MouseClicked
        
        int getid=0;
    String fname="";
    String lname="";
   
    String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 2);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
            selectedPosition = 2;
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    if(getid != -1){
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        byte[] imagedata = rs.getBytes("imgfile");
        format = new ImageIcon(imagedata);
        Image mm = format.getImage();
        Image img2 = mm.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        PictureLabel.setIcon(image);
        TheName.setText(fname+" "+lname);
        }else{
            
        }            
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }  
 
    }
    }//GEN-LAST:event_SiblingLabel1MouseClicked

    private void SiblingLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SiblingLabel2MouseClicked
        
        int getid=0;
    String fname="";
    String lname="";
   
    String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 3);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
            selectedPosition = 3;
            System.out.println("Position Right now: "+selectedPosition);
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    if(getid != -1){
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        byte[] imagedata = rs.getBytes("imgfile");
        format = new ImageIcon(imagedata);
        Image mm = format.getImage();
        Image img2 = mm.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        PictureLabel.setIcon(image);
        TheName.setText(fname+" "+lname);
        }else{
            
        }            
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }  
 
    }
        
        
        
    }//GEN-LAST:event_SiblingLabel2MouseClicked

    private void SiblingLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SiblingLabel3MouseClicked
        int getid=0;
    String fname="";
    String lname="";
   
    String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 4);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
            selectedPosition = 3;
            System.out.println("Position Right now: "+selectedPosition);
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    if(getid != -1){
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        byte[] imagedata = rs.getBytes("imgfile");
        format = new ImageIcon(imagedata);
        Image mm = format.getImage();
        Image img2 = mm.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        PictureLabel.setIcon(image);
        TheName.setText(fname+" "+lname);
        }else{
            
        }            
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }  
 
    }
    }//GEN-LAST:event_SiblingLabel3MouseClicked

    private void SiblingLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SiblingLabel4MouseClicked
        int getid=0;
    String fname="";
    String lname="";
   
    String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 5);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
            selectedPosition = 3;
            System.out.println("Position Right now: "+selectedPosition);
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    if(getid != -1){
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        byte[] imagedata = rs.getBytes("imgfile");
        format = new ImageIcon(imagedata);
        Image mm = format.getImage();
        Image img2 = mm.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        PictureLabel.setIcon(image);
        TheName.setText(fname+" "+lname);
        }else{
            
        }            
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }  
 
    }
    }//GEN-LAST:event_SiblingLabel4MouseClicked

    private void SiblingLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SiblingLabel5MouseClicked
        int getid=0;
    String fname="";
    String lname="";
   
    String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 6);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
            selectedPosition = 3;
            System.out.println("Position Right now: "+selectedPosition);
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    if(getid != -1){
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        byte[] imagedata = rs.getBytes("imgfile");
        format = new ImageIcon(imagedata);
        Image mm = format.getImage();
        Image img2 = mm.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        PictureLabel.setIcon(image);
        TheName.setText(fname+" "+lname);
        }else{
            
        }            
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }  
 
    }
    }//GEN-LAST:event_SiblingLabel5MouseClicked

    private void ParentsLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ParentsLabel1MouseClicked
        int getid=0;
    String fname="";
    String lname="";
   
    String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 7);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
            selectedPosition = 3;
            System.out.println("Position Right now: "+selectedPosition);
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    if(getid != -1){
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        byte[] imagedata = rs.getBytes("imgfile");
        format = new ImageIcon(imagedata);
        Image mm = format.getImage();
        Image img2 = mm.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        PictureLabel.setIcon(image);
        TheName.setText(fname+" "+lname);
        }else{
            
        }            
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }  
 
    }
    }//GEN-LAST:event_ParentsLabel1MouseClicked

    private void ParentsLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ParentsLabel2MouseClicked
        int getid=0;
    String fname="";
    String lname="";
   
    String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 8);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
            selectedPosition = 3;
            System.out.println("Position Right now: "+selectedPosition);
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    if(getid != -1){
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        byte[] imagedata = rs.getBytes("imgfile");
        format = new ImageIcon(imagedata);
        Image mm = format.getImage();
        Image img2 = mm.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        PictureLabel.setIcon(image);
        TheName.setText(fname+" "+lname);
        }else{
            
        }            
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }  
 
    }
    }//GEN-LAST:event_ParentsLabel2MouseClicked

    private void GrandpaLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GrandpaLabel1MouseClicked
        int getid=0;
    String fname="";
    String lname="";
   
    String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 9);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
            selectedPosition = 3;
            System.out.println("Position Right now: "+selectedPosition);
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    if(getid != -1){
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        byte[] imagedata = rs.getBytes("imgfile");
        format = new ImageIcon(imagedata);
        Image mm = format.getImage();
        Image img2 = mm.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        PictureLabel.setIcon(image);
        TheName.setText(fname+" "+lname);
        }else{
            
        }            
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }  
 
    }
    }//GEN-LAST:event_GrandpaLabel1MouseClicked

    private void GranmaLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GranmaLabel1MousePressed
       int getid=0;
    String fname="";
    String lname="";
   
    String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 10);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
            selectedPosition = 3;
            System.out.println("Position Right now: "+selectedPosition);
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    if(getid != -1){
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        byte[] imagedata = rs.getBytes("imgfile");
        format = new ImageIcon(imagedata);
        Image mm = format.getImage();
        Image img2 = mm.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        PictureLabel.setIcon(image);
        TheName.setText(fname+" "+lname);
        }else{
            
        }            
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }  
 
    }
    }//GEN-LAST:event_GranmaLabel1MousePressed

    private void Granma2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Granma2MouseClicked
        int getid=0;
    String fname="";
    String lname="";
   
    String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 12);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
            selectedPosition = 3;
            System.out.println("Position Right now: "+selectedPosition);
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    if(getid != -1){
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        byte[] imagedata = rs.getBytes("imgfile");
        format = new ImageIcon(imagedata);
        Image mm = format.getImage();
        Image img2 = mm.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        PictureLabel.setIcon(image);
        TheName.setText(fname+" "+lname);
        }else{
            
        }            
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }  
 
    }
    }//GEN-LAST:event_Granma2MouseClicked

    private void Grandpa2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Grandpa2MouseClicked
        int getid=0;
    String fname="";
    String lname="";
   
    String querysearch = "SELECT * FROM tree_tb WHERE position = ? AND tree = ?"; 
    try {
        ps = connect.getConnection().prepareStatement(querysearch);
        ps.setInt(1, 11);
        ps.setString(2, treetemp);
        rs = ps.executeQuery();
        
        if(rs.next()){
            getid = rs.getInt("anc_id");
            selectedPosition = 3;
            System.out.println("Position Right now: "+selectedPosition);
        }else{
            JOptionPane.showMessageDialog(this, "Error");
        }      
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    if(getid != -1){
        String queryimg2 = "SELECT * FROM ancestor_tb WHERE id = ?";
    try {
        ps = connect.getConnection().prepareStatement(queryimg2);
        ps.setInt(1, getid);
        rs = ps.executeQuery();   
        if(rs.next()){
            System.out.println(getid);
        fname = rs.getString("firstname");
        lname = rs.getString("lastname");
        byte[] imagedata = rs.getBytes("imgfile");
        format = new ImageIcon(imagedata);
        Image mm = format.getImage();
        Image img2 = mm.getScaledInstance(200,200, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        PictureLabel.setIcon(image);
        TheName.setText(fname+" "+lname);
        }else{
            
        }            
    } catch (SQLException ex) {
        Logger.getLogger(EditTree.class.getName()).log(Level.SEVERE, null, ex);
    }  
 
    }
    }//GEN-LAST:event_Grandpa2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(EditTree.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditTree.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditTree.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditTree.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditTree().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel FatherToGrandParents;
    private javax.swing.JLabel GrandParentsConnection;
    private javax.swing.JLabel GrandParentsConnection21;
    private javax.swing.JLabel GrandParentsConnection22;
    private javax.swing.JLabel Grandma1Line1;
    private javax.swing.JLabel Grandma1Line2;
    private javax.swing.JLabel Grandma2Line1;
    private javax.swing.JLabel Grandma2Line2;
    private javax.swing.JLabel Grandpa1Line1;
    private javax.swing.JLabel Grandpa1Line2;
    private javax.swing.JLabel Grandpa2;
    private javax.swing.JLabel Grandpa2Line1;
    private javax.swing.JLabel Grandpa2Line2;
    private javax.swing.JLabel GrandpaLabel1;
    private javax.swing.JLabel Granma2;
    private javax.swing.JLabel GranmaLabel1;
    private javax.swing.JLabel MainLabel;
    private javax.swing.JLabel MotherToChildren;
    private javax.swing.JLabel MotherToGrandParents;
    private javax.swing.JLabel ParentRelationship;
    private javax.swing.JLabel ParentsLabel1;
    private javax.swing.JLabel ParentsLabel2;
    private javax.swing.JLabel PictureLabel;
    private javax.swing.JLabel Sibling1Line1;
    private javax.swing.JLabel Sibling2Line1;
    private javax.swing.JLabel Sibling2Line2;
    private javax.swing.JLabel Sibling3Line1;
    private javax.swing.JLabel Sibling3Line2;
    private javax.swing.JLabel Sibling4Line1;
    private javax.swing.JLabel Sibling4Line2;
    private javax.swing.JLabel Sibling5Line1;
    private javax.swing.JLabel Sibling5Line2;
    private javax.swing.JLabel SiblingLabel1;
    private javax.swing.JLabel SiblingLabel2;
    private javax.swing.JLabel SiblingLabel3;
    private javax.swing.JLabel SiblingLabel4;
    private javax.swing.JLabel SiblingLabel5;
    private javax.swing.JLabel TheName;
    private javax.swing.JLabel YouLine1;
    private javax.swing.JLabel YouLine2;
    private javax.swing.JTextField firstnametext;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTextField jfamilytype;
    private javax.swing.JTextField jrelation;
    private javax.swing.JTextField lastnametext;
    private javax.swing.JButton savebutton;
    // End of variables declaration//GEN-END:variables
}
