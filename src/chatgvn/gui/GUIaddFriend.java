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
public class GUIaddFriend {

    public static JFrame _MainWindow = new JFrame();
    public JTextField _NameGroup = new JTextField();
    public static JTextField _AddName = new JTextField();
    public static JTextField _AddID = new JTextField();

    public static JButton _JBAddFriend = new JButton();
    public JButton _JBCreateGroup = new JButton();

    public JLabel _JLNameGroup = new JLabel();
    public JLabel _JLGioiThieu = new JLabel();
    public JLabel _JLabelAddName = new JLabel();
    public JLabel _JLabelAddID = new JLabel();

    public static JLabel status = new JLabel();

    public static JSONObject JsonCheckUser;
    public static JSONObject JsonTokenLogin;
    public JButton _close;

    public GUIaddFriend(JButton close, JSONObject checkusers, JSONObject tokenlogin) {
        this._close = close;
        this.JsonCheckUser = checkusers;
        this.JsonTokenLogin = tokenlogin;
    }

    public void buildWindowLogin() {
        handlingclose();
        _MainWindow.setTitle("Add a Friend");
        _MainWindow.setSize(370, 300);
        _MainWindow.setResizable(false);
        _MainWindow.setLocationRelativeTo(null);
        configureWindowLogin();
        press();
        _MainWindow.setVisible(true);

    }

    private void handlingclose() {
        //   _MainWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        _MainWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {

            }

            public void windowClosing(java.awt.event.WindowEvent evt) {
                _close.setEnabled(true);
            }

        });

    }

    private void press() {

        _JBAddFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                System.out.println("zzzzzzzzzzzz");
                System.out.println("Add Friend user---------");
                HandleApi handle = new HandleApi();
                JSONObject jsonObject = new JSONObject();
                JSONArray _ArayGroupMember = new JSONArray();
                JSONArray _AraygroupCreator = new JSONArray();
                try {
                     if (_AddName.getText().equals("") || _AddID.getText().equals("")) {
                        throw new Exception("input error kien!!!!");
                    }

                    jsonObject.put("groupName", _AddName.getText() + " && " + JsonCheckUser.getString("userName"));
                    jsonObject.put("groupType", "0");
                    //member in group
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("name", JsonCheckUser.getString("userName"));
                    jsonObject1.put("id", JsonCheckUser.getString("loginId"));
                    jsonObject1.put("status", "true");
                    _ArayGroupMember.put(jsonObject1);
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("name", _AddName.getText());
                    jsonObject2.put("id", _AddID.getText());
                    jsonObject2.put("status", "true");
                    _ArayGroupMember.put(jsonObject2);
                    jsonObject.put("groupMember", _ArayGroupMember);
                    ///
                    _AraygroupCreator.put(jsonObject1);
                    jsonObject.put("groupCreator", _AraygroupCreator);
                    //token
                    jsonObject.put("auth_token", JsonTokenLogin.get("token").toString());

                    System.out.println("ĐÂY là PARAM Tổng:");
                    System.out.println(jsonObject.toString());
                    System.out.println("=========MMMMKKKK============:");
                    _AddName.setText("");
                    _AddID.setText("");


                    String StrJsonAddFriend = handle.postApi(API_requestchat_POST, jsonObject.toString());
                    System.out.println("=========MMMMKKKK222222222============:");
                    System.out.println(StrJsonAddFriend);

                   JSONObject kq = new JSONObject(StrJsonAddFriend);
                    if (kq.getString("message").equals("Request success")) {
                        _AddName.setText("");
                        _AddID.setText("");
                        status.setText("Giửi lời mời thành công!!!");
                        GUIChat.CheckGroupNow();

                    }
                } catch (JSONException ex) {
                    System.out.println("kamezogogo");
                } catch (IOException ex) {
                   System.out.println("Loi tai HandAPi  _JBAddFriend.addActionListener ");
                } catch (Exception ex) {
                    System.out.println("nhap thieu thong tin !!!");
                     status.setText("nhap thieu thong tin !!!");
                }
                System.out.println("END Add Friend user---------------");

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
