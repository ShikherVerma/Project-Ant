package in.antaragni.ant.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import in.antaragni.ant.R;
import in.antaragni.ant.datamodels.Contact;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ContactFragment extends Fragment
{
  private static final String KEY_TITLE = "title";

  public ContactFragment()
  {
    // Required empty public constructor
  }

  public static ContactFragment newInstance(String title)
  {
    ContactFragment f = new ContactFragment();
    Bundle args = new Bundle();
    args.putString(KEY_TITLE, title);
    f.setArguments(args);
    return (f);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.fragment_contacts, container, false);
    setupRecyclerView(rv);
    return rv;
  }

  private void setupRecyclerView(RecyclerView recyclerView) {
    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
      getRandomSublist(Contact.sContactStrings, 30)));
  }

  private List<String> getRandomSublist(String[] array, int amount) {
    ArrayList<String> list = new ArrayList<>(amount);
    Random random = new Random();
    while (list.size() < amount) {
      list.add(array[random.nextInt(array.length)]);
    }
    return list;
  }

  public static class SimpleStringRecyclerViewAdapter
    extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<String> mValues;

    public static class ViewHolder extends RecyclerView.ViewHolder {
      public String mBoundString;

      public final View mView;
      public final ImageView mImageView;
      public final TextView mTextView;

      public ViewHolder(View view) {
        super(view);
        mView = view;
        mImageView = (ImageView) view.findViewById(R.id.avatar);
        mTextView = (TextView) view.findViewById(android.R.id.text1);
      }

      @Override
      public String toString() {
        return super.toString() + " '" + mTextView.getText();
      }
    }

    public String getValueAt(int position) {
      return mValues.get(position);
    }

    public SimpleStringRecyclerViewAdapter(Context context, List<String> items) {
      context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
      mBackground = mTypedValue.resourceId;
      mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.contact_list_item, parent, false);
      view.setBackgroundResource(mBackground);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
      holder.mBoundString = mValues.get(position);
      holder.mTextView.setText(mValues.get(position));

      holder.mView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
      });

      Glide.with(holder.mImageView.getContext())
        .load(Contact.getRandomContactDrawable())
        .fitCenter()
        .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
      return mValues.size();
    }
  }
}