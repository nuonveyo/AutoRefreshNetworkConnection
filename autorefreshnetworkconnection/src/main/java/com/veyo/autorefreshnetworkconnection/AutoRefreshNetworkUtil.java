package com.veyo.autorefreshnetworkconnection;

/**
 * Created by Veyo on 25-Jul-2019.
 */
public final class AutoRefreshNetworkUtil {
    private AutoRefreshNetworkUtil() {
    }

    public static void removeAllRegisterNetworkListener() {
        CheckNetworkConnectionHelper
                .getInstance()
                .unregisterNetworkChangeListener();
    }
}
