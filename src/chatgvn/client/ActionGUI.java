/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.client;

import chatgvn.control.CheckSendEmail;
import chatgvn.gui.GUILogin;
import chatgvn.gui.GUIRegister;
import chatgvn.gui.GUIResetAccount;
import static chatgvn.utils.ApiProject.API_LOGIN_POST;
import chatgvn.utils.HandleApi;
import chatgvn.utils.ValidateEmail;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Phan Huy
 */
public class ActionGUI {
    
    Logger log = Logger.getLogger(ActionGUI.class.getName());
    private GUILogin _guiLogin;
    private String emailRegister;
    private CheckSendEmail checkSendEmail;
    private HandleApi handleApi;
    
    public ActionGUI(GUILogin _guiLogin) {
        this._guiLogin = _guiLogin;
    }
    
    public void actionClickButtonLogin() {
        _guiLogin._jbLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = _guiLogin._jtfUserName.getText().trim();
                String passWord = _guiLogin._jtfPassWord.getText().trim();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("loginId", userName);
                    jsonObject.put("password", passWord);
                } catch (JSONException ex) {
                   log.warn("json error");
                }
             
                System.out.println(jsonObject.toString());
                System.out.println("url: " + API_LOGIN_POST);
                handleApi = new HandleApi();
                try {
                    handleApi.postApi(API_LOGIN_POST, jsonObject.toString());
                } catch (IOException ex) {
                    System.out.println("handle api eror 404");
                }
                
            }
        });
    }
    
    public void actionClickLabelForget() {
        _guiLogin._jlForgot.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mEvent) {
                _guiLogin._jfMainWindow.setVisible(false);
                GUIResetAccount guiReset = new GUIResetAccount();
                guiReset.buildWindowReset();
                getEmailReset(guiReset);
                
            }
            
        });
    }
    
    public void actionClickLabelRegister() {
        _guiLogin._jbRegister.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                _guiLogin._jfMainWindow.setVisible(false);
                GUIRegister guiRegister = new GUIRegister();
                guiRegister.buildWindowRegister();
                
                actionButtonRegister(guiRegister);
                
            }
            
        });
    }
    
    private void actionButtonRegister(final GUIRegister guiRegister) {
        guiRegister._jbRegister.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                String userName = guiRegister._jtfUserName.getText().trim();
                String passWord = guiRegister._jtfPassWord.getText().trim();
                String email = guiRegister._jtfEmail.getText().trim();
                String phoneNumber = guiRegister._jtfPhoneNumber.getText().trim();
                String questionSecrect = guiRegister._jtfQuestionSecret.getText().trim();
                System.out.println(userName.length());
                if (userName.equals(null) || passWord == "" || email == "" || phoneNumber == "" || questionSecrect == "") {
                    guiRegister._jlTextWaring.setVisible(true);
                }
            }
        });
    }
    
    private void getEmailReset(final GUIResetAccount guiReset) {
        
        guiReset._jbSendEmail.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent aEvent) {
                emailRegister = guiReset._jtfEmail.getText();
                guiReset._jtfEmail.setText("");
                ValidateEmail validateEmail = new ValidateEmail();
                
                if (!validateEmail.isValidEmailAddress(emailRegister)) {
                    guiReset._jlNotification.setText("email not invalid");
                    return;
                }
                checkSendEmail = new CheckSendEmail();
                if (checkSendEmail.checkDatabaseEmail(emailRegister)) {
                    beSendEmailReset(guiReset);
                } else {
                    guiReset._jlNotification.setText("email not correct");
                    return;
                }
            }
            
        });
    }
    
    private void beSendEmailReset(GUIResetAccount guiReset) {
        guiReset.buildWindowSendEmail();
    }
}
