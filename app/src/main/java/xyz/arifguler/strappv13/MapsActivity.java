package xyz.arifguler.strappv13;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    ArrayList<Double> Maker2,Maker1= new ArrayList<Double>();
    String[] in =new String[30];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Maker1=Foursquare.maker_x;
        Maker2=Foursquare.maker_y;
        in=Foursquare.yer_adi;
/*
        for(int i=0; Maker2.size()>i; i++) {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(Maker1.get(i), Maker2.get(i))).title("Hello Maps");
            mMap.addMarker(marker);
        }*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for(int i=0; Maker2.size()>i; i++) {

        MarkerOptions marker = new MarkerOptions().position(new LatLng(Maker1.get(i), Maker2.get(i))).title("Hello Maps");
        mMap.addMarker(marker);

        }
    }
}

/*package xyz.arifguler.strappv13;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    ArrayList<Double>  Maker2,Maker1= new ArrayList<Double>();
    String[] in =new String[30];
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Maker1=Foursquare.maker_x;
        Maker2=Foursquare.maker_y;
        in=Foursquare.yer_adi;

        for(int i=0; Maker2.size()>i; i++) {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(Maker1.get(i), Maker2.get(i))).title("Hello Maps");
            mMap.addMarker(marker);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng sydney = new LatLng(39.4, 38.25);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Elazığ"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps");
        //googleMap.addMarker(marker);

    }
}
*/