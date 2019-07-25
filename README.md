# AutoRefreshNetworkConnection

AutoRefreshNetworkConnection is a library that give you a very simple code and easy to use it. It can handle your tasks when the device connected or disconnected from internet (wifi or mobile data) by using observer pattern. 

## Installation

it's simple to config. open build.gradle file in your app module:

```bash
1. Enable java 8:
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}

2. Add dependency:
    dependencies {
    implementation 'com.veyo:autorefreshnetworkconnection:1.0.6'
}
```

Note: Make sure you have added uses-permission in AndroidManifest file
```bash
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
```

## Usage

```python
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      CheckNetworkConnectionHelper
                .getInstance()
                .registerNetworkChangeListener(new StopReceiveDisconnectedListener() {
                    @Override
                    public void onDisconnected() {
                        //Do your task on Network Disconnected!
                        Log.e(TAG, "onDisconnected");
                    }

                    @Override
                    public void onNetworkConnected() {
                        //Do your task on Network Connected!
                        Log.e(TAG, "onConnected");

                    }

                    @Override
                    public Context getContext() {
                        return MainActivity.this;
                    }
                });
}
```
StopReceiveDisconnectedListener: will perform operation on network connected and disconnected by providing current context of your activity or fragment. It's useful when you doesn't want to listen onDisconnected method call again and again after network connected.

You can also using OnNetworkConnectionChangeListener: will perform operation on network connected and disconnected by providing current context. It's useful you want to listen onConnected or onDisconnected method call again and again whenever network state change.

## Note
All listeners which were registered in the same context (fragment or activity etc.) will be call on network connectivity change state connected/disconnected. 


```python
AutoRefreshNetworkUtil.removeAllRegisterNetworkListener()
```

call AutoRefreshNetworkUtil.removeAllRegisterNetworkListener() before all acitivitys/fragments will be destory immediately to clear all listeners.
