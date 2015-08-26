package in.antaragni.ant.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.antaragni.ant.fragments.RecyclerViewFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
  private static int NUM_ITEMS = 4;

  public ViewPagerAdapter(FragmentManager fragmentManager)
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
        return RecyclerViewFragment.newInstance(0);
      case 1:
        return RecyclerViewFragment.newInstance(1);
      case 2:
        return RecyclerViewFragment.newInstance(2);
      case 3:
        return RecyclerViewFragment.newInstance(3);
      default:
        return null;
    }
  }

  // Returns the page title for the top indicator
  @Override
  public CharSequence getPageTitle(int position)
  {
    return "Day " + (position + 1);
  }
}