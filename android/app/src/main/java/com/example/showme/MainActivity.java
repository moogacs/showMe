package com.example.showme;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ServerSocket mServerSocket;
    private int mLocalPort;
    private NsdServiceInfo mServiceInfo;
    private NsdManager mNsdManager;
    private NsdManager.RegistrationListener mRegistrationListener;
    private String mServiceName;
    private List<NsdServiceInfo> mDiscoveredServices = new ArrayList<>();
    private Vibrator vibrator;
    public static int leftView = 0, rightView = 0, mainView = 0;

    private NsdManager.DiscoveryListener mDiscoveryListener = new NsdManager.DiscoveryListener() {
        @Override
        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
        }

        @Override
        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
        }

        @Override
        public void onDiscoveryStarted(String serviceType) {
            Log.d("DEBUG","Discovery started.");
        }

        @Override
        public void onDiscoveryStopped(String serviceType) {

        }

        @Override
        public void onServiceFound(NsdServiceInfo serviceInfo) {
            if(serviceInfo.getServiceType().equals(mServiceInfo.getServiceType()) && !serviceInfo.getServiceName().equals(mServiceName)){
                mDiscoveredServices.add(serviceInfo);
                Log.d("DEBUG","Service found and added.");
                vibrator = (Vibrator) getSystemService(MainActivity.VIBRATOR_SERVICE);
                vibrator.vibrate(1000);
            }
        }

        @Override
        public void onServiceLost(NsdServiceInfo serviceInfo) {

        }
    };


    private NsdManager.ResolveListener mResolveListener = new NsdManager.ResolveListener() {
        @Override
        public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
            mServiceInfo = serviceInfo;
            mLocalPort = mServiceInfo.getPort();
        }

        @Override
        public void onServiceResolved(NsdServiceInfo serviceInfo) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeServerSocket();
        initializeRegistrationListener();
        registerService();
        mNsdManager.discoverServices("_showMe._tcp.", NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
        mNsdManager.resolveService(mServiceInfo,mResolveListener);
        Log.d("Port","" + mLocalPort);
        Log.d("Name","" + mServiceInfo.getServiceName());
        Log.d("Type","" + mServiceInfo.getServiceType());

        openDialog();
    }

    public void openDialog() {
        MainDialog mainDialog = new MainDialog();
        mainDialog.show(getSupportFragmentManager(), "Main Dialog");
        mainDialog.setCancelable(false);
    }

    public void initializeServerSocket(){
        try{
        mServerSocket = new ServerSocket(0);
        mLocalPort = mServerSocket.getLocalPort();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void registerService(){
        mServiceInfo = new NsdServiceInfo();
        mServiceInfo.setServiceName("myApp"+ Math.random());
        mServiceInfo.setServiceType("_showMe._tcp.");
        mServiceInfo.setPort(mLocalPort);

        mNsdManager = (NsdManager) this.getSystemService(Context.NSD_SERVICE);
        try {
            mNsdManager.registerService(mServiceInfo, NsdManager.PROTOCOL_DNS_SD, mRegistrationListener);
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public void initializeRegistrationListener(){
        mRegistrationListener = new NsdManager.RegistrationListener() {
            @Override
            public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e("RegistrationListener", "Registration failed.");
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e("RegistrationListener", "Unregistration failed.");
            }

            @Override
            public void onServiceRegistered(NsdServiceInfo serviceInfo) {
                Log.d("RegistrationListener", "Service registered.");
                mServiceName = serviceInfo.getServiceName();
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo serviceInfo) {
                Log.d("RegistrationListener", "Service unregistered.");
            }
        };

    }

    public void tearDown(){
        mNsdManager.unregisterService(mRegistrationListener);
        mNsdManager.stopServiceDiscovery(mDiscoveryListener);
    }


}
