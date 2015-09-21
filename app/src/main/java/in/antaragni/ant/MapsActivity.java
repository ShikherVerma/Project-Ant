package in.antaragni.ant;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity
{

  private GoogleMap mMap; // Might be null if Google Play services APK is not available.
  private String loc;
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    setUpMapIfNeeded();
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    setUpMapIfNeeded();
  }


  private void setUpMapIfNeeded()
  {
    // Do a null check to confirm that we have not already instantiated the map.
    if (mMap == null)
    {
      // Try to obtain the map from the SupportMapFragment.
      mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
        .getMap();
      // Check if we were successful in obtaining the map.
      if (mMap != null)
      {
        setUpMap();
      }
    }

  }


  private void setUpMap()
  {
    mMap.addMarker(new MarkerOptions().position(new LatLng(26.511992, 80.234258)).title("CCD"));
    mMap.addMarker(new MarkerOptions().position(new LatLng(26.512067, 80.230352)).title("MT"));
    mMap.addMarker(new MarkerOptions().position(new LatLng(26.510955, 80.229986)).title("HALL 2 Canteen & Mess"));
    mMap.addMarker(new MarkerOptions().position(new LatLng(26.509622, 80.227903)).title("HALL 5 Canteen & Mess"));
    mMap.addMarker(new MarkerOptions().position(new LatLng(26.507096, 80.232132)).title("HALL 4 Canteen"));
    mMap.addMarker(new MarkerOptions().position(new LatLng(26.505086, 80.229315)).title("OAT"));
    mMap.addMarker(new MarkerOptions().position(new LatLng(26.504937, 80.228492)).title("HALL 8 Canteen"));
    mMap.addMarker(new MarkerOptions().position(new LatLng(26.507174, 80.227829)).title("HALL 7 Canteen"));
    mMap.addMarker(new MarkerOptions().position(new LatLng(26.509563, 80.231411)).title("HALL 1 Canteen"));
    mMap.addMarker(new MarkerOptions().position(new LatLng(26.514107, 80.234055)).title("CSE Canteen"));
    mMap.addMarker(new MarkerOptions().position(new LatLng(26.514636, 80.231832)).title("Canteen"));
    if (loc == null){
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.511992, 80.234258), 17));
    }
    else if (loc == "MT"){
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.512067, 80.230352), 17));
    }
    else if (loc == "HALL 2 Canteen & Mess"){
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.510955, 80.229986), 17));
    }
    else if (loc == "HALL 5 Canteen & Mess"){
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.509622, 80.227903), 17));
    }
    else if (loc == "HALL 4 Canteen"){
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.507096, 80.232132), 17));
    }
    else if (loc == "OAT"){
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.505086, 80.229315), 17));
    }
    else if (loc == "HALL 8 Canteen"){
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.504937, 80.228492), 17));
    }
    else if (loc == "HALL 7 Canteen"){
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.507174, 80.227829), 17));
    }
    else if (loc == "HALL 1 Canteen"){
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.509563, 80.231411), 17));
    }
    else if (loc == "CSE Canteen"){
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.514107, 80.234055), 17));
    }
    else if (loc == "Canteen"){
      mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.514107, 80.234055), 17));
    }
    mMap.setMyLocationEnabled(true);
    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    mMap.getUiSettings().setMapToolbarEnabled(true);

  }


}
