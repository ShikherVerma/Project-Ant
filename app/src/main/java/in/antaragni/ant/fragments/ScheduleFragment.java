package in.antaragni.ant.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.antaragni.ant.MainActivity;
import in.antaragni.ant.R;
import in.antaragni.ant.adapters.ScheduleViewPagerAdapter;

public class ScheduleFragment extends Fragment
{
  private static final String KEY_TITLE = "title";

  public ScheduleFragment()
  {
    // Required empty public constructor
  }

  public static ScheduleFragment newInstance(String title)
  {
    ScheduleFragment f = new ScheduleFragment();

    Bundle args = new Bundle();

    args.putString(KEY_TITLE, title);
    f.setArguments(args);

    return (f);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    View result = inflater.inflate(R.layout.fragment_schedule, container, false);
    ViewPager pager = (ViewPager) result.findViewById(R.id.vpPager);
    pager.setAdapter(buildAdapter());
    pager.setOffscreenPageLimit(3);

    // Give the TabLayout the ViewPager
    TabLayout tabLayout = (TabLayout) result.findViewById(R.id.sliding_tabs);
    tabLayout.setupWithViewPager(pager);

    return (result);
  }

  private PagerAdapter buildAdapter()
  {
    return (new ScheduleViewPagerAdapter(getChildFragmentManager()));
  }

}
