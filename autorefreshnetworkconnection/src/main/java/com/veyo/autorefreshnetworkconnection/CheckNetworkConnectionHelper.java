package com.veyo.autorefreshnetworkconnection;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.veyo.autorefreshnetworkconnection.listener.OnNetworkConnectionChangeListener;
import com.veyo.autorefreshnetworkconnection.receiver.NetworkBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

public class CheckNetworkConnectionHelper implements LifecycleObserver {
    private static final String TAG = CheckNetworkConnectionHelper.class.getName();
    private static CheckNetworkConnectionHelper sNetworkConnection = new CheckNetworkConnectionHelper();

    private AppCompatActivity mAppCompatActivity;
    private NetworkBroadcastReceiver mNetworkBroadcastReceiver;
    private List<OnNetworkConnectionChangeListener> mChangeListenerList;

    private NetworkBroadcastReceiver.NetworkState mNetworkState = NetworkBroadcastReceiver.NetworkState.NOTHING;

    public static CheckNetworkConnectionHelper getInstance() {
        return sNetworkConnection;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        Log.e(TAG, "onDestroy");
        removeStateChangeListener();
    }

    public void onNetworkConnectionChange(Activity activity,
                                          OnNetworkConnectionChangeListener onNetworkConnectionChangeListener) {
        if (activity == null) {
            return;
        }
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        if (mAppCompatActivity == null) {
            mAppCompatActivity = appCompatActivity;
        }
        appCompatActivity.getLifecycle().addObserver(this);

        checkAddNetworkChangeListener(onNetworkConnectionChangeListener);

        if (mChangeListenerList == null) {
            mChangeListenerList = new ArrayList<>();
        }
        mChangeListenerList.add(onNetworkConnectionChangeListener);

        if (mNetworkBroadcastReceiver == null) {
            mNetworkBroadcastReceiver = new NetworkBroadcastReceiver(networkState -> {
                mNetworkState = networkState;
                for (OnNetworkConnectionChangeListener listener : mChangeListenerList) {
                    checkAddNetworkChangeListener(listener);
                }
            });
            mAppCompatActivity.registerReceiver(mNetworkBroadcastReceiver,
                    mNetworkBroadcastReceiver.getIntentFilter());
        }
    }

    private void removeStateChangeListener() {
        if (mAppCompatActivity == null
                || mChangeListenerList == null
                || mChangeListenerList.isEmpty()) {
            return;
        }

        int lastIndex = mChangeListenerList.size() - 1;
        OnNetworkConnectionChangeListener listener = mChangeListenerList.get(lastIndex);

        mChangeListenerList.remove(listener);
        Log.e(TAG, "removeStateChangeListener: " + mChangeListenerList.size());
        if (mChangeListenerList.isEmpty()
                && mNetworkBroadcastReceiver != null) {
            mAppCompatActivity.unregisterReceiver(mNetworkBroadcastReceiver);
            mNetworkBroadcastReceiver = null;
        }
    }

    private void checkAddNetworkChangeListener(OnNetworkConnectionChangeListener listener) {
        if (mNetworkState == NetworkBroadcastReceiver.NetworkState.CONNECTED) {
            listener.onConnected();
        } else if (mNetworkState == NetworkBroadcastReceiver.NetworkState.DISCONNECTED) {
            listener.onDisconnected();
        }
    }
}
