package in.antaragni.ant.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.antaragni.ant.fragments.inner.CategoryViewFragment;
import in.antaragni.ant.fragments.inner.CompetitionTypesFragment;

public class EventsViewPagerAdapter extends FragmentPagerAdapter
{
  private static int NUM_ITEMS = 5;

  public EventsViewPagerAdapter(FragmentManager fragmentManager)
  {
    super(fragmentManager);
  }

  // Returns total number of pages
  @Override
  public int getCount()
  {
    return NUM_ITEMS;
  }

  // Returns the fragment to display for that page
  @Override
  public Fragment getItem(int position)
  {
    switch (position)
    {
      case 0:
        return CompetitionTypesFragment.newInstance();
      case 1:
        return CategoryViewFragment.newInstance("Semi Pro");
      case 2:
        return CategoryViewFragment.newInstance("Pro Nites");
      case 3:
        return CategoryViewFragment.newInstance("Pro Events");
      case 4:
        return CategoryViewFragment.newInstance("Social Initiatives");
      default:
        return null;
    }
  }

  // Returns the page title for the top indicator
  @Override
  public CharSequence getPageTitle(int position)
  {
    switch (position)
    {
      case 0 : return "Competition";
      case 1 : return "Semi Pro";
      case 2 : return "Pro Nites";
      case 3 : return "Pro Events";
      case 4 : return "Social Initiatives";
      default: return "ERROR";
    }
  }
}
