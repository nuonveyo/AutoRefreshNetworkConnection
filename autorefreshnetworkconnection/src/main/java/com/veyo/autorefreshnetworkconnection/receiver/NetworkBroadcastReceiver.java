package com.veyo.autorefreshnetworkconnection.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.veyo.autorefreshnetworkconnection.utils.NetworkStateUtil;

public class NetworkBroadcastReceiver extends BroadcastReceiver {
    private NetworkListener mNetworkListener;
    private IntentFilter mIntentFilter;

    public NetworkBroadcastReceiver(NetworkListener changeListener) {
        mNetworkListener = changeListener;
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mNetworkListener == null) {
            return;
        }

        if (intent.getAction() != null
                && intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            boolean isNetworkAvailable = NetworkStateUtil.isNetworkAvailable(context);
            if (isNetworkAvailable) {
                mNetworkListener.onNetworkState(NetworkState.CONNECTED);
            } else {
                mNetworkListener.onNetworkState(NetworkState.DISCONNECTED);
            }
        }
    }

    public IntentFilter getIntentFilter() {
        return mIntentFilter;
    }

    public interface NetworkListener {
        void onNetworkState(NetworkState networkState);
    }

    public enum NetworkState {
        CONNECTED,
        DISCONNECTED,
        NOTHING
    }
}
