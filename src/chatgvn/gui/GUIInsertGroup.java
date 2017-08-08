/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.gui;

import chatgvn.utils.HandleApi;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class GUIInsertGroup {

    public static JFrame _MainWindow = new JFrame();
    public JTextField _NameGroup = new JTextField();
    public JTextField _AddName = new JTextField();
    public JTextField _AddID = new JTextField();

    public JButton _JBAddListInvite = new JButton();
    public JButton _JBCreateGroup = new JButton();

    public JLabel _JLNameGroup = new JLabel();
    public JLabel _JLGioiThieu = new JLabel();
    public JLabel _JLabelAddName = new JLabel();
    public JLabel _JLabelAddID = new JLabel();

    public JLabel status = new JLabel();
    public JLabel status2 = new JLabel();
    public JButton _jbCreateGroupxxx;
    JSONObject xxxJSONObjectGroupNow;
    JSONObject xxxtokenlogin;

    public GUIInsertGroup(JButton _par, JSONObject b, JSONObject c) {
        this._jbCreateGroupxxx = _par;
        this.xxxJSONObjectGroupNow = b;
        this.xxxtokenlogin = c;
    }

    public void buildWindowLogin() {
        handlingclose();
        _MainWindow.setTitle("InsertGroupxxx");
        _MainWindow.setSize(370, 300);
        _MainWindow.setResizable(false);
        _MainWindow.setLocationRelativeTo(null);
        configureWindowLogin();
        press();
        _MainWindow.setVisible(true);

    }

    private void press() {
        _JBAddListInvite.addActionListener(new ActionListener() {
             HandleApi handle = new HandleApi();
            @Override
            public void actionPerformed(ActionEvent ae) {

                try {
                    System.out.println("PAR==========");
                    System.out.println(xxxtokenlogin.get("token"));
                    System.out.println(xxxJSONObjectGroupNow.getString("_id"));
                    System.out.println(xxxJSONObjectGroupNow.getString("groupType"));
                                     
//                     JSONObject Input = new JSONObject();
//                     Input.put("auth_token", xxxtokenlogin.get("token").toString());
//                     Input.put("userid",_AddID.getText());
//                     Input.put("username",_AddName.getText());
//                     Input.put("id",xxxJSONObjectGroupNow.getString("_id"));
//                     System.out.println("PAR==========");
//                     System.out.println(Input);
                     
                     
                } catch (JSONException ex) {
                    status.setText("LOI ROI MAY OI");
                }

            }
        });

    }

    private void handlingclose() {
       //   _MainWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        _MainWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {

            }

            public void windowClosing(java.awt.event.WindowEvent evt) {
                _jbCreateGroupxxx.setEnabled(true);

            }

        });

    }

    private void configureWindowLogin() {
        _MainWindow.getContentPane().setLayout(null);
        _MainWindow.getContentPane().setBackground(new java.awt.Color(148, 170, 214));

        //nhap ten tung thang
        _JLGioiThieu.setText("Nhap ten && loginID nguoi muon them:");
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

        // _JBAddListInvite 
        _JBAddListInvite.setText("Them vao Group dang chat");
        _MainWindow.getContentPane().add(_JBAddListInvite);
        _JBAddListInvite.setBounds(100, 150, 200, 20);

        //status
        _MainWindow.getContentPane().add(status);
        status.setForeground(Color.yellow);
        status.setBounds(130, 175, 200, 20);

    }
}
