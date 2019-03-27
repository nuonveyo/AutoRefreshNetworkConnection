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
  implementation 'com.veyo:autorefreshnetworkconnection:1.0.2'
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

        CheckNetworkConnectionHelper.getInstance()
                .onNetworkConnectionChange(this,
                        new OnNetworkConnectionChangeListener() {
                            @Override
                            public void onConnected() {
                                //Do your task on Network Connected!
                            }

                            @Override
                            public void onDisconnected() {
                                //Do your task on Network Disconnected!
                            }
                        });
    }
}

```
