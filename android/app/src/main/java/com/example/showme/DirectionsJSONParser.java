package com.example.showme;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectionsJSONParser {
    /** Receives a JSONObject and returns a list of lists containing latitude and longitude */
    public List<List<HashMap<String,String>>> parse(JSONObject jObject){

        List<List<HashMap<String, String>>> routes = new ArrayList<>();

        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;

        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for(int i=0;i<jRoutes.length();i++){
                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                List path = new ArrayList<HashMap<String, String>>();

                /** Traversing all legs */
                for(int j=0;j<jLegs.length();j++){
                    jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");


//                    String maneuver =jSteps[].get("maneuver");
//                    Log.i("impo", maneuver);

                    /** Traversing all steps */
                    for(int k=0;k<jSteps.length();k++){
                        String polyline = "";
                        polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
//
//
//                        try {
//                            String maneuver = (String) ((JSONObject) jSteps.get(k)).get("maneuver");
//                            if(maneuver.indexOf("right") > -1){
//                                Number lat = (Number)((JSONObject)((JSONObject)jSteps.get(k)).get("start_location")).get("lat");
//                                Number lng = (Number) ((JSONObject)((JSONObject)jSteps.get(k)).get("start_location")).get("lng");
//                                Log.i("impo",     maneuver);
//                                Log.i("impo",    lat.toString());
//                                Log.i("impo",    lng.toString());
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                        List<LatLng> list = decodePoly(polyline);

                        /** Traversing all points */
                        for(int l=0;l<list.size();l++){
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
                            hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
        }
        return routes;
    }


    public List<HashMap<String, HashMap<String, Double>>> parseTurnPoint(JSONObject jObject){

        List<HashMap<String, HashMap<String, Double>>> turnPoints = new ArrayList<>() ;
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;

        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for(int i=0;i<jRoutes.length();i++){
                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");

                /** Traversing all legs */
                for(int j=0;j<jLegs.length();j++){
                    jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                    /** Traversing all steps */
                    for(int k=0;k<jSteps.length();k++){
                        try {
                            String maneuver = (String) ((JSONObject) jSteps.get(k)).get("maneuver");
                            if(maneuver.indexOf("right") > -1){
                                Double lat = (Double)((JSONObject)((JSONObject)jSteps.get(k)).get("start_location")).get("lat");
                                Double lng = (Double) ((JSONObject)((JSONObject)jSteps.get(k)).get("start_location")).get("lng");

                                HashMap<String, Double> LatLang = new HashMap<>();
                                HashMap<String, HashMap<String, Double>> hm = new HashMap<>();

                                LatLang.put("lat", lat);
                                LatLang.put("lng", lng);
                                hm.put("right", LatLang);
                                turnPoints.add(hm);
                            }

                            if(maneuver.indexOf("left") > -1){
                                Double lat = (Double)((JSONObject)((JSONObject)jSteps.get(k)).get("start_location")).get("lat");
                                Double lng = (Double) ((JSONObject)((JSONObject)jSteps.get(k)).get("start_location")).get("lng");

                                HashMap<String, Double> LatLang = new HashMap<>();
                                HashMap<String, HashMap<String, Double>> hm = new HashMap<>();

                                LatLang.put("lat", lat);
                                LatLang.put("lng", lng);
                                hm.put("left", LatLang);
                                turnPoints.add(hm);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
        }

        return turnPoints;
    }

    /**
     * Method to decode polyline points
     * Courtesy : jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }
}
