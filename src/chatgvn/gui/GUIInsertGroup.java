/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.gui;

import static chatgvn.utils.ApiProject.API_AddGroup_POST;
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

    public static JButton _JBAddListInvite = new JButton();
    public JButton _JBCreateGroup = new JButton();

    public JLabel _JLNameGroup = new JLabel();
    public JLabel _JLGioiThieu = new JLabel();
    public JLabel _JLabelAddName = new JLabel();
    public JLabel _JLabelAddID = new JLabel();

    public static JLabel status = new JLabel();
    public JLabel status2 = new JLabel();
    public static JButton _jbCreateGroupxxx;
    public static JSONObject xxxJSONObjectGroupNow;
    public static JSONObject xxxtokenlogin;

    public GUIInsertGroup(JButton _par, JSONObject b, JSONObject c) {
        this._jbCreateGroupxxx = _par;
        this.xxxJSONObjectGroupNow = b;
        this.xxxtokenlogin = c;
        System.out.println("=============KKKKKKKKKKKKKKKKKKKKKKKKKKKKK==============");
        try {
            System.out.println(xxxtokenlogin.get("token"));
            System.out.println(xxxJSONObjectGroupNow.getString("_id"));
            System.out.println(xxxJSONObjectGroupNow.getString("groupType"));
            _AddID.setText("");
            _AddName.setText("");

        } catch (JSONException ex) {
            System.out.println("LOI o GUIInsertGroup");
        }

        System.out.println("=============KKKKKKKKKKKKKKKKKKKKKKKKKKKKK==============");

    }

    public void buildWindowLogin() {
        handlingclose();
        try {
            _MainWindow.setTitle("InsertGroupxx ->>>> " + xxxJSONObjectGroupNow.getString("groupName"));
        } catch (JSONException ex) {
            System.out.println("set tieu de loi ");
        }
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
                    //                   System.out.println("============================PAR==========");
//                    System.out.println(xxxtokenlogin.get("token"));
//                    System.out.println(xxxJSONObjectGroupNow.getString("_id"));                   
//                    System.out.println(xxxJSONObjectGroupNow.getString("groupType"));
//                    System.out.println(_AddID.getText());
//                    System.out.println(_AddName.getText());
                    //                  System.out.println("======================PAR==========");
                    if (_AddName.getText().equals("") || _AddID.getText().equals("")) {
                        throw new Exception("input error kien!!!!");
                    }
                    JSONObject Input = new JSONObject();
                    Input.put("auth_token", xxxtokenlogin.get("token").toString());
                    Input.put("userid", _AddID.getText());
                    Input.put("username", _AddName.getText());
                    Input.put("id", xxxJSONObjectGroupNow.getString("_id"));
                    System.out.println("============================PAR==========");
                    System.out.println(Input);
                    System.out.println("======================PAR==========");

                    HandleApi handle = new HandleApi();
                    String StrOutPutApi = handle.postApi(API_AddGroup_POST, Input.toString());
                    System.out.println("======aaaaaa=======================");
                    System.out.println(StrOutPutApi);
                    JSONObject kq = new JSONObject(StrOutPutApi);
                    if (kq.getString("message").equals("Add success")) {
                        status.setText("Thành Công rồi đới !!!!!!!!");
                        _AddID.setText("");
                        _AddName.setText("");
                    } else if (kq.getString("message").equals("User has exited")) {

                        status.setText("Đã có trong group !!!!!!!!");
                        _AddID.setText("");
                        _AddName.setText("");
                    }
                    System.out.println("========aaaaaa===============================");
                } catch (JSONException ex) {
                    status.setText("LOI ROI MAY OI");
                    _AddID.setText("");
                    _AddName.setText("");
                } catch (Exception ex) {
                    status.setText("LOI ROI MAY OI Nhap thong tin de");
                    _AddID.setText("");
                    _AddName.setText("");
                }

            }
        });

    }

    private void handlingclose() {
        // _MainWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        _MainWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                _MainWindow.dispose();
            }

            public void windowClosing(java.awt.event.WindowEvent evt) {
                _jbCreateGroupxxx.setEnabled(true);

            }

        });

    }

    private void configureWindowLogin() {
        _MainWindow.getContentPane().setLayout(null);
        _MainWindow.getContentPane().setBackground(new java.awt.Color(148, 170, 214));

        _JLGioiThieu.setText("Nhap ten && loginID them vao:");

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
