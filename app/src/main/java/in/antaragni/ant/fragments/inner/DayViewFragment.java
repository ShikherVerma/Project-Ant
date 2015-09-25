package in.antaragni.ant.fragments.inner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.antaragni.ant.EventDetailActivity;
import in.antaragni.ant.R;
import in.antaragni.ant.datahandler.DatabaseAccess;
import in.antaragni.ant.datamodels.Event;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;

public class DayViewFragment extends Fragment
{
  // Store instance variables
  private int mDay;
  private DatabaseAccess databaseAccess;

  // newInstance constructor for creating fragment with arguments
  public static DayViewFragment newInstance(int day)
  {
    DayViewFragment fragmentFirst = new DayViewFragment();
    Bundle args = new Bundle();
    args.putInt("Day Number", day);
    fragmentFirst.setArguments(args);
    return fragmentFirst;
  }

  // Store instance variables based on arguments passed
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    mDay = getArguments().getInt("Day Number", 1);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    databaseAccess = DatabaseAccess.getInstance(getActivity());
    databaseAccess.open();
    List<Event> eventList = databaseAccess.getEvent(mDay);
    databaseAccess.close();
    View view = inflater.inflate(R.layout.inner_fragment_day_view, container, false);
    ArrayList<Card> cards = new ArrayList<Card>();
    for (int i = 0; i < eventList.size(); i++)
    {
      CardExample card = new CardExample(getActivity(), eventList.get(i));
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

    private Event event;

    public CardExample(Context context, Event event)
    {
      super(context, R.layout.lib_card_example_inner_content);
      this.event = event;

      init();
    }

    private void init()
    {
      //Create a CardHeader
      CardHeader header = new CardHeader(getActivity());

      //Set the header title
      header.setTitle(event.getName());

      //Add a popup menu. This method set OverFlow button to visible
      header.setPopupMenu(R.menu.popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener()
      {
        @Override
        public void onMenuItemClick(BaseCard card, MenuItem item)
        {
          Toast.makeText(getActivity(), "Click on card menu" + event.getName() + " item=" + item.getTitle(), Toast.LENGTH_SHORT).show();
        }
      });
      addCardHeader(header);

      //Add ClickListener
      setOnClickListener(new OnCardClickListener()
      {
        @Override
        public void onClick(Card card, View view)
        {
          Toast.makeText(getContext(), "Click Listener card=" + event.getName(), Toast.LENGTH_SHORT).show();
          Context context = view.getContext();
          Intent intent = new Intent(context, EventDetailActivity.class);
          intent.putExtra(EventDetailActivity.EXTRA_NAME, event.getName());
          context.startActivity(intent);
        }
      });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view)
    {

      //Retrieve elements
      TextView categoryTitle = (TextView) parent.findViewById(R.id.card_category_title);
      TextView venueTitle = (TextView) parent.findViewById(R.id.card_venue_title);
      TextView timeTitle = (TextView) parent.findViewById(R.id.card_time_title);

      if (categoryTitle != null)
      {
        categoryTitle.setText(event.getCategory());
        categoryTitle.setOnClickListener(new View.OnClickListener()
        {
          @Override
          public void onClick(View view)
          {
            Toast.makeText(getContext(), "Click Listener card category", Toast.LENGTH_SHORT).show();
            Context context = view.getContext();
            Intent intent = new Intent(context, EventDetailActivity.class);
            intent.putExtra(EventDetailActivity.EXTRA_NAME, event.getName());
            context.startActivity(intent);
          }
        });
      }

      if (venueTitle != null)
      {
        venueTitle.setText(event.getVenue().getLocation());
        venueTitle.setOnClickListener(new View.OnClickListener()
        {
          @Override
          public void onClick(View view)
          {
            Toast.makeText(getContext(), "Click Listener card venue", Toast.LENGTH_SHORT).show();
            Context context = view.getContext();
            Intent intent = new Intent(context, EventDetailActivity.class);
            intent.putExtra(EventDetailActivity.EXTRA_NAME, event.getName());
            context.startActivity(intent);
          }
        });
      }

      if (timeTitle != null)
      {
        timeTitle.setText(event.getStart_time().get(Calendar.HOUR_OF_DAY) + ":" +
          event.getStart_time().get(Calendar.MINUTE) + "-" +
          event.getEnd_time().get(Calendar.HOUR_OF_DAY) + ":" +
          event.getEnd_time().get(Calendar.MINUTE));
        timeTitle.setOnClickListener(new View.OnClickListener()
        {
          @Override
          public void onClick(View view)
          {
            Toast.makeText(getContext(), "Click Listener card time", Toast.LENGTH_SHORT).show();
            Context context = view.getContext();
            Intent intent = new Intent(context, EventDetailActivity.class);
            intent.putExtra(EventDetailActivity.EXTRA_NAME, event.getName());
            context.startActivity(intent);
          }
        });

      }
    }
  }
}