package itesm.mx.campus_accesible.Mapa;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itesm.mx.campus_accesible.DB.AppDatabase;
import itesm.mx.campus_accesible.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mMapView;
    private GoogleMap mMap;
    private ArrayList<Punto> allPuntos;
    private OnFragmentInteractionListener mListener;
    private AppDatabase.DatabaseDelegate databaseDelegate;
    private LatLng origin;
    private LatLng dest;
    private boolean hasPickedOne;
    private boolean hasPickedTwo;


    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(ArrayList<Punto> puntos) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("puntos", puntos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            allPuntos = bundle.getParcelableArrayList("puntos");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);
        hasPickedOne = hasPickedTwo = false;

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(double[] uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            databaseDelegate = (AppDatabase.DatabaseDelegate) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for(Punto p : allPuntos){
            double longitude = p.getLongitude_coordinate();
            double latitude = p.getLatitude_coordinate();

            LatLng point = new LatLng(longitude, latitude);
            mMap.addMarker(new MarkerOptions().position(point).title("Marker in "+p.getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(point)
                    .zoom(17.5f).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                LatLng position = marker.getPosition();
                setPickedElements(position);
                return false;
            }
        });
    }

    private void setPickedElements(LatLng position){
        double longitude = position.longitude;
        double latitude = position.latitude;
        if(!hasPickedOne){
            origin = position;
            hasPickedOne = true;
        }else if(!hasPickedTwo && (longitude!= origin.longitude && latitude!=origin.latitude)){
            dest = position;
            hasPickedTwo = true;
        }
        if(hasPickedOne && hasPickedTwo){
            mMap.clear();
            addOriginDestMarker(origin);
            addOriginDestMarker(dest);
            drawPath();
        }
    }

    private void addOriginDestMarker(LatLng position){
        mMap.addMarker(new MarkerOptions().position(position).title("Origen")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .zoom(17.5f).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(double[] arr);
    }

    /*
     *
     *               All path drawing methods
     *
     *               Classes, methods and attributes obtained by
     *               George Mathew during his following tutorial:
     *               Route directions for driving mode, bicycling and walking mode in Google Map Android API V2
     *               <http://wptrafficanalyzer.in/blog/route-directions-for-driving-mode-bicycling-and-walking-mode-in-google-map-android-api-v2/>
     *
     */

    private void drawPath(){
        String url = getMapsDirectionsUrl(origin, dest);
        ReadTask downloadTask = new ReadTask();
        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }

    //      Get direction URL required to use Google Maps API
    private String  getMapsDirectionsUrl(LatLng origin,LatLng dest) {
        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Walking option
        String mode = "mode=walking";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor+"&"+mode;

        // Output format
        String output = "json";



        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    // URL class, it performas a URL call in the background

    private class ReadTask extends AsyncTask<String, Void , String> {

        @Override
        protected String doInBackground(String... url) {
            // TODO Auto-generated method stub
            String data = "";
            try {
                MapHttpConnection http = new MapHttpConnection();
                data = http.readUr(url[0]);
            } catch (Exception e) {
                // TODO: handle exception
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }

    }

    private class ParserTask extends AsyncTask<String,Integer, List<List<HashMap<String , String >>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {
            // TODO Auto-generated method stub
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(15);
                polyLineOptions.color(Color.BLUE);
            }

            mMap.addPolyline(polyLineOptions);

        }
    }


}