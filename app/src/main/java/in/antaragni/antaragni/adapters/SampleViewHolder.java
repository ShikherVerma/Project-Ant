package in.antaragni.antaragni.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.antaragni.antaragni.R;

/**
 * Created by shikher on 4/7/15.
 */

class SampleViewHolder extends RecyclerView.ViewHolder
{
  TextView paradoxQuestion;

  SampleViewHolder(View itemView)
  {
    super(itemView);
    paradoxQuestion = (TextView) itemView.findViewById(R.id.paradox_question);
  }
}