/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class GUIaddFriend {

    public static JFrame _MainWindow = new JFrame();
    public JTextField _NameGroup = new JTextField();
    public JTextField _AddName = new JTextField();
    public JTextField _AddID = new JTextField();

    public static JButton _JBAddFriend = new JButton();
    public JButton _JBCreateGroup = new JButton();

    public JLabel _JLNameGroup = new JLabel();
    public JLabel _JLGioiThieu = new JLabel();
    public JLabel _JLabelAddName = new JLabel();
    public JLabel _JLabelAddID = new JLabel();

    public JLabel status = new JLabel();

    public void buildWindowLogin() {
        _MainWindow.setTitle("Add a Friend");
        _MainWindow.setSize(370, 300);
        _MainWindow.setResizable(false);
        _MainWindow.setLocationRelativeTo(null);
        configureWindowLogin();
        press();
        _MainWindow.setVisible(true);

    }
    
    
     private void press() {
         _JBAddFriend.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent ae) {
                 
                 System.out.println("zzzzzzzzzzzz");
                 
             }
         });
        
    }

    private void configureWindowLogin() {
        _MainWindow.getContentPane().setLayout(null);
        _MainWindow.getContentPane().setBackground(new java.awt.Color(148, 170, 214));

        _JLGioiThieu.setText("Nhap ten && loginID:");

        _JLGioiThieu.setForeground(Color.yellow);
        _MainWindow.getContentPane().add(_JLGioiThieu);
        _JLGioiThieu.setBounds(50, 40, 300, 20);
        ////
        _JLabelAddName.setText("Ten:");
        _MainWindow.getContentPane().add(_JLabelAddName);
        _JLabelAddName.setBounds(50, 70, 50, 20);
        _MainWindow.getContentPane().add(_AddName);
        _AddName.setBounds(150, 70, 150, 20);

        _JLabelAddID.setText("ID:");
        _MainWindow.getContentPane().add(_JLabelAddID);
        _JLabelAddID.setBounds(50, 100, 50, 20);
        _MainWindow.getContentPane().add(_AddID);
        _AddID.setBounds(150, 100, 150, 20);

        // _JBAddFriend 
        _JBAddFriend.setText("Add Friend");
        _MainWindow.getContentPane().add(_JBAddFriend);
        _JBAddFriend.setBounds(100, 150, 200, 20);

        //status
        _MainWindow.getContentPane().add(status);
        status.setForeground(Color.yellow);
        status.setBounds(130, 175, 200, 20);

    }

   
}
