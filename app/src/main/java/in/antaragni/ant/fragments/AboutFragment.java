package in.antaragni.ant.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.antaragni.ant.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class AboutFragment extends Fragment
{
  private static final String KEY_TITLE = "title";

  public AboutFragment()
  {
    // Required empty public constructor
  }

  public static AboutFragment newInstance(String title)
  {
    AboutFragment f = new AboutFragment();

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
    return inflater.inflate(R.layout.fragment_about, container, false);
  }
}
