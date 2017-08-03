/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.gui;

import chatgvn.client.ActionGUI;
import chatgvn.socket.WebsocketClientEndpoint;
import static chatgvn.utils.ApiProject.API_CHECK_USERNAME_POST;
import static chatgvn.utils.ApiProject.API_CheckOnline_POST;
import static chatgvn.utils.ApiProject.API_LOGOUT_POST;
import chatgvn.utils.HandleApi;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import org.json.JSONArray;
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
    public static JList _ListOnline;
    public static DefaultListModel _JlistModel;
    public static JScrollPane _ListOnlineScrollPane;
    //

    public static JButton _jbDanhSach = new JButton();
    public static JButton _jbAcepFriend = new JButton();
    public static JButton _jbAndFriend = new JButton();

    //
    public static JList _ListOnlinekb;
    public static DefaultListModel _JlistModelkb;
    public static JScrollPane _ListOnlineScrollPanekb;

    public JTextField ToName = new JTextField();
    //
    public static WebsocketClientEndpoint a;

    public GUIChat(JSONObject info, String idlogin) {
        this._InfoTokenLogin = info;
        this._LoginId = idlogin;

    }

    public void buildWindowLogin() {
        _JlistModel = new DefaultListModel();
        _JlistModelkb = new DefaultListModel();
        handlingclose();
        _MainWindow.setTitle("ChatGvn");
        _MainWindow.setSize(1000, 800);
        _MainWindow.setResizable(false);
        _MainWindow.setLocationRelativeTo(null);

        try {
            a = new WebsocketClientEndpoint(new URI("ws://10.64.1.88:1234/chat"));

            a.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println("=========hehehehehehe========");
                    System.out.println(message);
                    System.out.println("=========hehehehehehe=======");

                }

            });

            configureWindowLogin();
        } catch (JSONException ex) {
            System.out.println("cau hinh loi roi ");
        } catch (URISyntaxException ex) {
            System.out.println("websocket loi r ");
        }
        press();
        HandingCheckUser();
        HandingCheckOnline();
        _MainWindow.setVisible(true);

    }

    private void press() {
        _Chatmsg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    a.sendMessage(_Chatmsg.getText());
                    _ChatWindow.setText(_ChatWindow.getText() + "\n" + _Chatmsg.getText());
                    _Chatmsg.setText("");
                }
            }
        });

        _jbsendchat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                a.sendMessage(_Chatmsg.getText());
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

        _ListOnline.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //    System.out.println(_ListOnline.getSelectedValue());
                ToName.setText((String) _ListOnline.getSelectedValue());
            }
        });
        
        
              _jbAcepFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                _ListOnlineScrollPane.setVisible(false);
                _ListOnlineScrollPanekb.setVisible(true);
            }
        });
        
        
        _jbDanhSach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
              
                _ListOnlineScrollPane.setVisible(true);
                _ListOnlineScrollPanekb.setVisible(false);
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

    public static void HandingCheckOnline() {
        System.out.println("check Online---------");
        HandleApi handle = new HandleApi();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("auth_token", _InfoTokenLogin.get("token"));
            String StrJsonCheckUser = handle.postApi(API_CheckOnline_POST, jsonObject.toString());
            System.out.println(StrJsonCheckUser);

            JSONArray kq = new JSONArray(StrJsonCheckUser);
            System.out.println("JSONarray:");
            System.out.println(kq);

            System.out.println("=====");
            _JlistModel.clear();
            for (int i = 0; i < kq.length(); i++) {
                _JlistModel.addElement(kq.getJSONObject(i).get("user"));
            }

        } catch (JSONException ex) {
            System.out.println("kamezogogo");
        } catch (IOException ex) {

        }
        System.out.println("Online------");
    }

    private void configureWindowLogin() throws JSONException {
        //    _MainWindow.setBackground(new java.awt.Color(0, 0, 0));
        _MainWindow.getContentPane().setLayout(null);
        _MainWindow.getContentPane().setBackground(new java.awt.Color(148, 170, 214));

        //create 1 list
        //set model list
        //_JlistModel = new DefaultListModel();
        //set list 54	117	23 // 16	54	103
        _ListOnline = new JList(_JlistModel);
        _ListOnline.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        _ListOnline.setSelectedIndex(0);
        _ListOnline.setForeground(new java.awt.Color(54, 117, 23));
        _ListOnline.setSelectionForeground(new java.awt.Color(16, 54, 103));
        //set list on ScrollPane
        _ListOnlineScrollPane = new JScrollPane(_ListOnline);
        _ListOnlineScrollPane.setBounds(0, 200, 260, 560);
        _MainWindow.getContentPane().add(_ListOnlineScrollPane);

        ////create 1 list kb
        _JlistModelkb.addElement("1111");
        _JlistModelkb.addElement("222");
        _JlistModelkb.addElement("3333");
        _ListOnlinekb = new JList(_JlistModelkb);
        _ListOnlinekb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        _ListOnlinekb.setSelectedIndex(0);
        _ListOnlinekb.setForeground(new java.awt.Color(54, 117, 23));
        _ListOnlinekb.setSelectionForeground(new java.awt.Color(16, 54, 103));
        //set list on ScrollPane
        _ListOnlineScrollPanekb = new JScrollPane(_ListOnlinekb);
        _ListOnlineScrollPanekb.setBounds(0, 200, 260, 560);
        _ListOnlineScrollPanekb.setVisible(false);
        _MainWindow.getContentPane().add(_ListOnlineScrollPanekb);

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
        /////

        /////_jbDanhSach
        _jbDanhSach.setText("Friend");
        _MainWindow.getContentPane().add(_jbDanhSach);
        _jbDanhSach.setBounds(10, 170, 90, 25);

        ///
        _jbAcepFriend.setText("Kết bạn");
        _MainWindow.getContentPane().add(_jbAcepFriend);
        _jbAcepFriend.setBounds(110, 170, 90, 25);

        _jbAndFriend.setText("Add Friend");
        _MainWindow.getContentPane().add(_jbAndFriend);
        _jbAndFriend.setBounds(210, 170, 150, 25);

        _MainWindow.getContentPane().add(ToName);
       // ToName.setEnabled(false);
        ToName.setDisabledTextColor(Color.RED);
        ToName.setBounds(500, 170, 100, 20);
    }

}
