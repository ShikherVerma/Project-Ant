package in.antaragni.ant.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.antaragni.ant.R;
import in.antaragni.ant.adapters.EventsViewPagerAdapter;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class EventFragment extends Fragment {
  private static final String KEY_TITLE = "title";

  public EventFragment() {
    // Required empty public constructor
  }

  public static EventFragment newInstance(String title) {
    EventFragment f = new EventFragment();

    Bundle args = new Bundle();

    args.putString(KEY_TITLE, title);
    f.setArguments(args);

    return (f);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    // Inflate the layout for this fragment
    // don't look at this layout it's just a listView to show how to handle the keyboard
    View result = inflater.inflate(R.layout.fragment_events, container, false);
    ViewPager pager = (ViewPager) result.findViewById(R.id.vpPager);
    pager.setAdapter(buildAdapter());

    // Give the TabLayout the ViewPager
    TabLayout tabLayout = (TabLayout) result.findViewById(R.id.sliding_tabs);
    tabLayout.setupWithViewPager(pager);

    return (result);
  }

  private PagerAdapter buildAdapter()
  {
    return (new EventsViewPagerAdapter(getChildFragmentManager()));
  }
}
