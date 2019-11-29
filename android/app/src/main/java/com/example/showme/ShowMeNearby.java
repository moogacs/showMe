package com.example.showme;

import android.os.Vibrator;
import android.util.Log;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate.Status;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.annotation.NonNull;

public class ShowMeNearby {

    private static String leftEndpointId;
    private static String rightEndpointId;
    private static boolean firstDevice;

    private static final String TAG = MainViewActivity.class.getSimpleName();

    static Payload bytesPayload = Payload.fromBytes("Hello world".getBytes());
    static String SERVICE_ID = "com.example.showme";

    public static void startAdvertising() {
        AdvertisingOptions advertisingOptions =
                new AdvertisingOptions.Builder().setStrategy(Strategy.P2P_CLUSTER ).build();
        Nearby.getConnectionsClient(MainActivity.getContext())
                .startAdvertising("candidate", SERVICE_ID, mConnectionLifecycleCallback, advertisingOptions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // We're advertising!
                                Log.i(TAG, "Start nearby Advertising");
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // We were unable to start advertising.
                                Log.i(TAG, "Start nearby Advertising Fails");
                            }
                        });
    }





    public static void startDiscovery() {
        DiscoveryOptions discoveryOptions =
                new DiscoveryOptions.Builder().setStrategy(Strategy.P2P_CLUSTER ).build();
        Nearby.getConnectionsClient(MainActivity.getContext())
                .startDiscovery(SERVICE_ID, mEndpointDiscoveryCallback, discoveryOptions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // We're discovering!
                                Log.i(TAG, "Start nearby Dicovery");
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // We're unable to start discovering.
                                Log.i(TAG, "Start nearby Dicovery Fails");
                            }
                        });
    }



    private static final ConnectionLifecycleCallback mConnectionLifecycleCallback =
            new ConnectionLifecycleCallback() {
                @Override
                public void onConnectionInitiated(String endpointId, ConnectionInfo connectionInfo) {
                    // Automatically accept the connection on both sides.
                    Nearby.getConnectionsClient(MainActivity.getContext()).acceptConnection(endpointId, mPayloadCallback);
                    Log.i(TAG, "START NEARBY" );
                }

                @Override
                public void onConnectionResult(String endpointId, ConnectionResolution result) {
                    switch (result.getStatus().getStatusCode()) {
                        case ConnectionsStatusCodes.STATUS_OK:
                            // We're connected! Can now start sending and receiving data.
                            Log.i(TAG, "Status ok  " );
                            MainActivity.vibrate();
                            Log.i(TAG, endpointId );
                            if (firstDevice == true){
                                leftEndpointId = endpointId ;
                                firstDevice = false;
                            } else {
                                rightEndpointId = endpointId ;
                                firstDevice = true;
                            }

                            break;
                        case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                            // The connection was rejected by one or both sides.
                            Log.i(TAG, " STATUS_CONNECTION_REJECTED  " );
                            break;
                        case ConnectionsStatusCodes.STATUS_ERROR:
                            // The connection broke before it was able to be accepted.
                            Log.i(TAG, "STATUS_ERROR" );
                            break;
                        default:
                            // Unknown status code
                            Log.i(TAG, "nothing" );
                    }
                }

                @Override
                public void onDisconnected(String endpointId) {
                    // We've been disconnected from this endpoint. No more data can be
                    // sent or received.
                    Log.i(TAG, "START disconnected" );
                }
            };





    private static final EndpointDiscoveryCallback mEndpointDiscoveryCallback =
            new EndpointDiscoveryCallback() {
                @Override
                public void onEndpointFound(final String endpointId, DiscoveredEndpointInfo info) {
                    // An endpoint was found. We request a connection to it.
                    Nearby.getConnectionsClient(MainActivity.getContext())
                            .requestConnection( "bla", endpointId, mConnectionLifecycleCallback)
                            .addOnSuccessListener(
                                    new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            // We successfully requested a connection. Now both sides
                                            // must accept before the connection is established.
                                            Log.i(TAG, "success : " + unused);
                                            Nearby.getConnectionsClient(MainActivity.getContext()).sendPayload(endpointId, bytesPayload);
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Nearby Connections failed to request the connection.
                                            Log.i(TAG, "An error occurred: " + e);
                                        }
                                    });
                }

                @Override
                public void onEndpointLost(String endpointId) {
                    // A previously discovered endpoint has gone away.
                }
            };



    private static final PayloadCallback mPayloadCallback = new PayloadCallback() {
        @Override
        public void onPayloadReceived(String endpointId, Payload payload) {
            // A new payload is being sent over.
            byte[] receivedBytes = payload.asBytes();
            Log.i(TAG, "rec: " + receivedBytes.toString());
            SideViewActivity.alterTheText();
            SideViewActivity.vibrate();

        }

        @Override
        public void onPayloadTransferUpdate(String endpointId, PayloadTransferUpdate update) {
            // Payload progress has updated.
            if (update.getStatus() == Status.SUCCESS) {
                Log.i(TAG, "successful update");
            }
        }
    };


    public static void changeTheText() {
//        Log.i(TAG, "changeTheText was called ");
//        Nearby.getConnectionsClient(MainActivity.getContext()).sendPayload(leftEndpointId, bytesPayload);
//        Nearby.getConnectionsClient(MainActivity.getContext()).sendPayload(rightEndpointId, bytesPayload);

        MainActivity.vibrate();
    }

    public static void vibrateLeft(){
        Nearby.getConnectionsClient(MainActivity.getContext()).sendPayload(leftEndpointId, bytesPayload);
    }

    public static void vibrateRight(){
        Nearby.getConnectionsClient(MainActivity.getContext()).sendPayload(rightEndpointId, bytesPayload);
    }

}
