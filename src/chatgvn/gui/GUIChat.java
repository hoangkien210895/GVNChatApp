/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.gui;

import chatgvn.client.ActionGUI;
import static chatgvn.utils.ApiProject.API_CHECK_USERNAME_POST;
import static chatgvn.utils.ApiProject.API_LOGOUT_POST;
import chatgvn.utils.HandleApi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Phan Huy
 */
public class GUIChat {

    public static JSONObject _InfoTokenLogin;
    public static String _LoginId;
    public static JSONObject JsonCheckUser;

    public static String USER_NAME = "UserName";
    public static String LOGIN = "Login";
    public static JFrame _MainWindow = new JFrame();
    public static JTextField _Chatmsg = new JTextField();
    public static JTextArea _ChatWindow = new JTextArea();
    public static JButton _jbsendchat = new JButton();
    public static JLabel _ChatSlogan = new JLabel();
    public static JLabel _mineinfo = new JLabel();
    public static JLabel _gioithieuinfo = new JLabel();
    public static JLabel _nameinfo = new JLabel();
    public static JLabel _EmailInfo = new JLabel();
    public static JLabel _PhoneInfo = new JLabel();
    public static JButton _jbsetinfo = new JButton();
    Logger log = Logger.getLogger(ActionGUI.class.getName());
    //list online
    public JList _ListOnline;
    public DefaultListModel _JlistModel;
    public JScrollPane _ListOnlineScrollPane;
    //

    public GUIChat(JSONObject info, String idlogin) {
        this._InfoTokenLogin = info;
        this._LoginId = idlogin;
    }

    public void buildWindowLogin() {
        press();
        HandingCheckUser();
        handlingclose();
        _MainWindow.setTitle("ChatGvn");
        _MainWindow.setSize(1000, 800);
        _MainWindow.setResizable(false);
        _MainWindow.setLocationRelativeTo(null);

        try {
            configureWindowLogin();
        } catch (JSONException ex) {
            System.out.println("cau hinh loi roi ");
        }

        _MainWindow.setVisible(true);

    }

    private void press() {
        _Chatmsg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    _ChatWindow.setText(_ChatWindow.getText() + "\n" + _Chatmsg.getText());
                    _Chatmsg.setText("");
                }
            }
        });

        _jbsendchat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                _ChatWindow.setText(_ChatWindow.getText() + "\n" + _Chatmsg.getText());
                _Chatmsg.setText("");
            }
        });
        _jbsetinfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                try {
                    GUIInfo guiinfo = new GUIInfo(_jbsetinfo, _LoginId, _InfoTokenLogin.get("token").toString());
                    guiinfo.buildWindowLogin();
                } catch (JSONException ex) {
                    System.out.println("loi 1100");
                }

                _jbsetinfo.setEnabled(false);
            }
        });
    }

    public static void HandingCheckUser() {
        System.out.println("check user---------");
        HandleApi handle = new HandleApi();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("auth_token", _InfoTokenLogin.get("token"));
            String StrJsonCheckUser = handle.postApi(API_CHECK_USERNAME_POST + _LoginId, jsonObject.toString());
            System.out.println(StrJsonCheckUser);
            JsonCheckUser = new JSONObject(StrJsonCheckUser);
            _nameinfo.setText("User Name:" + JsonCheckUser.getString("userName"));
            _EmailInfo.setText("Email:" + JsonCheckUser.getString("email"));
            _PhoneInfo.setText("SĐT:" + JsonCheckUser.getString("phone"));

        } catch (JSONException ex) {
            System.out.println("kamezogogo");
        } catch (IOException ex) {

        }
        System.out.println("end_checkuser------");
    }

    private void handlingclose() {
        _MainWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        _MainWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {

                System.exit(0);
            }

            public void windowClosing(java.awt.event.WindowEvent evt) {
                HandleApi handle = new HandleApi();
                try {
                    System.out.println("hiihi:");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("auth_token", _InfoTokenLogin.get("token"));
                    String j = handle.postApi(API_LOGOUT_POST, jsonObject.toString());
                    System.out.println(j);

                } catch (IOException ex) {

                } catch (JSONException ex) {

                }
            }

        });
    }

    private void configureWindowLogin() throws JSONException {
        //    _MainWindow.setBackground(new java.awt.Color(0, 0, 0));
        _MainWindow.getContentPane().setLayout(null);
        _MainWindow.getContentPane().setBackground(new java.awt.Color(148, 170, 214));

        //create 1 list
        //set model list
        _JlistModel = new DefaultListModel();
        _JlistModel.addElement("hahaha");
        //set list
        JList _ListOnline = new JList(_JlistModel);
        _ListOnline.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        _ListOnline.setSelectedIndex(0);
        _ListOnline.setForeground(new java.awt.Color(91, 189, 43));
        _ListOnline.setSelectionForeground(new java.awt.Color(110, 195, 201));
        //set list on ScrollPane
        _ListOnlineScrollPane = new JScrollPane(_ListOnline);
        _ListOnlineScrollPane.setBounds(0, 200, 260, 560);
        _MainWindow.getContentPane().add(_ListOnlineScrollPane);

        // _ChatWindow.
        _ChatWindow.setForeground(new java.awt.Color(0, 0, 255));
        _ChatWindow.setEditable(false);
        _MainWindow.getContentPane().add(_ChatWindow);
        _ChatWindow.setBounds(270, 200, 700, 480);

        //_Chatmsg
        _Chatmsg.setForeground(new java.awt.Color(0, 0, 255));
        //  _Chatmsg.requestFocus();
        _MainWindow.getContentPane().add(_Chatmsg);
        _Chatmsg.setBounds(270, 710, 600, 50);

        //_jbsendchat
        _jbsendchat.setText("Send");
        _MainWindow.getContentPane().add(_jbsendchat);
        _jbsendchat.setBounds(880, 725, 90, 25);

        //_ChatSlogan
        _ChatSlogan.setText("không yêu đừng nói lời cay đắng <3 (không trashtalk)");
        _MainWindow.getContentPane().add(_ChatSlogan);
        _ChatSlogan.setBounds(270, 685, 300, 25);

        // _mineinfo
//        _mineinfo.setText(_InfoTokenLogin.toString());
//        _MainWindow.getContentPane().add(_mineinfo);
//        _mineinfo.setBounds(0, 0, 300, 25);
        //_nameinfo
      //  _nameinfo.setText("User Name:" + JsonCheckUser.getString("userName"));
        _MainWindow.getContentPane().add(_nameinfo);
        _nameinfo.setBounds(10, 40, 300, 50);
        //_EmailInfo
      //  _EmailInfo.setText("Email:" + JsonCheckUser.getString("email"));
        _MainWindow.getContentPane().add(_EmailInfo);
        _EmailInfo.setBounds(10, 60, 300, 50);
        //_PhoneInfo
      //  _PhoneInfo.setText("SĐT:" + JsonCheckUser.getString("phone"));
        _MainWindow.getContentPane().add(_PhoneInfo);
        _PhoneInfo.setBounds(10, 80, 300, 50);

        //_jbsetinfo
        _jbsetinfo.setText("SET INFO");
        _MainWindow.getContentPane().add(_jbsetinfo);
        _jbsetinfo.setBounds(20, 140, 90, 25);
        //_gioithieuinfo
        _gioithieuinfo.setText("------THÔNG TIN NGƯỜI DÙNG------------");
        _MainWindow.getContentPane().add(_gioithieuinfo);
        _gioithieuinfo.setBounds(10, 0, 500, 50);
    }
}
