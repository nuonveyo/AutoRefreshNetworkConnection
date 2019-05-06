package com.veyo.autorefreshnetworkconnection.listener;

public abstract class StopReceiveDisconnectedListener implements OnNetworkConnectionChangeListener {
    private boolean mIsReadyReceiveConnectedListener;

    public boolean isReadyReceiveConnectedListener() {
        return mIsReadyReceiveConnectedListener;
    }

    public abstract boolean stopReceiveDisconnectedListener();

    @Override
    public void onConnected() {
        if (stopReceiveDisconnectedListener()) {
            mIsReadyReceiveConnectedListener = true;
        }
    }
}
