package in.antaragni.ant.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    View view = inflater.inflate(R.layout.fragment_about, container, false);
    TextView tv = (TextView) view.findViewById(R.id.tv);
    tv.setText("Antaragni is not a mere cultural festival. It is a legacy, a collection of experiences and accomplishments that has been accumulated for 50 years. It is a dynasty that has been build upon the sweat and toil of many. We take pride in our heritage and invite you to be a part of this journey.");
    return view;
  }
}
