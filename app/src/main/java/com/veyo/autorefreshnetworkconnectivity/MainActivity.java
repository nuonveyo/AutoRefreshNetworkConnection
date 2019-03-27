package com.veyo.autorefreshnetworkconnectivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.veyo.autorefreshnetworkconnection.CheckNetworkConnectionHelper;
import com.veyo.autorefreshnetworkconnection.listener.OnNetworkConnectionChangeListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);

        CheckNetworkConnectionHelper checkNetworkConnectionHelper = CheckNetworkConnectionHelper.getInstance();
        checkNetworkConnectionHelper.onNetworkConnectionChange(this,
                new OnNetworkConnectionChangeListener() {
                    @Override
                    public void onConnected() {
                        //Do your task on Network Connected!
                        Log.e(TAG, "onConnected");
                        textView.setText("Network Connected!");
                        textView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                    }

                    @Override
                    public void onDisconnected() {
                        //Do your task on Network Disconnected!
                        Log.e(TAG, "onDisconnected");
                        textView.setText("Network Disconnected!");
                        textView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
                    }
                });

        findViewById(R.id.button).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Main2Activity.class)));

    }
}
