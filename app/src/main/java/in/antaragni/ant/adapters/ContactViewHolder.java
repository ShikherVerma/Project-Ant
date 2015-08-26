package in.antaragni.ant.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.antaragni.ant.R;
import in.antaragni.ant.datamodels.Contact;

public class ContactViewHolder extends RecyclerView.ViewHolder
{
  TextView paradoxQuestion;
  public String mBoundString;
  public View mView;
  public ImageView mImageView;
  public TextView mTextView;

  ContactViewHolder(View itemView)
  {
    super(itemView);
    paradoxQuestion = (TextView) itemView.findViewById(R.id.paradox_question);
  }

  public ViewHolder(View view)
  {
    super(view);
    mView = view;
    mImageView = (ImageView) view.findViewById(R.id.avatar);
    mTextView = (TextView) view.findViewById(android.R.id.text1);
  }

  @Override
  public String toString()
  {
    return super.toString() + " '" + mTextView.getText();
  }
}

public class ViewHolder extends RecyclerView.ViewHolder
{
  public String mBoundString;

  public View mView;
  public ImageView mImageView;
  public TextView mTextView;

  public ViewHolder(View view)
  {
    super(view);
    mView = view;
    mImageView = (ImageView) view.findViewById(R.id.avatar);
    mTextView = (TextView) view.findViewById(android.R.id.text1);
  }

  @Override
  public String toString()
  {
    return super.toString() + " '" + mTextView.getText();
  }

  public String getValueAt(int position)
  {
    return mValues.get(position);
  }

  public ContactListAdapter(Context context, List<String> items)
  {
    context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
    mBackground = mTypedValue.resourceId;
    mValues = items;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View view = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.contact_list_item, parent, false);
    view.setBackgroundResource(mBackground);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position)
  {
    holder.mBoundString = mValues.get(position);
    holder.mTextView.setText(mValues.get(position));

    holder.mView.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        Context context = v.getContext();
      }
    });

    Glide.with(holder.mImageView.getContext())
      .load(Contact.getRandomContactDrawable())
      .fitCenter()
      .into(holder.mImageView);
  }
}