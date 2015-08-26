package in.antaragni.ant.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import in.antaragni.ant.R;
import in.antaragni.ant.datamodels.Contact;

//new adapter for the recycler view
public class ContactListAdapter extends RecyclerView.Adapter<ContactViewHolder>
{
  private List<Contact> contactList;
  private static final Random RANDOM = new Random();

  private final TypedValue mTypedValue = new TypedValue();
  private int mBackground;

  public ContactListAdapter(Context context)
  {
    context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
    mBackground = mTypedValue.resourceId;

    List<Contact> contactList = Arrays.asList(
      new Contact("Name", "Post", "9876543210", getDrawableFromName(context, "Name")),
      new Contact("Name", "Post", "9876543210", getDrawableFromName(context, "Name")),
      new Contact("Name", "Post", "9876543210", getDrawableFromName(context, "Name")),
      new Contact("Name", "Post", "9876543210", getDrawableFromName(context, "Name")),
      new Contact("Name", "Post", "9876543210", getDrawableFromName(context, "Name")),
      new Contact("Name", "Post", "9876543210", getDrawableFromName(context, "Name")),
      new Contact("Name", "Post", "9876543210", getDrawableFromName(context, "Name")),
      new Contact("Name", "Post", "9876543210", getDrawableFromName(context, "Name")),
      new Contact("Name", "Post", "9876543210", getDrawableFromName(context, "Name")),
      new Contact("Name", "Post", "9876543210", getDrawableFromName(context, "Name")),
      new Contact("Name", "Post", "9876543210", getDrawableFromName(context, "Name"))
      );
    this.contactList = contactList;
  }

  private int getDrawableFromName(Context c, String name)
  {
    //TODO : add image for each contact person and remove this random function
    //return c.getResources().getIdentifier(name, "drawable", "in.antaragni.ant");
    switch (RANDOM.nextInt(5))
    {
      default:
      case 0:
        return R.drawable.cheese_1;
      case 1:
        return R.drawable.cheese_2;
      case 2:
        return R.drawable.cheese_3;
      case 3:
        return R.drawable.cheese_4;
      case 4:
        return R.drawable.cheese_5;
    }
  }

  @Override
  public int getItemCount()
  {
    return contactList.size();
  }

  public Contact getValueAt(int position)
  {
    return contactList.get(position);
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
        Intent intent = new Intent(context, CheeseDetailActivity.class);
        intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString);

        context.startActivity(intent);
      }
    });

    Glide.with(holder.mImageView.getContext())
      .load(Cheeses.getRandomCheeseDrawable())
      .fitCenter()
      .into(holder.mImageView);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View view = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.list_item, parent, false);
    view.setBackgroundResource(mBackground);
    return new ViewHolder(view);
  }
}