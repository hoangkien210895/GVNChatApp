/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Phan Huy
 */
public class GUIRegister {

    public JFrame _jfWindowRegister = new JFrame();

    private JLabel _jlUserName = new JLabel();
    private JLabel _jlPassWord = new JLabel();
    private JLabel _jlEmail = new JLabel();
    private JLabel _jlPhoneNumber = new JLabel();
    private JLabel _jlQuestionSecret = new JLabel();

    public JLabel _jlTextWaring = new JLabel();

    public JTextField _jtfUserName = new JTextField();
    public JPasswordField _jtfPassWord = new JPasswordField();
    public JTextField _jtfEmail = new JTextField();
    public JTextField _jtfPhoneNumber = new JTextField();
    public JTextField _jtfQuestionSecret = new JTextField();

    public JButton _jbRegister = new JButton();
    public JButton _jbBACK = new JButton();

    public void buildWindowRegister() {
        handlingclose();
        _jfWindowRegister.setTitle("Register");
        _jfWindowRegister.setSize(300, 280);
        _jfWindowRegister.setResizable(false);
        _jfWindowRegister.setLocationRelativeTo(null);
        configureWindowLogin();
        _jfWindowRegister.setVisible(true);
    }

    private void handlingclose() {
        _jfWindowRegister.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        _jfWindowRegister.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                System.exit(0);
            }

        });
    }

    public void configureWindowLogin() {
        _jfWindowRegister.setBackground(new java.awt.Color(255, 255, 255));
        _jfWindowRegister.getContentPane().setLayout(null);

        _jlUserName.setText("UserName");
        _jfWindowRegister.getContentPane().add(_jlUserName);
        _jlUserName.setBounds(15, 20, 65, 20);

        _jlPassWord.setText("PassWord");
        _jfWindowRegister.getContentPane().add(_jlPassWord);
        _jlPassWord.setBounds(15, 50, 65, 20);

        _jlEmail.setText("Email");
        _jfWindowRegister.getContentPane().add(_jlEmail);
        _jlEmail.setBounds(15, 80, 65, 20);

        _jlPhoneNumber.setText("Phone");
        _jfWindowRegister.getContentPane().add(_jlPhoneNumber);
        _jlPhoneNumber.setBounds(15, 110, 65, 20);

        _jlQuestionSecret.setText("What was the name of your first pet ? ");
        _jfWindowRegister.getContentPane().add(_jlQuestionSecret);
        _jlQuestionSecret.setBounds(55, 140, 250, 20);

        _jtfUserName.setForeground(new java.awt.Color(0, 0, 255));
        _jtfUserName.requestFocus();
        _jfWindowRegister.getContentPane().add(_jtfUserName);
        _jtfUserName.setBounds(85, 20, 150, 20);

        _jtfPassWord.setForeground(new java.awt.Color(0, 0, 255));
        _jtfPassWord.requestFocus();
        _jfWindowRegister.getContentPane().add(_jtfPassWord);
        _jtfPassWord.setBounds(85, 50, 150, 20);

        _jtfEmail.setForeground(new java.awt.Color(0, 0, 255));
        _jtfEmail.requestFocus();
        _jfWindowRegister.getContentPane().add(_jtfEmail);
        _jtfEmail.setBounds(85, 80, 150, 20);

        _jtfPhoneNumber.setForeground(new java.awt.Color(0, 0, 255));
        _jtfPhoneNumber.requestFocus();
        _jfWindowRegister.getContentPane().add(_jtfPhoneNumber);
        _jtfPhoneNumber.setBounds(85, 110, 150, 20);

        _jtfQuestionSecret.setForeground(new java.awt.Color(0, 0, 255));
        _jtfQuestionSecret.requestFocus();
        _jfWindowRegister.getContentPane().add(_jtfQuestionSecret);
        _jtfQuestionSecret.setBounds(85, 165, 150, 20);

        _jbRegister.setText("Register");
        _jfWindowRegister.getContentPane().add(_jbRegister);
        _jbRegister.setBounds(100, 200, 90, 25);

        _jbBACK.setText("BACK");
        _jfWindowRegister.getContentPane().add(_jbBACK);
        _jbBACK.setBounds(100, 200, 90, 25);
        _jbBACK.setVisible(false);

        _jlTextWaring.setText("You mustn't leave space or email not validate ");
        _jlTextWaring.setForeground(new java.awt.Color(255, 0, 0));
        _jfWindowRegister.getContentPane().add(_jlTextWaring);
        _jlTextWaring.setBounds(25, 230, 250, 20);
        _jlTextWaring.setVisible(false);

    }
}
