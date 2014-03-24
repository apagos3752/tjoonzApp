package maps.example.tjoonz;

import java.util.HashMap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tjoonz.app.R;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapsFrag extends Fragment  implements LocationListener{

	private SupportMapFragment fragment;
	private GoogleMap map;
	private LocationManager locationManager;
	private ServerStub server;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		server = new ServerStub();
		createMarker();
		return inflater.inflate(R.layout.activity_main, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		FragmentManager fm = getChildFragmentManager();
		fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
		if (fragment == null) {
			fragment = SupportMapFragment.newInstance();
			fm.beginTransaction().replace(R.id.map, fragment).commit();
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		if (map == null) {
			map = fragment.getMap();
			map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("yop yop"));
		}

		//Obtention de la référence du service
		locationManager = (LocationManager) this.getActivity().getSystemService(this.getActivity().LOCATION_SERVICE);

		//Si le GPS est disponible, on s'y abonne
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			abonnementGPS();
		}

		placeMarker();
	}

	public void abonnementGPS() {
		//On s'abonne
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
		map.setMyLocationEnabled(true);
	}

	public void desabonnementGPS() {
		//Si le GPS est disponible, on s'y abonne
		locationManager.removeUpdates(this);
		map.setMyLocationEnabled(false);
	}

	public void createMarker(){
		server.genMarkersInfo();
	}

	public void placeMarker(){
		HashMap<String, UserInfo> hm = server.getData();

		for(String key: hm.keySet()){
			UserInfo ui = hm.get(key);

			Marker markerUser = map.addMarker(new MarkerOptions().title(ui.getUsername()).snippet(ui.getCurrentSong()).position(new LatLng(ui.getLatitude(), ui.getLongitude())));
		}    	
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

}
