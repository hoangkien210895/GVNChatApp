/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.gui;

import chatgvn.client.ActionGUI;
import chatgvn.socket.WebsocketClientEndpoint;
import static chatgvn.utils.ApiProject.API_AccepRequest_POST;
import static chatgvn.utils.ApiProject.API_CHECK_USERNAME_POST;
import static chatgvn.utils.ApiProject.API_CheckGroup_POST;
import static chatgvn.utils.ApiProject.API_CheckOnline_POST;
import static chatgvn.utils.ApiProject.API_CheckRequest_POST;
import static chatgvn.utils.ApiProject.API_LOGOUT_POST;
import static chatgvn.utils.ApiProject.API_LeftGroup_POST;
import static chatgvn.utils.ApiProject.API_LoadLogChat_POST;
import static chatgvn.utils.ApiProject.API_requestchat_POST;
import chatgvn.utils.HandleApi;
import java.awt.Color;
import java.awt.Point;
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
import javax.swing.JViewport;
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
    public static JScrollPane _ListOnlineScrollChatWindow;
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

    public JLabel _NhapTenAddFriend = new JLabel();
    public JTextField _ToName = new JTextField();
    public JLabel _NhapLoginIDAddFriend = new JLabel();
    public JTextField _ToLoginID = new JTextField();

    public JLabel _TrangThaiAcep = new JLabel();
    public static JSONArray _JsonGroupNow;
    public static JSONArray _JsonRequetsNow;
    public static JButton _AcceptInvite = new JButton();
    public static JButton _DeleteGroup = new JButton();

    //JSONArray kq
    public static JButton _jbCreateGroup = new JButton();
    public static JButton _jbInsertGroup = new JButton();

    //
    public static WebsocketClientEndpoint a;
    public boolean readysendmes = false;
    public static JSONObject JSONObjectGroupNow = new JSONObject();
    public static JSONObject JSONObjectRequetsNow = new JSONObject();
    public static GUIInsertGroup guiInsertGroup;
    public static String stringcchat;
    //

    public GUIChat(JSONObject info, String idlogin) {
        this._InfoTokenLogin = info;
        this._LoginId = idlogin;

    }

    public void buildWindowLogin() {
        _JlistModel = new DefaultListModel();
        _JlistModelkb = new DefaultListModel();
        HandingCheckUser();
        handlingclose();
        _MainWindow.setTitle("ChatGvn");
        _MainWindow.setSize(1000, 800);
        _MainWindow.setResizable(false);
        _MainWindow.setLocationRelativeTo(null);

        try {
            a = new WebsocketClientEndpoint(new URI("ws://10.64.100.23:1234/chat"));

            a.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println("=========HandingMessWebSocket========");
                    handeMessWebsocket(message);
                    System.out.println("=========EndHandingMessWebSocket=======");

                }

            });
            indentification();

            configureWindowLogin();
        } catch (JSONException ex) {
            System.out.println("cau hinh loi roi ");
        } catch (URISyntaxException ex) {
            System.out.println("websocket loi r ");
        }

        CheckRequest();
        // HandingCheckOnline();
        CheckGroupNow();
        press();
        _MainWindow.setVisible(true);

    }

    private void press() {
        _Chatmsg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    SendChat();

                }
            }
        });

        _jbsendchat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                SendChat();
            }
        });
        _jbAndFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GUIaddFriend newgui = new GUIaddFriend(_jbAndFriend, JsonCheckUser, _InfoTokenLogin);
                newgui.buildWindowLogin();
                _jbAndFriend.setEnabled(false);

            }
        });
        ///bat hanh dong kick vao button setinfo
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
        ////bat hanh dong dung chuot vao List room chat 
        _ListOnline.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                try {
                    System.out.println("FUJKKKKKKKKK-------------------------");
                    JSONObjectGroupNow = _JsonGroupNow.getJSONObject(_ListOnline.getSelectedIndex());
                    System.out.println(JSONObjectGroupNow);
                    System.out.println("FUJKKKKKKKKK-------------------------");

                    System.out.println("Check LogChat-------------------------");
                    Loadlogchat(_JsonGroupNow.getJSONObject(_ListOnline.getSelectedIndex()).get("_id").toString());
                } catch (JSONException ex) {
                    System.out.println("loi hihi");
                } catch (IOException ex) {
                    System.out.println("loi hihi22222");
                }
                System.out.println("Check LogChat End-------------------------");
                _DeleteGroup.setVisible(true);
                _AcceptInvite.setVisible(false);

            }

        });
        ///bat hanh dong kick vao button nsertGroup
        _jbInsertGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                _jbInsertGroup.setEnabled(false);
                System.out.println("FUCKINGGGGG-------------------------");
                try {
                    System.out.println(JSONObjectGroupNow.getString("_id"));
                    System.out.println(JSONObjectGroupNow.getString("groupType"));
                    System.out.println("FUCKINGGGGG-------------------------");
                    if (JSONObjectGroupNow.getString("groupType").equals("0")) {
                        throw new Exception("input error kien!!!!");
                    }
                    guiInsertGroup = null;
                    guiInsertGroup = new GUIInsertGroup(_jbInsertGroup, JSONObjectGroupNow, _InfoTokenLogin);
                    guiInsertGroup.buildWindowLogin();

                } catch (JSONException ex) {
                    System.out.println("loi ne baby");
                    _jbInsertGroup.setEnabled(true);
                    _MainWindow.setEnabled(true);
                } catch (Exception ex) {
                    System.out.println("loi ne baby");
                    _jbInsertGroup.setEnabled(true);
                    _MainWindow.setEnabled(true);
                }

            }
        });
        //bat hanh dong dung chuot List danh sach moi` ket ban
        _ListOnlinekb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    JSONObjectRequetsNow = _JsonRequetsNow.getJSONObject(_ListOnlinekb.getSelectedIndex());

                    System.out.println(JSONObjectRequetsNow);

                } catch (JSONException ex) {
                    System.out.println("loi o _ListOnlinekb.addMouseListener");
                }
                _DeleteGroup.setVisible(false);
                _AcceptInvite.setVisible(true);

            }
        });

        _jbAcepFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                CheckRequest();
                _ListOnlineScrollPane.setVisible(false);
                _ListOnlineScrollPanekb.setVisible(true);
            }
        });
        //bat hanh dong kick vao nut room
        _jbDanhSach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                CheckGroupNow();
                _ListOnlineScrollPane.setVisible(true);
                _ListOnlineScrollPanekb.setVisible(false);
            }
        });

        _AcceptInvite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HandleApi handle = new HandleApi();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", JSONObjectRequetsNow.get("_id").toString());
                    jsonObject.put("loginId", _LoginId);
                    jsonObject.put("auth_token", _InfoTokenLogin.get("token"));
                    System.out.println("PRAMMETTERRRRRR");
                    System.out.println(jsonObject);
                    String StrJsonCheckUser = handle.postApi(API_AccepRequest_POST, jsonObject.toString());
                    System.out.println(StrJsonCheckUser);
                    CheckRequest();
                } catch (JSONException ex) {
                    System.out.println("EERORR In _AcceptInvite.addActionListener");
                } catch (IOException ex) {
                    System.out.println("EERORR In _AcceptInvite.addActionListener handAPI");
                }

            }
        });

        _jbCreateGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                _jbCreateGroup.setEnabled(false);
                GUICreateGroup guiCreateGroup = new GUICreateGroup(_jbCreateGroup, JsonCheckUser, _InfoTokenLogin);
                guiCreateGroup.buildWindowLogin();
            }
        });

        _DeleteGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HandleApi handle = new HandleApi();
                try {
                    JSONObject input = new JSONObject();
                    input.put("id", JSONObjectGroupNow.getString("_id"));
                    input.put("loginId", _LoginId);
                    input.put("auth_token", _InfoTokenLogin.getString("token"));
                    System.out.println("GAUUUUUUUUUU GAUUUUUUUU");
                    System.out.println(input);
                    System.out.println("GAUUUUUUUUUU GAUUUUUUUU");
                    String kq = handle.postApi(API_LeftGroup_POST, input.toString());
                    System.out.println("STRINGGGGGG KQ:");
                    JSONObject fromApi = new JSONObject(kq);
                    System.out.println(fromApi);
                    System.out.println("STRINGGGGGG KQ1111111:");
                    if (fromApi.getString("message").equals("Left success")) {
                        System.out.println("day la dai phat thanh tieng noi viet name oke roi dm");
                        _ChatWindow.setText("");
                       // JSONObjectGroupNow = null;
                        CheckGroupNow();
                    }

                } catch (JSONException ex) {
                    System.out.println("day la dai phat thanh tieng noi viet name loi roi dm");
                } catch (IOException ex) {
                    System.out.println("day la dai phat thanh tieng noi viet name loi roi dm handleAPI");
                }

            }
        });

    }

    private void SendChat() {
        if (readysendmes == true) {

            //    a.sendMessage(_Chatmsg.getText());
            try {
                JSONObject jsonObjectPar = new JSONObject();
                jsonObjectPar.put("api", "send message");
                jsonObjectPar.put("group_id", JSONObjectGroupNow.get("_id").toString());
                jsonObjectPar.put("token_sender", _InfoTokenLogin.get("token").toString());
                jsonObjectPar.put("sender", _LoginId);
                jsonObjectPar.put("message", _Chatmsg.getText());
                jsonObjectPar.put("user_name", JsonCheckUser.getString("userName"));
                System.out.println("PARAMMEST CHAT:");
                System.out.println(jsonObjectPar);
                a.sendMessage(jsonObjectPar.toString());
                //  _ChatWindow.setText(_ChatWindow.getText() + "\n" + _Chatmsg.getText());
                _Chatmsg.setText("");

            } catch (JSONException ex) {
                System.out.println("SendChat lỗi");
            }
        }
    }

    //load log chat
    private void Loadlogchat(String idgroup) throws JSONException, IOException {
//            System.out.println("Par:");
//            System.out.println(idgroup);
//            System.out.println(_InfoTokenLogin.get("token"));
        _ChatWindow.setText("");
        HandleApi handle = new HandleApi();
        JSONObject jsonObjectPar = new JSONObject();
        jsonObjectPar.put("id", idgroup);
        jsonObjectPar.put("auth_token", _InfoTokenLogin.get("token").toString());
        String StrJsonChecChatLog = handle.postApi(API_LoadLogChat_POST, jsonObjectPar.toString());
        System.out.println(StrJsonChecChatLog);
        JSONObject jsonObjectKQ = new JSONObject(StrJsonChecChatLog);
        JSONArray _JsonArayChatLog = new JSONArray(jsonObjectKQ.get("chatLog").toString());
        System.out.println("JsonChatLog:");
        System.out.println(_JsonArayChatLog);
        _ChatWindow.setText("");
        for (int i = 0; i < _JsonArayChatLog.length(); i++) {
            JSONObject temp = new JSONObject(_JsonArayChatLog.getJSONObject(i).toString());
            _ChatWindow.setText(_ChatWindow.getText() + "\n" + temp.get("name").toString() + ": " + temp.get("content").toString());
            // System.out.println(temp.get("name").toString() + ": " + temp.get("content"));
        }

    }

    ///giửi định danh
    private void indentification() throws JSONException {
        JSONObject jsonObjectPar = new JSONObject();
        jsonObjectPar.put("api", "indentification");
        jsonObjectPar.put("token", _InfoTokenLogin.get("token").toString());
        jsonObjectPar.put("user_name", _LoginId);
        jsonObjectPar.put("name", JsonCheckUser.getString("userName"));
        a.sendMessage(jsonObjectPar.toString());
    }

    ///load ra thong tin ve nguoi dung dang online(la chinh minh)
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

    //xu ly khi dong phan memchat
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

    //xu ly khi nhan 1 thong diep tu websocket
    private void handeMessWebsocket(String mes) {

        try {
            System.out.println("TỪ Sever:");
            System.out.println(mes);
            JSONObject jsonObject = new JSONObject(mes);
            if (jsonObject.get("api").equals("indentification") && jsonObject.get("message").equals("success")) {
                System.out.println("ban da san sàng de giửi tin");
                readysendmes = true;
            } else {
                if(jsonObject.get("api").equals("send message")){
                
                
                if (jsonObject.get("group_id").toString().equals(JSONObjectGroupNow.get("_id").toString())) {
                    String conten = jsonObject.get("sender").toString() + ": " + jsonObject.get("message").toString();
                    stringcchat = conten;
                    _ChatWindow.setText(_ChatWindow.getText() + "\n" + conten);
                    //
                    JViewport vp = _ListOnlineScrollChatWindow.getViewport();
                    Point p = vp.getViewPosition();
                    p.y += _ChatWindow.getPreferredSize().height;
                    _ListOnlineScrollChatWindow.revalidate();
                    vp.setViewPosition(p);

                }
                
                }
                

            }
        } catch (JSONException ex) {
            System.out.println("hihii");
        }

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

    private void CheckRequest() {

        System.out.println("check Request---------");
        HandleApi handle = new HandleApi();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("auth_token", _InfoTokenLogin.get("token"));
            String StrJsonCheckGroup = handle.postApi(API_CheckRequest_POST + _LoginId, jsonObject.toString());
            System.out.println(StrJsonCheckGroup);

            _JsonRequetsNow = new JSONArray(StrJsonCheckGroup);
            System.out.println("JSONarray:");
            System.out.println(_JsonRequetsNow);

            System.out.println("=====");
            _JlistModelkb.clear();
            for (int i = 0; i < _JsonRequetsNow.length(); i++) {
                //_JlistModelkb.addElement(kq.getJSONObject(i).get("_id"));
                _JlistModelkb.addElement(_JsonRequetsNow.getJSONObject(i).get("groupName"));
            }

        } catch (JSONException ex) {
            System.out.println("kamezogogo");
        } catch (IOException ex) {

        }
        System.out.println("check Request---------");
    }

    public static void CheckGroupNow() {

        System.out.println("check Group---------");
        HandleApi handle = new HandleApi();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("auth_token", _InfoTokenLogin.get("token"));
            String StrJsonCheckGroup = handle.postApi(API_CheckGroup_POST + _LoginId, jsonObject.toString());
            System.out.println(StrJsonCheckGroup);

            _JsonGroupNow = new JSONArray(StrJsonCheckGroup);
            System.out.println("JSONarray:");
            System.out.println(_JsonGroupNow);

            System.out.println("=====");
            _JlistModel.clear();
            for (int i = 0; i < _JsonGroupNow.length(); i++) {
                // _JlistModel.addElement(_JsonGroupNow.getJSONObject(i).get("_id"));
                _JlistModel.addElement(_JsonGroupNow.getJSONObject(i).get("groupName"));
            }

        } catch (JSONException ex) {
            System.out.println("kamezogogo");
        } catch (IOException ex) {

        }
        System.out.println("check Group---------");
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
       // _ListOnline.setSelectedIndex(0);
        _ListOnline.setForeground(new java.awt.Color(54, 117, 23));
        _ListOnline.setSelectionForeground(new java.awt.Color(16, 54, 103));
        //set list on ScrollPane
        _ListOnlineScrollPane = new JScrollPane(_ListOnline);
        _ListOnlineScrollPane.setBounds(0, 200, 260, 560);
        _MainWindow.getContentPane().add(_ListOnlineScrollPane);

        ////create 1 list kb
        _ListOnlinekb = new JList(_JlistModelkb);
        _ListOnlinekb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       // _ListOnlinekb.setSelectedIndex(0);
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
        _ChatWindow.setColumns(20);
        _ChatWindow.setRows(5);
        _ListOnlineScrollChatWindow = new JScrollPane(_ChatWindow);
        _ListOnlineScrollChatWindow.setViewportView(_ChatWindow);
        _MainWindow.getContentPane().add(_ListOnlineScrollChatWindow);
        _ListOnlineScrollChatWindow.setBounds(270, 200, 700, 480);

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
        _jbsetinfo.setText("Set Info");
        _MainWindow.getContentPane().add(_jbsetinfo);
        _jbsetinfo.setBounds(250, 40, 90, 25);

        //_gioithieuinfo
        _gioithieuinfo.setText("------THÔNG TIN NGƯỜI DÙNG------------");
        _MainWindow.getContentPane().add(_gioithieuinfo);
        _gioithieuinfo.setBounds(10, 0, 500, 50);
        /////

        /////_jbDanhSach
        _jbDanhSach.setText("Room");
        _MainWindow.getContentPane().add(_jbDanhSach);
        _jbDanhSach.setBounds(10, 170, 90, 25);

        ///
        _jbAcepFriend.setText("Mời");
        _MainWindow.getContentPane().add(_jbAcepFriend);
        _jbAcepFriend.setBounds(110, 170, 90, 25);

        _jbAndFriend.setText("Chat Friend");
        _MainWindow.getContentPane().add(_jbAndFriend);
        _jbAndFriend.setBounds(810, 170, 150, 25);

        //_jbCreateGroup
        _jbCreateGroup.setText("Create Group");
        _MainWindow.getContentPane().add(_jbCreateGroup);
        _jbCreateGroup.setBounds(500, 170, 150, 25);

        //_jbInsertGroup
        _jbInsertGroup.setText("Insert Group");
        _MainWindow.getContentPane().add(_jbInsertGroup);
        _jbInsertGroup.setBounds(660, 170, 120, 25);

        _TrangThaiAcep.setForeground(Color.yellow);
        _MainWindow.getContentPane().add(_TrangThaiAcep);
        _TrangThaiAcep.setBounds(750, 150, 300, 20);

        _AcceptInvite.setText("Accept");
        _MainWindow.getContentPane().add(_AcceptInvite);
        _AcceptInvite.setBounds(210, 170, 90, 25);
        _AcceptInvite.setVisible(false);

        _DeleteGroup.setText("Delete");
        _MainWindow.getContentPane().add(_DeleteGroup);
        //310, 170, 90, 25
        _DeleteGroup.setBounds(210, 170, 90, 25);
        _DeleteGroup.setVisible(false);

        ///
    }

}
