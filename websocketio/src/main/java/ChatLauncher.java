import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatLauncher {

    public static void main(String[] args) throws InterruptedException, IOException {
        Configuration configuration = new Configuration();
        configuration.setHostname("192.168.1.35");
        configuration.setPort(9092);
        final SocketIOServer socketIOServer = new SocketIOServer(configuration);

        System.out.println("Soket dinleniyor..");

        socketIOServer.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("Someone connected!");
            }
        });

        socketIOServer.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                System.out.println("Someone disconnected!");
            }
        });

        socketIOServer.addEventListener("messageEvent", ChatPOJO.class, new DataListener<ChatPOJO>() {
            @Override
            public void onData(SocketIOClient socketIOClient, ChatPOJO chatPOJO, AckRequest ackRequest) throws Exception {
                socketIOServer.getBroadcastOperations().sendEvent("messageBroadcast",chatPOJO);
            }
        });
        socketIOServer.start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        while(!line.equalsIgnoreCase("q")){
            line = bufferedReader.readLine();
        }
        System.out.println("Soket sonlandırılıyor..");
        bufferedReader.close();
        socketIOServer.stop();
    }
}
