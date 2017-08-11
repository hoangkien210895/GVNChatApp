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
import java.awt.Font;
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
    public static JScrollPane _ListRoomScrollChatWindow;
    Logger log = Logger.getLogger(ActionGUI.class.getName());
    //list online
    public static JList _ListRoom;
    public static DefaultListModel _JlistModelroom;
    public static JScrollPane _ListRoomScrollPane;
    //

    public static JButton _jbDanhSachRoom = new JButton();
    public static JButton _jbShowRequestKb = new JButton();
    public static JButton _jbAndFriend = new JButton();

    //
    public static JList _ListKb;
    public static DefaultListModel _JlistModelkb;
    public static JScrollPane _ListKbScrollPane;

    public JLabel _NhapTenAddFriend = new JLabel();
    public JTextField _ToName = new JTextField();
    public JLabel _NhapLoginIDAddFriend = new JLabel();
    public JTextField _ToLoginID = new JTextField();

    public JLabel _TrangThaiAcep = new JLabel();
    public static JSONArray _JsonArrayGroupNow;
    public static JSONArray _JsonArrayRequetsNow;
    public static JSONArray _JsonArrayFriendNow;
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

    ///thiet ke  cho List online Stars
    public JButton _jbListFriend = new JButton();
    public static DefaultListModel _JlistModelFriend;
    public static JList _ListFriend;
    public static JScrollPane _ListFriendScrollPane;
//thiet ke  cho List online End

    public GUIChat(JSONObject info, String idlogin) {
        this._InfoTokenLogin = info;
        this._LoginId = idlogin;

    }

    public void buildWindowLogin() {
        _JlistModelroom = new DefaultListModel();
        _JlistModelkb = new DefaultListModel();
        _JlistModelFriend = new DefaultListModel();
        HandingCheckUser();
        handlingclose();
        _MainWindow.setTitle("ChatGvn");
        _MainWindow.setSize(880, 690 - 40);
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
        _ListRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                try {
                    System.out.println("FUJKKKKKKKKK-------------------------");
                    JSONObjectGroupNow = _JsonArrayGroupNow.getJSONObject(_ListRoom.getSelectedIndex());
                    System.out.println(JSONObjectGroupNow);
                    System.out.println("FUJKKKKKKKKK-------------------------");

                    System.out.println("Check LogChat-------------------------");
                    Loadlogchat(_JsonArrayGroupNow.getJSONObject(_ListRoom.getSelectedIndex()).get("_id").toString());
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
        _ListKb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    JSONObjectRequetsNow = _JsonArrayRequetsNow.getJSONObject(_ListKb.getSelectedIndex());

                    System.out.println(JSONObjectRequetsNow);

                } catch (JSONException ex) {
                    System.out.println("loi o _ListKb.addMouseListener");
                }
                _DeleteGroup.setVisible(false);
                _AcceptInvite.setVisible(true);

            }
        });
        //cath action clik List friend 
        _ListFriend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("huhuhu");
                System.out.println(_ListFriend.getSelectedIndex());
                int vtClinkListFriend = _ListFriend.getSelectedIndex();

                try {
                    JSONObject x = _JsonArrayFriendNow.getJSONObject(vtClinkListFriend);
                    String nameGroupcheck1 = x.getString("id") + " && " + _LoginId;
                    String nameGroupcheck2 = _LoginId + " && " + x.getString("id");
                    System.out.println("nameGroupcheck12=========");
                    System.out.println(nameGroupcheck1);
                    System.out.println(nameGroupcheck2);
                    System.out.println("nameGroupcheck12=========");
                    //lam gi do de no vao phong cua thang ban day
                    for (int i = 0; i < _JsonArrayGroupNow.length(); i++) {

                        if (_JsonArrayGroupNow.getJSONObject(i).getString("groupType").endsWith("0")) {
                            String NameGroupI = _JsonArrayGroupNow.getJSONObject(i).getString("groupName");

                            if (NameGroupI.endsWith(nameGroupcheck1) || NameGroupI.endsWith(nameGroupcheck2)) {
                                System.out.println("DANG O DAY NE:");
                                System.out.println(NameGroupI);
                                 JSONObjectGroupNow = _JsonArrayGroupNow.getJSONObject(i);
                                ///lay dc JsonojecGruopNOW roi _JsonArrayGroupNow.getJSONObject(i)
                                Loadlogchat(JSONObjectGroupNow.getString("_id"));
                                
                                break;
                                //
                            }
                        }
                    }
                _DeleteGroup.setVisible(true);
                _AcceptInvite.setVisible(false);

                    //}
                } catch (JSONException ex) {
                  
                    System.out.println("LOI trong tam kiem soat");
                } catch (Exception ex) {
                    System.out.println("OH MY BABY BABYGIRL");
                }

            }
        });
        //bat hanh dong kick vao nut Invite
        _jbShowRequestKb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                CheckRequest();
                _jbListFriend.setBackground(Color.white);
                _jbShowRequestKb.setBackground(new java.awt.Color(254, 248, 134));
                _jbDanhSachRoom.setBackground(Color.white);
                _ListFriendScrollPane.setVisible(false);
                _ListRoomScrollPane.setVisible(false);
                _ListKbScrollPane.setVisible(true);

            }
        });
        //bat hanh dong kick vao JBUTTON ListFriend
        _jbListFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                _jbListFriend.setBackground(new java.awt.Color(254, 248, 134));
                _jbShowRequestKb.setBackground(Color.white);
                _jbDanhSachRoom.setBackground(Color.white);
                _ListFriendScrollPane.setVisible(true);
                _ListRoomScrollPane.setVisible(false);
                _ListKbScrollPane.setVisible(false);

            }
        });
        //bat hanh dong kick vao nut Roomvv
        _jbDanhSachRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                CheckGroupNow();
                _jbListFriend.setBackground(Color.white);
                _jbShowRequestKb.setBackground(Color.white);
                _jbDanhSachRoom.setBackground(new java.awt.Color(254, 248, 134));
                _ListFriendScrollPane.setVisible(false);
                _ListRoomScrollPane.setVisible(true);
                _ListKbScrollPane.setVisible(false);
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
                        _ChatWindow.setText("ĐÃ XÓA GROUP NÀY HIHI ");
                
                        CheckGroupNow();
                         JSONObjectGroupNow = null;
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
            System.out.println("TỪ Sever:XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            System.out.println(mes);
            JSONObject jsonObjectsocket = new JSONObject(mes);

            if (jsonObjectsocket.get("api").equals("indentification") && jsonObjectsocket.get("message").equals("success")) {
                System.out.println("ban da san sàng de giửi tin");
                readysendmes = true;
            } else if (jsonObjectsocket.get("api").equals("send message")) {

                if (jsonObjectsocket.get("group_id").toString().equals(JSONObjectGroupNow.get("_id").toString())) {
                    String conten = jsonObjectsocket.get("sender").toString() + ": " + jsonObjectsocket.get("message").toString();
                    stringcchat = conten;
                    _ChatWindow.setText(_ChatWindow.getText() + "\n" + conten);
                    ////ve lai chatwindow
                    JViewport vp = _ListRoomScrollChatWindow.getViewport();
                    Point p = vp.getViewPosition();
                    p.y += _ChatWindow.getPreferredSize().height;
                    _ListRoomScrollChatWindow.revalidate();
                    vp.setViewPosition(p);

                }

            } else if (jsonObjectsocket.get("api").equals("friend")) {
                System.out.println("HREEEEEEE1 ARRAY:");
                System.out.println(jsonObjectsocket.getString("friend"));
                _JsonArrayFriendNow = new JSONArray(jsonObjectsocket.getString("friend"));
                _JlistModelFriend.removeAllElements();
                for (int i = 0; i < _JsonArrayFriendNow.length(); i++) {

                    _JlistModelFriend.addElement(_JsonArrayFriendNow.getJSONObject(i).get("name"));

                }

            }
        } catch (JSONException ex) {
            System.out.println("ERRJSON");
        } catch (Exception ex) {
            System.out.println("ERRRRRRRXXXXXXXXX");
        }

    }

    private void CheckRequest() {

        System.out.println("check Request---------");
        HandleApi handle = new HandleApi();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("auth_token", _InfoTokenLogin.get("token"));
            String StrJsonCheckGroup = handle.postApi(API_CheckRequest_POST + _LoginId, jsonObject.toString());
            System.out.println(StrJsonCheckGroup);

            _JsonArrayRequetsNow = new JSONArray(StrJsonCheckGroup);
            System.out.println("JSONarray:");
            System.out.println(_JsonArrayRequetsNow);

            System.out.println("=====");
            _JlistModelkb.clear();
            for (int i = 0; i < _JsonArrayRequetsNow.length(); i++) {
                if (_JsonArrayRequetsNow.getJSONObject(i).getString("groupType").endsWith("0")) {
                    String ss = _JsonArrayRequetsNow.getJSONObject(i).getString("groupName");
                    String[] a = ss.split("&&");
                    _JlistModelkb.addElement(a[1]);
                } else {
                    _JlistModelkb.addElement(_JsonArrayRequetsNow.getJSONObject(i).getString("groupName"));
                }

            }

        } catch (JSONException ex) {
            System.out.println("kamezogogo");
        } catch (Exception ex) {
            System.out.println("LOI ne");

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

            _JsonArrayGroupNow = new JSONArray(StrJsonCheckGroup);
            System.out.println("JSONarray:");
            System.out.println(_JsonArrayGroupNow);

            System.out.println("=====");
            _JlistModelroom.clear();
            for (int i = 0; i < _JsonArrayGroupNow.length(); i++) {
                // _JlistModel.addElement(_JsonArrayGroupNow.getJSONObject(i).get("_id"));
                _JlistModelroom.addElement(_JsonArrayGroupNow.getJSONObject(i).get("groupName"));
            }

        } catch (JSONException ex) {
            System.out.println("kamezogogo");
        } catch (IOException ex) {

        }
        System.out.println("check Group---------");
    }

    private void configureWindowLogin() throws JSONException {
        //    _MainWindow.setBackground(new java.awt.Color(0, 0, 0));
        ///thiet lap giao dien(quan trong vcđ)
        _MainWindow.getContentPane().setLayout(null);
        _MainWindow.getContentPane().setBackground(new java.awt.Color(148, 170, 214));
///////****************************Cac LIST START***********************************************////

        ///////////1111111111**LIST ROOM***11111//////////
        //set model list
        //_JlistModel = new DefaultListModel();
        _ListRoom = new JList(_JlistModelroom);
        _ListRoom.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // _ListRoom.setSelectedIndex(0);
        _ListRoom.setForeground(new java.awt.Color(54, 117, 23));
        _ListRoom.setSelectionForeground(new java.awt.Color(16, 54, 103));
        _ListRoomScrollPane = new JScrollPane(_ListRoom);
        _ListRoomScrollPane.setBounds(0, 140 - 30, 260, 560 - 130);
        _ListRoomScrollPane.setVisible(false);
        _MainWindow.getContentPane().add(_ListRoomScrollPane);
        ///////////1111111111**LIST ROOM***11111//////////

        ///////////2222222222**LIST MOI Kb***22222///////////       
        _ListKb = new JList(_JlistModelkb);
        _ListKb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        _ListKb.setForeground(new java.awt.Color(54, 117, 23));
        _ListKb.setSelectionForeground(new java.awt.Color(16, 54, 103));
        //set list on ScrollPane
        _ListKbScrollPane = new JScrollPane(_ListKb);
        _ListKbScrollPane.setBounds(0, 140 - 30, 260, 560 - 130);
        _ListKbScrollPane.setVisible(false);
        _MainWindow.getContentPane().add(_ListKbScrollPane);
        ///////////2222222222**LIST MOI Kb***22222///////////

        ///////////3333333333**LIST Show Friend***333333333///////////
//        _JlistModelFriend.addElement("11111");
//        _JlistModelFriend.addElement("233333");
        _JlistModelFriend.addElement("list friend:load....");
        _ListFriend = new JList(_JlistModelFriend);
        _ListFriend.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        _ListFriend.setForeground(new java.awt.Color(54, 117, 23));
        _ListFriend.setSelectionForeground(new java.awt.Color(16, 54, 103));
        //set list on ScrollPane
        _ListFriendScrollPane = new JScrollPane(_ListFriend);
        _ListFriendScrollPane.setBounds(0, 140 - 30, 260, 560 - 130);
        _ListFriendScrollPane.setVisible(true);
        _MainWindow.getContentPane().add(_ListFriendScrollPane);

        ///////////3333333333**LIST Show Friend***333333333///////////
        //////////////****************************Cac LIST END***********************************************////
        // _ChatWindow.**********************
        Font font = new Font("Arial", Font.ITALIC, 17);
        //  textArea.setFont(font);
        _ChatWindow.setForeground(new Color(178, 0, 31));
        _ChatWindow.setEditable(false);
        _ChatWindow.setColumns(20);
        _ChatWindow.setRows(5);
        _ChatWindow.setFont(font);
        _ListRoomScrollChatWindow = new JScrollPane(_ChatWindow);
        _ListRoomScrollChatWindow.setViewportView(_ChatWindow);
        _MainWindow.getContentPane().add(_ListRoomScrollChatWindow);
        _ListRoomScrollChatWindow.setBounds(270, 140 - 30, 600, 430);

        // _ChatWindow.**********************
        //_Chatmsg***********
        _Chatmsg.setForeground(new java.awt.Color(178, 0, 31));
        _Chatmsg.requestFocus();
        _Chatmsg.setFont(font);
        _MainWindow.getContentPane().add(_Chatmsg);
        _Chatmsg.setBounds(270, 650 - 50 - 30, 500, 35);

        //_jbsendchat
        _jbsendchat.setText("Send");
        _jbsendchat.setForeground(new Color(27, 79, 147));
        _MainWindow.getContentPane().add(_jbsendchat);
        _jbsendchat.setBounds(780, 665 - 50 - 40, 90, 25);

        //_ChatSlogan197	124	172
        _ChatSlogan.setForeground(new Color(143, 0, 109));
        _ChatSlogan.setText("Không yêu đừng nói lời trashtalk <3 ");
        _MainWindow.getContentPane().add(_ChatSlogan);
        _ChatSlogan.setBounds(270, 625 - 50 - 30, 300, 25);

        ////////////////*****INFO THONG TIN NG DUNG Star*****//////////////////
        //27	79	147
        //_nameinfo
        //  _nameinfo.setText("User Name:" + JsonCheckUser.getString("userName"));
        _nameinfo.setForeground(new Color(27, 79, 147));
        _MainWindow.getContentPane().add(_nameinfo);
        _nameinfo.setBounds(10, 20, 300, 50);
        //_EmailInfo
        _EmailInfo.setForeground(new Color(27, 79, 147));
        _MainWindow.getContentPane().add(_EmailInfo);
        _EmailInfo.setBounds(200, 20, 300, 50);
        //_PhoneInfo
        _PhoneInfo.setForeground(new Color(27, 79, 147));
        _MainWindow.getContentPane().add(_PhoneInfo);
        _PhoneInfo.setBounds(550, 20, 300, 50);

        JLabel a = new JLabel();
        a.setForeground(new Color(27, 79, 147));
        a.setText("---------------------------------------------------------------------------------------------------------------------------"
                + "----------------------------------------------------------------------------------------------------------------------------");
        _MainWindow.getContentPane().add(a);
        a.setBounds(0, 40, 1000, 50);

        //_jbsetinfo
        _jbsetinfo.setForeground(new Color(27, 79, 147));
        _jbsetinfo.setText("Set Info");
        _MainWindow.getContentPane().add(_jbsetinfo);
        _jbsetinfo.setBounds(700, 30, 90, 25);

        //_gioithieuinfo
        _gioithieuinfo.setForeground(new Color(27, 79, 147));
        _gioithieuinfo.setText("------THÔNG TIN NGƯỜI DÙNG------------");
        _MainWindow.getContentPane().add(_gioithieuinfo);
        _gioithieuinfo.setBounds(350, 0, 500, 50);
        /////

        ////////////////*****INFO THONG TIN NG DUNG END*****//////////////////
        //0,580
        ///_jbListFriend  229	70	70
        _jbListFriend.setText("List Friend");
        _jbListFriend.setForeground(new Color(27, 79, 147));
        _jbListFriend.setBackground(new java.awt.Color(254, 248, 134));
        _MainWindow.getContentPane().add(_jbListFriend);
        _jbListFriend.setBounds(0, 580 + 20 - 30, 95, 25);

        /////_jbDanhSachRoom
        _jbDanhSachRoom.setText("Room");
        _jbDanhSachRoom.setForeground(new Color(27, 79, 147));
        _MainWindow.getContentPane().add(_jbDanhSachRoom);
        _jbDanhSachRoom.setBounds(100, 580 + 20 - 30, 70, 25);

        ///ds moi Invite 
        _jbShowRequestKb.setText("Invite");
        _jbShowRequestKb.setForeground(new Color(27, 79, 147));
        _MainWindow.getContentPane().add(_jbShowRequestKb);
        _jbShowRequestKb.setBounds(175, 580 + 20 - 30, 90, 25);

        _jbAndFriend.setText("Add Friend");
        _jbAndFriend.setForeground(new Color(27, 79, 147));
        _MainWindow.getContentPane().add(_jbAndFriend);
        _jbAndFriend.setBounds(930 - 170, 110 - 30, 110, 25);

        //_jbCreateGroup
        _jbCreateGroup.setText("Create Group");
        _jbCreateGroup.setForeground(new Color(27, 79, 147));
        _MainWindow.getContentPane().add(_jbCreateGroup);
        _jbCreateGroup.setBounds(620 - 100, 110 - 30, 110, 25);

        //_jbInsertGroup
        _jbInsertGroup.setText("Insert Group");
        _jbInsertGroup.setForeground(new Color(27, 79, 147));
        _MainWindow.getContentPane().add(_jbInsertGroup);
        _jbInsertGroup.setBounds(780 - 140, 110 - 30, 110, 25);

        _AcceptInvite.setText("Accept");
        _AcceptInvite.setForeground(new Color(27, 79, 147));
        _MainWindow.getContentPane().add(_AcceptInvite);
        _AcceptInvite.setBounds(170, 110 - 30, 90, 25);
        _AcceptInvite.setVisible(true);

        _DeleteGroup.setText("Delete");
        _DeleteGroup.setForeground(new Color(27, 79, 147));
        _MainWindow.getContentPane().add(_DeleteGroup);
        //310, 170, 90, 25
        _DeleteGroup.setBounds(170, 110 - 30, 90, 25);
        _DeleteGroup.setVisible(false);

        ///
    }

}
