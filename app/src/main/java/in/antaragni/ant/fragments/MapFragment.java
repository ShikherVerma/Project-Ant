package in.antaragni.ant.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import in.antaragni.ant.datahandler.DatabaseAccess;
import in.antaragni.ant.R;
import in.antaragni.ant.adapters.RotaTask;
import in.antaragni.ant.datamodels.Venue;


public class MapFragment extends Fragment {
  private GoogleMap map;
  private String from_loc,to_loc,from_coor,to_coor;
  private DatabaseAccess dba;
  public static MapFragment newInstance(String title) {
    MapFragment f = new MapFragment();
    Bundle args = new Bundle();

    args.putString("mapssssss", title);
    f.setArguments(args);

    return (f);
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_map, container, false);
    map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map)).getMap();
    map.getUiSettings().setMapToolbarEnabled(false);
    map.setMyLocationEnabled(true);
    //map setup

    dba = DatabaseAccess.getInstance(getActivity());

    final Context context = this.getActivity();
    Button button = (Button) v.findViewById(R.id.button);
    Spinner fromlist = (Spinner) v.findViewById(R.id.from_dropdown_list);
    Spinner tolist = (Spinner) v.findViewById(R.id.to_dropdown_list);
    String[] items = new String[] { "CCD", "MT", "Hall 2", "Hall 5", "Hall 1", "Hall 8","Hall 7", "Hall 4", "OAT", "CSE Canteen", "Canteen"  };
    dba.open();

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
      android.R.layout.simple_spinner_item, items);
    fromlist.setAdapter(adapter);
    tolist.setAdapter(adapter);
    fromlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        from_loc = (String) parent.getItemAtPosition(position);
        Venue from_venue = dba.getVenue(from_loc);
        from_loc = from_venue.getLatLng().toString();
        from_coor = from_loc.substring(10, from_loc.length() - 1);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
      }
    });
    tolist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        to_loc = (String) parent.getItemAtPosition(position);
        Venue to_venue = dba.getVenue(to_loc);
        to_loc = to_venue.getLatLng().toString();
        to_coor = to_loc.substring(10, to_loc.length() - 1);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
      }
    });
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        new RotaTask(context, map, from_coor, to_coor).execute();
      }
    });
    return v;
  }
}