package com.example.tjoonz;
import java.util.HashMap;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements LocationListener {

	private LocationManager locationManager;
	private GoogleMap gMap;
	private ServerStub server;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    //////////-Gestion Google Map-//////////
        gMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        server = new ServerStub();
        createMarker();
        
    //////////-Gestion Accelerometre-//////////
        SensorManager handler = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }
    
    public void onResume() {
        super.onResume();

        //Obtention de la référence du service
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        //Si le GPS est disponible, on s'y abonne
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            abonnementGPS();
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();

        //On appelle la méthode pour se désabonner
        desabonnementGPS();
    }

    /**
    * Méthode permettant de s'abonner à la localisation par GPS.
    */
    public void abonnementGPS() {
        //On s'abonne
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
        gMap.setMyLocationEnabled(true);
        placeMarker();
    }

    /**
    * Méthode permettant de se désabonner de la localisation par GPS.
    */
    public void desabonnementGPS() {
        //Si le GPS est disponible, on s'y abonne
        locationManager.removeUpdates(this);
        gMap.setMyLocationEnabled(false);
    }

    @Override
    public void onLocationChanged(final Location location) {
        //Mise à jour des coordonnées
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());      
       //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    @Override
    public void onProviderDisabled(final String provider) {
        //Si le GPS est désactivé on se désabonne
        if("gps".equals(provider)) {
            desabonnementGPS();
        }        
    }

    @Override
    public void onProviderEnabled(final String provider) {
        //Si le GPS est activé on s'abonne
        if("gps".equals(provider)) {
            abonnementGPS();
        }
    }

    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) { }
    
    public void createMarker(){
    	server.genMarkersInfo();
    }
    
    public void placeMarker(){
    	HashMap<String, UserInfo> hm = server.getData();
    	
    	for(String key: hm.keySet()){
			UserInfo ui = hm.get(key);
			
			Marker markerUser = gMap.addMarker(new MarkerOptions().title(ui.getUsername()).snippet(ui.getCurrentSong()).position(new LatLng(ui.getLatitude(), ui.getLongitude())));
		}    	
    }
  }