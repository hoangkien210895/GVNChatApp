/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.gui;

/**
 *
 * @author Administrator
 */
import static chatgvn.utils.ApiProject.API_CHECK_USERNAME_POST;
import static chatgvn.utils.ApiProject.API_ChangPass_POST;
import static chatgvn.utils.ApiProject.API_SET_USERNAME_POST;
import chatgvn.utils.HandleApi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class GUIInfo {

    private String logInId;
    private String auth_token;

    public JFrame _MainWindow = new JFrame();
    public JTextField _JTFChangName = new JTextField();
    public JButton _JBsend = new JButton();
    public JLabel _JLChangeName = new JLabel();
    public JButton _jbsetinfo = new JButton();
    //below
    public JLabel _JLChangePass = new JLabel();
    public JTextField _JTFChangPass = new JTextField();
    public JButton _JBsendPass = new JButton();
    public JLabel _JLquestion = new JLabel();
    public JTextField _JTFquestion = new JTextField();

    ///
      public JLabel _JLChangeoke = new JLabel();
    
    public void buildWindowLogin() {
        pess();
        handlingclose();
        _MainWindow.setTitle("Set Info");
        _MainWindow.setSize(300, 400);
        _MainWindow.setResizable(true);
        _MainWindow.setLocationRelativeTo(null);
        configureWindowLogin();
        _MainWindow.setVisible(true);

    }

    public GUIInfo(JButton setinfo, String logInIds, String token) {
        this.logInId = logInIds;
        this._jbsetinfo = setinfo;
        this.auth_token = token;
    }

    private void handlingclose() {
        //   _MainWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        _MainWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {

            }

            public void windowClosing(java.awt.event.WindowEvent evt) {
                _jbsetinfo.setEnabled(true);
            }

        });

    }

    private void pess() {
        _JBsend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("this fucking par");
                System.out.println(logInId);
                System.out.println(auth_token);
                HandleApi handle = new HandleApi();
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("userName", _JTFChangName.getText());
                    jsonObject.put("loginId", logInId);
                    jsonObject.put("auth_token", auth_token);
                    String StrChangeUser = handle.postApi(API_SET_USERNAME_POST, jsonObject.toString());
                    System.out.println(StrChangeUser);
                    GUIChat.HandingCheckUser();
                } catch (JSONException ex) {
                    Logger.getLogger(GUIInfo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GUIInfo.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        
           _JBsendPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("this fucking par");
                System.out.println(logInId);
                System.out.println(auth_token);
                HandleApi handle = new HandleApi();
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("loginId", logInId);
                    jsonObject.put("password", _JTFChangPass.getText());
                    jsonObject.put("password_question", "1");
                    jsonObject.put("password_answer",_JTFquestion.getText());
                    String StrChangeUser = handle.postApi(API_ChangPass_POST, jsonObject.toString());
                    System.out.println(StrChangeUser);
                     JSONObject kq = new JSONObject(StrChangeUser);
                     if(kq.get("status").equals("success")){
                         _JLChangeoke.setText("=====Thành Công=====");
                     }
                    
                } catch (JSONException ex) {
                    Logger.getLogger(GUIInfo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GUIInfo.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        
    }

    private void configureWindowLogin() {

        _MainWindow.getContentPane().setLayout(null);
        _MainWindow.getContentPane().setBackground(new java.awt.Color(148, 170, 214));
        ////
        _JTFChangName.setForeground(new java.awt.Color(0, 0, 255));
        _MainWindow.getContentPane().add(_JTFChangName);
        _JTFChangName.setBounds(70, 50, 150, 25);
        //91	189	43
        _JLChangeName.setText("Nhập tên cần thay đôi:");
        _MainWindow.getContentPane().add(_JLChangeName);
        _JLChangeName.setBounds(70, 20, 150, 25);
        //button
        _JBsend.setText("Chang Name");
        _MainWindow.getContentPane().add(_JBsend);
        _JBsend.setBounds(70, 80, 150, 25);
        
           /////////////////////////
        _JLChangePass.setText("Nhập Pass mới :");
        _MainWindow.getContentPane().add(_JLChangePass);
        _JLChangePass.setBounds(70, 120, 150, 25);

        _JTFChangPass.setForeground(new java.awt.Color(0, 0, 255));
        _MainWindow.getContentPane().add(_JTFChangPass);
        _JTFChangPass.setBounds(70, 150, 150, 25);

        _JLquestion.setText("Nhập Câu Trả Lời Bí Mật :");
        _MainWindow.getContentPane().add(_JLquestion);
        _JLquestion.setBounds(70, 170, 150, 25);

        _JTFquestion.setForeground(new java.awt.Color(0, 0, 255));
        _MainWindow.getContentPane().add(_JTFquestion);
        _JTFquestion.setBounds(70, 190, 150, 25);

        _JBsendPass.setText("Change Pass");
        _MainWindow.getContentPane().add(_JBsendPass);
        _JBsendPass.setBounds(70, 230, 150, 25);
       
        
        _JLChangeoke.setText("=====----=====");
        _MainWindow.getContentPane().add(_JLChangeoke);
        _JLChangeoke.setBounds(100, 260, 150, 25);

    }

}
