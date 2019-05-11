package com.veyo.autorefreshnetworkconnection.listener;

import android.content.Context;

public interface OnNetworkConnectionChangeListener {
    void onConnected();

    void onDisconnected();

    Context getContext();
}
