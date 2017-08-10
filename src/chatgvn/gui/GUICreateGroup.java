/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.gui;

import static chatgvn.utils.ApiProject.API_requestchat_POST;
import chatgvn.utils.HandleApi;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
public class GUICreateGroup {

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

    public static JLabel status = new JLabel();
    public static JLabel status2 = new JLabel();

    public JButton _jbCreateGroupxxx;
    private JSONArray ArrayListInvite = new JSONArray();
    private JSONObject tokenlogin = new JSONObject();
    private JSONObject jsonCheckUser = new JSONObject();
    private JSONObject mine = new JSONObject();

    public GUICreateGroup() {
    }

    public GUICreateGroup(JButton a, JSONObject b, JSONObject c) {
        this._jbCreateGroupxxx = a;
        this.jsonCheckUser = b;
        this.tokenlogin = c;

        try {
            mine.put("name", jsonCheckUser.getString("userName"));
            mine.put("id", jsonCheckUser.getString("loginId"));
            mine.put("status", "true");
            ArrayListInvite.put(mine);
        } catch (JSONException ex) {
            System.out.println("ERROR GUICreateGroup");
        }

    }

    public void buildWindowLogin() {
        handlingclose();
        _MainWindow.setTitle("CreateGroupxxx");
        _MainWindow.setSize(370, 300);
        _MainWindow.setResizable(false);
        _MainWindow.setLocationRelativeTo(null);
        configureWindowLogin();
        press();
        _MainWindow.setVisible(true);

    }

    private void press() {
        _JBAddListInvite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JSONObject Object = new JSONObject();
                try {
                    if (_AddName.getText().equals("") || _AddID.getText().equals("")) {
                        throw new Exception("input error kien!!!!");
                    }
                    Object.put("name", _AddName.getText());
                    Object.put("id", _AddID.getText());
                    Object.put("status", "true");
                    ArrayListInvite.put(Object);
                    _AddName.setText("");
                    _AddID.setText("");
                    status.setText("success!!! +" + ArrayListInvite.length());

                } catch (JSONException ex) {
                    System.out.println("_JBAddListInvite ERROR");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    status.setText(e.getMessage());

                }

            }
        });

        _JBCreateGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                System.out.println("=============");
                JSONObject InputJson = new JSONObject();
                JSONArray _AraygroupCreator = new JSONArray();

                try {
                     if (_NameGroup.getText().equals("")) {
                        throw new Exception("input error kien!!!!");
                    }
                    InputJson.put("groupName", _NameGroup.getText());
                    InputJson.put("groupType", "1");
                    InputJson.put("groupMember", ArrayListInvite);
                    _AraygroupCreator.put(mine);
                    InputJson.put("groupCreator", _AraygroupCreator);
                    InputJson.put("auth_token", tokenlogin.getString("token"));

                    HandleApi handle = new HandleApi();
                    String StrJsonGroup = handle.postApi(API_requestchat_POST, InputJson.toString());
                    System.out.println("======aaaaaa=======================");
                    System.out.println(StrJsonGroup);
                    JSONObject kq = new JSONObject(StrJsonGroup);
                    if(kq.getString("message").equals("Request success")){
                          status2.setText("Thành Công rồi đới !!!!!!!!");
                          GUIChat.CheckGroupNow();
                    }
                    System.out.println("========aaaaaa===============================");

                } catch (JSONException ex) {
                    System.out.println("JSON _JBCreateGroup ERROOR");
                    status2.setText("Thất bại !!!!!!!!");
                } catch (Exception ex) {
                    System.out.println("JSON _JBCreateGroup ERROOR HAnd API");
                    status2.setText("Thất bại !!!!!!!!");
                }

                //reset lai ArrayListInvite
                ArrayListInvite = new JSONArray();
                ArrayListInvite.put(mine);

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

                //  System.exit(0);
            }

        });

    }

    private void configureWindowLogin() {
        _MainWindow.getContentPane().setLayout(null);
        _MainWindow.getContentPane().setBackground(new java.awt.Color(148, 170, 214));

        //nhap ten group
        _JLNameGroup.setText("Name Group");
        _MainWindow.getContentPane().add(_JLNameGroup);
        _JLNameGroup.setBounds(50, 10, 100, 20);

        _MainWindow.getContentPane().add(_NameGroup);
        _NameGroup.setBounds(150, 10, 150, 20);

        //nhap ten tung thang
        _JLGioiThieu.setText("Nhap ten && loginID nguoi muon them:");
        _JLGioiThieu.setForeground(Color.yellow);
        _MainWindow.getContentPane().add(_JLGioiThieu);
        _JLGioiThieu.setBounds(50, 50, 300, 20);
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
        _JBAddListInvite.setText("Them vao List add group");
        _MainWindow.getContentPane().add(_JBAddListInvite);
        _JBAddListInvite.setBounds(100, 150, 200, 20);

        //status
        status.setText("Thành công hoặc ko!!!!!!!!");
        _MainWindow.getContentPane().add(status);
        status.setForeground(Color.yellow);
        status.setBounds(130, 175, 200, 20);

        //_JBCreateGroup
        _JBCreateGroup.setText("Create Group");
        _MainWindow.getContentPane().add(_JBCreateGroup);
        _JBCreateGroup.setForeground(Color.red);
        _JBCreateGroup.setBounds(130, 200, 150, 20);

        //status2
        status2.setText("!!!!!!!!");
        _MainWindow.getContentPane().add(status2);
        status2.setForeground(Color.red);
        status2.setBounds(130, 230, 150, 20);
    }

}
