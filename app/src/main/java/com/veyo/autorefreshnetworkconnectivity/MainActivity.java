package com.veyo.autorefreshnetworkconnectivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.veyo.autorefreshnetworkconnection.CheckNetworkConnectionHelper;
import com.veyo.autorefreshnetworkconnection.listener.StopReceiveDisconnectedListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView);

        CheckNetworkConnectionHelper
                .getInstance()
                .registerNetworkChangeListener(new StopReceiveDisconnectedListener() {
                    @Override
                    public void onDisconnected() {
                        //Do your task on Network Disconnected!
                        Log.e(TAG, "onDisconnected");
                        textView.setText("Network Disconnected!");
                        textView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
                    }

                    @Override
                    public void onNetworkConnected() {
                        //Do your task on Network Connected!
                        Log.e(TAG, "onConnected");
                        textView.setText("Network Connected!");
                        textView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                    }

                    @Override
                    public Context getContext() {
                        return MainActivity.this;
                    }
                });

        findViewById(R.id.button).setOnClickListener(v ->
                startActivity(new Intent(this, Main2Activity.class)));
    }
}
