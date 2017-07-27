/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.client;

import chatgvn.control.CheckSendEmail;
import chatgvn.gui.GUIChat;
import chatgvn.gui.GUILogin;
import chatgvn.gui.GUIRegister;
import chatgvn.gui.GUIResetAccount;
import static chatgvn.utils.ApiProject.API_LOGIN_POST;
import static chatgvn.utils.ApiProject.API_REGISTER_POST;
import chatgvn.utils.HandleApi;
import chatgvn.utils.ValidateEmail;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
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
    private JSONObject info_action; 
    public ActionGUI(GUILogin _guiLogin) {
        this._guiLogin = _guiLogin;
        press();
    }

    private void press() {
        _guiLogin._jtfUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

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
                        String relust = null;
                        handleApi = new HandleApi();
                        try {
                            relust = handleApi.postApi(API_LOGIN_POST, jsonObject.toString());
                            System.out.println("kq:tra ve");
                            System.out.println(relust);
                            info_action = new JSONObject(relust);
                            if (info_action.get("status").equals("success")) {
                                _guiLogin._jfMainWindow.setFocusableWindowState(false);
                                _guiLogin._jfMainWindow.setVisible(false);
                                GUIChat guichat = new GUIChat(info_action);
                                guichat.buildWindowLogin();
                            }

                        } catch (IOException ex) {
                            log.warn("handle api eror 404");
                        } catch (JSONException ex) {
                            log.warn("handle api JSON eror 404");
                        }

                    }

                }
            }
        });

        _guiLogin._jtfPassWord.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

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
                    String relust = null;
                    handleApi = new HandleApi();
                    try {
                        relust = handleApi.postApi(API_LOGIN_POST, jsonObject.toString());
                        System.out.println("kq:tra ve");
                        System.out.println(relust);
                        info_action = new JSONObject(relust);
                        if (info_action.get("status").equals("success")) {
                            _guiLogin._jfMainWindow.setFocusableWindowState(false);
                            _guiLogin._jfMainWindow.setVisible(false);
                            GUIChat guichat = new GUIChat(info_action);
                            guichat.buildWindowLogin();
                        }

                    } catch (IOException ex) {
                        log.warn("handle api eror 404");
                    } catch (JSONException ex) {
                        log.warn("handle api JSON eror 404");
                    }

                }
            }
        });

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
                String relust = null;
                handleApi = new HandleApi();
                try {
                    relust = handleApi.postApi(API_LOGIN_POST, jsonObject.toString());
                    System.out.println("kq:tra ve");
                    System.out.println(relust);
                     info_action = new JSONObject(relust);
                    if (info_action.get("status").equals("success")) {
                        _guiLogin._jfMainWindow.setFocusableWindowState(false);
                        _guiLogin._jfMainWindow.setVisible(false);
                        GUIChat guichat = new GUIChat(info_action);
                        guichat.buildWindowLogin();
                    }

                } catch (IOException ex) {
                    log.warn("handle api eror 404");
                    System.exit(0);
                } catch (JSONException ex) {
                    log.warn("handle api JSON eror 404");
                    System.exit(0);
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

                if (userName.equals("") || passWord.equals("") || email.equals("") || questionSecrect.equals("")) {
                    guiRegister._jlTextWaring.setVisible(true);
                    return;
                }
                ValidateEmail a = new ValidateEmail();
                if (!a.isValidEmailAddress(email)) {
                    guiRegister._jlTextWaring.setVisible(true);
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("loginId", userName);    //1
                    jsonObject.put("userName", userName);   //2
                    jsonObject.put("password", passWord);   //3
                    jsonObject.put("password_question", "1");  //4
                    jsonObject.put("password_answer", questionSecrect);   //5
                    jsonObject.put("phone", phoneNumber);   //6
                    jsonObject.put("email", email);   //7                                  
                } catch (JSONException ex) {
                    log.warn("json error");
                }

                System.out.println(jsonObject.toString());
                System.out.println("url: " + API_REGISTER_POST);

                String relust;
                handleApi = new HandleApi();
                try {
                    relust = handleApi.postApi(API_REGISTER_POST, jsonObject.toString());
                    System.out.println("kq:tra ve");
                    System.out.println(relust);
                    JSONObject kq = new JSONObject(relust);
                    if (kq.get("status").equals("success")) {
                        actionResgisterSuccess(guiRegister);
                    } else {
                        guiRegister._jlTextWaring.setText("Created fail");
                        guiRegister._jlTextWaring.setVisible(true);
                    }
                } catch (IOException ex) {
                    log.warn("handle api eror 404");
                } catch (JSONException ex) {
                    log.warn("handle api eror 404 json");
                }
            }

        });
    }

    private void actionResgisterSuccess(final GUIRegister guiRegister) {
        guiRegister._jlTextWaring.setText("Created success");
        guiRegister._jbRegister.setVisible(false);
        guiRegister._jbBACK.setVisible(true);
        guiRegister._jlTextWaring.setVisible(true);

        guiRegister._jbBACK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _guiLogin._jfMainWindow.setVisible(true);
                guiRegister._jfWindowRegister.setVisible(false);
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
