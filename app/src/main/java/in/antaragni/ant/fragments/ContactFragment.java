package in.antaragni.ant.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.cocosw.bottomsheet.BottomSheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import in.antaragni.ant.R;
import in.antaragni.ant.datahandler.DatabaseAccess;
import in.antaragni.ant.datamodels.Contact;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ContactFragment extends Fragment
{
  private static final String KEY_TITLE = "title";
  private DatabaseAccess databaseAccess;

  public ContactFragment()
  {
    // Required empty public constructor
  }

  public static ContactFragment newInstance(String title) {
    ContactFragment f = new ContactFragment();
    Bundle args = new Bundle();
    args.putString(KEY_TITLE, title);
    f.setArguments(args);
    return (f);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    // don't look at this layout it's just a listView to show how to handle the keyboard
    RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.fragment_contacts, container, false);
    setupRecyclerView(rv);
    return rv;
  }

  private void setupRecyclerView(RecyclerView recyclerView) {
    databaseAccess = DatabaseAccess.getInstance(getActivity());
    databaseAccess.open();
    List<Contact> contactList = databaseAccess.getContact();
    databaseAccess.close();
    List<String> nameList = new ArrayList<>();
    for (int i=0;i<contactList.size();i++) {
      nameList.add(contactList.get(i).getName()/* + "\n" + contactList.get(i).getPost()*/);
    }
    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), contactList));
  }


  public class SimpleStringRecyclerViewAdapter
    extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<Contact> mValues;

    public class ViewHolder extends RecyclerView.ViewHolder {
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


    public SimpleStringRecyclerViewAdapter(Context context, List<Contact> items) {
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
      holder.mBoundString = mValues.get(position).getName();
      String text = "<font>" + mValues.get(position).getName() + "</font> <br> <font> <small>" + mValues.get(position).getPost() + "</small> </font>";
      holder.mTextView.setText(Html.fromHtml(text));

      holder.mView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          new BottomSheet.Builder(getActivity()).title("Options").sheet(R.menu.contact_detail_menu).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              switch (which) {
                case R.id.call:
                  Intent intent = new Intent(Intent.ACTION_CALL);
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    intent.setPackage("com.android.server.telecom");
                  } else {
                    intent.setPackage("com.android.phone");
                  }
                  intent.setData(Uri.parse("tel:" + mValues.get(position).getNumber()));
                  getActivity().startActivity(intent);
                  break;
                case R.id.save:
                  Intent intent1 = new Intent(Intent.ACTION_INSERT);
                  intent1.setType(ContactsContract.Contacts.CONTENT_TYPE);
                  intent1.putExtra(ContactsContract.Intents.Insert.NAME, mValues.get(position).getName());
                  intent1.putExtra(ContactsContract.Intents.Insert.PHONE, mValues.get(position).getNumber());
                  if (intent1.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent1);
                  }
                  break;
              }
            }
          }).show();
        }
      });
      Resources resource =  getActivity().getResources();
      String imagename = mValues.get(position).getName();
      imagename = imagename.replace(' ','_');
      imagename = imagename.replace('-','_');
      imagename = imagename.toLowerCase();
      int resID = resource.getIdentifier(imagename, "drawable", getActivity().getPackageName());
      if (resID == 0){
        resID = 2130837637;
      }
      Glide.with(holder.mImageView.getContext())
        .load(resID)
        .fitCenter()
        .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
      return mValues.size();
    }
  }
}