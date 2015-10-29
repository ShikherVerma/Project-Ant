package in.antaragni.ant.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.antaragni.ant.fragments.inner.CategoryViewFragment;

public class EventsViewPagerAdapter extends FragmentPagerAdapter
{
  private static int NUM_ITEMS = 10;

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
        return CategoryViewFragment.newInstance("Informals");
      case 1:
        return CategoryViewFragment.newInstance("Pronite");
      case 2:
        return CategoryViewFragment.newInstance("Professional show");
      case 3:
        return CategoryViewFragment.newInstance("dance");
      case 4:
        return CategoryViewFragment.newInstance("hle");
      case 5:
        return CategoryViewFragment.newInstance("drama");
      case 6:
        return CategoryViewFragment.newInstance("els");
      case 7:
        return CategoryViewFragment.newInstance("music");
      case 8:
        return CategoryViewFragment.newInstance("quiz");
      case 9:
        return CategoryViewFragment.newInstance("fmc");
      case 10:
        return CategoryViewFragment.newInstance("fine_arts");
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
      case 0:
        return "Informals";
      case 1:
        return "Pronite";
      case 2:
        return "Pro show";
      case 3:
        return "Dance";
      case 4:
        return "Hindi Lits";
      case 5:
        return "Dramatics";
      case 6:
        return "English Lits";
      case 7:
        return "Music";
      case 8:
        return "Quiz";
      case 9:
        return "Films & Media";
      case 10:
        return "Fine Arts";
      default:
        return "ERROR";
    }
  }
}
