package in.antaragni.ant.fragments.inner;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

public class DayViewFragment extends Fragment
{
  // Store instance variables
  private int mDay;
  private DatabaseAccess databaseAccess;
  private long mGoogleCalendarNumber = -1;

  // The indices for the projection array above.
  private static final int PROJECTION_ID_INDEX = 0;
  private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
  private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
  private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

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
    // Run query
    Cursor cur = null;
    ContentResolver cr = getActivity().getContentResolver();
    Uri uri = CalendarContract.Calendars.CONTENT_URI;
    String selection = "(" + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?)";
    String[] selectionArgs = new String[]{"com.google"};
    // Submit the query and get a Cursor object back.
    cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
    Log.i("ant calendar", " attempting to read calendar" + mDay);
    while (cur.moveToNext())
    {
      long calID = 0;
      String displayName = null;
      String accountName = null;
      String ownerName = null;

      // Get the field values
      calID = cur.getLong(PROJECTION_ID_INDEX);
      displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
      accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
      ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
      Log.i("ant calendar", " day :" + mDay + " data : " + calID + "  " + accountName + "  " + displayName + "  " + ownerName);
      if (mGoogleCalendarNumber == -1)
      {
        Log.i("ant calendar", " calendar id is " + calID );
        mGoogleCalendarNumber = calID;
      }
    }
    cur.close();
  }

  // Projection array. Creating indices for this array instead of doing
  // dynamic lookups improves performance.
  public static final String[] EVENT_PROJECTION = new String[]{
    CalendarContract.Calendars._ID,                           // 0
    CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
    CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
    CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
  };

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    databaseAccess = DatabaseAccess.getInstance(getActivity());
    databaseAccess.open();
    List<Event> eventList = databaseAccess.getEventbyDay(mDay);
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
    if(mDay==4)
    ((MainActivity)getActivity()).dismissloading();
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
          final String REMINDER = "Set Reminder";
          final String MAP = "View Map";
          switch (item.getTitle().toString())
          {
            case REMINDER:
              if (mGoogleCalendarNumber != -1)
              {
                new BottomSheet.Builder(getActivity()).title("Reminder me").sheet(R.menu.reminder_menu).listener(new DialogInterface.OnClickListener()
                {
                  @Override
                  public void onClick(DialogInterface dialog, int which)
                  {
                    switch (which)
                    {
                      case R.id.min5:
                        setReminder(5);
                        break;
                      case R.id.min10:
                        setReminder(10);
                        break;
                      case R.id.min30:
                        setReminder(30);
                        break;
                    }
                  }
                }).show();
              } else
              {
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(2015, event.getStart_time().get(Calendar.MONTH), event.getStart_time().get(Calendar.DATE), event.getStart_time().get(Calendar.HOUR), event.getStart_time().get(Calendar.MINUTE));
                Log.i("ant calendar", event.getStart_time().get(Calendar.MONTH) + "  " + event.getStart_time().get(Calendar.DATE) +"  " + event.getStart_time().get(Calendar.HOUR) +"  " + event.getStart_time().get(Calendar.MINUTE));
                Calendar endTime = Calendar.getInstance();
                endTime.set(2015, event.getEnd_time().get(Calendar.MONTH), event.getEnd_time().get(Calendar.DATE), event.getEnd_time().get(Calendar.HOUR), event.getEnd_time().get(Calendar.MINUTE));
                Log.i("ant calendar", event.getEnd_time().get(Calendar.MONTH) + "  " + event.getEnd_time().get(Calendar.DATE) + "  " + event.getEnd_time().get(Calendar.HOUR) + "  " + event.getEnd_time().get(Calendar.MINUTE));
                Intent intent = new Intent(Intent.ACTION_INSERT)
                  .setData(CalendarContract.Events.CONTENT_URI)
                  .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                  .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                  .putExtra(CalendarContract.Events.TITLE, event.getName())
                  .putExtra(CalendarContract.Events.ALLOWED_REMINDERS, 1)
                  .putExtra(CalendarContract.Events.DESCRIPTION, event.getDescription());
                startActivity(intent);
              }
              break;
            case MAP:
              String v = event.getVenue().getLocation();
              if (v != null)
              ((MainActivity)getActivity()).startMap(v);
              else
                Toast.makeText(getContext(), "Error in venue", Toast.LENGTH_SHORT).show();
              /*
              Intent intent = new Intent(getContext(), MainActivity.class);
              intent.putExtra(MainActivity.EXTRA_ACTION, event.getVenue().getLocation());
              getContext().startActivity(intent);
              */
              break;
          }
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

    public void setReminder(int min)
    {
      long startMillis = 0;
      long endMillis = 0;
      Calendar beginTime = Calendar.getInstance();
      beginTime.set(2015,event.getStart_time().get(Calendar.MONTH) - 1 , event.getStart_time().get(Calendar.DATE), event.getStart_time().get(Calendar.HOUR), event.getStart_time().get(Calendar.MINUTE));
      Log.i("ant calendar", event.getStart_time().get(Calendar.MONTH) + "  " + event.getStart_time().get(Calendar.DATE) + "  " + event.getStart_time().get(Calendar.HOUR) + "  " + event.getStart_time().get(Calendar.MINUTE));
      startMillis = beginTime.getTimeInMillis();
      Log.i("ant calendar", event.getEnd_time().get(Calendar.MONTH)  + "  " + event.getEnd_time().get(Calendar.DATE) + "  " + event.getEnd_time().get(Calendar.HOUR) + "  " + event.getEnd_time().get(Calendar.MINUTE));
      Calendar endTime = Calendar.getInstance();
      endTime.set(2015 , event.getEnd_time().get(Calendar.MONTH) - 1, event.getEnd_time().get(Calendar.DATE), event.getEnd_time().get(Calendar.HOUR), event.getEnd_time().get(Calendar.MINUTE));
      endMillis = endTime.getTimeInMillis();

      ContentResolver cr = getActivity().getContentResolver();
      ContentValues values = new ContentValues();
      values.put(CalendarContract.Events.DTSTART, startMillis);
      values.put(CalendarContract.Events.DTEND, endMillis);
      values.put(CalendarContract.Events.TITLE, event.getName());
      values.put(CalendarContract.Events.DESCRIPTION, event.getDescription());
      values.put(CalendarContract.Events.CALENDAR_ID, mGoogleCalendarNumber);
      values.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Calcutta");
      Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
      // get the event ID that is the last element in the Uri
      long eventID = Long.parseLong(uri.getLastPathSegment());
      cr = getActivity().getContentResolver();
      values = new ContentValues();
      values.put(CalendarContract.Reminders.MINUTES, min);
      values.put(CalendarContract.Reminders.EVENT_ID, eventID);
      values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
      uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
      Toast.makeText(getActivity(), "Reminder set for " + min + " minutes before event", Toast.LENGTH_SHORT).show();
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
        int shour = event.getStart_time().get(Calendar.HOUR_OF_DAY);
        int min =  event.getStart_time().get(Calendar.MINUTE);
        String smin;
        if (min == 0)
          smin = min + "0";
        else
          smin = min + "";
        String starttime;
        if (shour > 12)
        {
          shour = shour - 12;
          starttime = shour + ":" + smin + " PM";
        } else
        {
          starttime = shour + ":" + smin + " AM";
        }
        timeTitle.setText(starttime);
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
