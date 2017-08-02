/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatgvn.socket;

/**
 *
 * @author Administrator
 */
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.websocket.ClientEndpoint;

/**
 *
 * @author Administrator
 */
@ClientEndpoint
public class main {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        WebsocketClientEndpoint a = new WebsocketClientEndpoint(new URI("ws://10.64.1.88:1234/chat"));
        a.sendMessage("123");
        a.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
            public void handleMessage(String message) {
                System.out.println(message);
            }
        });
        Thread.sleep(5000);
    }
}
