/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.socket;

import java.net.URI;
import javax.websocket.*;

@ClientEndpoint
public class WSClient {

    private static Object waitLock = new Object();

    @OnMessage
    public void onMessage(String message) {
//the new USD rate arrives from the websocket server side.
        System.out.println("Received msg: " + message);
    }
    
    
    
    

    private static void wait4TerminateSignal() {
        synchronized (waitLock) {
            try {
                waitLock.wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) {
        WebSocketContainer container = null;//
        Session session = null;
        try {
            //Tyrus is plugged via ServiceLoader API. See notes above
            container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(WSClient.class, URI.create("ws://10.64.1.88:1234/chat"));
            wait4TerminateSignal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
            
        }
        
    }
    
}