package in.antaragni.antaragni.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.antaragni.antaragni.R;
import in.antaragni.antaragni.adapters.SampleListAdapter;

/**
 * Created by shikher on 29/6/15.
 */
public class RecyclerViewFragment extends Fragment
{
  // Store instance variables
  private String title;
  private int page;

  // newInstance constructor for creating fragment with arguments
  public static RecyclerViewFragment newInstance(int page, String title) {
    RecyclerViewFragment fragmentFirst = new RecyclerViewFragment();
    Bundle args = new Bundle();
    args.putInt("someInt", page);
    args.putString("someTitle", title);
    fragmentFirst.setArguments(args);
    return fragmentFirst;
  }

  // Store instance variables based on arguments passed
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    page = getArguments().getInt("someInt", 0);
    title = getArguments().getString("someTitle");
  }

  // Inflate the view for the fragment based on layout XML
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
    TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
    tvLabel.setText(page + " -- " + title);

    RecyclerView rv = (RecyclerView) view.findViewById(R.id.sample_recycler_view);
    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    rv.setAdapter(new SampleListAdapter());

    return view;
  }

}