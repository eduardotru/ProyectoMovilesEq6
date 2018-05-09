package itesm.mx.campus_accesible.Mapa;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
    private ArrayList<Edge> edges;
    private OnFragmentInteractionListener mListener;
    private AppDatabase.DatabaseDelegate databaseDelegate;
    private LatLng origin;
    private LatLng dest;
    private boolean hasPickedOne;
    private boolean hasPickedTwo;
    private FloatingActionButton fab;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(ArrayList<Punto> puntos, ArrayList<Edge> edges) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("puntos", puntos);
        args.putParcelableArrayList("edges",edges);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            allPuntos = bundle.getParcelableArrayList("puntos");
            edges = bundle.getParcelableArrayList("edges");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container,
                false);
        fab = (FloatingActionButton) v.findViewById(R.id.fab_mapeditor);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRutes();
            }
        });
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
        addMarkers();
    }


    private void setPickedElements(LatLng position){
        double longitude = position.longitude;
        double latitude = position.latitude;
        if(!hasPickedOne){
            origin = position;
            hasPickedOne = true;
        }else if(!hasPickedTwo && (longitude!= origin.longitude || latitude!=origin.latitude)){
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

    private void clearRutes(){
        mMap.clear();
        hasPickedOne = false;
        hasPickedTwo = false;
        addMarkers();

    }

    private void addMarkers(){
        for(Punto p : allPuntos){
            double longitude = p.getLongitude_coordinate();
            double latitude = p.getLatitude_coordinate();

            LatLng point = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(point)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(point)
                    .zoom(17.3f).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.chosen_marker));
                LatLng position = marker.getPosition();
                setPickedElements(position);
                return false;
            }
        });
    }




    private void addOriginDestMarker(LatLng position){
        mMap.addMarker(new MarkerOptions().position(position)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.chosen_marker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .zoom(17.5f).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            double lat_origin = savedInstanceState.getDouble("lat_origin");

            double long_origin = savedInstanceState.getDouble("long_origin");
            System.out.println(lat_origin+" "+long_origin);
            origin = new LatLng(lat_origin,long_origin);

            double lat_dest = savedInstanceState.getDouble("lat_dest");
            double long_dest = savedInstanceState.getDouble("long_dest");
            dest = new LatLng(lat_dest,long_dest);


            addOriginDestMarker(origin);
            addOriginDestMarker(dest);
            drawPath();

        }
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("lat_origin",origin.latitude);
        outState.putDouble("long_origin",origin.longitude);
        outState.putDouble("lat_dest", dest.latitude);
        outState.putDouble("long_dest",dest.longitude);

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
        ReadTask downloadTask = new ReadTask();
        // Start downloading json data from Google Directions API
        downloadTask.execute(origin, dest);
    }

    private class ReadTask extends AsyncTask<LatLng, Void , ArrayList<LatLng>> {

        @Override
        protected ArrayList<LatLng> doInBackground(LatLng... params) {
            /*

             * Agregar la lista de edges
             * */
            ShortestPath sp = new ShortestPath(edges, allPuntos);
            return sp.shortestPath(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(ArrayList<LatLng> result) {
            PolylineOptions polyLineOptions = new PolylineOptions();

            polyLineOptions.addAll(result);
            polyLineOptions.width(15);
            polyLineOptions.color(Color.BLUE);

            mMap.addPolyline(polyLineOptions);

        }

    }
}