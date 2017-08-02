/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.client;

import chatgvn.gui.GUIChat;
import chatgvn.gui.GUILogin;
import chatgvn.gui.GUIResetAccount;

/**
 *
 * @author Phan Huy
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GUILogin loginGui = new GUILogin();
        ActionGUI actionGui = new ActionGUI(loginGui);
                 
        displayLogin(loginGui);
        actionLogin(actionGui);
        actionRegister(actionGui);
        actionForget(actionGui);

        
        
    }

    private static void displayLogin(GUILogin loginGui) {
        loginGui.buildWindowLogin();
    }

    private static void actionLogin(ActionGUI actionGui) {
        actionGui.actionClickButtonLogin();
    }

    private static void actionRegister(ActionGUI actionGui) {
        actionGui.actionClickLabelRegister();
    }

    private static void actionForget(ActionGUI actionGui) {

        actionGui.actionClickLabelForget();
    }

}
