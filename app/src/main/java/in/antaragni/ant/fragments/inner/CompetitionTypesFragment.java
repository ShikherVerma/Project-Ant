package in.antaragni.ant.fragments.inner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import in.antaragni.ant.R;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardViewNative;

public class CompetitionTypesFragment extends Fragment
{

  // newInstance constructor for creating fragment with arguments
  public static CompetitionTypesFragment newInstance()
  {
    return new CompetitionTypesFragment();
  }

  // Store instance variables based on arguments passed
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    View view = inflater.inflate(R.layout.inner_fragment_competition_types, container, false);
    init_card((CardViewNative) view.findViewById(R.id.card_dance), "Dance");
    init_card((CardViewNative) view.findViewById(R.id.card_dramatics), "Dramatics");
    init_card((CardViewNative) view.findViewById(R.id.card_music), "Musicals");
    init_card((CardViewNative) view.findViewById(R.id.card_fine_arts), "Fine Arts");
    init_card((CardViewNative) view.findViewById(R.id.card_english_lits), "English Lit");
    init_card((CardViewNative) view.findViewById(R.id.card_hindi_lits), "Hindi Lit");
    init_card((CardViewNative) view.findViewById(R.id.card_quiz), "Quizs");
    init_card((CardViewNative) view.findViewById(R.id.card_films), "Films & Photography");

    return view;
  }

  /**
   * Builds a Material Card with Large and small icons as supplemental actions
   */
  private void init_card(CardViewNative cardView, final String name)
  {

    MaterialLargeImageCard card =
      MaterialLargeImageCard.with(getActivity())
        .setTextOverImage(name)
        .useDrawableId(getDrawable())
        .build();

    card.setOnClickListener(new Card.OnCardClickListener()
    {
      @Override
      public void onClick(Card card, View view)
      {
        Toast.makeText(getActivity(), " Click on ActionArea " + name, Toast.LENGTH_SHORT).show();
      }
    });

    //Set card in the CardViewNative
    cardView.setCard(card);
  }

  private int getDrawable()
  {
    double d = Math.random();
    if (d > 0.5)
      return R.drawable.bheed;
    else
      return R.drawable.im_beach;
  }
}