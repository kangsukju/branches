package websocket;
import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;

public class Client extends WebSocketClient {

    public Client(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public Client(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("new connection opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

    static int num = 0;
    @Override
    public void onMessage(String message) {
        System.out.println("received message: " + message);
        
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.send("from client: "+num++);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occured:" + ex);
    }

    public static void main(String[] args) throws URISyntaxException {      
        WebSocketClient client = new Client(new URI("ws://192.168.0.8:8887"), new Draft_10());
        client.connect();
    }
}


