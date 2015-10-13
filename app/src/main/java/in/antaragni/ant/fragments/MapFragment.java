package in.antaragni.ant.fragments;


import android.animation.ValueAnimator;
import android.content.Context;


import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import in.antaragni.ant.R;
import in.antaragni.ant.adapters.RotaTask;
import in.antaragni.ant.datahandler.DatabaseAccess;
import in.antaragni.ant.datamodels.Venue;


public class MapFragment extends Fragment implements
  GoogleApiClient.ConnectionCallbacks,
  GoogleApiClient.OnConnectionFailedListener,
  LocationListener
{
  private GoogleMap map;
  private String from_loc, to_loc, from_coor, to_coor;
  private DatabaseAccess dba;
  private GoogleApiClient mGoogleApiClient;
  private LocationRequest mLocationRequest;

  public static MapFragment newInstance(String title)
  {
    MapFragment f = new MapFragment();
    Bundle args = new Bundle();

    args.putString("venue", title);
    f.setArguments(args);

    return (f);
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
      .addConnectionCallbacks(this)
      .addOnConnectionFailedListener(this)
      .addApi(LocationServices.API)
      .build();
    View v = inflater.inflate(R.layout.fragment_map, container, false);
    Bundle arguments = getArguments();
    String title = arguments.getString("venue");
    map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map)).getMap();
    map.getUiSettings().setMapToolbarEnabled(false);
    map.setMyLocationEnabled(true);

    mLocationRequest = LocationRequest.create()
      .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
      .setInterval(10 * 1000)        // 10 seconds, in milliseconds
      .setFastestInterval(1 * 1000); // 1 second, in milliseconds
    //map setup

    dba = DatabaseAccess.getInstance(getActivity());

    final Context context = this.getActivity();
    Button button = (Button) v.findViewById(R.id.button);
    Spinner fromlist = (Spinner) v.findViewById(R.id.from_dropdown_list);
    Spinner tolist = (Spinner) v.findViewById(R.id.to_dropdown_list);
    String[] from_items = new String[]{"From","My Location" ,"CCD", "MT", "Hall 2", "Hall 5", "Hall 1", "Hall 8", "Hall 7", "Hall 4", "OAT", "CSE Canteen", "Canteen"};
    String[] to_items = new String[]{"To","CCD", "MT", "Hall 2", "Hall 5", "Hall 1", "Hall 8", "Hall 7", "Hall 4", "OAT", "CSE Canteen", "Canteen"};
    dba.open();
    if (title != null) {
      Venue init_pos = dba.getVenue(title);
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(init_pos.getLatLng(), 15));
      final Marker marker = map.addMarker(new MarkerOptions().position(init_pos.getLatLng()).title(title).alpha(0));
      ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
      animator.setDuration(500);
      animator.setStartDelay(1000);
      animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
          float scale = (float) animation.getAnimatedValue();
          marker.setAlpha(scale);
        }
      });
      animator.start();
    }
    else {
      LatLng iitk = new LatLng(26.512339,80.232900);
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(iitk, 15));
      Marker marker = map.addMarker(new MarkerOptions().position(iitk)
        .title(title));
    }

    CameraPosition temp = map.getCameraPosition();
    Log.wtf("qwe", temp.toString());
    ArrayAdapter<String> from_adapter = new ArrayAdapter<String>(this.getActivity(),
      android.R.layout.simple_spinner_item, from_items);
    ArrayAdapter<String> to_adapter = new ArrayAdapter<String>(this.getActivity(),
      android.R.layout.simple_spinner_item, to_items);
    if (title != null) {
      fromlist.setAdapter(from_adapter);
      tolist.setAdapter(to_adapter);
      tolist.setSelection(to_adapter.getPosition(title));
    }
    else {
      fromlist.setAdapter(from_adapter);
      tolist.setAdapter(to_adapter);
    }
    fromlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
      {
        from_loc = (String) parent.getItemAtPosition(position);
        if (from_loc == "From") {
          //Do  nothing
        }
        else if (from_loc == "My Location") {

        }
        else {
          Venue from_venue = dba.getVenue(from_loc);
          from_loc = from_venue.getLatLng().toString();
          from_coor = from_loc.substring(10, from_loc.length() - 1);
        }

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent)
      {
        //to do something
      }
    });
    tolist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
      {
        to_loc = (String) parent.getItemAtPosition(position);
        if (to_loc != "To") {
          Venue to_venue = dba.getVenue(to_loc);
          to_loc = to_venue.getLatLng().toString();
          to_coor = to_loc.substring(10, to_loc.length() - 1);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent)
      {
        // TODO Auto-generated method stub
      }
    });
    button.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        if ((from_loc != "From") && (to_loc != "To")) {
          new RotaTask(context, map, from_coor, to_coor).execute();
        }
        else {
          Toast.makeText(context, "Please select locations", Toast.LENGTH_LONG).show();
        }
      }
    });
    return v;
  }




  private boolean isGooglePlayServicesAvailable() {
    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
    if (ConnectionResult.SUCCESS == status) {
      return true;
    } else {
      GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 0).show();
      return false;
    }
  }

  @Override
  public void onConnected(Bundle bundle)
  {
    Log.wtf("qwe","connected");
    Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    if (location == null) {
      LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }
    else {
      handleNewLocation(location);
    }
  }

  @Override
  public void onConnectionSuspended(int i)
  {

  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult)
  {

  }

  private void handleNewLocation(Location location) {
    double currentLatitude = location.getLatitude();
    double currentLongitude = location.getLongitude();
    Log.wtf("qwe", "handle");
  }

  @Override
  public void onLocationChanged(Location location)
  {
    handleNewLocation(location);
  }
}