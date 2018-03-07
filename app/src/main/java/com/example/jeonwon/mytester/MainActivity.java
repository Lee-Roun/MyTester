package com.example.jeonwon.mytester;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = (Button)findViewById(R.id.buttonLgn);
        Button btnJoin = (Button)findViewById(R.id.buttonJoin);
        Button btnFind = (Button)findViewById(R.id.buttonFind);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch(view.getId()){
                    case R.id.buttonFind:

                        break;
                    case R.id.buttonJoin:
                        Intent intent = new Intent(MainActivity.this, SubActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.buttonLgn:

                        break;
                }

            }
        };

        btnJoin.setOnClickListener(listener);
        btnFind.setOnClickListener(listener);
        btnLogin.setOnClickListener(listener);

    }
}
