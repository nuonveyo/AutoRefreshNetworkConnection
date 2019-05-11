# AutoRefreshNetworkConnection

AutoRefreshNetworkConnection is a library that give you a very simple code and easy to use it. It can handle your tasks when the device connected or disconnected from internet (wifi or mobile data).

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
    implementation 'com.veyo:autorefreshnetworkconnection:1.0.4'
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

      CheckNetworkConnectionHelper.getInstance().onNetworkConnectionChange(this, new StopReceiveDisconnectedListener() {
        @Override
        public void onConnected() {
            super.onConnected();
            //Do your task on Network Connected!
            Log.e(TAG, "onConnected");
        }

        @Override
        public void onDisconnected() {
            //Do your task on Network Disconnected!
            Log.e(TAG, "onDisconnected");
        }

        @Override
        public boolean stopReceiveDisconnectedListener() {
            return true;
        }

        @Override
        public Context getContext() {
            return MainActivity.this;
        }
    });
  }
}
```
Class StopReceiveDisconnectedListener will override three methods:
- onConnected(): this method will listen when network connected. call super.onConnected() when you override stopReceiveDisconnectedListener() with return true, do this mean that you don't want to listen callback again after onConnected() method worked!
- onDisconnected(): this method will listen when network disconnected.
- stopReceiveDisconnectedListener(): override this with default return false, if true the listener will not work again after onConnected() method worked!
- getContext(): override to return current context for multiple-listener in the same context can listener when network connected or disconnected 

you can also using class OnNetworkConnectionChangeListener instance. The listener will call when network connected or disconnected.
