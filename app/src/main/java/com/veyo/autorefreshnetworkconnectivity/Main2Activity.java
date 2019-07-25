package com.veyo.autorefreshnetworkconnectivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.veyo.autorefreshnetworkconnection.AutoRefreshNetworkUtil;
import com.veyo.autorefreshnetworkconnection.CheckNetworkConnectionHelper;
import com.veyo.autorefreshnetworkconnection.listener.OnNetworkConnectionChangeListener;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = Main2Activity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView = findViewById(R.id.textView);
        textView.setOnClickListener(v -> {
            AutoRefreshNetworkUtil.removeAllRegisterNetworkListener();

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        CheckNetworkConnectionHelper
                .getInstance()
                .registerNetworkChangeListener(new OnNetworkConnectionChangeListener() {
                    @Override
                    public void onDisconnected() {
                        //Do your task on Network Disconnected!
                        Log.e(TAG, "onDisconnected");
                        textView.setText("Network Disconnected!");
                        textView.setTextColor(ContextCompat.getColor(Main2Activity.this, R.color.colorAccent));
                    }

                    @Override
                    public void onConnected() {
                        //Do your task on Network Connected!
                        Log.e(TAG, "onConnected");
                        textView.setText("Network Connected!");
                        textView.setTextColor(ContextCompat.getColor(Main2Activity.this, R.color.colorPrimary));
                    }

                    @Override
                    public Context getContext() {
                        return Main2Activity.this;
                    }
                });

        CheckNetworkConnectionHelper
                .getInstance()
                .registerNetworkChangeListener(new OnNetworkConnectionChangeListener() {
                    @Override
                    public void onDisconnected() {
                        //Do your task on Network Disconnected!
                        Log.e(TAG, "2 onDisconnected");
                        textView.setTextColor(ContextCompat.getColor(Main2Activity.this, R.color.colorAccent));
                    }

                    @Override
                    public void onConnected() {
                        //Do your task on Network Connected!
                        Log.e(TAG, "2 onConnected");
                    }

                    @Override
                    public Context getContext() {
                        return Main2Activity.this;
                    }
                });
    }
}
