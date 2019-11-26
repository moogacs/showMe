# Maps
## Maps structure
This is the maps view where the user can specify the place he wants to navigate to and start using the application.

## Classes
* [MainViewActivity](#MainViewActivity)
* [DirectionsJSONParser](#DirectionsJSONParser)

## Methods
* [onCreate](#onCreate)
* [startLocationUpdates](#startLocationUpdates)
* [locationCallback](#locationCallback)
* [stopLocationUpdates](#stopLocationUpdates)
* [onMapReady](#onMapReady)
* [getLocationPermission](#getLocationPermission)
* [getDeviceLocation](#getDeviceLocation)
* [updateLocationUI](#updateLocationUI)
* [drawRoute](#drawRoute)
* [getDirectionsUrl](#getDirectionsUrl)
* [downloadUrl](#downloadUrl)
* [DownloadTask](#DownloadTask)
* [onPostExecute](#onPostExecute)
* [ParserTask](#ParserTask)
* [parse](#parse)
* [parseTurnPoint](#parseTurnPoint)
* [decodePoly](#decodePoly)

### MainViewActivity
This class represents the main view activity of the application which shall be the google map and routing/direction got.

### Methods
onCreate()
> Set the map UI, then by using [NEARBY.md](NEARBY.md) startAdvertising() and startDiscovery() methods to start the Near by API for discovering the other peers for left and right directions
>From LocationServices gets the last known location
>Starts the Support Fragement for the Maps search and auto complete suggestion UI for [Places ](https://developers.google.com/places/android-sdk/autocomplete)

startLocationUpdates()
> It's a method for creating request to locate the device using  [requestLocationUpdates](https://developer.android.com/reference/android/location/LocationManager)  by making a requests as recommended by google for accurucy with a call back and it's called in onResume method and after the usere has chose a place

locationCallback
for every location got from the locaiton call back it's checked againest the prepared Left and Right turn points Locations in distance of <b>10(m)</b> and if so then it send a message to other devices through ShowMeNearby to vibrate based on the type of the turn if it's left or right.

```Java
	new LocationRequest().setPriority(100).setInterval(5)
```

stopLocationUpdates()
 >It stops requesting the current location and it's called in onPause method


onMapReady()
>onMapReady it fires autmatically by android system and then starts a 3 other methods  getLocationPermission(), updateLocationUI(), getDeviceLocation()

getLocationPermission()
> It checks the location permission if granted or not and If not make a request to allow it, which later on the flag mLocationPermissionGranted will allow the application to procced.

getDeviceLocation()
> It sets the map's camera position to the current location of the device.
onRequestPermissionsResult

updateLocationUI()
>If the location is allowed it shows detect my location icon on the map and if not it reset everything and call getLocationPermission again.
>

drawRoute()
> Getting URL to the Google Directions API and download <b>AsyncTask</b> and then executes it to be called in the background.

getDirectionsUrl(LatLng origin,LatLng dest)
>Building the direction url request by adding the current origin and the destnitation, the mode of the request(walking, driving .. ) and the API key to have in results.
```
https://maps.googleapis.com/maps/api/directions/outputFormat?parameters
```

downloadUrl(String strUrl) throws IOException
>Creating an http connection to communicate with url, then Connecting to url and  Reading data from url then return the data.


DownloadTask extends AsyncTask
>Downloading data in non-ui thread using doInBackground and Fetching the data from web service, then Executes in UI thread, after the execution of doInBackground() AsyncTask [details](https://developer.android.com/reference/android/os/AsyncTask)

onPostExecute
> is called after the AsyncTask finished and start parsing the result using <b>ParserTask</b>


ParserTask extends AsyncTask
>return routes point List

>It done as well in the background by createing a new object of <b>DirectionsJSONParser</b> the calling <b>parse(object)</b>  to parse the JSON results from the direction API url and by calling <b>parseTurnPoint(jObject)</b> it parse the Left and Right turn point in the direction route.

>After that task is finished Traversing through all the routes and Fetching all the points in i-th route and add them to the <b>map</b>
>adds the turn points to the lists left, right respectivlly.
>add the Polyline after it's created from the route list.

### DirectionsJSONParser
#### Methods
parse()
>Receives a JSONObject and returns a list of lists containing latitude and longitude
>Traversing all routes, legs,steps and points by going through the JSON objects inside the results sof the direction API request

parseTurnPoint()
> After Traversing the steps, searching for <b>maneuver</b> and then split them to left/right  Langitude and Latitude points.

decodePoly()
>Method to decode polyline points [src](jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java)
