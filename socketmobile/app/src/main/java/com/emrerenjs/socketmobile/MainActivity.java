package com.emrerenjs.socketmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.emrerenjs.socketmobile.Models.UserSocketModel;

public class MainActivity extends AppCompatActivity {

    private EditText usernameET,groupnameET;
    private Button connectBTN;

    private void bindViews(){
        usernameET = findViewById(R.id.usernameET);
        groupnameET = findViewById(R.id.groupnameET);
        connectBTN = findViewById(R.id.connectBTN);
        connectBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ChatActivity.class);
                intent.putExtra("UserSocketModel",new UserSocketModel(
                        usernameET.getText().toString(),
                        groupnameET.getText().toString()
                ));
                startActivity(intent);
                finish();
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }
}