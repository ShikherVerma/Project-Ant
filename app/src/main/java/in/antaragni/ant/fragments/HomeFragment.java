package in.antaragni.ant.fragments;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.antaragni.ant.EventDetailActivity;
import in.antaragni.ant.MainActivity;
import in.antaragni.ant.R;
import in.antaragni.ant.datahandler.DatabaseAccess;
import in.antaragni.ant.datamodels.Event;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
{
  private static final String KEY_TITLE = "title";
  private DatabaseAccess databaseAccess;

  public HomeFragment()
  {
    // Required empty public constructor
  }

  public static HomeFragment newInstance(String title)
  {
    HomeFragment f = new HomeFragment();

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
    databaseAccess = DatabaseAccess.getInstance(getActivity());
    databaseAccess.open();
    List<String> notificationList = databaseAccess.getnotification();
    databaseAccess.close();
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    ArrayList<Card> cards = new ArrayList<Card>();
    for (int i = 0; i < notificationList.size(); i++)
    {
      CardExample card = new CardExample(getActivity(), notificationList.get(i));
      cards.add(card);
    }
    CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);
    CardListView listView = (CardListView) view.findViewById(R.id.carddemo_list_base1);
    AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(mCardArrayAdapter);
    animCardArrayAdapter.setAbsListView(listView);
    listView.setExternalAdapter(animCardArrayAdapter, mCardArrayAdapter);
    return view;
  }

  public class CardExample extends Card
  {

    private String mNotification;

    public CardExample(Context context, String notification)
    {
      super(context, R.layout.lib_card_example_inner_content);
      mNotification = notification;
      init();
    }

    private void init()
    {
      //Create a CardHeader
      CardHeader header = new CardHeader(getActivity());

      //Set the header title
      header.setTitle(mNotification);

      //TODO : set limit to 50 words after that on click it shows a popup dialog box with the whole news.
    }
  }
}
