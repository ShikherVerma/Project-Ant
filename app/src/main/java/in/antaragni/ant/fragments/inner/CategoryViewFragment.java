package in.antaragni.ant.fragments.inner;

import android.content.Context;
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

import in.antaragni.ant.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;

public class CategoryViewFragment extends Fragment
{
  // Store instance variables
  private String mCategory;

  // newInstance constructor for creating fragment with arguments
  public static CategoryViewFragment newInstance(String category)
  {
    CategoryViewFragment fragmentFirst = new CategoryViewFragment();
    Bundle args = new Bundle();
    args.putString("Category", category);
    fragmentFirst.setArguments(args);
    return fragmentFirst;
  }

  // Store instance variables based on arguments passed
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    mCategory = getArguments().getString("Category", "Dance");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.inner_fragment_category_view, container, false);
    ArrayList<Card> cards = new ArrayList<Card>();
    for (int i = 0; i < 20; i++)
    {
      CardExample card = new CardExample(getActivity(), "My title " + i, "Inner text " + i);

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

    protected String mTitleHeader;
    protected String mTitleMain;

    public CardExample(Context context, String titleHeader, String titleMain)
    {
      super(context, R.layout.lib_card_example_inner_content);
      this.mTitleHeader = titleHeader;
      this.mTitleMain = titleMain;

      init();
    }

    private void init()
    {
      //Create a CardHeader
      CardHeader header = new CardHeader(getActivity());

      //Set the header title
      header.setTitle(mTitleHeader);

      //Add a popup menu. This method set OverFlow button to visible
      header.setPopupMenu(R.menu.popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener()
      {
        @Override
        public void onMenuItemClick(BaseCard card, MenuItem item)
        {
          Toast.makeText(getActivity(), "Click on card menu" + mTitleHeader + " item=" + item.getTitle(), Toast.LENGTH_SHORT).show();
        }
      });
      addCardHeader(header);

      //Add ClickListener
      setOnClickListener(new OnCardClickListener()
      {
        @Override
        public void onClick(Card card, View view)
        {
          Toast.makeText(getContext(), "Click Listener card=" + mTitleHeader, Toast.LENGTH_SHORT).show();
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
        categoryTitle.setText("category");
        categoryTitle.setOnClickListener(new View.OnClickListener()
        {
          @Override
          public void onClick(View view)
          {
            Toast.makeText(getContext(), "Click Listener card category", Toast.LENGTH_SHORT).show();
          }
        });
      }

      if (venueTitle != null)
      {
        venueTitle.setText("OAT");
        venueTitle.setOnClickListener(new View.OnClickListener()
        {
          @Override
          public void onClick(View view)
          {
            Toast.makeText(getContext(), "Click Listener card venue", Toast.LENGTH_SHORT).show();
          }
        });
      }

      if (timeTitle != null)
      {
        timeTitle.setText("32:00");
        timeTitle.setOnClickListener(new View.OnClickListener()
        {
          @Override
          public void onClick(View view)
          {
            Toast.makeText(getContext(), "Click Listener card time", Toast.LENGTH_SHORT).show();
          }
        });

      }
    }
  }
}