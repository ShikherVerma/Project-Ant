package in.antaragni.ant.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import in.antaragni.ant.R;
import in.antaragni.ant.datamodels.Contact;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ContactFragment extends Fragment
{
  private static final String KEY_TITLE = "title";

  public ContactFragment()
  {
    // Required empty public constructor
  }

  public static ContactFragment newInstance(String title) {
    ContactFragment f = new ContactFragment();

    Bundle args = new Bundle();

    args.putString(KEY_TITLE, title);
    f.setArguments(args);

    return (f);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    // don't look at this layout it's just a listView to show how to handle the keyboard
    return inflater.inflate(R.layout.fragment_contacts, container, false);
  }
}