/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.gui;

import chatgvn.client.ActionGUI;
import static chatgvn.utils.ApiProject.API_LOGOUT_POST;
import chatgvn.utils.HandleApi;
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

    private JSONObject _info_chat;
    public static String USER_NAME = "UserName";
    public static String LOGIN = "Login";
    public JFrame _MainWindow = new JFrame();
    public JTextField _Chatmsg = new JTextField();
    public JTextArea _ChatWindow = new JTextArea();
    public JButton _jbsendchat = new JButton();
    public JLabel _ChatSlogan = new JLabel();
    public JLabel _mineinfo = new JLabel();
    Logger log = Logger.getLogger(ActionGUI.class.getName());
    //list online
    public JList _ListOnline;
    public DefaultListModel _JlistModel;
    public JScrollPane _ListOnlineScrollPane;
    //
    
    public GUIChat(JSONObject info) {
        this._info_chat =info;
        
    }

    
    public void buildWindowLogin() {
        press();
        handlingclose();
        _MainWindow.setTitle("ChatGvn");
        _MainWindow.setSize(1000, 800);
        _MainWindow.setResizable(true);
        _MainWindow.setLocationRelativeTo(null);
        configureWindowLogin();
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
    }

    private void handlingclose() {
        _MainWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        _MainWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
               
                System.exit(0);
            }

            public void windowClosing(java.awt.event.WindowEvent evt) {
                   HandleApi handle  = new HandleApi();
                try {
                    System.out.println("hiihi:");
                  String j =  handle.postApi(API_LOGOUT_POST,_info_chat.getString("token") );
                    System.out.println(j);
                    
                } catch (IOException ex) {
                    
                } catch (JSONException ex) {
                    
                }
            }

        });
    }

    private void configureWindowLogin() {
        //    _MainWindow.setBackground(new java.awt.Color(0, 0, 0));
        _MainWindow.getContentPane().setLayout(null);
        _MainWindow.getContentPane().setBackground(new java.awt.Color(148, 170, 214));

        //create 1 list
        //set model list
        _JlistModel = new DefaultListModel();
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
         _mineinfo.setText(_info_chat.toString());
        _MainWindow.getContentPane().add(_mineinfo);
        _mineinfo.setBounds(0, 0, 300, 25);
    }
}
