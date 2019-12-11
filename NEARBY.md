# Nearby Connections
This is the google Nearby Connections API, we use it to establish a connection between the three devices.

## Classes
* [ShowMeNearby](#ShowMeNearby)

## Methods
* [startAdvertising](#startAdvertising)
* [startDiscovery](#startDiscovery)
* [setStrategy](#setStrategy)
* [onConnectionInitiated](#onConnectionInitiated)
* [onConnectionResult](#onConnectionResult)
* [sendPayload](#sendPayload)
* [vibrateLeft](#vibrateLeft)
* [vibrateRight](#vibrateRight)


### ShowMeNearby
In this class we implement all the google Nearby Connections API methods that allow apps to easily discover, connect to, and exchange data with nearby devices in real-time, regardless of network connectivity.

### Methods
##### startAdvertising()
> The device starts advertising itselfs to nearby devices, The [ConnectionLifecycleCallback parameter](https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/ConnectionLifecycleCallback) is the callback that will be invoked when discoverers request to connect to the advertiser.

##### startDiscovery()
> The device starts looking for nearby devices that are advertising.

##### setStrategy(STRATEGY)
> Here we set one of the [Strategies](https://developers.google.com/nearby/connections/strategies) supported by the Nearby Connections API, in our case we chose the P2P_CLUSTER strategy as it fullfils our requirements and is the easiest strategy to implement .

##### onConnectionInitiated(String endpointId, ConnectionInfo connectionInfo)
> This method of the [ConnectionLifecycleCallback callback](https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/ConnectionLifecycleCallback) class notify both sides of the connection initiation.

##### onConnectionResult(String endpointId, ConnectionResolution result)
> This method of the [ConnectionLifecycleCallback callback](https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/ConnectionLifecycleCallback) class gives us the state of the connection with the other device, if the two devices connected successfully then we assign the device's endpointId to either the leftEndpointId or the rightEndpointId static private variables to differentiate when sending the vibration signal between the left and the right device.

##### sendPayload()
> Once connections are established between devices, we can exchange data by sending Payload objects via this method and receiving Payload objects in an implementation of a [PayloadCallback](https://developers.google.com/android/reference/com/google/android/gms/nearby/connection/PayloadCallback.html).

##### vibrateLeft()
> We use this method to send a Payload to the left device to vibrate when we need turn left.

##### vibrateRight()
>  We use this method to send a Payload to the right device to vibrate when we need turn right.
