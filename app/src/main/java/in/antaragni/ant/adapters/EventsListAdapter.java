package in.antaragni.ant.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import in.antaragni.ant.R;
import in.antaragni.ant.datamodels.Event;
import in.antaragni.ant.datamodels.Venue;

/**
 * Created by shikher on 4/7/15.
 */

//new adapter for the recycler view
public class EventsListAdapter extends RecyclerView.Adapter<SampleViewHolder>
{
  private List<Event> schedule;

  public EventsListAdapter(Context context)
  {
    List<Event> schedule = Arrays.asList(/*
      new Event("Inauguration", "Director's Adress", new GregorianCalendar(2015, 10, 29, 00, 00, 00),
        new GregorianCalendar(2015, 10, 29, 00, 00, 00), 1, new Venue("Auditorium"), getRandomDescription(),
        context.getResources().getDrawable(R.drawable.bheed)),

      new Event("Informals", "Informals", new GregorianCalendar(2015, 10, 29, 00, 00, 00),
        new GregorianCalendar(2015, 10, 29, 00, 00, 00), 1, new Venue("Mall Stage"), getRandomDescription(),
        context.getResources().getDrawable(R.drawable.bheed)),

      new Event("Pronites", "Prima Nocte", new GregorianCalendar(2015, 10, 29, 00, 00, 00),
        new GregorianCalendar(2015, 10, 29, 00, 00, 00), 1, new Venue("Audi Ground"), getRandomDescription(),
        context.getResources().getDrawable(R.drawable.bheed)),

      new Event("Inauguration", "Director's Adress", new GregorianCalendar(2015, 10, 29, 00, 00, 00),
        new GregorianCalendar(2015, 10, 29, 00, 00, 00), 1, new Venue("Auditorium"), getRandomDescription(),
        context.getResources().getDrawable(R.drawable.bheed)),

      new Event("Informals", "Informals", new GregorianCalendar(2015, 10, 29, 00, 00, 00),
        new GregorianCalendar(2015, 10, 29, 00, 00, 00), 1, new Venue("Mall Stage"), getRandomDescription(),
        context.getResources().getDrawable(R.drawable.bheed)),

     new Event("Pronites", "Prima Nocte", new GregorianCalendar(2015, 10, 29, 00, 00, 00),
        new GregorianCalendar(2015, 10, 29, 00, 00, 00), 1, new Venue("Audi Ground"), getRandomDescription(),
        context.getResources().getDrawable(R.drawable.bheed)),

      new Event("Inauguration", "Director's Adress", new GregorianCalendar(2015, 10, 29, 00, 00, 00),
        new GregorianCalendar(2015, 10, 29, 00, 00, 00), 1, new Venue("Auditorium"), getRandomDescription(),
        context.getResources().getDrawable(R.drawable.bheed)),

      new Event("Informals", "Informals", new GregorianCalendar(2015, 10, 29, 00, 00, 00),
        new GregorianCalendar(2015, 10, 29, 00, 00, 00), 1, new Venue("Mall Stage"), getRandomDescription(),
        context.getResources().getDrawable(R.drawable.bheed)),

      new Event("Pronites", "Prima Nocte", new GregorianCalendar(2015, 10, 29, 00, 00, 00),
        new GregorianCalendar(2015, 10, 29, 00, 00, 00), 1, new Venue("Audi Ground"), getRandomDescription(),
        context.getResources().getDrawable(R.drawable.bheed))*/
    );
    this.schedule = schedule;
  }

  public String getRandomDescription()
  {
    return "Lorem ipsum dolor sit amet ," +
      " consectetur adipiscing elit. Nullam vitae fermentum arcu, " +
      "sed varius libero. Phasellus nisl urna, consequat in ante eu," +
      " facilisis sollicitudin neque. Etiam sit amet ultrices risus. " +
      "Proin a lorem elit. Nunc eu sagittis ex. Nulla facilisi. In scelerisque vulputate ultricies." +
      " Cras bibendum mi sit amet nunc suscipit tincidunt. Curabitur vitae eros quam." +
      " Nam eleifend, est non gravida vulputate, diam elit tincidunt dolor," +
      " in porttitor lectus sapien eget nibh. Vestibulum eleifend, diam nec facilisis interdum," +
      " diam neque tempor dui, a dignissim libero dolor in diam.";
  }

  @Override
  public SampleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
  {
    final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
    final View v = layoutInflater.inflate(R.layout.sample_card, viewGroup, false);
    return new SampleViewHolder(v);
  }

  @Override
  public void onBindViewHolder(SampleViewHolder sampleViewHolder, int i)
  {
    sampleViewHolder.paradoxQuestion.setText(schedule.get(i).getName());
  }

  @Override
  public int getItemCount()
  {
    return schedule.size();
  }
}