package com.veyo.autorefreshnetworkconnection;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.veyo.autorefreshnetworkconnection.listener.OnNetworkConnectionChangeListener;
import com.veyo.autorefreshnetworkconnection.listener.StopReceiveDisconnectedListener;
import com.veyo.autorefreshnetworkconnection.receiver.NetworkBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

public class CheckNetworkConnectionHelper implements LifecycleObserver {
    private static final String TAG = CheckNetworkConnectionHelper.class.getName();
    private static CheckNetworkConnectionHelper sNetworkConnection = new CheckNetworkConnectionHelper();

    private AppCompatActivity mAppCompatActivity;
    private Context mCurrentContext;
    private NetworkBroadcastReceiver mNetworkBroadcastReceiver;
    private List<OnNetworkConnectionChangeListener> mChangeListenerList;

    private NetworkBroadcastReceiver.NetworkState mNetworkState = NetworkBroadcastReceiver.NetworkState.NOTHING;

    public static CheckNetworkConnectionHelper getInstance() {
        return sNetworkConnection;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        removeStateChangeListener();
    }

    public void onNetworkConnectionChange(Activity activity,
                                          OnNetworkConnectionChangeListener onNetworkConnectionChangeListener) {
        if (activity == null) {
            return;
        }
        mCurrentContext = activity;
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        if (mAppCompatActivity == null) {
            mAppCompatActivity = appCompatActivity;
        }
        appCompatActivity.getLifecycle().addObserver(this);
        List<OnNetworkConnectionChangeListener> networkListeners =
                getNewListeners(onNetworkConnectionChangeListener);
        checkAddNetworkChangeListener(networkListeners);

        if (mChangeListenerList == null) {
            mChangeListenerList = new ArrayList<>();
        }
        mChangeListenerList.addAll(networkListeners);

        if (mNetworkBroadcastReceiver == null) {
            mNetworkBroadcastReceiver = new NetworkBroadcastReceiver(networkState -> {
                mNetworkState = networkState;
                List<OnNetworkConnectionChangeListener> listener = getLastNetworkChangeListener();
                checkAddNetworkChangeListener(listener);
            });
            mAppCompatActivity.registerReceiver(mNetworkBroadcastReceiver,
                    mNetworkBroadcastReceiver.getIntentFilter());
        }
    }

    private List<OnNetworkConnectionChangeListener> getNewListeners(OnNetworkConnectionChangeListener listener) {
        List<OnNetworkConnectionChangeListener> listeners = new ArrayList<>();
        listeners.add(listener);
        return listeners;
    }

    private void removeStateChangeListener() {
        if (mAppCompatActivity == null
                || mChangeListenerList == null
                || mChangeListenerList.isEmpty()) {
            return;
        }

        List<OnNetworkConnectionChangeListener> listeners = getLastNetworkChangeListener();
        Log.e(TAG, "remove: " + listeners.size());
        for (OnNetworkConnectionChangeListener listener : listeners) {
            mChangeListenerList.remove(listener);
        }

        if (mChangeListenerList.isEmpty()
                && mNetworkBroadcastReceiver != null) {
            mAppCompatActivity.unregisterReceiver(mNetworkBroadcastReceiver);
            mCurrentContext = null;
            mNetworkBroadcastReceiver = null;
        } else {
            int lastIndex = mChangeListenerList.size() - 1;
            mCurrentContext = mChangeListenerList.get(lastIndex).getContext();

            List<OnNetworkConnectionChangeListener> lastNetworkChangeListener = getLastNetworkChangeListener();
            checkAddNetworkChangeListener(lastNetworkChangeListener);
        }
    }

    private void checkAddNetworkChangeListener(List<OnNetworkConnectionChangeListener> listeners) {
        for (OnNetworkConnectionChangeListener listener : listeners) {
            if (listener == null || (listener instanceof StopReceiveDisconnectedListener
                    && ((StopReceiveDisconnectedListener) listener).isReadyReceiveConnectedListener())) {
                continue;
            }

            if (mNetworkState == NetworkBroadcastReceiver.NetworkState.CONNECTED) {
                listener.onConnected();
            } else if (mNetworkState == NetworkBroadcastReceiver.NetworkState.DISCONNECTED) {
                listener.onDisconnected();
            }
        }
    }

    private List<OnNetworkConnectionChangeListener> getLastNetworkChangeListener() {
        List<OnNetworkConnectionChangeListener> dataList = new ArrayList<>();
        for (OnNetworkConnectionChangeListener listener : mChangeListenerList) {
            Log.e(TAG, "My Context: " + listener.getContext()
                    + " == " + mCurrentContext);
            if (listener.getContext() != null
                    && listener.getContext().equals(mCurrentContext)) {
                dataList.add(listener);
            }
        }
        return dataList;
    }
}
