package in.antaragni.ant.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import in.antaragni.ant.R;
import in.antaragni.ant.datamodels.SampleData;

/**
 * Created by shikher on 4/7/15.
 */

//new adapter for the recycler view
public class SampleListAdapter extends RecyclerView.Adapter<SampleViewHolder>
{
  private final List<SampleData> paradox;

  public SampleListAdapter()
  {
    List<SampleData> paradox = Arrays.asList(
      new SampleData("Bedtime Paradox!! Guaranteed to keep you thinking all night"),
      new SampleData("No keyboard detected. Press F1 to continue"),
      new SampleData("What happens if Pinocchio says \"my nose will now grow\"?"),
      new SampleData("If everything is possible then is it possible for something to be impossible?"),
      new SampleData("Does the set of all those sets which do not contain themselves contain itself?"),
      new SampleData("If dentist make money out of bad teeth, why would you trust a product suggested by them?"),
      new SampleData("Nobody goes to that restaurant because it is too crowded."),
      new SampleData("If you didn't get this message, call me"),
      new SampleData("Deep down, you're really shallow"),
      new SampleData("What a pity that youth must be wasted on the young. - George Bernard Shaw"),
      new SampleData("I am running out of these paradoxes"),
      new SampleData("oh an old one! Next sentence is true. Previous sentence is false."),
      new SampleData("Untitled paradox 1")
    );
    this.paradox = paradox;
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
    sampleViewHolder.paradoxQuestion.setText(paradox.get(i).getQuestion());
  }

  @Override
  public int getItemCount()
  {
    return paradox.size();
  }
}
