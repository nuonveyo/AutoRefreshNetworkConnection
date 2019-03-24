# AutoRefreshNetworkConnection

AutoRefreshNetworkConnection is a library that give you a very simple code and easy use it. It's handle your task when the device connected or disconnected from internet (fiwi or mobile data). 

## Configuration

it's simple to config. open build.gradle file in your app module:

```bash
1. Enable java 8:
compileOptions {
  sourceCompatibility JavaVersion.VERSION_1_8
  targetCompatibility JavaVersion.VERSION_1_8
}

2. Add dependency:
implementation 'com.veyo:autorefreshnetworkconnection:1.0.0'
```

## Usage

```python
CheckNetworkConnectionHelper.getInstance().onNetworkConnectionChange(this,
                new OnNetworkConnectionChangeListener() {
                    @Override
                    public void onConnected() {
                        //do your task on network connected!
                    }

                    @Override
                    public void onDisconnected() {
                        //do your task on network disconnected!
                    }
                });
```
