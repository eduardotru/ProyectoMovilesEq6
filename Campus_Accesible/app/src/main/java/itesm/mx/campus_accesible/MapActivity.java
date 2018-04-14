package itesm.mx.campus_accesible;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import itesm.mx.campus_accesible.Mapa.AppDatabase;
import itesm.mx.campus_accesible.Mapa.DatabaseInitializer;
import itesm.mx.campus_accesible.Mapa.Punto;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private AppDatabase mDb;

    private ArrayList<Punto> allPuntos;

    private ArrayList<Punto> destinationPuntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mDb = AppDatabase.getInstance(getApplicationContext());
        populateDB();
        fetchData();

        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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

                LatLng position = marker.getPosition();
                double longitude = position.longitude;
                double latitude = position.latitude;

                fetchDestination(longitude,latitude);
                return false;
            }
        });

    }

    private void populateDB(){
        DatabaseInitializer.populate(mDb);
    }

    private void fetchData(){
        allPuntos = new ArrayList<>();
        List<Punto> puntos = mDb.puntoModel().getAll();
        for(Punto p : puntos){
            allPuntos.add(p);
        }

    }




    private void fetchDestination(double longitude, double latitude){
        destinationPuntos = new ArrayList<>();
        List<Punto> puntos = mDb.puntoModel().getDestination(longitude,latitude);
        System.out.println("SOS!!!"+puntos.size());
        for(Punto p : puntos){
            System.out.println(p.getName());
        }
    }
}
