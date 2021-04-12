package com.emrerenjs.socketmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.emrerenjs.socketmobile.Models.MessageModel;
import com.emrerenjs.socketmobile.Models.UserSocketModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class ChatActivity extends AppCompatActivity {

    private UserSocketModel userSocketModel;
    private EditText messageET;
    private Button sendMessageBTN;
    private Socket socket;

    /*eventEmitters*/
    private Emitter.Listener messageBroadcast = new Emitter.Listener(){
        @Override
        public void call(Object... args) {
            System.out.println(args[0]);
        }
    };

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.v("Socket Connect:","Connected!");
        }
    };
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.v("Socket Connect:",args[0].toString());
        }
    };
    private Emitter.Listener onDisconnect = new Emitter.Listener(){
        @Override
        public void call(Object... args) {
            Log.v("Socket Connect (Disconnect)",args[0].toString());
        }
    };
    /*eventEmitters*/

    private void bindViews(){
        messageET = findViewById(R.id.messageET);
        sendMessageBTN = findViewById(R.id.sendMessageBTN);
        sendMessageBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("username",userSocketModel.getUsername());
                    obj.put("message",messageET.getText().toString());
                    Log.v("Socket Connect",obj.toString());
                    socket.emit("messageEvent",obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        userSocketModel = (UserSocketModel) this.getIntent().getSerializableExtra("UserSocketModel");
        bindViews();
        connectSocket();
    }

    private void connectSocket(){
        try{
            socket = IO.socket("http://192.168.1.37:9092");
            socket.once(Socket.EVENT_CONNECT_ERROR,onConnectError);
            socket.once(Socket.EVENT_DISCONNECT,onDisconnect);
            socket.once(Socket.EVENT_CONNECT,onConnect);
            socket.on("messageBroadcast",messageBroadcast);
            socket.connect();

        }catch(URISyntaxException exception){
            Toast.makeText(this, "Bağlantı başarısız..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.off("messageBroadcast",messageBroadcast);
        socket.disconnect();
    }
}