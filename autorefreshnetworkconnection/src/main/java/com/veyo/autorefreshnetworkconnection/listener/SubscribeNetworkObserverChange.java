package com.veyo.autorefreshnetworkconnection.listener;

import com.veyo.autorefreshnetworkconnection.receiver.NetworkBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veyo on 15-Jul-2019.
 */
public abstract class SubscribeNetworkObserverChange implements Subject {

    private List<OnNetworkConnectionChangeListener> mNetworkObserverList;

    @Override
    public void registerObserver(OnNetworkConnectionChangeListener listener) {
        if (mNetworkObserverList == null) {
            mNetworkObserverList = new ArrayList<>();
        }

        if (!mNetworkObserverList.contains(listener)) {
            mNetworkObserverList.add(listener);
        }
    }

    @Override
    public void unregisterObserver(OnNetworkConnectionChangeListener listener) {
        mNetworkObserverList.remove(listener);
    }

    @Override
    public void notifyNetworkObserverChange(NetworkBroadcastReceiver.NetworkState networkState) {
        if (mNetworkObserverList != null && !mNetworkObserverList.isEmpty()) {
            for (OnNetworkConnectionChangeListener listener : mNetworkObserverList) {
                if (networkState == NetworkBroadcastReceiver.NetworkState.CONNECTED) {
                    listener.onConnected();
                } else if (networkState == NetworkBroadcastReceiver.NetworkState.DISCONNECTED) {
                    listener.onDisconnected();
                }
            }
        }
    }

    public List<OnNetworkConnectionChangeListener> getNetworkObserverList() {
        if (mNetworkObserverList == null) {
            mNetworkObserverList = new ArrayList<>();
        }
        return mNetworkObserverList;
    }
}
