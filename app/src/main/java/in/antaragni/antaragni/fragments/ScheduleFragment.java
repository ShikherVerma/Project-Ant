package in.antaragni.antaragni.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.antaragni.antaragni.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {
  private static final String KEY_TITLE = "title";

  public ScheduleFragment() {
    // Required empty public constructor
  }

  public static ScheduleFragment newInstance(String title) {
    ScheduleFragment f = new ScheduleFragment();

    Bundle args = new Bundle();

    args.putString(KEY_TITLE, title);
    f.setArguments(args);

    return (f);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    // don't look at this layout it's just a listView to show how to handle the keyboard
    return inflater.inflate(R.layout.fragment_schedule, container, false);
  }
}
